CREATE TABLE tbl_user (
    id int8 NOT NULL,
    del_at char(1) NULL,
    email varchar(255) NULL,
    nick_name varchar(255) NULL,
    oauth_at char(1) NULL,
    "password" varchar(255) NULL,
    create_date timestamp(6) NULL,
    create_by varchar(255) NULL,
    modified_date timestamp(6) NULL,
    modified_by varchar(255) NULL,
    CONSTRAINT tbl_user_pkey PRIMARY KEY (id)
);

-- tbl_oauth_user 테이블 생성
CREATE TABLE tbl_oauth_user (
    id int8 NOT NULL,
    user_id int8 NULL,
    oauth_id int8 NOT NULL,
    del_at char(1) NULL,
    email varchar(255) NULL,
    nick_name varchar(255) NULL,
    oauth_server_type varchar(255) NOT NULL,
    create_date timestamp(6) NULL,
    create_by varchar(255) NULL,
    modified_date timestamp(6) NULL,
    modified_by varchar(255) NULL,
    CONSTRAINT tbl_oauth_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_user_id UNIQUE (user_id), -- user_id 열에 유니크 제약 조건 추가
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES tbl_user(id) -- user_id를 tbl_user의 id와 참조하는 외래 키 추가
);