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
    -- from works$IntermediateOrder
    PRODUCT_ID uuid not null,
    MIXER_ID uuid not null,
    BATCH_QUANTITY integer not null,
    --
    -- from works$DecantingOrder
    DECANTED_PRODUCT_ID uuid,
    INFORMATION varchar(255),
    TARGET_VOLUME decimal(19, 2),
    --
    -- from works$WorksOrder
    PRODUCT_ID uuid,
    MIXER_ID uuid,
    BATCH_QUANTITY integer,
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
    CODE_NUMBER integer,
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
    --
    primary key (STOCK_ITEM_ID)
)^
-- end WORKS_CONTAINER
-- begin WORKS_RAW_MATERIAL
create table WORKS_RAW_MATERIAL (
    STOCK_ITEM_ID uuid,
    --
    SPEC_FILE_ID uuid,
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
    KEEP_AWAY boolean,
    PHYSICAL_FORM varchar(50) not null,
    SPEC_FILE_ID uuid,
    IS_FINAL boolean,
    --
    primary key (STOCK_ITEM_ID)
)^
-- end WORKS_PRODUCT
-- begin WORKS_LABLE
create table WORKS_LABLE (
    STOCK_ITEM_ID uuid,
    --
    SIZE_X integer,
    SIZE_Y integer,
    IMAGE_FILE_ID uuid,
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
    PARTS_PER100 decimal,
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

-- begin WORKS_DECANTING_ORDER_TARGET
create table WORKS_DECANTING_ORDER_TARGET (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DECANTING_ORDER_ID uuid not null,
    CONTAINER_ID uuid not null,
    UNIT_COST decimal not null,
    QUANTITY integer not null,
    CUSTOMERS_OWN boolean,
    ADDITIONAL boolean,
    LINE_CAPACITY decimal(19, 2),
    LINE_COST decimal,
    --
    primary key (ID)
)^
-- end WORKS_DECANTING_ORDER_TARGET
-- begin WORKS_SALES_ORDER_CONTAINER
create table WORKS_SALES_ORDER_CONTAINER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SALES_ORDER_ID uuid not null,
    CONTAINER_ID uuid not null,
    UNIT_PRICE decimal not null,
    QUANTITY decimal(19, 2) not null,
    --
    primary key (ID)
)^
-- end WORKS_SALES_ORDER_CONTAINER
-- begin WORKS_SALES_ORDER_RAW_MATERIAL
create table WORKS_SALES_ORDER_RAW_MATERIAL (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SALES_ORDER_ID uuid not null,
    RAW_MATERIAL_ID uuid not null,
    UNIT_PRICE decimal not null,
    QUANTITY decimal(19, 2) not null,
    --
    primary key (ID)
)^
-- end WORKS_SALES_ORDER_RAW_MATERIAL
-- begin WORKS_STOCK_COUNT
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
    CURRENT_STATUS varchar(50) not null,
    --
    primary key (ID)
)^
-- end WORKS_STOCK_COUNT
-- begin WORKS_STOCK_COUNT_ITEM
create table WORKS_STOCK_COUNT_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    STOCK_COUNT_ID uuid not null,
    STOCK_ITEM_ID uuid not null,
    CURRENT_VALUE decimal not null,
    COUNTED_QUANTITY decimal(19, 4) not null,
    CURRENT_QUANTITY decimal(19, 4) not null,
    --
    primary key (ID)
)^
-- end WORKS_STOCK_COUNT_ITEM
-- begin WORKS_STOCK_INTAKE
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
)^
-- end WORKS_STOCK_INTAKE
-- begin WORKS_STOCK_INTAKE_ITEM
create table WORKS_STOCK_INTAKE_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    STOCK_INTAKE_ID uuid not null,
    STOCK_ITEM_ID uuid not null,
    QUANTITY decimal(19, 2) not null,
    UNIT_PRICE decimal not null,
    --
    primary key (ID)
)^
-- end WORKS_STOCK_INTAKE_ITEM
-- begin WORKS_SYSTEM_KEY
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
)^
-- end WORKS_SYSTEM_KEY
-- begin WORKS_PRODUCT_CONTAINER
create table WORKS_PRODUCT_CONTAINER (
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
    CORROSIVE_LABEL_ID uuid,
    FLAMMABLE_LABEL_ID uuid,
    POISONOUS_LABEL_ID uuid,
    KEEP_AWAY_LABEL_ID uuid,
    PRODUCT_LABEL_ID uuid,
    --
    primary key (ID)
)^
-- end WORKS_PRODUCT_CONTAINER
-- begin WORKS_PRICE_UPDATE
create table WORKS_PRICE_UPDATE (
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
    CURRENT_STATUS varchar(50) not null,
    --
    primary key (ID)
)^
-- end WORKS_PRICE_UPDATE
-- begin WORKS_PRICE_UPDATE_ITEM
create table WORKS_PRICE_UPDATE_ITEM (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRICE_UPDATE_ID uuid not null,
    STOCK_ITEM_ID uuid not null,
    PRICE decimal not null,
    --
    primary key (ID)
)^
-- end WORKS_PRICE_UPDATE_ITEM
-- begin WORKS_STOCK_USAGE
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
)^
-- end WORKS_STOCK_USAGE
-- begin WORKS_PRICE_LIST
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
    RAW_MATERIAL_COST decimal not null,
    CONTAINER_COST decimal not null,
    OVERHEAD_COST decimal not null,
    LABEL_COST decimal not null,
    PRICE decimal not null,
    --
    primary key (ID)
)^
-- end WORKS_PRICE_LIST
-- begin WORKS_CALENDAR
create table WORKS_CALENDAR (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    THE_DATE date not null,
    ISO_DATE varchar(255) not null,
    MONTH_NAME varchar(255) not null,
    MONTH_NO integer not null,
    YEAR4 integer not null,
    YEAR2 integer not null,
    PERIOD varchar(255) not null,
    DT_START timestamp not null,
    DT_END timestamp not null,
    --
    primary key (ID)
)^
-- end WORKS_CALENDAR
-- begin WORKS_WORKS_ORDER_KEY
create table WORKS_WORKS_ORDER_KEY (
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
    MANUFACTURING_KEY_ID uuid not null,
    --
    primary key (ID)
)^
-- end WORKS_WORKS_ORDER_KEY
-- begin WORKS_INTERMEDIATE_ORDER_INGREDIENT
create table WORKS_INTERMEDIATE_ORDER_INGREDIENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    INTERMEDIATE_ORDER_ID uuid not null,
    SEQUENCE_NO integer not null,
    RAW_MATERIAL_ID uuid not null,
    MASS decimal not null,
    PARTS_PER100 decimal not null,
    --
    primary key (ID)
)^
-- end WORKS_INTERMEDIATE_ORDER_INGREDIENT

-- begin WORKS_INSTRUCTION
create table WORKS_INSTRUCTION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end WORKS_INSTRUCTION
