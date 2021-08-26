CREATE SCHEMA `critter` ;

CREATE USER 'sa'@'localhost' IDENTIFIED BY 'udacity';
GRANT ALL ON critter.* TO 'sa'@'localhost';