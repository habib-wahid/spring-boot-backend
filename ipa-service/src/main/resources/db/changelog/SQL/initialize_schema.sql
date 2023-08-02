-- CREATE SCHEMA IF NOT EXISTS "master";
-- CREATE SCHEMA IF NOT EXISTS "usba";
-- CREATE SCHEMA IF NOT EXISTS "airastra";


create table if not exists master.tenant_config
(
    id     varchar,
    name   varchar unique not null,
    schema varchar unique not null,
    issuer varchar unique not null,
    constraint pk_tenant_config primary key (id)
);

INSERT INTO master.tenant_config
VALUES ('usba', 'US Bangla Air', 'usba', 'https://idp.example.org/tenant-1'),
       ('airastra', 'Air Astra', 'airastra', 'https://idp.example.org/tenant-2') on conflict do nothing;