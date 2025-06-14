package kg.bilim_app.mobile_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.bilim_app.mobile_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${telegram.bot-token}")
    private String botToken;

    @Override
    public String authenticate(String initData) {
        Map<String, String> rawData = parseRaw(initData);
        String expectedHash = rawData.remove("hash");

        // Telegram HMAC: secret = HMAC_SHA256("WebAppData", SHA256(bot_token))
        byte[] secret = hmac("WebAppData", sha256(botToken.getBytes(StandardCharsets.UTF_8)));

        // Строим data_check_string из raw-данных (без URL-декодирования!)
        String dataCheckString = rawData.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));

        String calculatedHash = bytesToHex(hmac(dataCheckString, secret));

        log.info("Expected: {}", expectedHash);
        log.info("Calculated: {}", calculatedHash);

        if (!calculatedHash.equals(expectedHash)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid init data");
        }

        // Декодируем user только для JSON-десериализации
        String userJsonRaw = rawData.get("user");
        String userJson = URLDecoder.decode(userJsonRaw, StandardCharsets.UTF_8);

        try {
            JsonNode user = mapper.readTree(userJson);
            long id = user.get("id").asLong();
            return jwtService.generateToken(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user info");
        }
    }

    // Парсинг initData без декодирования значений
    private Map<String, String> parseRaw(String initData) {
        Map<String, String> map = new HashMap<>();
        for (String pair : initData.split("&")) {
            String[] p = pair.split("=", 2);
            if (p.length == 2) {
                map.put(p[0], p[1]); // ❗️не декодируем!
            }
        }
        return map;
    }

    private byte[] hmac(String data, byte[] key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private byte[] sha256(byte[] input) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(input);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}