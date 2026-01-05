package vnua.fita.vxlam.bookshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private long expiresIn;

    private String username;
    private String fullname;
    private String email;
    private String phone;
    private Set<String> roles;
}