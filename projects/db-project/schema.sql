.mode tabs

-- database of all available classes & detailed info
drop table if exists class;
create table if not exists class(
    classID string,
    className string,
    AG string,
    credits integer,
    prereq string,
    primary key (classID)
);

-- A-G requirements:
-- A: Social science
-- B: English
-- C: Math
-- D: Lab science
-- E: Foreign language
-- F: Visual/performing arts
-- G: Elective
-- H: Physical education

-- represents a single class period for a single person
drop table if exists course;
create table if not exists course(
    classID string,
    studentID integer,
    period integer,
    foreign key (classID) references class(classID),
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