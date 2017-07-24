create table WORKS_ORDER (
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
    DOCUMENT_NO varchar(10),
    DOCUMENT_ON timestamp not null,
    DESCRIPTION varchar(255) not null,
    UNIT varchar(50) not null,
    VOLUME decimal not null,
    MASS decimal(19, 2) not null,
    RAW_MATERIAL_COST decimal not null,
    CONTAINER_COST decimal not null,
    LABLE_COST decimal not null,
    OVERHEAD_COST decimal not null,
    CURRENT_STATUS varchar(50) not null,
    --
    -- from works$SalesOrder
    INVOICE_NO varchar(255),
    --
    -- from works$DecantingOrder
    DECANTED_PRODUCT_ID uuid not null,
    SOURCE_VOLUME decimal(19, 2),
    TARGET_VOLUME decimal(19, 2),
    --
    -- from works$WorksOrder
    PRODUCT_ID uuid,
    MIXER_ID uuid,
    BATCH_QUANTITY integer,
    MANUFACTURING_KEY varchar(50),
    --
    primary key (ID)
);
