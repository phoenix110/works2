create table WORKS_DECANTING_ORDER_TARGET (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DECANTING_ORDER_ID uuid not null,
    CONTAINER_ID uuid not null,
    QUANTITY integer not null,
    --
    primary key (ID)
);
