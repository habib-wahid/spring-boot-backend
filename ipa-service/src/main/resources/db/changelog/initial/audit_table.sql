create table usba.revinfo (
                         rev bigint not null,
                         revtstmp bigint,
                         primary key (rev)
);

create sequence usba.revinfo_seq start with 1 increment by 50;

create table usba.adm_group_aud (
                               id bigint not null,
                               rev integer not null,
                               revtype smallint,
                               description varchar(255),
                               name varchar(255),
                               primary key (rev, id)
);

create table usba.adm_password_policy_aud (
                                         id bigint not null,
                                         rev integer not null,
                                         revtype smallint,
                                         contains_digit boolean,
                                         contains_lowercase boolean,
                                         contains_special_characters boolean,
                                         password_length integer,
                                         primary key (rev, id)
);

alter table if exists usba.adm_group_aud
    add constraint FKbag08wxf9cy3ox8s42pv1fuk1
    foreign key (rev)
    references usba.revinfo;

alter table if exists usba.adm_password_policy_aud
    add constraint FKtqrrcymjpxielru43go2vqwbr
    foreign key (rev)
    references usba.revinfo;