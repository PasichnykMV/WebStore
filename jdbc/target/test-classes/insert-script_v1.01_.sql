
INSERT INTO users (role_id,email,password,name,last_name,creation_date,user_id,is_enable)
VALUES (1,'testuser1@gmail.com','user1','Test1','Test1ovich','2015-01-12',1,TRUE );
INSERT INTO users (role_id,email,password,name,last_name,creation_date,user_id,is_enable)
VALUES (1,'testuser2@gmail.com','user2','Test2','Test2ovich','2015-01-12',2,TRUE );
INSERT INTO users (role_id,email,password,name,last_name,creation_date,user_id,is_enable)
VALUES (1,'testuser3@gmail.com','user3','Test3','Test3ovich','2015-01-12',3,TRUE );
SELECT setval('users_user_id_seq', 3);
