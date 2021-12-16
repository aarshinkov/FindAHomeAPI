CREATE TABLE users(
	user_id varchar(200) not null primary key,
	email varchar(100) not null unique,
	password varchar(100) not null,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	created_on timestamp not null default NOW()
);

CREATE TABLE roles(
	role varchar(40) not null primary key
);

INSERT INTO roles VALUES ('ADMIN');
INSERT INTO roles VALUES ('USER');

CREATE TABLE user_roles(
	user_id varchar(200) not null references users(user_id),
	role varchar(40) not null references roles(role),	
	CONSTRAINT user_roles_pk PRIMARY KEY (user_id, role)
);

CREATE TABLE estates(
	estate_id varchar(200) not null primary key,
	name varchar(100) not null,
	country varchar(50) not null default 'bg',
	city varchar(100) not null,
	neighborhood varchar(200),
	price numeric not null,
	rooms int not null default 1,
	floor int not null default 1,
	area numeric not null,
	address text not null,
	is_rent boolean not null default false,
	created_on timestamp not null default NOW(),
	edited_on timestamp,
	user_id varchar(200) not null references users(user_id)
);