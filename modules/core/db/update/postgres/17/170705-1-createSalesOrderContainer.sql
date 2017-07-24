create table WORKS_SALES_ORDER_CONTAINER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SALES_ORDER_ID uuid not null,
    CONTAINER_ID uuid not null,
    UNIT_PRICE decimal not null,
    QUANTITY decimal(19, 2) not null,
    --
    primary key (ID)
);
