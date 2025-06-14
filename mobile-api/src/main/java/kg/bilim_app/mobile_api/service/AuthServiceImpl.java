package kg.bilim_app.mobile_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${telegram.bot-token}")
    private String botToken;

    @Override
    public String authenticate(String initData) {
        log.info("Authenticating user {}", initData);
        Map<String, String> data = parse(initData);
        String hash = data.remove("hash");

        // 1. sha256(botToken)
        byte[] botTokenHash;
        try {
            botTokenHash = MessageDigest.getInstance("SHA-256")
                    .digest(botToken.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalStateException("SHA-256 error", e);
        }

        // 2. HMAC("WebAppData", sha256(botToken))
        byte[] secret = hmac("WebAppData", botTokenHash);

        // 3. Sort and join data
        String dataCheckString = data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));

        // 4. Compute HMAC
        String calculated = bytesToHex(hmac(dataCheckString, secret));

        log.info("Expected hash: {}", hash);
        log.info("Calculated hash: {}", calculated);

        if (!calculated.equals(hash)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid init data");
        }

        String userJson = data.get("user");
        if (userJson == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User info missing");
        }

        try {
            JsonNode node = objectMapper.readTree(userJson);
            long id = node.get("id").asLong();
            return jwtService.generateToken(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user info");
        }
    }

    private Map<String, String> parse(String initData) {
        Map<String, String> map = new HashMap<>();
        for (String pair : initData.split("&")) {
            String[] p = pair.split("=", 2);
            if (p.length == 2) {
                String key = p[0];
                String value = URLDecoder.decode(URLDecoder.decode(p[1], StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                map.put(key, value);
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
            throw new IllegalStateException("HMAC error", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}