-- Flyway: undo V4.4
DROP TABLE IF EXISTS users_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

-- Recreate the original user table from version 1
CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30),
    email VARCHAR(50),
    password VARCHAR(30),
    dietary_restrictions VARCHAR(30)
);

CREATE TABLE system_users (
    id              BIGSERIAL NOT NULL,
    name            VARCHAR(30) NOT NULL UNIQUE,
    password        VARCHAR(64),
    secret_key      VARCHAR(512),
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    email           VARCHAR(50) NOT NULL UNIQUE
);

ALTER TABLE system_users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

-- Recreate the original roles table from version 4.2
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

CREATE TABLE system_users_roles (
    user_id     BIGINT NOT NULL,
    role_id     BIGINT NOT NULL
);

ALTER TABLE system_users_roles
    ADD CONSTRAINT users_fk FOREIGN KEY ( user_id )
        REFERENCES system_users ( id );

ALTER TABLE system_users_roles
    ADD CONSTRAINT role_fk FOREIGN KEY ( role_id )
        REFERENCES roles ( id );

insert into roles (name, allowed_resource, allowed_read, allowed_create, allowed_update, allowed_delete) values
('Admin', '/', TRUE , TRUE, TRUE, TRUE),
('Manager', '/depts,/departments,/employees,/ems,/acnts,/accounts', TRUE, TRUE, TRUE, FALSE),
('user', '/employees,/ems,/acnts,/accounts', TRUE, FALSE, FALSE, FALSE)
;
commit;

insert into system_users (name, password, first_name, last_name, email) values
('dwang', '25f9e794323b453885f5181f1b624d0b', 'David', 'Wang', 'dwang@training.ascendingdc.com'),
('rhang', '25f9e794323b453885f5181f1b624d0b', 'Ryo', 'Hang', 'rhang@training.ascendingdc.com'),
('xyhuang', '25f9e794323b453885f5181f1b624d0b', 'Xinyue', 'Huang', 'xyhuang@training.ascendingdc.com')
;
commit;

insert into system_users_roles values
(1, 1),
(2, 2),
(3, 3),
(1, 2),
(1, 3)
;
commit;