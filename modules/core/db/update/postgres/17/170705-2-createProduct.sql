alter table WORKS_PRODUCT add constraint FK_WORKS_PRODUCT_CATEGORY foreign key (CATEGORY_ID) references WORKS_CATEGORY(ID);
alter table WORKS_PRODUCT add constraint FK_WORKS_PRODUCT_SPEC_FILE foreign key (SPEC_FILE_ID) references SYS_FILE(ID);
alter table WORKS_PRODUCT add constraint FK_WORKS_PRODUCT_STOCK_ITEM foreign key (STOCK_ITEM_ID) references WORKS_STOCK_ITEM(ID);
create index IDX_WORKS_PRODUCT_CATEGORY on WORKS_PRODUCT (CATEGORY_ID);
create index IDX_WORKS_PRODUCT_SPEC_FILE on WORKS_PRODUCT (SPEC_FILE_ID);
