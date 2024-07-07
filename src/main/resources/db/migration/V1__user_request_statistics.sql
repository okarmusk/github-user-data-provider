create table if not exists user_request_statistic (
    login varchar(128) primary key not null,
    request_count integer not null default 1
);
