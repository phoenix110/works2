alter table WORKS_DECANTING_ORDER_TARGET drop column LINE_CAPACITY cascade ;
alter table WORKS_DECANTING_ORDER_TARGET add column LINE_CAPACITY decimal(19, 2) ;
