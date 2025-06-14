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
import java.util.HashMap;
import java.util.Map;
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
        log.info("initData: {}", initData);

        /* 1. разбираем initData один раз URL-decode, но
              значения оставляем закодированными */
        Map<String, String> params = parse(initData);

        /* 2. вытаскиваем hash, НО signature оставляем! */
        String expectedHash = params.remove("hash");

        /* 3. секрет:  secret = HMAC_SHA256(data = botToken, key = "WebAppData") */
        byte[] secret = hmac(
                botToken.getBytes(StandardCharsets.UTF_8),           // data
                "WebAppData".getBytes(StandardCharsets.UTF_8));      // key

        /* 4. data_check_string = все оставшиеся пары (вкл. signature) */
        String dcs = params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));

        /* 5. calculated = HMAC_SHA256(data_check_string, secret) */
        String calculated = bytesToHex(
                hmac(dcs.getBytes(StandardCharsets.UTF_8), secret));

        log.info("Expected hash: {}", expectedHash);
        log.info("Calculated hash: {}", calculated);
        log.info("dataCheckString:\n{}", dcs);

        if (!calculated.equals(expectedHash)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid initData");
        }

        /* 6. декодируем user для JSON-парсинга и выдаём JWT */
        String userJson = URLDecoder.decode(params.get("user"), StandardCharsets.UTF_8);
        try {
            JsonNode user = mapper.readTree(userJson);
            long telegramId = user.get("id").asLong();
            return jwtService.generateToken(telegramId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user JSON");
        }
    }

    /* --- helpers -------------------------------------------------------- */

    /** URL-decode всей строки, но значения сохраняем «сырыми» */
    private Map<String, String> parse(String encoded) {
        String decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
        Map<String, String> map = new HashMap<>();
        for (String pair : decoded.split("&")) {
            int idx = pair.indexOf('=');
            if (idx > 0) {
                map.put(pair.substring(0, idx), pair.substring(idx + 1));
            }
        }
        return map;
    }

    /** HMAC-SHA256: data → key (порядок как в Telegram) */
    private byte[] hmac(byte[] data, byte[] key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data);
        } catch (Exception e) {
            throw new IllegalStateException("HMAC error", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
