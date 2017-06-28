alter table WORKS_STOCK_COUNT add column CURRENT_STATUS varchar(50) ^
update WORKS_STOCK_COUNT set CURRENT_STATUS = 'N' where CURRENT_STATUS is null ;
alter table WORKS_STOCK_COUNT alter column CURRENT_STATUS set not null ;
