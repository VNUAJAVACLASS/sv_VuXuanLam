package vnua.fita.vxlam.bookshop.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}") // lấy chuỗi bí mật từ application.properties
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")  // lấy thời gian hết hạn của jwtToken
    private long jwtExpirationMs;
    // Trong JwtService.java
    public long getJwtExpirationMs() {
        return jwtExpirationMs;
    }
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String username, UUID uuid) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", uuid.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    // Lấy ra username từ token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Ham lay ra username co trong token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // đưa khóa mật vào
                .build()
                .parseClaimsJws(token) // đưa chuỗi jwtToken vào
                .getBody(); // giải mã lấy ra phần payload dưới dạng Claims
        return claimsResolver.apply(claims); // áp dụng hàm truyền vào cho claims ở trên
    }

    public boolean isTokenValid(String token, String username) {
        String user = extractUsername(token);
        return (user.equals(username) && !isTokenExpired(token));
    }
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Kiểm tra token hết hạn chưa
    private boolean isTokenExpired(String token) {
        final Date exp = extractClaim(token, Claims::getExpiration);
        return exp.before(new Date()); // thời điểm hết hạn đã qua
    }
}
