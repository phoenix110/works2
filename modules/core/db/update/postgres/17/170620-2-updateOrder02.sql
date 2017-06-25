alter table WORKS_ORDER add column INVOICE_NO varchar(255) ^
update WORKS_ORDER set INVOICE_NO = '' where INVOICE_NO is null ;
alter table WORKS_ORDER alter column INVOICE_NO set not null ;
