package vnua.fita.vxlam.bookshop.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vnua.fita.vxlam.bookshop.security.JwtAuthenticationFilter;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    //Bean ma hoa password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Tắt CSRF vì sử dụng Stateless JWT
                .authorizeHttpRequests(auth -> auth
                        // 1. Mở cổng cho Swagger & OpenAPI (Không yêu cầu xác thực)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // 2. Mở cổng cho Auth & Public API (Không yêu cầu xác thực)
                        .requestMatchers("/api/auth/**").permitAll()

                        // LẤY SÁCH: Cho phép tất cả mọi người (kể cả chưa đăng nhập)
                        .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()

                        // --- QUY TẮC PHÂN QUYỀN (SỬ DỤNG hasAuthority) ---

                        // 3a. QUẢN LÝ SÁCH (POST, PUT, DELETE): Yêu cầu vai trò ROLE_ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/books/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/books/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/books/**").hasAuthority("ROLE_ADMIN")

                        // 3b. GIỎ HÀNG: Yêu cầu vai trò USER/ADMIN
                        .requestMatchers("/api/cart/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        // 3c. ĐƠN HÀNG: Yêu cầu vai trò USER/ADMIN
                        .requestMatchers("/api/orders/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        // 4. Tất cả các request khác phải xác thực
                        .anyRequest().authenticated()
                )
                // Cấu hình Stateless (không lưu Session)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Thêm JWT Filter trước filter xác thực mặc định
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    // CORS: 1 ứng dụng như localhost:8088 sẽ không cho phép truy cập dịch vụ của nó từ cùng domain khác cổng
    // hoặc khác domain, và có thể thêm giới hạn các phương thức, header được phép...
    // tạo bean cấu hình chi tiết cho cors sử dụng ở bên trên
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // cho phép ứng dụng VueJS truy cập API
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        //configuration.setAllowCredentials(true); // nếu cần

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
