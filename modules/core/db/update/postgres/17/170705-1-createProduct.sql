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
    SPEC_FILE_ID uuid,
    --
    primary key (STOCK_ITEM_ID)
);
