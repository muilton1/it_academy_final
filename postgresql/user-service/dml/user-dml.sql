\connect userservice;

INSERT INTO security.role(
id, name)
VALUES (1, 'ROLE_USER'),(2, 'ROLE_ADMIN');

INSERT INTO security.users (uuid,dt_create, dt_update, mail, nick, password, role, status) values ('7f7a1266-f0f7-44b4-ace5-eba224909adf', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin@mail.ru', 'admin', '$2a$10$UkDNQFoxy4kXAmSsQj7EdOcnWCydw63MQEP6LhwNy2vjluSK1Co12', 'ADMIN', 'ACTIVATED');

insert into security.users_roles (user_uuid, roles_id) values ('7f7a1266-f0f7-44b4-ace5-eba224909adf', 2);
