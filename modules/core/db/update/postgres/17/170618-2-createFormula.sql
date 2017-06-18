alter table WORKS_FORMULA add constraint FK_WORKS_FORMULA_PRODUCT foreign key (PRODUCT_ID) references WORKS_PRODUCT(STOCK_ITEM_ID);
alter table WORKS_FORMULA add constraint FK_WORKS_FORMULA_RAW_MATERIAL foreign key (RAW_MATERIAL_ID) references WORKS_RAW_MATERIAL(STOCK_ITEM_ID);
create unique index IDX_WORKS_FORMULA_UNQ on WORKS_FORMULA (PRODUCT_ID, SEQUENCE_NO) where DELETE_TS is null ;
create index IDX_WORKS_FORMULA_PRODUCT on WORKS_FORMULA (PRODUCT_ID);
create index IDX_WORKS_FORMULA_RAW_MATERIAL on WORKS_FORMULA (RAW_MATERIAL_ID);
