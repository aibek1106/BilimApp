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
        // 1) разбираем «сырые» пары ключ=значение, без декодирования значений
        Map<String, String> raw = parseRaw(initData);

        // 2) забираем и удаляем из map hash и signature
        String expectedHash  = raw.remove("hash");
        raw.remove("signature");

        // 3) секретный ключ: HMAC-SHA256(botToken, key="WebAppData")
        byte[] secret = hmac("WebAppData".getBytes(StandardCharsets.UTF_8),
                botToken.getBytes(StandardCharsets.UTF_8));

        // 4) строим data_check_string из оставшихся raw-параметров
        String dataCheckString = raw.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));

        // 5) считаем HMAC-SHA256(data_check_string, key=secret)
        String calculated = bytesToHex(hmac(secret, dataCheckString.getBytes(StandardCharsets.UTF_8)));

        log.info("Expected hash:   {}", expectedHash);
        log.info("Calculated hash: {}", calculated);
        log.info("dataCheckString {}", dataCheckString);

        if (!calculated.equals(expectedHash)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid init data");
        }

        // 6) теперь декодируем user и парсим JSON
        String userJson = URLDecoder.decode(raw.get("user"), StandardCharsets.UTF_8);
        try {
            JsonNode user = mapper.readTree(userJson);
            long id = user.get("id").asLong();
            return jwtService.generateToken(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user info");
        }
    }

    /** Разбирает строку «key1=val1&key2=val2…» без URL-decode значений */
    private Map<String, String> parseRaw(String initData) {
        Map<String, String> map = new HashMap<>();
        for (String pair : initData.split("&")) {
            String[] p = pair.split("=", 2);
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /** HMAC-SHA256 над data с ключом key */
    private byte[] hmac(byte[] key, byte[] data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data);
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