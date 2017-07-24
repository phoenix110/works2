create table WORKS_PRICE_UPDATE_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRICE_UPDATE_ID uuid not null,
    STOCK_ITEM_ID uuid not null,
    PRICE decimal not null,
    --
    primary key (ID)
);
