package kg.bilim_app.mobile_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import kg.bilim_app.mobile_api.security.JwtService;

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

        // 1. разбираем строку: одно URL-декодирование
        Map<String, String> data = parse(initData);
        String hash = data.remove("hash");

        // 2. secret = HMAC_SHA256("WebAppData", sha256(botToken))
        byte[] botTokenHash = sha256(botToken.getBytes(StandardCharsets.UTF_8));
        byte[] secret = hmac("WebAppData", botTokenHash);

        // 3. data_check_string
        String dcs = data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));

        // 4. вычисляем hash
        String calc = bytesToHex(hmac(dcs, secret));

        log.info("Expected: {}", hash);
        log.info("Calculated: {}", calc);

        if (!calc.equals(hash)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid init data");
        }

        // 5. юзер-json нужно декодировать ещё раз, чтобы распарсить
        String userJson = URLDecoder.decode(data.get("user"), StandardCharsets.UTF_8);
        try {
            JsonNode node = mapper.readTree(userJson);
            long id = node.get("id").asLong();
            return jwtService.generateToken(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user info");
        }
    }

    /* ---------- util ---------- */

    private Map<String, String> parse(String initData) {
        Map<String, String> map = new HashMap<>();
        for (String pair : initData.split("&")) {
            String[] p = pair.split("=", 2);
            if (p.length == 2) {
                map.put(p[0], URLDecoder.decode(p[1], StandardCharsets.UTF_8));
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

    private byte[] sha256(byte[] bytes) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(bytes);
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