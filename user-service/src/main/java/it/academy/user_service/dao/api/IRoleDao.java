package it.academy.user_service.dao.api;

import it.academy.user_service.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDao extends JpaRepository<Role, Long> {
    Role findById(long id);
}
