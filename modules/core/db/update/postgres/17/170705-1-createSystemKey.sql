create table WORKS_SYSTEM_KEY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CONTEXT varchar(255) not null,
    KEY_ varchar(255) not null,
    VALUE_ varchar(255),
    --
    primary key (ID)
);
