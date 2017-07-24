create table WORKS_WORKS_ORDER_INGREDIENT (
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
    SEQUENCE_NO integer not null,
    RAW_MATERIAL_ID uuid not null,
    MASS decimal not null,
    KG_COST decimal not null,
    --
    primary key (ID)
);
