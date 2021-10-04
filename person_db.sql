CREATE TABLE Persons (
                         PersonID int,
                         LastName varchar(255),
                         FirstName varchar(255),
                         Address varchar(255),
                         City varchar(255)
);

INSERT INTO Persons (PersonID, LastName, FirstName, Address, City) values (1, 'Crone', 'Todd', 'Somewhere', 'Fuquay');
INSERT INTO Persons (PersonID, LastName, FirstName, Address, City) values (2, 'Onuki', 'André', 'Somewhere', 'Virginia');
INSERT INTO Persons (PersonID, LastName, FirstName, Address, City) values (3, 'Ellis', 'Brad', 'Somewhere', 'Portland');
INSERT INTO Persons (PersonID, LastName, FirstName, Address, City) values (4, 'Keller', 'Jason', 'Somewhere', 'Portland');
INSERT INTO Persons (PersonID, LastName, FirstName, Address, City) values (5, 'Xia', 'Xi', 'Somewhere', 'Portland');
INSERT INTO Persons (PersonID, LastName, FirstName, Address, City) values (6, 'Ford', 'Kevyn', 'Somewhere', 'Jersey');

select * from Persons;

select id,
       user,
       host,
       db,
       command,
    time,
    state,
    info
from information_schema.processlist;