package vnua.fita.vxlam.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vnua.fita.vxlam.bookshop.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
