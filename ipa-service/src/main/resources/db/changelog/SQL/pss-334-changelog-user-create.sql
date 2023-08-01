CREATE TABLE adm_user_type
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(30) UNIQUE,
    description VARCHAR(255),
    CONSTRAINT pk_adm_user_type PRIMARY KEY (id)
);

INSERT INTO adm_user_type
VALUES (1, 'BACK_OFFICE', 'Back office user'),
       (2, 'CORPORATE', 'Corporate user'),
       (3, 'AGENT', 'Agent user');

ALTER TABLE adm_user
    ADD IF NOT EXISTS user_type_id BIGINT;

ALTER TABLE adm_user
    ALTER COLUMN user_type_id SET NOT NULL;

UPDATE adm_user
set user_type_id =1
WHERE id = 1;