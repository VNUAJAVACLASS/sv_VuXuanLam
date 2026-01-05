package vnua.fita.vxlam.bookshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vnua.fita.vxlam.bookshop.dto.RegisterRequest;

public interface UserService extends UserDetailsService {

    RegisterRequest createUser(RegisterRequest registerRequest);

}
