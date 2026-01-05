package vnua.fita.vxlam.bookshop.initdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vnua.fita.vxlam.bookshop.entity.Role;
import vnua.fita.vxlam.bookshop.entity.User;
import vnua.fita.vxlam.bookshop.repository.RoleRepository;
import vnua.fita.vxlam.bookshop.repository.UserRepository;

import java.util.Set;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("///////////////////////////");

        // 1. Khởi tạo các Role nếu chưa tồn tại



        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("ROLE_USER") == null) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }


        String adminUsername = "admin";
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = new User();
            admin.setUsername(adminUsername);

            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@bookshop.com");
            admin.setFullname("Hệ Thống Super Admin");
            admin.setPhone("0123456789");


            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            Role userRole = roleRepository.findByName("ROLE_USER");
            admin.setRoles(Set.of(adminRole, userRole));

            userRepository.save(admin);
            System.out.println(">>> Đã khởi tạo tài khoản Super User thành công!");
        }
    }
}