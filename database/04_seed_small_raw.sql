-- ADMIN
INSERT INTO admin (email, first_name, last_name, is_email_verified, role) VALUES
('admin1@gmail.com','An','Nguyen',true,'SUPER_ADMIN'),
('admin2@gmail.com','Binh','Tran',true,'ADMIN'),
('admin3@gmail.com','Chi','Le',false,'ADMIN'),
('admin4@gmail.com','Dung','Pham',true,'MODERATOR'),
('admin5@gmail.com','Huy','Vo',false,'ADMIN');

-- USER
INSERT INTO user (current_job,email,address,password,first_name,last_name,status,sex,time_zone,country,is_email_verified) VALUES
('Developer','user1@gmail.com','HN','123','Nam','Nguyen','active','male','GMT+7','Vietnam',true),
('Tester','user2@gmail.com','HCM','123','Lan','Tran','active','female','GMT+7','Vietnam',true),
('Manager','user3@gmail.com','DN','123','Hoa','Le','inactive','female','GMT+7','Vietnam',false),
('Student','user4@gmail.com','Hue','123','Tuan','Pham','pending','male','GMT+7','Vietnam',false),
('Designer','user5@gmail.com','CT','123','Mai','Vo','banned','female','GMT+7','Vietnam',true);

-- MANAGE
INSERT INTO manage VALUES
(1,1),(2,1),(3,2),(4,3),(5,4);

-- USER PHONE
INSERT INTO user_phone_number VALUES
(1,'0900000001'),
(2,'0900000002'),
(3,'0900000003'),
(4,'0900000004'),
(5,'0900000005');

-- MENTEE
INSERT INTO mentee VALUES
(1,'Learn Java','Beginner'),
(2,'Learn Python','Intermediate'),
(3,'Learn AI','Advanced'),
(4,'Learn SQL','Beginner'),
(5,'Learn React','Intermediate');

-- MENTOR
INSERT INTO mentor VALUES
(1,'Java',5,4.5),
(2,'Python',6,4.7),
(3,'AI',8,4.9),
(4,'SQL',4,4.3),
(5,'React',7,4.8);

-- MESSAGE
INSERT INTO message (mentor_id, mentee_id, content, sender_role) VALUES
(1,2,'Hello','mentor'),
(2,3,'Hi','mentee'),
(3,4,'Guide','mentor'),
(4,5,'Help','mentee'),
(5,1,'Welcome','mentor');

-- RATE
INSERT INTO rate VALUES
(1,2,'Good',5,'2024-01-01'),
(2,3,'Nice',4,'2024-01-02'),
(3,4,'Excellent',5,'2024-01-03'),
(4,5,'Ok',3,'2024-01-04'),
(5,1,'Perfect',5,'2024-01-05');

-- CATEGORY
INSERT INTO category VALUES
(1,'Programming',NULL),
(2,'Web',1),
(3,'AI',1),
(4,'Database',1),
(5,'Mobile',1);

-- SKILLS
INSERT INTO skills VALUES
(1,'Java'),
(2,'Python'),
(3,'SQL'),
(4,'React'),
(5,'AI');

-- SKILL CATEGORY
INSERT INTO skill_category VALUES
(1,1),(2,3),(3,4),(4,2),(5,3);

-- MENTOR SKILLS
INSERT INTO mentor_skills VALUES
(1,1),(2,2),(3,5),(4,3),(5,4);

-- COMPANY
INSERT INTO company VALUES
(1,'FPT'),
(2,'VNG'),
(3,'Viettel'),
(4,'VNPT'),
(5,'Tiki');

-- WORK FOR
INSERT INTO work_for VALUES
(1,1,'Dev','2020-01-01','2022-01-01'),
(2,2,'Tester','2019-01-01','2021-01-01'),
(3,3,'AI Eng','2018-01-01','2022-01-01'),
(4,4,'DBA','2021-01-01','2023-01-01'),
(5,5,'Frontend','2020-06-01','2022-06-01');

-- AVAILABLE TIME
INSERT INTO available_time VALUES
(1,'2024-01-01','2024-12-31','08:00','10:00'),
(2,'2024-01-01','2024-12-31','09:00','11:00'),
(3,'2024-01-01','2024-12-31','10:00','12:00'),
(4,'2024-01-01','2024-12-31','13:00','15:00'),
(5,'2024-01-01','2024-12-31','14:00','16:00');

-- SESSION
INSERT INTO session (mentor_id, description, session_name, duration, price) VALUES
(1,'Java basic','Java 101',1,100),
(2,'Python basic','Python 101',1,120),
(3,'AI intro','AI 101',2,200),
(4,'SQL basic','SQL 101',1,90),
(5,'React basic','React 101',1,110);

-- DISCOUNT
INSERT INTO discount VALUES
(1,'New Year',10,'2024-01-01','2024-01-31','NY10','active'),
(2,'Summer',15,'2024-06-01','2024-06-30','SUM15','active'),
(3,'Sale1',20,'2024-02-01','2024-02-28','S20','inactive'),
(4,'Sale2',25,'2024-03-01','2024-03-31','S25','active'),
(5,'VIP',30,'2024-01-01','2024-12-31','VIP30','active');

-- MENTEE SESSION
INSERT INTO mentee_session (mentee_id, session_id, discount_id, date, start_time, end_time, link) VALUES
(1,1,1,'2024-01-10','08:00','09:00','link1'),
(2,2,2,'2024-01-11','09:00','10:00','link2'),
(3,3,3,'2024-01-12','10:00','12:00','link3'),
(4,4,4,'2024-01-13','13:00','14:00','link4'),
(5,5,5,'2024-01-14','14:00','15:00','link5');

-- PAYMENT
INSERT INTO payment (mentee_session_id, date, total_money, status) VALUES
(1,'2024-01-10',90,'paid'),
(2,'2024-01-11',100,'paid'),
(3,'2024-01-12',180,'pending'),
(4,'2024-01-13',80,'paid'),
(5,'2024-01-14',100,'failed');

-- WISHLIST
INSERT INTO wishlist VALUES
(1,1,'List1'),
(2,2,'List2'),
(3,3,'List3'),
(4,4,'List4'),
(5,5,'List5');

-- WISHLIST SESSION
INSERT INTO wishlist_session VALUES
(1,1),(2,2),(3,3),(4,4),(5,5);

-- NOTIFICATION
INSERT INTO notification (admin_id,user_id,title,content,date,time) VALUES
(1,1,'Hello','Welcome','2024-01-01','08:00'),
(2,2,'Update','System update','2024-01-02','09:00'),
(3,3,'Alert','Check account','2024-01-03','10:00'),
(4,4,'Info','New mentor','2024-01-04','11:00'),
(5,5,'Promo','Discount','2024-01-05','12:00');