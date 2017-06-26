create table WORKS_STOCK_COUNT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOCUMENT_NO varchar(255) not null,
    DOCUMENT_ON timestamp not null,
    DESCRIPTION varchar(255),
    --
    primary key (ID)
);
