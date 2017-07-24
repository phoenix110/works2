alter table WORKS_LABLE add constraint FK_WORKS_LABLE_IMAGE_FILE foreign key (IMAGE_FILE_ID) references SYS_FILE(ID);
alter table WORKS_LABLE add constraint FK_WORKS_LABLE_STOCK_ITEM foreign key (STOCK_ITEM_ID) references WORKS_STOCK_ITEM(ID);
create index IDX_WORKS_LABLE_IMAGE_FILE on WORKS_LABLE (IMAGE_FILE_ID);
