create table WORKS_MIXER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(25) not null,
    MIN_LOAD integer not null,
    MAX_LOAD integer not null,
    UNIT varchar(50) not null,
    CURRENT_STATUS varchar(50) not null,
    PHYSICAL_FORM varchar(50) not null,
    --
    primary key (ID)
);
