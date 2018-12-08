alter table WORKS_PACKING add constraint FK_WORKS_PACKING_ON_STOCK_ITEM foreign key (STOCK_ITEM_ID) references WORKS_STOCK_ITEM(ID) on delete CASCADE;
