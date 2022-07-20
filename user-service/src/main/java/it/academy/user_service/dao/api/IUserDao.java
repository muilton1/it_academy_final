package it.academy.user_service.dao.api;

import it.academy.user_service.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserDao extends JpaRepository<User, UUID> {
    User findByUuid(UUID uuid);

    User findByMail(String mail);
}
