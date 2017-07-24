create table WORKS_CONTAINER (
    STOCK_ITEM_ID uuid,
    --
    CAPACITY decimal(19, 2) not null,
    COST_PER_UNIT decimal not null,
    --
    primary key (STOCK_ITEM_ID)
);
