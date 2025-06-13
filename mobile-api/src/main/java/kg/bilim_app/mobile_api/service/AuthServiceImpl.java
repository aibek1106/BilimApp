package kg.bilim_app.mobile_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kg.bilim_app.mobile_api.security.JwtService;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${telegram.bot-token}")
    private String botToken;

    private Mac hmacSha256;

    @PostConstruct
    void init() throws Exception {
        hmacSha256 = Mac.getInstance("HmacSHA256");
    }

    @Override
    public String authenticate(String initData) {
        Map<String, String> data = parse(initData);
        String hash = data.remove("hash");

        byte[] secret = hmac("WebAppData", botToken.getBytes(StandardCharsets.UTF_8));
        String dataCheckString = data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));
        String calculated = bytesToHex(hmac(dataCheckString, secret));
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
                String value = java.net.URLDecoder.decode(p[1], StandardCharsets.UTF_8);
                map.put(key, value);
            }
        }
        return map;
    }

    private byte[] hmac(String data, byte[] key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "HmacSHA256");
            hmacSha256.init(keySpec);
            return hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
