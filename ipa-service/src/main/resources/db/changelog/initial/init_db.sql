

CREATE TABLE IF NOT EXISTS master.tenant_config (
                                                    "id" varchar NOT NULL
                                                        CONSTRAINT tenant_pk
                                                            PRIMARY KEY,
                                                    "name" varchar NOT NULL
                                                        CONSTRAINT tenant_name_uc
                                                            UNIQUE,
                                                    "schema" varchar NOT NULL
                                                        CONSTRAINT tenant_schema_uc
                                                            UNIQUE,
                                                    "issuer" varchar NOT NULL
                                                        CONSTRAINT tenant_issuer_uc
                                                            UNIQUE
);

CREATE TABLE IF NOT EXISTS usba.aircraft ("name" varchar PRIMARY KEY);
CREATE TABLE IF NOT EXISTS airastra.aircraft ("name" varchar PRIMARY KEY);

-- changeset mdabulbasar:1690266244420-1
CREATE SEQUENCE IF NOT EXISTS usba.hibernate_sequence START WITH 1 INCREMENT BY 1;


CREATE TABLE IF NOT EXISTS  usba.adm_action
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    created_by   BIGINT,
    updated_by   BIGINT,
    updated_date TIMESTAMP WITHOUT TIME ZONE,
    version      INTEGER                                 NOT NULL,
    name         VARCHAR(255),
    description  VARCHAR(255),
    sort_order   INTEGER,
    menu_id      BIGINT,
    CONSTRAINT pk_adm_action PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_currency
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    created_by   BIGINT,
    updated_by   BIGINT,
    updated_date TIMESTAMP WITHOUT TIME ZONE,
    version      INTEGER                                 NOT NULL,
    name         VARCHAR(255),
    code         VARCHAR(255),
    CONSTRAINT pk_adm_currency PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_department
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    created_by   BIGINT,
    updated_by   BIGINT,
    updated_date TIMESTAMP WITHOUT TIME ZONE,
    version      INTEGER                                 NOT NULL,
    name         VARCHAR(255),
    CONSTRAINT pk_adm_department PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_designation
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    created_by   BIGINT,
    updated_by   BIGINT,
    updated_date TIMESTAMP WITHOUT TIME ZONE,
    version      INTEGER                                 NOT NULL,
    name         VARCHAR(255),
    CONSTRAINT pk_adm_designation PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_group
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    created_by   BIGINT,
    updated_by   BIGINT,
    updated_date TIMESTAMP WITHOUT TIME ZONE,
    version      INTEGER                                 NOT NULL,
    name         VARCHAR(255),
    description  VARCHAR(255),
    CONSTRAINT pk_adm_group PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_group_wise_action_mapping
