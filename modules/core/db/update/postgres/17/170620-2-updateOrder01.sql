alter table WORKS_ORDER add column SOURCE_VOLUME decimal(19, 2) ^
update WORKS_ORDER set SOURCE_VOLUME = 0 where SOURCE_VOLUME is null ;
alter table WORKS_ORDER alter column SOURCE_VOLUME set not null ;
alter table WORKS_ORDER add column TARGET_VOLUME decimal(19, 2) ^
update WORKS_ORDER set TARGET_VOLUME = 0 where TARGET_VOLUME is null ;
alter table WORKS_ORDER alter column TARGET_VOLUME set not null ;
