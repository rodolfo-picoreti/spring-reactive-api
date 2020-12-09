
create table if not exists t_customer (
    id serial primary key not null,
    ssn varchar(64) unique not null,
    first_name varchar(64),
    last_name varchar(128),
    gender varchar(32),
    date_of_birth date,
    email varchar(128) unique not null,
    phone_number varchar(32),
    version integer
);