(
    action_id BIGINT NOT NULL,
    group_id  BIGINT NOT NULL,
    CONSTRAINT pk_adm_group_wise_action_mapping PRIMARY KEY (action_id, group_id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_menu
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date  TIMESTAMP WITHOUT TIME ZONE,
    created_by    BIGINT,
    updated_by    BIGINT,
    updated_date  TIMESTAMP WITHOUT TIME ZONE,
    version       INTEGER                                 NOT NULL,
    name          VARCHAR(255),
    description   VARCHAR(255),
    screen_id     VARCHAR(255),
    url           VARCHAR(255),
    icon          VARCHAR(255),
    sort_order    INTEGER,
    sub_module_id BIGINT,
    CONSTRAINT pk_adm_menu PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_module
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    created_by   BIGINT,
    updated_by   BIGINT,
    updated_date TIMESTAMP WITHOUT TIME ZONE,
    version      INTEGER                                 NOT NULL,
    name         VARCHAR(255),
    description  VARCHAR(255),
    sort_order   INTEGER,
    CONSTRAINT pk_adm_module PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_password_policy
(
    id                          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date                TIMESTAMP WITHOUT TIME ZONE,
    created_by                  BIGINT,
    updated_by                  BIGINT,
    updated_date                TIMESTAMP WITHOUT TIME ZONE,
    version                     INTEGER                                 NOT NULL,
    password_length             INTEGER,
    contains_uppercase          BOOLEAN,
    contains_lowercase          BOOLEAN,
    contains_digit              BOOLEAN,
    contains_special_characters BOOLEAN,
    CONSTRAINT pk_adm_password_policy PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_password_reset
(
    token_id   UUID NOT NULL,
    user_id    BIGINT,
    expiration TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_adm_password_reset PRIMARY KEY (token_id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_personal_info
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date     TIMESTAMP WITHOUT TIME ZONE,
    created_by       BIGINT,
    updated_by       BIGINT,
    updated_date     TIMESTAMP WITHOUT TIME ZONE,
    version          INTEGER                                 NOT NULL,
    first_name       VARCHAR(255),
    last_name        VARCHAR(255),
    department_id    BIGINT,
    designation_id   BIGINT,
    email_official   VARCHAR(255),
    email_other      VARCHAR(255),
    mobile_number    VARCHAR(255),
    telephone_number VARCHAR(255),
    access_level     VARCHAR(255),
    airport          VARCHAR(255),
    CONSTRAINT pk_adm_personal_info PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_personal_info_currency_mapping
(
    currency_id      BIGINT NOT NULL,
    personal_info_id BIGINT NOT NULL,
    CONSTRAINT pk_adm_personal_info_currency_mapping PRIMARY KEY (currency_id, personal_info_id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_personal_info_point_of_sale_mapping
(
    personal_info_id BIGINT NOT NULL,
    point_of_sale_id BIGINT NOT NULL,
    CONSTRAINT pk_adm_personal_info_point_of_sale_mapping PRIMARY KEY (personal_info_id, point_of_sale_id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_point_of_sale
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    created_by   BIGINT,
    updated_by   BIGINT,
    updated_date TIMESTAMP WITHOUT TIME ZONE,
    version      INTEGER                                 NOT NULL,
    name         VARCHAR(255),
    CONSTRAINT pk_adm_point_of_sale PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_refresh_token
(
    token_id   UUID NOT NULL,
    user_id    BIGINT,
    expiration TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_adm_refresh_token PRIMARY KEY (token_id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_sub_module
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    created_by   BIGINT,
    updated_by   BIGINT,
    updated_date TIMESTAMP WITHOUT TIME ZONE,
    version      INTEGER                                 NOT NULL,
    name         VARCHAR(255),
    description  VARCHAR(255),
    sort_order   INTEGER,
    module_id    BIGINT,
    CONSTRAINT pk_adm_sub_module PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS  usba.adm_user
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date     TIMESTAMP WITHOUT TIME ZONE,
    created_by       BIGINT,
    updated_by       BIGINT,
    updated_date     TIMESTAMP WITHOUT TIME ZONE,
    version          INTEGER                                 NOT NULL,
    email            VARCHAR(255),
    username         VARCHAR(255),
    password         VARCHAR(255),
    company_code     VARCHAR(255),
    active           BOOLEAN                                 NOT NULL,
    group_id         BIGINT,
    CONSTRAINT pk_adm_user PRIMARY KEY (id)
);

-- changeset mdabulbasar:1690266244420-19
CREATE TABLE IF NOT EXISTS  usba.adm_user_additional_action_permission
(
    action_id BIGINT NOT NULL,
    user_id   BIGINT NOT NULL,
    CONSTRAINT pk_adm_user_additional_action_permission PRIMARY KEY (action_id, user_id)
);

CREATE TABLE if not exists usba.adm_email_data
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_date    TIMESTAMP WITHOUT TIME ZONE,
    created_by      BIGINT,
    updated_by      BIGINT,
    updated_date    TIMESTAMP WITHOUT TIME ZONE,
    version         INTEGER                                 NOT NULL,
    subject         VARCHAR(255),
    email_type      VARCHAR(15),
    attachment_path VARCHAR(255),
    body            VARCHAR(5000),
    CONSTRAINT pk_adm_email_data PRIMARY KEY (id)
);

CREATE TABLE if not exists usba.adm_otp
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    otp_code        VARCHAR(6),
    expiration      TIMESTAMP WITHOUT TIME ZONE,
    resend_timer    TIMESTAMP WITHOUT TIME ZONE,
    otp_type        INTEGER,
    user_id         BIGINT NOT NULL,
    CONSTRAINT pk_adm_otp PRIMARY KEY (id)
);

CREATE TABLE if not exists usba.adm_otp_log
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    otp_code        VARCHAR(6),
    otp_status      INTEGER,
    otp_type        INTEGER,
    otp_status_date TIMESTAMP WITHOUT TIME ZONE,
    user_id         BIGINT NOT NULL,
    CONSTRAINT pk_adm_otp_log PRIMARY KEY (id)
);

create table if not exists usba.revinfo (
                                            rev bigint not null,
                                            revtstmp bigint,
                                            primary key (rev)
);

create sequence if not exists usba.revinfo_seq start with 1 increment by 50;

create table if not exists usba.adm_group_aud (
                                                  id bigint not null,
                                                  rev integer not null,
                                                  revtype smallint,
                                                  description varchar(255),
                                                  name varchar(255),
                                                  primary key (rev, id)
);

create table if not exists usba.adm_password_policy_aud (
                                                            id bigint not null,
                                                            rev integer not null,
                                                            revtype smallint,
                                                            contains_digit boolean,
                                                            contains_lowercase boolean,
                                                            contains_special_characters boolean,
                                                            password_length integer,
                                                            primary key (rev, id)
);

ALTER TABLE usba.adm_user
    ADD COLUMN IF NOT EXISTS personal_info_id BIGINT ;

ALTER TABLE usba.adm_personal_info
    ADD column if not exists point_of_sale_id BIGINT ;

ALTER TABLE adm_user ADD COLUMN
    IF NOT EXISTS user_code VARCHAR(6);

alter table usba.adm_group_aud DROP Constraint if exists FKbag08wxf9cy3ox8s42pv1fuk1;
alter table usba.adm_group_aud
    add constraint FKbag08wxf9cy3ox8s42pv1fuk1
        foreign key (rev)
            references revinfo;

alter table usba.adm_otp
    DROP CONSTRAINT IF EXISTS FK_ADM_OTP_ON_USER;

ALTER TABLE usba.adm_otp
    DROP CONSTRAINT IF EXISTS FK_ADM_OTP_ON_USER;
ALTER TABLE usba.adm_otp
    ADD CONSTRAINT FK_ADM_OTP_ON_USER FOREIGN KEY (user_id) REFERENCES usba.adm_user (id);

ALTER TABLE usba.adm_otp_log
    DROP CONSTRAINT IF EXISTS FK_ADM_OTP_LOG_ON_USER;
ALTER TABLE usba.adm_otp_log
    ADD CONSTRAINT FK_ADM_OTP_LOG_ON_USER FOREIGN KEY (user_id) REFERENCES usba.adm_user (id);

ALTER TABLE usba.adm_password_policy_aud
    DROP CONSTRAINT IF EXISTS FKtqrrcymjpxielru43go2vqwbr;

alter table if exists usba.adm_password_policy_aud
    add constraint FKtqrrcymjpxielru43go2vqwbr
        foreign key (rev)
            references revinfo;

-- changeset mdabulbasar:1690266244420-20
ALTER TABLE usba.adm_action
    DROP CONSTRAINT IF EXISTS uc_adm_action_name;
ALTER TABLE usba.adm_action
    ADD CONSTRAINT uc_adm_action_name UNIQUE (name);

-- changeset mdabulbasar:1690266244420-21
ALTER TABLE usba.adm_currency
    DROP CONSTRAINT IF EXISTS uc_adm_currency_code;

ALTER TABLE usba.adm_currency
    ADD CONSTRAINT uc_adm_currency_code UNIQUE (code);

-- changeset mdabulbasar:1690266244420-22
ALTER TABLE usba.adm_department
    DROP CONSTRAINT IF EXISTS uc_adm_department_name;

ALTER TABLE usba.adm_department
    ADD CONSTRAINT uc_adm_department_name UNIQUE (name);


ALTER TABLE usba.adm_designation
    DROP CONSTRAINT IF EXISTS uc_adm_designation_name;

ALTER TABLE usba.adm_designation
    ADD CONSTRAINT uc_adm_designation_name UNIQUE (name);

ALTER TABLE usba.adm_designation
    DROP CONSTRAINT IF EXISTS uc_adm_group_name;

ALTER TABLE usba.adm_designation
    ADD CONSTRAINT uc_adm_group_name UNIQUE (name);

ALTER TABLE usba.adm_menu
    DROP CONSTRAINT IF EXISTS uc_adm_menu_name;

ALTER TABLE usba.adm_menu
    ADD CONSTRAINT uc_adm_menu_name UNIQUE (name);

ALTER TABLE usba.adm_menu
    DROP CONSTRAINT IF EXISTS uc_adm_menu_screenid;

ALTER TABLE usba.adm_menu
    ADD CONSTRAINT uc_adm_menu_screenid UNIQUE (screen_id);

ALTER TABLE usba.adm_menu
    DROP CONSTRAINT IF EXISTS uc_adm_menu_url;
ALTER TABLE usba.adm_menu
    ADD CONSTRAINT uc_adm_menu_url UNIQUE (url);

ALTER TABLE usba.adm_module
    DROP CONSTRAINT IF EXISTS uc_adm_module_name;
ALTER TABLE usba.adm_module
    ADD CONSTRAINT uc_adm_module_name UNIQUE (name);

ALTER TABLE usba.adm_point_of_sale
    DROP CONSTRAINT IF EXISTS uc_adm_point_of_sale_name;
ALTER TABLE usba.adm_point_of_sale
    ADD CONSTRAINT uc_adm_point_of_sale_name UNIQUE (name);

ALTER TABLE usba.adm_user
    DROP CONSTRAINT IF EXISTS uc_adm_user_email;
ALTER TABLE usba.adm_user
    ADD CONSTRAINT uc_adm_user_email UNIQUE (email);

ALTER TABLE usba.adm_user
    DROP CONSTRAINT IF EXISTS uc_adm_user_username;
ALTER TABLE usba.adm_user
    ADD CONSTRAINT uc_adm_user_username UNIQUE (username);

ALTER TABLE usba.adm_action
    DROP CONSTRAINT IF EXISTS FK_ADM_ACTION_ON_MENU;
ALTER TABLE usba.adm_action
    ADD CONSTRAINT FK_ADM_ACTION_ON_MENU FOREIGN KEY (menu_id) REFERENCES usba.adm_menu (id);

ALTER TABLE usba.adm_menu
    DROP CONSTRAINT IF EXISTS FK_ADM_MENU_ON_SUBMODULE;
ALTER TABLE usba.adm_menu
    ADD CONSTRAINT FK_ADM_MENU_ON_SUBMODULE FOREIGN KEY (sub_module_id) REFERENCES usba.adm_sub_module (id);

ALTER TABLE usba.adm_password_reset
    DROP CONSTRAINT IF EXISTS FK_ADM_PASSWORD_RESET_ON_USER;
ALTER TABLE usba.adm_password_reset
    ADD CONSTRAINT FK_ADM_PASSWORD_RESET_ON_USER FOREIGN KEY (user_id) REFERENCES usba.adm_user (id);

ALTER TABLE usba.adm_personal_info
    DROP CONSTRAINT IF EXISTS FK_ADM_PERSONAL_INFO_ON_DEPARTMENT;
ALTER TABLE usba.adm_personal_info
    ADD CONSTRAINT FK_ADM_PERSONAL_INFO_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES usba.adm_department (id);

ALTER TABLE usba.adm_personal_info
    DROP CONSTRAINT IF EXISTS FK_ADM_PERSONAL_INFO_ON_DESIGNATION;
ALTER TABLE usba.adm_personal_info
    ADD CONSTRAINT FK_ADM_PERSONAL_INFO_ON_DESIGNATION FOREIGN KEY (designation_id) REFERENCES usba.adm_designation (id);

ALTER TABLE usba.adm_refresh_token
    DROP CONSTRAINT IF EXISTS FK_ADM_REFRESH_TOKEN_ON_USER;
ALTER TABLE usba.adm_refresh_token
    ADD CONSTRAINT FK_ADM_REFRESH_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES usba.adm_user (id);

ALTER TABLE usba.adm_sub_module
    DROP CONSTRAINT IF EXISTS FK_ADM_SUB_MODULE_ON_MODULE;
ALTER TABLE usba.adm_sub_module
    ADD CONSTRAINT FK_ADM_SUB_MODULE_ON_MODULE FOREIGN KEY (module_id) REFERENCES usba.adm_module (id);

ALTER TABLE usba.adm_user
    DROP CONSTRAINT IF EXISTS FK_ADM_USER_ON_GROUP;
ALTER TABLE usba.adm_user
    ADD CONSTRAINT FK_ADM_USER_ON_GROUP FOREIGN KEY (group_id) REFERENCES usba.adm_group (id);

ALTER TABLE usba.adm_user
    DROP CONSTRAINT IF EXISTS FK_ADM_USER_ON_PERSONALINFO;
ALTER TABLE usba.adm_user
    ADD CONSTRAINT FK_ADM_USER_ON_PERSONALINFO FOREIGN KEY (personal_info_id) REFERENCES usba.adm_personal_info (id);

ALTER TABLE usba.adm_group_wise_action_mapping
    DROP CONSTRAINT IF EXISTS fk_admgrowisactmap_on_action;
ALTER TABLE usba.adm_group_wise_action_mapping
    ADD CONSTRAINT fk_admgrowisactmap_on_action FOREIGN KEY (action_id) REFERENCES usba.adm_action (id);

ALTER TABLE usba.adm_group_wise_action_mapping
    DROP CONSTRAINT IF EXISTS fk_admgrowisactmap_on_group;
ALTER TABLE usba.adm_group_wise_action_mapping
    ADD CONSTRAINT fk_admgrowisactmap_on_group FOREIGN KEY (group_id) REFERENCES usba.adm_group (id);

ALTER TABLE usba.adm_personal_info_currency_mapping
    DROP CONSTRAINT IF EXISTS fk_admperinfcurmap_on_currency;
ALTER TABLE usba.adm_personal_info_currency_mapping
    ADD CONSTRAINT fk_admperinfcurmap_on_currency FOREIGN KEY (currency_id) REFERENCES usba.adm_currency (id);

ALTER TABLE usba.adm_personal_info_currency_mapping
    DROP CONSTRAINT IF EXISTS fk_admperinfcurmap_on_personal_info;
ALTER TABLE usba.adm_personal_info_currency_mapping
    ADD CONSTRAINT fk_admperinfcurmap_on_personal_info FOREIGN KEY (personal_info_id) REFERENCES usba.adm_personal_info (id);

ALTER TABLE usba.adm_personal_info_point_of_sale_mapping
    DROP CONSTRAINT IF EXISTS fk_admperinfpoiofsalmap_on_personal_info;
ALTER TABLE usba.adm_personal_info_point_of_sale_mapping
    ADD CONSTRAINT fk_admperinfpoiofsalmap_on_personal_info FOREIGN KEY (personal_info_id) REFERENCES usba.adm_personal_info (id);

ALTER TABLE usba.adm_personal_info_point_of_sale_mapping
    DROP CONSTRAINT IF EXISTS fk_admperinfpoiofsalmap_on_point_of_sale;
ALTER TABLE usba.adm_personal_info_point_of_sale_mapping
    ADD CONSTRAINT fk_admperinfpoiofsalmap_on_point_of_sale FOREIGN KEY (point_of_sale_id) REFERENCES usba.adm_point_of_sale (id);

ALTER TABLE usba.adm_user_additional_action_permission
    DROP CONSTRAINT IF EXISTS fk_admuseaddactper_on_action;
ALTER TABLE usba.adm_user_additional_action_permission
    ADD CONSTRAINT fk_admuseaddactper_on_action FOREIGN KEY (action_id) REFERENCES usba.adm_action (id);

ALTER TABLE usba.adm_user_additional_action_permission
    DROP CONSTRAINT IF EXISTS fk_admuseaddactper_on_user;
ALTER TABLE usba.adm_user_additional_action_permission
    ADD CONSTRAINT fk_admuseaddactper_on_user FOREIGN KEY (user_id) REFERENCES usba.adm_user (id);

ALTER TABLE usba.adm_user
    ADD COLUMN IF NOT EXISTS is2fa_enabled BOOLEAN NOT NULL;

alter table usba.adm_user
    add column if not exists password_expiry_date timestamp (6);