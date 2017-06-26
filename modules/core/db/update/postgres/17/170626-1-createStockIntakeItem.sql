create table WORKS_STOCK_INTAKE_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    STOCK_INTAKE_ID uuid not null,
    STOCK_ITEM_ID uuid not null,
    QUANTITY decimal(19, 2) not null,
    UNIT_PRICE decimal not null,
    --
    primary key (ID)
);
