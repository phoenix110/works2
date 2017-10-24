create table WORKS_PRICE_LIST (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID uuid not null,
    CONTAINER_ID uuid not null,
    PRICE_ON date not null,
    PRICE decimal not null,
    --
    primary key (ID)
);
