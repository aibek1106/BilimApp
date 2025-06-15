package kg.bilim_app.common.logging;

/**
 * Optional bean used by {@link RequestResponseLoggingFilter} to persist IP addresses.
 */
public interface AccessLogSaver {
    void save(String ip, String path, String method);
}
