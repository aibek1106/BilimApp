package kg.bilim_app.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import kg.bilim_app.common.logging.AesEncryptor;
import kg.bilim_app.common.logging.AccessLogSaver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Logs each request and response while encrypting sensitive parameters.
 */
@Slf4j
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private final AesEncryptor encryptor;
    private final AccessLogSaver accessLogSaver;

    public RequestResponseLoggingFilter(
            @Value("${logging.secret-key:ChangeMe1234567890}") String key,
            ObjectProvider<AccessLogSaver> saverProvider) {
        this.encryptor = new AesEncryptor(key);
        this.accessLogSaver = saverProvider.getIfAvailable();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            logRequest(wrappedRequest);
            logResponse(wrappedResponse);
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
        body = sanitize(body);

        Map<String, String[]> params = request.getParameterMap();
        Map<String, String> sanitized = new HashMap<>();
        params.forEach((k, v) -> sanitized.put(k, encryptIfSecret(k, String.join(",", v))));

        String ip = resolveIp(request);
        log.info("Request: {} {} ip={} params={} body={}",
                request.getMethod(), request.getRequestURI(), ip, sanitized, body);
        if (accessLogSaver != null) {
            try {
                accessLogSaver.save(ip, request.getRequestURI(), request.getMethod());
            } catch (Exception e) {
                log.debug("Failed to persist access log", e);
            }
        }
    }

    private void logResponse(ContentCachingResponseWrapper response) throws IOException {
        String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        body = sanitize(body);
        log.info("Response: status={} body={}", response.getStatus(), body);
    }

    private String resolveIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String sanitize(String body) {
        Pattern p = Pattern.compile("\"(token|password|botToken)\"\s*:\s*\"(.*?)\"", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(body);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String enc = encryptor.encrypt(m.group(2));
            m.appendReplacement(sb, m.group(1) + "\":\"" + enc + "\"");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String encryptIfSecret(String name, String value) {
        String lower = name.toLowerCase();
        if (lower.contains("token") || lower.contains("password")) {
            return encryptor.encrypt(value);
        }
        return value;
    }
}
