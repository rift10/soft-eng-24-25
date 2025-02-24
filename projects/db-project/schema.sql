.mode tabs

drop table if exists classes;
create table if not exists classes(
    classID string,
    name string,
    AG string,
    grades integer,
    slc string,
    prereq string,
    primary key (classID)
    );

drop table if exists schedule;
create table if not exists schedule(
    studentID integer,
    grade integer,
    zeroID string,
    firstID string, 
    secondID string,
    thirdID string,
    fourthID string,
    fifthID string,
    sixthID string,
    seventhID string,
    extID string,
    foreign key (zeroID) references classes(classID),
    foreign key (firstID) references classes(classID),
    foreign key (secondID) references classes(classID),
    foreign key (thirdID) references classes(classID),
    foreign key (fourthID) references classes(classID),
    foreign key (fifthID) references classes(classID),
    foreign key (sixthID) references classes(classID),
    foreign key (seventhID) references classes(classID),
    foreign key (extID) references classes(classID),
    foreign key (studentID) references student(studentID)
    );

drop table if exists student;
create table if not exists student(
    studentID integer,
    name string,
    DOB string,
    classOf string,
    slc string,
    primary key (studentID)
    );