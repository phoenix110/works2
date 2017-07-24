create table WORKS_WORKS_ORDER_PACKING (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    WORKS_ORDER_ID uuid not null,
    CONTAINER_ID uuid not null,
    QUANTITY integer not null,
    UNIT_COST decimal not null,
    CUSTOMERS_OWN boolean,
    ADDITIONAL boolean,
    --
    primary key (ID)
);
