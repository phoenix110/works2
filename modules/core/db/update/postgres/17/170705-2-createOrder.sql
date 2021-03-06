alter table WORKS_ORDER add constraint FK_WORKS_ORDER_PRODUCT foreign key (PRODUCT_ID) references WORKS_PRODUCT(STOCK_ITEM_ID);
alter table WORKS_ORDER add constraint FK_WORKS_ORDER_MIXER foreign key (MIXER_ID) references WORKS_MIXER(ID);
alter table WORKS_ORDER add constraint FK_WORKS_ORDER_DECANTED_PRODUCT foreign key (DECANTED_PRODUCT_ID) references WORKS_PRODUCT(STOCK_ITEM_ID);
create unique index IDX_WORKS_ORDER_UK_DOCUMENT_NO on WORKS_ORDER (DOCUMENT_NO) where DELETE_TS is null ;
create index IDX_WORKS_ORDER_PRODUCT on WORKS_ORDER (PRODUCT_ID);
create index IDX_WORKS_ORDER_MIXER on WORKS_ORDER (MIXER_ID);
create index IDX_WORKS_ORDER_DECANTED_PRODUCT on WORKS_ORDER (DECANTED_PRODUCT_ID);
