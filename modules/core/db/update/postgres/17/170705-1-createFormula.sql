create table WORKS_FORMULA (
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
    SEQUENCE_NO integer not null,
    RAW_MATERIAL_ID uuid not null,
    PARTS_PER100 decimal not null,
    --
    primary key (ID)
);
