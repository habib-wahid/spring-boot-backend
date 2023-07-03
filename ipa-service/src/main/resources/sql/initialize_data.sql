-- Create Super Admin => username='admin' password='admin'
insert into ipa_admin_user(first_name, last_name, email, username, password, active, version)
values ('Admin', 'Admin', 'admin@gmail.com', 'admin', '$2a$10$FszQtoGSFc/WDJZvOUQiQeGhGiw7KwwXh7tMRbMBpUtYLE.PGmM/y',
        true, 0);


with tmp_values(id, name, title, version) as
         (values (1, 'INVENTORY', 'Inventory', 0),
                 (2, 'PRICING', 'Pricing', 0),
                 (3, 'SALES', 'Sales', 0),
                 (4, 'ADMIN', 'Admin', 0))
select id, name, title, version
into table tmp_adm_module
from tmp_values;

merge into ipa_admin_module ac_module
    using tmp_adm_module tm_module
    on ac_module.id = tm_module.id
    when matched then do nothing
    when not matched then
    insert (id, name, title, version)
    values (tm_module.id, tm_module.name, tm_module.title, tm_module.version);

-- create temp menu table

with tmp_menus(id, name, url, icon, module_id, version) as
         (values (1, 'Aircraft', 'menu_url1', 'menu_icon1', 1, 0),
                 (2, 'Cabin', 'menu_url2', 'menu_icon2', 1, 0),
                 (3, 'PRBD', 'menu_url3', 'menu_icon3', 1, 0),
                 (4, 'Fare Basis', 'menu_url4', 'menu_icon4', 2, 0),
                 (5, 'Quick Sales', 'menu_url5', 'menu_icon4', 3, 0),
                 (6, 'User', 'menu_url6', 'menu_icon6', 4, 0),
                 (7, 'Group', 'menu_url7', 'menu_icon6', 4, 0),
                 (8, 'Role', 'menu_url8', 'menu_icon6', 4, 0),
                 (9, 'Menu', 'menu_url9', 'menu_icon6', 4, 0))
select id, name, url, icon, module_id, version
into table tmp_adm_menu
from tmp_menus;

-- merge menu
merge into ipa_admin_menu ac_menu
    using tmp_adm_menu tm_menu
    on ac_menu.id = tm_menu.id
    when matched then do nothing
    when not matched then
    insert (id, name, url, icon, module_id, version)
    values (tm_menu.id, tm_menu.name, tm_menu.url, tm_menu.icon, tm_menu.module_id, tm_menu.version);


-- create temp action table
with tmp_actions(id, name, menu_id, version) as
         (values (1, 'AIRCRAFT_CREATE', 1, 0),
                 (2, 'AIRCRAFT_READ', 1, 0),
                 (3, 'AIRCRAFT_UPDATE', 1, 0),
                 (4, 'AIRCRAFT_DELETE', 1, 0),
                 (5, 'CABIN_CREATE', 2, 0),
                 (6, 'CABIN_READ', 2, 0),
                 (7, 'CABIN_UPDATE', 2, 0),
                 (8, 'CABIN_DELETE', 2, 0),
                 (9, 'PRBD_CREATE', 3, 0),
                 (10, 'PRBD_READ', 3, 0),
                 (11, 'PRBD_UPDATE', 3, 0),
                 (12, 'PRBD_DELETE', 3, 0),
                 (13, 'FARE_BASIS_CREATE', 4, 0),
                 (14, 'FARE_BASIS_READ', 4, 0),
                 (15, 'FARE_BASIS_UPDATE', 4, 0),
                 (16, 'FARE_BASIS_DELETE', 4, 0),
                 (17, 'USER_CREATE', 6, 0),
                 (18, 'USER_READ', 6, 0),
                 (19, 'USER_UPDATE', 6, 0),
                 (20, 'USER_DELETE', 6, 0),
                 (21, 'GROUP_CREATE', 7, 0),
                 (22, 'GROUP_READ', 7, 0),
                 (23, 'GROUP_UPDATE', 7, 0),
                 (24, 'GROUP_DELETE', 7, 0),
                 (25, 'ROLE_CREATE', 8, 0),
                 (26, 'ROLE_READ', 8, 0),
                 (27, 'ROLE_UPDATE', 8, 0),
                 (28, 'ROLE_DELETE', 8, 0),
                 (29, 'MENU_CREATE', 9, 0),
                 (30, 'MENU_READ', 9, 0),
                 (31, 'MENU_UPDATE', 9, 0),
                 (32, 'MENU_DELETE', 9, 0))

select id, name, menu_id, version
into table tmp_adm_action
from tmp_actions;


-- create table
--     if not exists tmp_adm_action
-- (
--     id      bigint,
--     name    varchar(255),
--     menu_id int,
--     version int,
--     constraint pk_adm_action primary key
--         (id)
-- );
--
-- -- insert data
--
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (1, 'AIRCRAFT_CREATE', 1, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (2, 'AIRCRAFT_READ', 1, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (3, 'AIRCRAFT_UPDATE', 1, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (4, 'AIRCRAFT_DELETE', 1, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (5, 'CABIN_CREATE', 2, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (6, 'CABIN_READ', 2, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (7, 'CABIN_UPDATE', 2, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (8, 'CABIN_DELETE', 2, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (9, 'PRBD_CREATE', 3, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (10, 'PRBD_READ', 3, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (11, 'PRBD_UPDATE', 3, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (12, 'PRBD_DELETE', 3, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (13, 'FARE_BASIS_CREATE', 4, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (14, 'FARE_BASIS_READ', 4, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (15, 'FARE_BASIS_UPDATE', 4, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (16, 'FARE_BASIS_DELETE', 4, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (17, 'USER_CREATE', 6, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (18, 'USER_READ', 6, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (19, 'USER_UPDATE', 6, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (20, 'USER_DELETE', 6, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (21, 'GROUP_CREATE', 7, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (22, 'GROUP_READ', 7, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (23, 'GROUP_UPDATE', 7, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (24, 'GROUP_DELETE', 7, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (25, 'ROLE_CREATE', 8, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (26, 'ROLE_READ', 8, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (27, 'ROLE_UPDATE', 8, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (28, 'ROLE_DELETE', 8, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (29, 'MENU_CREATE', 9, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (30, 'MENU_READ', 9, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (31, 'MENU_UPDATE', 9, 0);
-- insert into tmp_adm_action(id, name, menu_id, version)
-- values (32, 'MENU_DELETE', 9, 0);

-- merge actions
merge into ipa_admin_action ac_action
    using tmp_adm_action tm_action
    on ac_action.id = tm_action.id
    when matched then do nothing
    when not matched then
    insert (id, name, menu_id, version)
    values (tm_action.id, tm_action.name, tm_action.menu_id, tm_action.version);

-- drop the temp action table
