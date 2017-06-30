alter table WORKS_LABLE add constraint FK_WORKS_LABLE_IMAGE_FILE foreign key (IMAGE_FILE_ID) references SYS_FILE(ID);
create index IDX_WORKS_LABLE_IMAGE_FILE on WORKS_LABLE (IMAGE_FILE_ID);
