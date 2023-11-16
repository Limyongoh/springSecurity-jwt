CREATE TABLE tbl_user (
    id int8 NOT NULL,
    del_at char(1) NULL,
    oauth_id int8 NULL,
    oauth_at char(1) NULL,
    oauth_server_type varchar(255) NULL,
    email varchar(255) NULL,
    "password" varchar(255) NULL,
    name varchar(255) NULL,
    nick_name varchar(255) NULL,
    phone_number varchar(255) NULL,
    refresh_token varchar(255) NULL,
    create_date timestamp(6) NULL,
    create_by varchar(255) NULL,
    modified_date timestamp(6) NULL,
    modified_by varchar(255) NULL,
    CONSTRAINT tbl_user_pkey PRIMARY KEY (id)
);
