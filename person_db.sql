CREATE TABLE Persons (
                         PersonID int,
                         LastName varchar(255),
                         FirstName varchar(255),
                         Address varchar(255),
                         City varchar(255)
);

INSERT INTO Persons (PersonID, LastName, FirstName, Address, City) values (1, 'Crone', 'Todd', 'Somewhere', 'Fuquay');

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