alter table WORKS_MIXER add column PHYSICAL_FORM varchar(50) ^
update WORKS_MIXER set PHYSICAL_FORM = 'L' where PHYSICAL_FORM is null ;
alter table WORKS_MIXER alter column PHYSICAL_FORM set not null ;
