alter table WORKS_PRODUCT add column PHYSICAL_FORM varchar(50) ^
update WORKS_PRODUCT set PHYSICAL_FORM = 'L' where PHYSICAL_FORM is null ;
alter table WORKS_PRODUCT alter column PHYSICAL_FORM set not null ;
