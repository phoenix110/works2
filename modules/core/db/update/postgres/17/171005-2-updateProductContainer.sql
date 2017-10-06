-- update WORKS_PRODUCT_CONTAINER set PRODUCT_ID = <default_value> where PRODUCT_ID is null ;
alter table WORKS_PRODUCT_CONTAINER alter column PRODUCT_ID set not null ;
-- update WORKS_PRODUCT_CONTAINER set CONTAINER_ID = <default_value> where CONTAINER_ID is null ;
alter table WORKS_PRODUCT_CONTAINER alter column CONTAINER_ID set not null ;
