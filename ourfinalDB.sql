/* STEP 0: clean everything that could be related to lab4 */
USE lab4;
DROP USER 'mysql'@'localhost';
DROP DATABASE IF EXISTS lab4;
FLUSH PRIVILEGES;


/* STEP 1: now we create DB and user needed for lab4*/
CREATE USER 'mysql'@'localhost' IDENTIFIED BY 'prac';
GRANT ALL PRIVILEGES ON *.* TO 'mysql'@'localhost';
CREATE DATABASE lab4;
USE lab4;

/* STEP 2: creating all the needed tables */
DROP TABLE IF EXISTS User;
CREATE TABLE User(
  id int(10) not null AUTO_INCREMENT,
  username varchar(255) not null,
  `name` varchar(255) not null,
  surname varchar(255) not null,
  mail varchar(255) not null,
  tel varchar(255) not null,
  dob date not null,
  pwd varchar(255) not null,
  /*usertype possible values: 'Administrator‘, 'Common', ‘Brand‘*/
  usertype varchar(255) not null,
  about varchar(255) not null,
  primary key(id),
  unique key username(username)
);

DROP TABLE IF EXISTS Following;
CREATE TABLE Following(
  userId int(10) not null,
  followedId int(10) not null,
  primary key(userId,followedId),
  foreign key (userId) references User(id),
  foreign key (followedId) references User(id)
);

DROP TABLE IF EXISTS Tweet;
CREATE TABLE Tweet(
  id int(10) not null AUTO_INCREMENT,
  userId int(10) default null,
  `date` timestamp null default null,
  text varchar(150) default null,
  postId int(10) default null,
  countLikes int(10) default 0,
  countHashtag int(10) default 0,
  countComment int(10) default 0,
  primary key(id),
  foreign key (userId) references User(id)
);

ALTER TABLE Tweet ADD   foreign key (postId) references Tweet(id);


DROP TABLE IF EXISTS TweetLikes;
CREATE TABLE TweetLikes(
  userId int(10) not null,
  postId int(10) not null,
  primary key (userId,postId),
  foreign key (userId) references User(id),
  foreign key (postId) references Tweet(id)
);

-- ESTA ES LA ÚNICA TABLA QUE ELLOS NO TIENEN Y NOSOTROS TENÍAMOS DE MÁS
/*
CREATE TABLE Comment (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
text VARCHAR(255),
tweet int,
author int,
Foreign key (tweet) references Tweet(id),
Foreign key (author) references User(id)
);
*/

/* ADMINISTRATORS (US) WILL BE ADD BY DEFAULT */
INSERT INTO User (username, `name`, surname, mail, tel, dob, pwd, usertype, about)
VALUES 
  ('marinasb28', 'Marina', 'Suárez', 'marina@gmail.com', '123456789', '2001-07-28', 'Marin@2', 'Administrator', "Hey, what's up?"),
  ('arne_be', 'Arne', 'Berrseheim', 'arne@gmail.com', '123123123', '2000-10-01', 'Hello@123', 'Administrator', "I'm finnaly done with my TFG"),
  ('mariacp3', 'Maria', 'Cerezo','maria@gmail.com', '672182625', '2000-10-01', 'Mari@3!', 'Administrator','Good morning everybody!'),
  ("claudia_quera", "Claudia", "Quera","claudia@gmail.com", "612345678",'2001-04-07', "Claudi@1", "Administrator", "I love basketball!"),
  
  ('randomUser1', 'Max', 'Mustermann', 'max@gmail.com', '123456123', '2000-01-01', 'Password@123', 'Common', 'Im here and using WhatsApp.'),
  ('randomUser2', 'Maxime', 'Muster', 'maxime@gmail.com', '123456124', '2000-01-01', 'Password@123', 'Common', 'Im here and using WhatsApp.'),
  ('randomUser3', 'Theo', 'Mann', 'theo@gmail.com', '123456125', '2000-01-01', 'Password@123', 'Common', 'Im here and using WhatsApp.');

/* NOW WE INSERT SOME DEFAULT DATA (tweets, comments...)*/
INSERT INTO Tweet (userId, `date`, text, postId, countLikes, countHashtag, countComment)
VALUES 
	(5, '2023-05-03', 'Hello World!',  null, 0, 0, 0),
    (4, '2023-05-03', 'Hello World!',  null, 0, 0, 0),
    (2, '2023-05-03', 'Hello World too!',  1, 0, 0, 0);
    

/* WE CHECK ALL THE DATA */
select * from User;
select * from Tweet;
