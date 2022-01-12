drop table if exists LEMON;
drop table if exists SEARCH_AUDIT;
create table LEMON(
    id number,
    GIS_LOCATION varchar,
    BATTERY_POWER number,
    TYPE varchar
);
create table SEARCH_AUDIT(
     SEARCH_GIS varchar,
     USERNAME varchar,
     NO_RESULTS number,
    DATE date
);

INSERT INTO LEMON VALUES ( 1,'abc',1000, 'LARGE' );