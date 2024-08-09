INSERT INTO _USER (_name, email, password) values('test', 'test@test.com', '{bcrypt}$2a$10$bOaj5ooeViWvQf89qK3qNel4ZP1MPxlx/PyHy8J2X6Tl9blcPuA6a');
INSERT INTO ROLE values('ADMIN', 'Administrator');
INSERT INTO ROLE values('END_USER', 'End User');
INSERT INTO USER_ROLE values(1, 'ADMIN');