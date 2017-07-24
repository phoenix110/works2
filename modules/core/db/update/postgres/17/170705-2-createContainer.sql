alter table WORKS_CONTAINER add constraint FK_WORKS_CONTAINER_STOCK_ITEM foreign key (STOCK_ITEM_ID) references WORKS_STOCK_ITEM(ID);
