package it.academy.user_service.dao.api;

import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dao.enums.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserDao extends JpaRepository<User, UUID> {
    User findByUuid(UUID uuid);

    User findByMail(String mail);

    User findByNick(String nick);

    User findByRole(EUserRole role);
}
