CREATE TABLE IF NOT EXISTS satellite (
    name        varchar(100)    primary key,
    position_x  integer         not null,
    position_y  integer         not null
);

CREATE TABLE IF NOT EXISTS satellite_com (
    satellite   varchar(100)    primary key unique references satellite,
    received_at timestamp       not null,
    distance    numeric(6, 2)   not null,
    message     varchar(50)[]   not null
);
