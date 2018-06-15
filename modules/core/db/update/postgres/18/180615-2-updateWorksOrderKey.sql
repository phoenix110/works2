alter table WORKS_WORKS_ORDER_KEY rename column manufacturing_key to manufacturing_key__u83372 ;
alter table WORKS_WORKS_ORDER_KEY alter column manufacturing_key__u83372 drop not null ;
-- alter table WORKS_WORKS_ORDER_KEY add column MANUFACTURING_KEY_ID uuid ^
-- update WORKS_WORKS_ORDER_KEY set MANUFACTURING_KEY_ID = <default_value> ;
-- alter table WORKS_WORKS_ORDER_KEY alter column MANUFACTURING_KEY_ID set not null ;
alter table WORKS_WORKS_ORDER_KEY add column MANUFACTURING_KEY_ID uuid not null ;
