.mode tabs;

drop table if exists classes;
create table if not exists classes(
    classID integer,
    name string,
    AG string,
    grades integer,
    slc string,
    prereq string
    );

drop table if exists schedule;
create table if not exists schedule(
    studentID integer,
    zeroID string,
    firstID string, 
    secondID string,
    thirdID string,
    fourthID string,
    fifthID string,
    sixthID string,
    seventhID string,
    extID string
    );

drop table if exists student;
create table if not exists student(
    id integer,
    name string,
    grade integer,
    slc string
    );