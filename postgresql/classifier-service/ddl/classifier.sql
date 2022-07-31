CREATE DATABASE classifierservice;

GRANT ALL PRIVILEGES ON DATABASE postgres TO postgres;

\connect classifierservice;

CREATE SCHEMA IF NOT EXISTS classifier
    AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS classifier.categories
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    title character varying COLLATE pg_catalog."default",
    CONSTRAINT categories_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS classifier.categories
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS classifier.countries
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    title character varying COLLATE pg_catalog."default",
    description character varying COLLATE pg_catalog."default",
    CONSTRAINT countries_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS classifier.countries
    OWNER to postgres;