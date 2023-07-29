DROP TABLE IF EXISTS users CASCADE;
-- DROP TABLE IF EXISTS roles CASCADE;

CREATE TABLE users (
    id              BIGSERIAL NOT NULL,
    name            VARCHAR(30) NOT NULL UNIQUE,
    password        VARCHAR(64),
    secret_key      VARCHAR(512),
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    email           VARCHAR(50) NOT NULL UNIQUE
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

CREATE TABLE roles (
    id                  BIGSERIAL NOT NULL,
    name                VARCHAR(30) NOT NULL UNIQUE,
    allowed_resource    VARCHAR(200),
    allowed_read        BOOLEAN NOT NULL DEFAULT FALSE,
    allowed_create      BOOLEAN NOT NULL DEFAULT FALSE,
    allowed_update      BOOLEAN NOT NULL DEFAULT FALSE,
    allowed_delete      BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER TABLE roles ADD CONSTRAINT role_pk PRIMARY KEY ( id );

CREATE TABLE users_roles (
    user_id     BIGINT NOT NULL,
    role_id     BIGINT NOT NULL
);

ALTER TABLE users_roles
    ADD CONSTRAINT users_fk FOREIGN KEY ( user_id )
        REFERENCES users ( id );

ALTER TABLE users_roles
    ADD CONSTRAINT role_fk FOREIGN KEY ( role_id )
        REFERENCES roles ( id );