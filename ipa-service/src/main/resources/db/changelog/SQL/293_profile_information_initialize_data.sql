-- ablonewolf-293-1

ALTER TABLE adm_user
    ADD COLUMN
        if not exists is2fa_enabled BOOLEAN;

ALTER TABLE adm_user
    ADD COLUMN
        IF NOT EXISTS user_code VARCHAR(6);


CREATE TABLE IF NOT EXISTS adm_user_currency_mapping
(
    user_id     BIGINT NOT NULL,
    currency_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS adm_user_airport_mapping
(
    user_id    BIGINT NOT NULL,
    airport_id BIGINT NOT NULL
);

ড


ALTER TABLE adm_user
    ADD COLUMN
        IF NOT EXISTS point_of_sale_id BIGINT NOT NULL;



update adm_user
set is2fa_enabled = false
where id = 1;

update adm_user
set user_code = 'adm11'
where id = 1;


update adm_user
set is2fa_enabled = false
where id = 1;

