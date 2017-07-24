alter table WORKS_PRICE_UPDATE add column CURRENT_STATUS varchar(50) ^
update WORKS_PRICE_UPDATE set CURRENT_STATUS = 'N' where CURRENT_STATUS is null ;
alter table WORKS_PRICE_UPDATE alter column CURRENT_STATUS set not null ;
alter table WORKS_PRICE_UPDATE drop column DOCUMENT_STATUS cascade ;
