create table base_rate (rate_id bigint not null auto_increment,
    hours bigint not null,
    rate double precision not null,
    relevance_date datetime(6) not null,
    group_id bigint,
    primary key (rate_id)
);

create table cargo_operation (operation_id bigint not null auto_increment,
    operation_name varchar(255),
    primary key (operation_id)
);

create table cargo_type (type_id bigint not null auto_increment,
    type_name varchar(255) not null,
    primary key (type_id)
);

create table customer (customer_id bigint not null auto_increment,
    created datetime(6),
    customer_full_name varchar(255),
    customer_name varchar(255),
    user_id bigint,
    primary key (customer_id)
);

create table delivery_of_wagon (delivery_id bigint not null auto_increment,
    cargo_weight double precision not null,
    created datetime(6),
    end_date datetime(6),
    load_unload_work bit not null,
    shunting_works double precision not null,
    start_date datetime(6),
    user_id bigint,
    operation_id bigint,
    cargo_type_id bigint,
    customer_id bigint,
    memo_of_delivery_id bigint,
    memo_of_dispatch_id bigint,
    owner_id bigint,
    wagon_id bigint,
    type_id bigint,
    primary key (delivery_id)
);

create table index_to_base_rate (index_id bigint not null auto_increment,
    index_to_rate double precision not null,
    relevance_date datetime(6) not null,
    primary key (index_id)
);

create table memo_of_delivery (memo_of_delivery_id bigint not null auto_increment,
    comment varchar(255),
    created datetime(6),
    start_date datetime(6),
    user_id bigint,
    operation_id bigint,
    customer_id bigint,
    signer_id bigint,
    primary key (memo_of_delivery_id)
);

create table memo_of_dispatch (memo_of_dispatch_id bigint not null auto_increment,
    comment varchar(255),
    created datetime(6),
    end_date datetime(6),
    user_id bigint,
    operation_id bigint,
    customer_id bigint,
    signer_id bigint,
    statement_id bigint,
    primary key (memo_of_dispatch_id)
);

create table owner (owner_id bigint not null auto_increment,
    owner_name varchar(255),
    primary key (owner_id)
);

create table penalty (penalty_id bigint not null auto_increment,
    penalty double precision not null,
    relevance_date datetime(6) not null,
    type_id bigint,
    primary key (penalty_id)
);

create table
    refresh_token (id bigint not null auto_increment,
    created_date datetime(6),
    token varchar(255),
    user_id bigint,
    primary key (id)
);

create table signer (signer_id bigint not null auto_increment,
    first_name varchar(255),
    initials varchar(255),
    last_name varchar(255),
    middle_name varchar(255),
    customer_id bigint,
    primary key (signer_id)
);

create table statement (statement_id bigint not null auto_increment,
    comment varchar(255),
    created datetime(6),
    user_id bigint,
    operation_id bigint,
    customer_id bigint,
    signer_id bigint,
    primary key (statement_id)
);

create table tariff (tariff_id bigint not null auto_increment,
    relevance_date datetime(6) not null,
    tariff double precision not null,
    type_id bigint,
    primary key (tariff_id)
);

create table tariff_type (type_id bigint not null auto_increment,
    type_code varchar(255) not null,
    type_name varchar(255) not null,
    primary key (type_id)
);

create table time_norm (norm_id bigint not null auto_increment,
    norm double precision not null,
    relevance_date datetime(6) not null,
    type_id bigint,
    primary key (norm_id)
);

create table time_norm_type (type_id bigint not null auto_increment,
    type_code varchar(255) not null,
    type_name varchar(255) not null,
    primary key (type_id)
);

create table user (user_id bigint not null auto_increment,
    created datetime(6),
    email varchar(255),
    enabled bit not null,
    first_name varchar(255),
    initials varchar(255),
    last_name varchar(255),
    middle_name varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (user_id)
);

create table user_role (user_id bigint not null,
    roles varchar(255)
);

create table wagon (wagon_id bigint not null auto_increment,
    wagon_number varchar(255) not null,
    primary key (wagon_id)
);

create table wagon_group (group_id bigint not null auto_increment,
    group_name varchar(255) not null,
    primary key (group_id)
);

create table wagon_type (type_id bigint not null auto_increment,
    type_name varchar(255) not null,
    group_id bigint,
    primary key (type_id)
);

alter table tariff_type
    add constraint tariff_type_type_code_uk
    unique (type_code);

alter table tariff_type
    add constraint tariff_type_type_name_uk
    unique (type_name);

alter table time_norm_type
    add constraint time_norm_type_type_code_uk
    unique (type_code);

