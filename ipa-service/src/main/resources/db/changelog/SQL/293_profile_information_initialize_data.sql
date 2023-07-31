-- ablonewolf-293-1

ALTER TABLE adm_user ADD COLUMN
    if not exists is2fa_enabled BOOLEAN;

ALTER TABLE adm_user ADD COLUMN
    IF NOT EXISTS user_code VARCHAR(6);

update adm_user set is2fa_enabled = false
where id = 1;

update adm_user set user_code = 'adm11'
where id = 1;

