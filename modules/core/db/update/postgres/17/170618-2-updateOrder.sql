-- alter table WORKS_ORDER add column PRODUCT_ID uuid ^
-- update WORKS_ORDER set PRODUCT_ID = <default_value> ;
-- alter table WORKS_ORDER alter column PRODUCT_ID set not null ;
alter table WORKS_ORDER add column PRODUCT_ID uuid not null ;
alter table WORKS_ORDER drop column PRODUCT cascade ;
