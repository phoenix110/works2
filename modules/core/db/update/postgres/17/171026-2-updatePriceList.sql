alter table WORKS_PRICE_LIST add column RAW_MATERIAL_COST decimal ^
alter table WORKS_PRICE_LIST alter column RAW_MATERIAL_COST set not null ;
alter table WORKS_PRICE_LIST add column CONTAINER_COST decimal ^
alter table WORKS_PRICE_LIST alter column CONTAINER_COST set not null ;
alter table WORKS_PRICE_LIST add column OVERHEAD_COST decimal ^
alter table WORKS_PRICE_LIST alter column OVERHEAD_COST set not null ;
alter table WORKS_PRICE_LIST add column LABEL_COST decimal ^
alter table WORKS_PRICE_LIST alter column LABEL_COST set not null ;
