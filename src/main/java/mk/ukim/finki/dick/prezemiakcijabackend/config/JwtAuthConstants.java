package mk.ukim.finki.dick.prezemiakcijabackend.config;

public class JwtAuthConstants {

    public static final String SECRET = "s3cr3tt0k3n";
    public static final long EXPIRATION_TIME = 1800000; // 30 minutes;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
