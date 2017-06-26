alter table WORKS_ORDER drop column DOCUMENT_ON cascade ;
alter table WORKS_ORDER add column DOCUMENT_ON timestamp ^
update WORKS_ORDER set DOCUMENT_ON = current_timestamp where DOCUMENT_ON is null ;
alter table WORKS_ORDER alter column DOCUMENT_ON set not null ;
