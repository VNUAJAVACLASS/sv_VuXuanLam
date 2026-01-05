package vnua.fita.vxlam.bookshop.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vnua.fita.vxlam.bookshop.dto.RegisterRequest;
import vnua.fita.vxlam.bookshop.entity.User;
import vnua.fita.vxlam.bookshop.repository.RoleRepository;
import vnua.fita.vxlam.bookshop.repository.UserRepository;
import vnua.fita.vxlam.bookshop.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override

    public RegisterRequest createUser(RegisterRequest registerRequest) {
        // 1. Kiểm tra xem username đã tồn tại trong DB chưa
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            // Ném lỗi nếu đã tồn tại, GlobalExceptionHandler sẽ bắt lỗi này và trả về ApiResponse.error
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        // 3. Mã hóa mật khẩu trước khi lưu vào database (Bảo mật)
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setFullname(registerRequest.getFullname());
        user.setPhone(registerRequest.getPhone());

        // 4. Mặc định gán vai trò (Roles) cho user mới nếu cần
         user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
        // 5. Lưu xuống Database
        userRepository.save(user);

        return registerRequest;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + username));
        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // truyền hàm mũi tên vào map
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(

                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
