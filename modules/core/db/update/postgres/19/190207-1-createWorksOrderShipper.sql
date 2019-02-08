create table WORKS_WORKS_ORDER_SHIPPER (
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
    PACKING_ID uuid not null,
    QUANTITY decimal(19, 2) not null,
    UNIT_COST decimal not null,
    --
    primary key (ID)
);
