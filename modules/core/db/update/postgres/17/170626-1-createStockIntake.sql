create table WORKS_STOCK_INTAKE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SUPPLIER varchar(255) not null,
    DOCUMENT_NO varchar(255) not null,
    DOCUMENT_ON date not null,
    CURRENT_STATUS varchar(50) not null,
    --
    primary key (ID)
);
