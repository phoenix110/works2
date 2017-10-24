create table WORKS_STOCK_USAGE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    STOCK_ITEM_ID uuid not null,
    USED_ON date not null,
    QUANTITY decimal not null,
    --
    primary key (ID)
);