alter table time_norm_type
    add constraint time_norm_type_type_name_uk
    unique (type_name);

alter table user
    add constraint user_username_uk
    unique (username);

alter table wagon
    add constraint wagon_wagon_number_uk
    unique (wagon_number);

alter table base_rate
    add constraint base_rate_wagon_group_fk
    foreign key (group_id)
    references wagon_group (group_id);

alter table customer
    add constraint customer_user_fk
    foreign key (user_id)
    references user (user_id);

alter table delivery_of_wagon
    add constraint delivery_of_wagon_user_fk
    foreign key (user_id)
    references user (user_id);

alter table delivery_of_wagon
    add constraint delivery_of_wagon_cargo_operation_fk
    foreign key (operation_id)
    references cargo_operation (operation_id);

alter table delivery_of_wagon
    add constraint delivery_of_wagon_cargo_type_fk
    foreign key (cargo_type_id)
    references cargo_type (type_id);

alter table delivery_of_wagon
    add constraint delivery_of_wagon_customer_fk
    foreign key (customer_id)
    references customer (customer_id);

alter table delivery_of_wagon
    add constraint delivery_of_wagon_memo_of_delivery_fk
    foreign key (memo_of_delivery_id)
    references memo_of_delivery (memo_of_delivery_id);

alter table delivery_of_wagon
    add constraint delivery_of_wagon_memo_of_dispatch_fk
    foreign key (memo_of_dispatch_id)
    references memo_of_dispatch (memo_of_dispatch_id);

alter table delivery_of_wagon
    add constraint delivery_of_wagon_owner_fk
    foreign key (owner_id)
    references owner (owner_id);

alter table delivery_of_wagon
    add constraint delivery_of_wagon_wagon_fk
    foreign key (wagon_id)
    references wagon (wagon_id);

alter table delivery_of_wagon
    add constraint delivery_of_wagon_wagon_type_fk
    foreign key (type_id)
    references wagon_type (type_id);

alter table memo_of_delivery
    add constraint memo_of_delivery_user_fk
    foreign key (user_id)
    references user (user_id);

alter table memo_of_delivery
    add constraint memo_of_delivery_cargo_operation_fk
    foreign key (operation_id)
    references cargo_operation (operation_id);

alter table memo_of_delivery
    add constraint memo_of_delivery_customer_fk
    foreign key (customer_id)
    references customer (customer_id);

alter table memo_of_delivery
    add constraint memo_of_delivery_signer_fk
    foreign key (signer_id)
    references signer (signer_id);

alter table memo_of_dispatch
    add constraint memo_of_dispatch_user_fk
    foreign key (user_id)
    references user (user_id);

alter table memo_of_dispatch
    add constraint memo_of_dispatch_cargo_operation_fk
    foreign key (operation_id)
    references cargo_operation (operation_id);

alter table memo_of_dispatch
    add constraint memo_of_dispatch_customer_fk
    foreign key (customer_id)
    references customer (customer_id);

alter table memo_of_dispatch
    add constraint memo_of_dispatch_signer_fk
    foreign key (signer_id)
    references signer (signer_id);

alter table memo_of_dispatch
    add constraint memo_of_dispatch_statement_fk
    foreign key (statement_id)
    references statement (statement_id);

alter table penalty
    add constraint penalty_wagon_type_fk
    foreign key (type_id)
    references wagon_type (type_id);

alter table refresh_token
    add constraint refresh_token_user_fk
    foreign key (user_id)
    references user (user_id);

alter table signer
    add constraint signer_customer_fk
    foreign key (customer_id)
    references customer (customer_id);

alter table statement
    add constraint statement_user_fk
    foreign key (user_id)
    references user (user_id);

alter table statement
    add constraint statement_cargo_operation_fk
    foreign key (operation_id)
    references cargo_operation (operation_id);

alter table statement
    add constraint statement_customer_fk
    foreign key (customer_id)
    references customer (customer_id);

alter table statement
    add constraint statement_signer_fk
    foreign key (signer_id)
    references signer (signer_id);

alter table tariff
    add constraint tariff_tariff_type_fk
    foreign key (type_id)
    references tariff_type (type_id);

alter table time_norm
    add constraint time_norm_time_norm_type_fk
    foreign key (type_id)
    references time_norm_type (type_id);

alter table user_role
    add constraint user_role_user_fk
    foreign key (user_id)
    references user (user_id);

alter table wagon_type
    add constraint wagon_type_wagon_group_fk
    foreign key (group_id)
    references wagon_group (group_id);
