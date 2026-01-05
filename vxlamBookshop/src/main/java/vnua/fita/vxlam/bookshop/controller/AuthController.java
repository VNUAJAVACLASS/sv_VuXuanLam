package vnua.fita.vxlam.bookshop.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vnua.fita.vxlam.bookshop.dto.AuthRequest;
import vnua.fita.vxlam.bookshop.dto.RegisterRequest;
import vnua.fita.vxlam.bookshop.dto.response.ApiResponse;
import vnua.fita.vxlam.bookshop.dto.response.AuthResponse;
import vnua.fita.vxlam.bookshop.entity.User;
import vnua.fita.vxlam.bookshop.repository.UserRepository;
import vnua.fita.vxlam.bookshop.security.JwtService;
import vnua.fita.vxlam.bookshop.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // Đăng ký tài khoản
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            userService.createUser(request);
            return ResponseEntity.ok(
                    ApiResponse.success("Đăng ký tài khoản thành công!", null)
            );
        } catch (RuntimeException e) {
            // Lỗi username đã tồn tại hoặc các lỗi validate khác
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        try {
            // Xác thực username + password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user"));

            // Lấy danh sách roles dưới dạng String
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            // Nếu thành công → tạo JWT
            String jwt = jwtService.generateToken( user.getUsername(),
                    user.getId());

            AuthResponse authResponse = AuthResponse.builder()
                    .token(jwt)
                    .expiresIn(jwtService.getJwtExpirationMs()) // nếu bạn có method này
                    .username(user.getUsername())
                    .fullname(user.getFullname())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .roles(roles)
                    .build();
            return ResponseEntity.ok(
                    ApiResponse.success("Đăng nhập thành công!", authResponse)
            );

        } catch (BadCredentialsException | UsernameNotFoundException e) {

            return ResponseEntity.status(401)
                    .body(ApiResponse.error("Tài khoản hoặc mật khẩu không chính xác"));
        }
    }
}