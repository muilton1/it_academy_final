CREATE DATABASE classifierservice
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;


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