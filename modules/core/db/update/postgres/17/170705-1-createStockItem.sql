create table WORKS_STOCK_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    DTYPE varchar(31),
    --
    CODE varchar(30) not null,
    DESCRIPTION varchar(100),
    REORDER decimal(19, 2) not null,
    MAX_STOCK decimal(19, 2) not null,
    UNIT varchar(50) not null,
    CURRENT_STATUS varchar(50) not null,
    --
    primary key (ID)
);
