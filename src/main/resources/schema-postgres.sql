CREATE TABLE IF NOT EXISTS satellite_com (
    name        varchar(100)    primary key,
    distance    numeric(6, 2)   not null,
    message     varchar(50)[]   not null
);
