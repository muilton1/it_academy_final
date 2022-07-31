CREATE DATABASE userservice;

GRANT ALL PRIVILEGES ON DATABASE postgres TO postgres;

\connect userservice;

CREATE SCHEMA IF NOT EXISTS security
    AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS security.role
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT t_role_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS security.role
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS security.users
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    mail character varying(255) COLLATE pg_catalog."default",
    nick character varying(255) COLLATE pg_catalog."default",
    password text COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT t_user_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS security.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS security.users_roles
(
    user_uuid uuid NOT NULL,
    roles_id bigint NOT NULL,
    CONSTRAINT fk_role FOREIGN KEY (roles_id)
        REFERENCES security.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_user FOREIGN KEY (user_uuid)
        REFERENCES security.users (uuid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS security.users_roles
    OWNER to postgres;