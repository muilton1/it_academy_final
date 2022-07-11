CREATE DATABASE eventservice
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE SCHEMA IF NOT EXISTS event
    AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS event.concerts
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    title character varying COLLATE pg_catalog."default",
    description character varying COLLATE pg_catalog."default",
    dt_event timestamp(3) without time zone,
    dt_end_of_sale timestamp(3) without time zone,
    type character varying COLLATE pg_catalog."default",
    status character varying COLLATE pg_catalog."default",
    category uuid,
    CONSTRAINT concerts_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS event.concerts
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS event.films
(
    uuid uuid NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    title character varying COLLATE pg_catalog."default",
    description character varying COLLATE pg_catalog."default",
    dt_event timestamp(3) without time zone,
    dt_end_of_sale timestamp(3) without time zone,
    type character varying COLLATE pg_catalog."default",
    status character varying COLLATE pg_catalog."default",
    release_date date,
    country uuid,
    release_year bytea,
    duration bigint,
    CONSTRAINT films_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS event.films
    OWNER to postgres;
