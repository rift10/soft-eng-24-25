.mode tabs

-- database of all available classes & extra info
drop table if exists classes;
create table if not exists classes(
    classID string,
    name string,
    AG string,
    prereq string,
    primary key (classID)
);

-- represents a single class period for a single person
drop table if exists course;
create table if not exists course(
    classID string,
    studentID integer,
    period integer,
    foreign key (classID) references classes(classID),
    foreign key (studentID) references student(studentID)
);

-- database of all students
drop table if exists student;
create table if not exists student(
    studentID integer,
    name string,
    DOB string,
    classOf integer,
    slc string,
    primary key (studentID)
);