-- begin WORKS_CATEGORY
create table WORKS_CATEGORY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(10) not null,
    DESCRIPTION varchar(255) not null,
    TAGS varchar(255),
    --
    primary key (ID)
)^
-- end WORKS_CATEGORY
-- begin WORKS_ORDER
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
    DOCUMENT_ON date not null,
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
    -- from works$WorksOrder
    PRODUCT_ID uuid not null,
    MIXER_ID uuid not null,
    BATCH_QUANTITY integer not null,
    MANUFACTURING_KEY varchar(50),
    --
    primary key (ID)
)^
-- end WORKS_ORDER
-- begin WORKS_STOCK_ITEM
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
)^
-- end WORKS_STOCK_ITEM
-- begin WORKS_MIXER
create table WORKS_MIXER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(25) not null,
    MIN_LOAD integer not null,
    MAX_LOAD integer not null,
    UNIT varchar(50) not null,
    CURRENT_STATUS varchar(50) not null,
    PHYSICAL_FORM varchar(50) not null,
    --
    primary key (ID)
)^
-- end WORKS_MIXER
-- begin WORKS_CONTAINER
create table WORKS_CONTAINER (
    STOCK_ITEM_ID uuid,
    --
    CAPACITY decimal(19, 2) not null,
    COST_PER_UNIT decimal not null,
    --
    primary key (STOCK_ITEM_ID)
)^
-- end WORKS_CONTAINER
-- begin WORKS_RAW_MATERIAL
create table WORKS_RAW_MATERIAL (
    STOCK_ITEM_ID uuid,
    --
    COST decimal not null,
    --
    primary key (STOCK_ITEM_ID)
)^
-- end WORKS_RAW_MATERIAL
-- begin WORKS_PRODUCT
create table WORKS_PRODUCT (
    STOCK_ITEM_ID uuid,
    --
    SPECIFIC_GRAVITY decimal(19, 4) not null,
    APPLY_OVERHEAD boolean not null,
    CATEGORY_ID uuid not null,
    IS_CORROSIVE boolean,
    IS_FLAMMABLE boolean,
    IS_POISONOUS boolean,
    PHYSICAL_FORM varchar(50) not null,
    --
    primary key (STOCK_ITEM_ID)
)^
-- end WORKS_PRODUCT
-- begin WORKS_LABLE
create table WORKS_LABLE (
    STOCK_ITEM_ID uuid,
    --
    UNIT_COST decimal not null,
    --
    primary key (STOCK_ITEM_ID)
)^
-- end WORKS_LABLE
-- begin WORKS_WORKS_ORDER_PACKING
create table WORKS_WORKS_ORDER_PACKING (
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
    CONTAINER_ID uuid not null,
    QUANTITY integer not null,
    UNIT_COST decimal not null,
    CUSTOMERS_OWN boolean,
    ADDITIONAL boolean,
    --
    primary key (ID)
)^
-- end WORKS_WORKS_ORDER_PACKING
-- begin WORKS_WORKS_ORDER_INGREDIENT
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
)^
-- end WORKS_WORKS_ORDER_INGREDIENT
-- begin WORKS_WORKS_ORDER_LABLE
create table WORKS_WORKS_ORDER_LABLE (
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
    LABLE_ID uuid not null,
    QUANTITY integer not null,
    UNIT_COST decimal not null,
    --
    primary key (ID)
)^
-- end WORKS_WORKS_ORDER_LABLE
-- begin WORKS_FORMULA
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
)^
-- end WORKS_FORMULA
