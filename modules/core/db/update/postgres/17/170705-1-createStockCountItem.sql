create table WORKS_STOCK_COUNT_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    STOCK_COUNT_ID uuid not null,
    STOCK_ITEM_ID uuid not null,
    CURRENT_VALUE decimal not null,
    COUNTED_QUANTITY decimal(19, 2) not null,
    CURRENT_QUANTITY decimal(19, 2) not null,
    --
    primary key (ID)
);
