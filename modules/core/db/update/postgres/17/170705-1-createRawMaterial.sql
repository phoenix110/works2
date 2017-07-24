create table WORKS_RAW_MATERIAL (
    STOCK_ITEM_ID uuid,
    --
    COST decimal not null,
    SPEC_FILE_ID uuid,
    --
    primary key (STOCK_ITEM_ID)
);
