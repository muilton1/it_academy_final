package it.academy.user_service.dto;

import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dao.enums.EUserRole;
import it.academy.user_service.dao.enums.EUserStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Validated
public class UserDto implements Serializable {
    private String mail;
    private String nick;
    private EUserRole role;
    private EUserStatus status;
    private String password;

    public UserDto() {
    }

    public UserDto(User entity) {
        this.mail = entity.getMail();
        this.nick = entity.getNick();
        this.role = entity.getRole();
        this.status = entity.getStatus();
        this.password = entity.getPassword();
    }

    @NotEmpty(message = "Заполните email !")
    @Email(message = "Неверный формат почты!")
    public String getMail() {
        return mail;
    }


    @Size(min = 2, max = 20, message = "Никнейм должен быть от 2 до 20 символов!")
    public String getNick() {
        return nick;
    }

    public EUserRole getRole() {
        return role;
    }

    public EUserStatus getStatus() {
        return status;
    }

    @NotEmpty(message = "Заполните псевдоним !")
    @Size(min = 2, max = 20, message = "Никнейм должен быть от 2 до 20 символов!")
    public String getPassword() {
        return password;
    }
}
