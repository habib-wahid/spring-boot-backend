-- Create Super Admin => username='admin' password='admin'
insert into adm_user(id, email, username, password, group_id, is2fa_enabled, user_type, user_code, active, version,
                     personal_info_id)
values (1, 'admin@gmail.com', 'admin',
        '$2a$10$FszQtoGSFc/WDJZvOUQiQeGhGiw7KwwXh7tMRbMBpUtYLE.PGmM/y', 1, false, 'admin', 'admin001', true, 0, 1)
on conflict do nothing;

