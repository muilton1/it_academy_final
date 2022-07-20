package it.academy.user_service.dao.api;

import it.academy.user_service.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleDao extends JpaRepository<Role, Long> {
    Role findById(long id);
}
