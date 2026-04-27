USE [mentor_mentee];
GO

SET IDENTITY_INSERT [admin] ON;
INSERT INTO [admin] (admin_id, email, password, first_name, last_name, role, is_email_verified) VALUES
(1, N'linh.tran@mentorhub.com', N'$2y$10$adminhash01', N'Linh', N'Trần', N'SuperAdmin', 1),
(2, N'minh.nguyen@mentorhub.com', N'$2y$10$adminhash02', N'Minh', N'Nguyễn', N'SupportAdmin', 1),
(3, N'olivia.park@mentorhub.com', N'$2y$10$adminhash03', N'Olivia', N'Park', N'ContentAdmin', 1),
(4, N'david.lee@mentorhub.com', N'$2y$10$adminhash04', N'David', N'Lee', N'OperationAdmin', 1),
(5, N'admin5@gmail.com', N'$2y$10$adminhash05', N'Huy', N'Vo', N'ADMIN', 0);

SET IDENTITY_INSERT [admin] OFF;

GO

SET IDENTITY_INSERT [user] ON;
INSERT INTO [user] (user_id, email, password, first_name, last_name, sex, status, current_job, address, country, time_zone, avatar, is_email_verified) VALUES
(1, N'an.nguyen@gmail.com', N'$2y$10$userhash01', N'An', N'Nguyễn', N'Male', N'Active', N'Frontend Developer', N'123 Lê Lợi, Quận 1', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/quidignissimoscumque.png?size=50x50&set=set1', 1),
(2, N'binh.tran@gmail.com', N'$2y$10$userhash02', N'Bình', N'Trần', N'Female', N'Active', N'Backend Developer', N'45 Nguyễn Huệ, Quận 1', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/autemearumad.png?size=50x50&set=set1', 1),
(3, N'chi.le@gmail.com', N'$2y$10$userhash03', N'Chi', N'Lê', N'Female', N'Active', N'Computer Science Student', N'78 Hai Bà Trưng, Quận 3', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/natusnonnobis.png?size=50x50&set=set1', 1),
(4, N'duy.pham@gmail.com', N'$2y$10$userhash04', N'Duy', N'Phạm', N'Male', N'Active', N'UI/UX Designer', N'22 Võ Văn Tần, Quận 3', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/euminventorecum.png?size=50x50&set=set1', 1),
(5, N'emma.lee@gmail.com', N'$2y$10$userhash05', N'Emma', N'Lee', N'Female', N'Active', N'Data Analyst', N'12 Orchard Road', N'Singapore', N'Asia/Singapore', N'https://robohash.org/doloresolutavoluptatum.png?size=50x50&set=set1', 1),
(6, N'faris.khan@gmail.com', N'$2y$10$userhash06', N'Faris', N'Khan', N'Male', N'Inactive', N'Junior Developer', N'88 Jalan Ampang', N'Malaysia', N'Asia/Kuala_Lumpur', N'https://robohash.org/sintveniampossimus.png?size=50x50&set=set1', 0),
(7, N'giang.vo@gmail.com', N'$2y$10$userhash07', N'Giang', N'Võ', N'Female', N'Active', N'Product Manager', N'19 Pasteur, Quận 3', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/laboreexincidunt.png?size=50x50&set=set1', 1),
(8, N'harry.wilson@gmail.com', N'$2y$10$userhash08', N'Harry', N'Wilson', N'Male', N'Active', N'QA Engineer', N'221B Baker Street', N'United Kingdom', N'Europe/London', N'https://robohash.org/doloremeasuscipit.png?size=50x50&set=set1', 1),
(9, N'isabella.rossi@gmail.com', N'$2y$10$userhash09', N'Isabella', N'Rossi', N'Female', N'Active', N'Data Engineer', N'Via Roma 15', N'Italy', N'Europe/Rome', N'https://robohash.org/natusvoluptasquo.png?size=50x50&set=set1', 1),
(10, N'jack.chen@gmail.com', N'$2y$10$userhash10', N'Jack', N'Chen', N'Male', N'Pending', N'CS Undergraduate', N'101 Nanjing Road', N'China', N'Asia/Shanghai', N'https://robohash.org/estmollitiarem.png?size=50x50&set=set1', 0),
(11, N'khanh.do@gmail.com', N'$2y$10$userhash11', N'Khánh', N'Đỗ', N'Female', N'Active', N'Mobile Developer', N'7 Phan Xích Long, Phú Nhuận', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/temporeharumnon.png?size=50x50&set=set1', 1),
(12, N'long.bui@gmail.com', N'$2y$10$userhash12', N'Long', N'Bùi', N'Male', N'Active', N'DevOps Engineer', N'28 Trần Hưng Đạo, Quận 5', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/etsuntet.png?size=50x50&set=set1', 1),
(13, N'maya.patel@gmail.com', N'$2y$10$userhash13', N'Maya', N'Patel', N'Female', N'Active', N'Business Analyst', N'MG Road 44', N'India', N'Asia/Kolkata', N'https://robohash.org/voluptatemoditut.png?size=50x50&set=set1', 1),
(14, N'nam.hoang@gmail.com', N'$2y$10$userhash14', N'Nam', N'Hoàng', N'Male', N'Active', N'Full-stack Developer', N'66 Cách Mạng Tháng 8, Quận 10', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/eiusoccaecatiarchitecto.png?size=50x50&set=set1', 1),
(15, N'oliver.smith@gmail.com', N'$2y$10$userhash15', N'Oliver', N'Smith', N'Male', N'Banned', N'Freelancer', N'54 King Street', N'Australia', N'Australia/Sydney', N'https://robohash.org/omnisquasinobis.png?size=50x50&set=set1', 0),
(16, N'phuong.trinh@gmail.com', N'$2y$10$userhash16', N'Phương', N'Trịnh', N'Female', N'Active', N'Software Tester', N'9 Nguyễn Đình Chiểu, Quận 1', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/dictasequiet.png?size=50x50&set=set1', 1),
(17, N'quang.ngo@gmail.com', N'$2y$10$userhash17', N'Quang', N'Ngô', N'Male', N'Active', N'Machine Learning Engineer', N'3B Điện Biên Phủ, Bình Thạnh', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/estblanditiisqui.png?size=50x50&set=set1', 1),
(18, N'rina.sato@gmail.com', N'$2y$10$userhash18', N'Rina', N'Sato', N'Female', N'Active', N'UX Researcher', N'Shibuya 5-2', N'Japan', N'Asia/Tokyo', N'https://robohash.org/verototameaque.png?size=50x50&set=set1', 1),
(19, N'son.dang@gmail.com', N'$2y$10$userhash19', N'Sơn', N'Đặng', N'Male', N'Active', N'Java Intern', N'18 Lý Thường Kiệt, Hà Nội', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/doloresetquisquam.png?size=50x50&set=set1', 1),
(20, N'thao.nguyen@gmail.com', N'$2y$10$userhash20', N'Thảo', N'Nguyễn', N'Female', N'Active', N'Marketing Executive', N'5 Nguyễn Văn Cừ, Quận 5', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/nisihicdolor.png?size=50x50&set=set1', 1),
(21, N'uma.kim@gmail.com', N'$2y$10$userhash21', N'Uma', N'Kim', N'Female', N'Active', N'Cloud Engineer', N'Gangnam-daero 72', N'South Korea', N'Asia/Seoul', N'https://robohash.org/ducimusaccusantiumquae.png?size=50x50&set=set1', 1),
(22, N'viet.lam@gmail.com', N'$2y$10$userhash22', N'Việt', N'Lâm', N'Male', N'Active', N'Data Science Student', N'11 Tô Hiến Thành, Đà Nẵng', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/laborumarchitectoaliquid.png?size=50x50&set=set1', 1),
(23, N'wendy.johnson@gmail.com', N'$2y$10$userhash23', N'Wendy', N'Johnson', N'Female', N'Pending', N'Graduate Student', N'210 Pine Street', N'United States', N'America/Los_Angeles', N'https://robohash.org/laborumfugiatnihil.png?size=50x50&set=set1', 0),
(24, N'xuan.phan@gmail.com', N'$2y$10$userhash24', N'Xuân', N'Phan', N'Other', N'Active', N'Content Designer', N'44 Nguyễn Thị Minh Khai, Quận 1', N'Vietnam', N'Asia/Ho_Chi_Minh', N'https://robohash.org/dolorumetipsa.png?size=50x50&set=set1', 1);

SET IDENTITY_INSERT [user] OFF;

GO

INSERT INTO [manage] (user_id, admin_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 1),
(6, 4),
(7, 3),
(8, 3),
(9, 4),
(10, 2),
(11, 1),
(12, 4),
(13, 3),
(14, 1),
(15, 4),
(16, 2),
(17, 1),
(18, 3),
(19, 2),
(20, 3),
(21, 4),
(22, 1),
(23, 2),
(24, 3);


GO

INSERT INTO [user_phone_number] (user_id, phone_number) VALUES
(1, N'0901000001'),
(1, N'0901000011'),
(2, N'0901000002'),
(3, N'0901000003'),
(4, N'0901000004'),
(5, N'6501000005'),
(6, N'6011000006'),
(7, N'0901000007'),
(8, N'4401000008'),
(9, N'3901000009'),
(10, N'8601000010'),
(11, N'0901000011'),
(12, N'0901000012'),
(13, N'9101000013'),
(14, N'0901000014'),
(15, N'6101000015'),
(16, N'0901000016'),
(17, N'0901000017'),
(18, N'8101000018'),
(19, N'0901000019'),
(20, N'0901000020'),
(21, N'8201000021'),
(22, N'0901000022'),
(23, N'1201000023'),
(24, N'0901000024');


GO

INSERT INTO [mentee] (user_id, learning_goal, level) VALUES
(3, N'Improve backend architecture skills and system design for internships.', N'Beginner'),
(5, N'Learn data engineering and transition into MLOps.', N'Intermediate'),
(6, N'Build confidence in Java and SQL fundamentals.', N'Beginner'),
(8, N'Move from QA to automation testing and testing strategy.', N'Intermediate'),
(10, N'Find a mentor for university projects and interview prep.', N'Beginner'),
(13, N'Learn product analytics and stakeholder communication.', N'Intermediate'),
(15, N'Rebuild career path after a long freelance break.', N'Beginner'),
(16, N'Advance from manual testing to SDET role.', N'Intermediate'),
(19, N'Pass backend internship interview at a product company.', N'Beginner'),
(20, N'Switch from marketing to UX writing / content design.', N'Beginner'),
(22, N'Understand data science roadmap with real projects.', N'Beginner'),
(23, N'Prepare portfolio and improve confidence in English interviews.', N'Intermediate');


GO

INSERT INTO [mentor] (user_id, expertise, language, experience_years, avg_star) VALUES
(1, N'Frontend architecture, React, TypeScript, performance tuning, mentoring juniors', N'Vietnamese, English', 6, 4.8),
(2, N'Backend systems, Java, Spring Boot, database design, REST API, microservices', N'Vietnamese, English', 8, 4.92),
(4, N'Product design, Figma, UX research, portfolio coaching', N'Vietnamese, English', 5, 4.74),
(7, N'Product strategy, stakeholder communication, roadmap planning', N'Vietnamese, English', 7, 4.65),
(9, N'Data engineering, warehouse modeling, ETL/ELT pipeline design', N'English, Italian', 9, 4.88),
(11, N'Flutter, Kotlin basics, clean architecture for mobile teams', N'Vietnamese, English', 4, 4.58),
(12, N'DevOps, Docker, CI/CD, Kubernetes fundamentals, cloud deployment', N'Vietnamese, English', 7, 4.79),
(14, N'Full-stack development, Node.js, PostgreSQL, API security', N'Vietnamese, English', 6, 4.69),
(17, N'Machine learning, MLOps, model deployment, experiment tracking', N'Vietnamese, English', 5, 4.86),
(18, N'UX research, user interviews, usability testing, service design', N'Japanese, English', 8, 4.77),
(21, N'Cloud architecture, AWS, Terraform, platform engineering', N'Korean, English', 10, 4.91),
(24, N'Content design, UX writing, bilingual product copy', N'Vietnamese, English', 5, 4.63);


GO

SET IDENTITY_INSERT [notification] ON;
INSERT INTO [notification] (notification_id, admin_id, target_role, title, content, sent_date, sent_time) VALUES
(1, 1, N'ALL', N'Welcome to MentorHub', N'Chào mừng bạn đến với MentorHub. Explore mentors, complete your profile, and book your first session.', N'2026-04-17', N'09:00:00'),
(2, 2, N'MENTEE', N'Profile Reminder', N'Vui lòng cập nhật profile để hệ thống match mentor tốt hơn. Please add your learning goal and current role.', N'2026-04-17', N'10:15:00'),
(3, 1, N'MENTEE', N'Discount Week', N'Use code SPRING10 để giảm 10% cho selected sessions this week.', N'2026-04-18', N'08:30:00'),
(4, 3, N'ALL', N'Community Event', N'Join our online event: Career Q&A for interns and junior developers — hoàn toàn miễn phí.', N'2026-04-19', N'18:00:00'),
(5, 4, N'MENTEE', N'Payment Status', N'Your payment has been received. Cảm ơn bạn đã đặt lịch cùng mentor.', N'2026-04-20', N'11:20:00'),
(6, 2, N'MENTEE', N'Mentor Reply', N'Bạn có tin nhắn mới từ mentor. Check your inbox to continue the conversation.', N'2026-04-20', N'15:45:00'),
(7, 3, N'ALL', N'System Maintenance', N'Hệ thống sẽ bảo trì từ 01:00 đến 02:00 ICT. Some features may be temporarily unavailable.', N'2026-04-21', N'22:00:00'),
(8, 1, N'MENTOR', N'Feature Update', N'Bạn đã có thể thêm session vào wishlist và compare prices easier now.', N'2026-04-22', N'09:10:00');

SET IDENTITY_INSERT [notification] OFF;

GO

INSERT INTO [user_notification] (user_id, notification_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(3, 2),
(5, 2),
(6, 2),
(10, 2),
(13, 2),
(15, 2),
(20, 2),
(23, 2),
(3, 3),
(5, 3),
(6, 3),
(8, 3),
(10, 3),
(16, 3),
(19, 3),
(22, 3),
(1, 4),
(3, 4),
(5, 4),
(8, 4),
(10, 4),
(13, 4),
(19, 4),
(22, 4),
(23, 4),
(3, 5),
(5, 5),
(19, 5),
(22, 5),
(3, 6),
(5, 6),
(6, 6),
(19, 6),
(20, 6),
(1, 7),
(2, 7),
(7, 7),
(9, 7),
(12, 7),
(21, 7),
(3, 8),
(5, 8),
(8, 8),
(20, 8),
(23, 8),
(24, 8);


GO

SET IDENTITY_INSERT [category] ON;
INSERT INTO [category] (category_id, category_name, category_super_id) VALUES
(1, N'Software Development', NULL),
(2, N'Design', NULL),
(3, N'Data', NULL),
(4, N'Cloud & DevOps', NULL),
(5, N'Product', NULL),
(6, N'Frontend', 1),
(7, N'Backend', 1),
(8, N'Mobile', 1),
(9, N'UI/UX', 2),
(10, N'Data Engineering', 3),
(11, N'Machine Learning', 3),
(12, N'Platform Engineering', 4),
(13, N'Product Management', 5),
(14, N'Content Design', 2);

SET IDENTITY_INSERT [category] OFF;

GO

SET IDENTITY_INSERT [skills] ON;
INSERT INTO [skills] (skill_id, skill_name) VALUES
(1, N'ReactJS'),
(2, N'TypeScript'),
(3, N'Next.js'),
(4, N'Spring Boot'),
(5, N'MySQL'),
(6, N'System Design'),
(7, N'Figma'),
(8, N'UX Research'),
(9, N'Python'),
(10, N'ETL Pipeline'),
(11, N'Flutter'),
(12, N'Kotlin'),
(13, N'Docker'),
(14, N'Kubernetes'),
(15, N'AWS'),
(16, N'Terraform'),
(17, N'Node.js'),
(18, N'PostgreSQL'),
(19, N'API Security'),
(20, N'MLOps'),
(21, N'Airflow'),
(22, N'Data Modeling'),
(23, N'Product Strategy'),
(24, N'Stakeholder Communication'),
(25, N'UX Writing'),
(26, N'Usability Testing'),
(27, N'CI/CD'),
(28, N'Prompt Engineering'),
(29, N'Portfolio Review'),
(30, N'Interview Coaching');

SET IDENTITY_INSERT [skills] OFF;

GO

INSERT INTO [skill_category] (skill_id, category_id) VALUES
(1, 6),
(2, 6),
(3, 6),
(4, 7),
(5, 7),
(6, 7),
(17, 7),
(18, 7),
(19, 7),
(11, 8),
(12, 8),
(7, 9),
(8, 9),
(26, 9),
(29, 9),
(9, 10),
(10, 10),
(21, 10),
(22, 10),
(20, 11),
(28, 11),
(13, 12),
(14, 12),
(15, 12),
(16, 12),
(27, 12),
(23, 13),
(24, 13),
(30, 13),
(25, 14);


GO

INSERT INTO [mentor_skills] (mentor_id, skill_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 6),
(1, 30),
(2, 4),
(2, 5),
(2, 6),
(2, 18),
(2, 19),
(2, 30),
(4, 7),
(4, 8),
(4, 26),
(4, 29),
(7, 23),
(7, 24),
(7, 30),
(9, 9),
(9, 10),
(9, 21),
(9, 22),
(11, 11),
(11, 12),
(11, 30),
(12, 13),
(12, 14),
(12, 15),
(12, 16),
(12, 27),
(14, 17),
(14, 18),
(14, 19),
(14, 6),
(17, 9),
(17, 20),
(17, 28),
(17, 30),
(18, 8),
(18, 26),
(18, 29),
(21, 13),
(21, 15),
(21, 16),
(21, 27),
(24, 25),
(24, 29),
(24, 30);


GO

SET IDENTITY_INSERT [company] ON;
INSERT INTO [company] (company_id, company_name) VALUES
(1, N'FPT Software'),
(2, N'VNG Corporation'),
(3, N'Shopee'),
(4, N'Grab'),
(5, N'Tiki'),
(6, N'TechNova Labs'),
(7, N'CloudBridge Asia'),
(8, N'BluePixel Studio');

SET IDENTITY_INSERT [company] OFF;

GO

INSERT INTO [work_for] (mentor_id, company_id, job_title, start_day, end_day) VALUES
(1, 1, N'Senior Frontend Engineer', N'2022-01-01', N'2024-12-31'),
(1, 6, N'Frontend Consultant', N'2025-01-01', N'2026-12-31'),
(2, 2, N'Backend Team Lead', N'2021-06-01', N'2025-12-31'),
(2, 5, N'Architecture Advisor', N'2026-01-01', N'2026-12-31'),
(4, 3, N'Senior Product Designer', N'2023-03-01', N'2025-03-31'),
(4, 8, N'Freelance UX Consultant', N'2025-04-01', N'2026-12-31'),
(7, 4, N'Product Manager', N'2020-05-01', N'2024-06-30'),
(7, 6, N'Strategy Lead', N'2024-07-01', N'2026-12-31'),
(9, 4, N'Data Engineering Manager', N'2019-01-01', N'2023-12-31'),
(9, 7, N'Principal Data Engineer', N'2024-01-01', N'2026-12-31'),
(11, 5, N'Mobile Engineer', N'2022-07-01', N'2025-06-30'),
(12, 1, N'DevOps Lead', N'2021-02-01', N'2026-12-31'),
(14, 6, N'Full-stack Engineer', N'2021-08-01', N'2024-12-31'),
(14, 2, N'Platform Developer', N'2025-01-01', N'2026-12-31'),
(17, 7, N'ML Engineer', N'2022-11-01', N'2026-12-31'),
(18, 8, N'UX Research Lead', N'2020-03-01', N'2026-12-31'),
(21, 7, N'Cloud Architect', N'2018-09-01', N'2026-12-31'),
(24, 6, N'Content Designer', N'2023-01-01', N'2026-12-31');


GO

INSERT INTO [available_time] (mentor_id, start_day, end_day, start_time, end_time) VALUES
(1, N'2026-04-20', N'2026-04-20', N'19:00:00', N'21:00:00'),
(1, N'2026-04-21', N'2026-04-21', N'19:00:00', N'21:00:00'),
(1, N'2026-04-24', N'2026-04-24', N'20:00:00', N'22:00:00'),
(2, N'2026-04-20', N'2026-04-20', N'20:00:00', N'22:00:00'),
(2, N'2026-04-22', N'2026-04-22', N'20:00:00', N'22:00:00'),
(2, N'2026-04-25', N'2026-04-25', N'09:00:00', N'11:00:00'),
(4, N'2026-04-21', N'2026-04-21', N'18:30:00', N'20:30:00'),
(4, N'2026-04-23', N'2026-04-23', N'19:00:00', N'21:00:00'),
(7, N'2026-04-20', N'2026-04-20', N'18:00:00', N'20:00:00'),
(7, N'2026-04-24', N'2026-04-24', N'18:00:00', N'20:00:00'),
(9, N'2026-04-21', N'2026-04-21', N'20:30:00', N'22:30:00'),
(9, N'2026-04-26', N'2026-04-26', N'09:00:00', N'11:00:00'),
(11, N'2026-04-22', N'2026-04-22', N'19:00:00', N'21:00:00'),
(12, N'2026-04-20', N'2026-04-20', N'07:00:00', N'09:00:00'),
(12, N'2026-04-23', N'2026-04-23', N'20:00:00', N'22:00:00'),
(14, N'2026-04-21', N'2026-04-21', N'19:30:00', N'21:30:00'),
(17, N'2026-04-22', N'2026-04-22', N'20:00:00', N'22:00:00'),
(18, N'2026-04-24', N'2026-04-24', N'18:00:00', N'20:00:00'),
(21, N'2026-04-25', N'2026-04-25', N'13:00:00', N'15:00:00'),
(24, N'2026-04-26', N'2026-04-26', N'10:00:00', N'12:00:00');


GO

SET IDENTITY_INSERT [session] ON;
INSERT INTO [session] (session_id, mentor_id, description, session_name, duration, price) VALUES
(1, 1, N'1:1 session to review React project structure, state management, and performance. Có thể review code trực tiếp.', N'React Project Review', 1, 25.0),
(2, 1, N'Optimize rendering, bundle size, and Core Web Vitals for production apps.', N'Frontend Performance Clinic', 1, 30.0),
(3, 2, N'Discuss REST API design, database schema, and backend best practices.', N'Backend Career Mentoring', 2, 40.0),
(4, 2, N'Mock interview for Java backend internship roles.', N'Java Backend Mock Interview', 1, 35.0),
(5, 4, N'Portfolio and product design feedback with actionable improvement plan.', N'UX Portfolio Review', 1, 30.0),
(6, 4, N'User interview planning, script writing, and research synthesis.', N'UX Research Coaching', 1, 32.0),
(7, 7, N'How to think like a PM: roadmap, prioritization, and stakeholder updates.', N'Product Management 101', 1, 28.0),
(8, 7, N'Case-based coaching for product sense and communication.', N'PM Interview Prep', 1, 34.0),
(9, 9, N'Learn the foundations of ETL, warehouse layers, and data modeling.', N'Data Engineering Roadmap', 2, 45.0),
(10, 9, N'Hands-on session on SQL modeling and pipeline design.', N'SQL & Pipeline Design Review', 1, 38.0),
(11, 11, N'Review your Flutter project architecture and state management.', N'Flutter App Review', 1, 29.0),
(12, 12, N'Set up CI/CD with Docker and GitHub Actions. Có demo thực hành.', N'CI/CD Starter Session', 2, 42.0),
(13, 12, N'Kubernetes fundamentals for beginners, with practical diagrams.', N'Kubernetes for Beginners', 1, 39.0),
(14, 14, N'Node.js backend review: routing, auth, and database integration.', N'Node.js Backend Review', 1, 33.0),
(15, 17, N'Roadmap into ML engineering and MLOps from zero to project-ready.', N'ML Career Roadmap', 1, 37.0),
(16, 17, N'Prompt engineering and experiment workflow for applied AI teams.', N'Applied AI Workflow Coaching', 1, 36.0),
(17, 18, N'How to run usability testing and turn findings into design decisions.', N'Usability Testing Workshop', 1, 31.0),
(18, 21, N'AWS and Terraform basics for junior cloud engineers.', N'Cloud Foundations', 2, 48.0),
(19, 21, N'Designing scalable infrastructure: cost, reliability, and trade-offs.', N'Cloud Architecture Review', 1, 50.0),
(20, 24, N'Build a UX writing portfolio with bilingual samples.', N'UX Writing Portfolio Coaching', 1, 27.0);

SET IDENTITY_INSERT [session] OFF;

GO

SET IDENTITY_INSERT [discount] ON;
INSERT INTO [discount] (discount_id, discount_name, discount_value, start_day, end_day, code, status) VALUES
(1, N'Spring 10%', 10.0, N'2026-04-18 00:00:00', N'2026-04-25 23:59:59', N'SPRING10', N'Active'),
(2, N'New User 5$', 5.0, N'2026-04-01 00:00:00', N'2026-05-01 23:59:59', N'NEW5', N'Active'),
(3, N'April Career Boost', 12.0, N'2026-04-15 00:00:00', N'2026-04-30 23:59:59', N'BOOST12', N'Active'),
(4, N'Expired March Promo', 15.0, N'2026-03-01 00:00:00', N'2026-03-31 23:59:59', N'MARCH15', N'Expired'),
(5, N'Design Week', 8.0, N'2026-04-20 00:00:00', N'2026-04-27 23:59:59', N'DESIGN8', N'Active'),
(6, N'Cloud Starter', 7.5, N'2026-04-19 00:00:00', N'2026-05-10 23:59:59', N'CLOUD75', N'Inactive');

SET IDENTITY_INSERT [discount] OFF;

GO

SET IDENTITY_INSERT [mentee_session] ON;
INSERT INTO [mentee_session] (mentee_session_id, mentee_id, session_id, discount_id, date, start_time, end_time, link) VALUES
(1, 3, 1, 1, N'2026-04-20', N'19:00:00', N'20:00:00', N'https://meet.example.com/session-1'),
(2, 5, 9, 2, N'2026-04-22', N'20:00:00', N'22:00:00', N'https://meet.example.com/session-2'),
(3, 6, 4, NULL, N'2026-04-23', N'20:00:00', N'21:00:00', N'https://meet.example.com/session-3'),
(4, 5, 5, 5, N'2026-04-21', N'18:30:00', N'19:30:00', N'https://meet.example.com/session-4'),
(5, 8, 12, 3, N'2026-04-23', N'20:00:00', N'22:00:00', N'https://meet.example.com/session-5'),
(6, 10, 3, 2, N'2026-04-25', N'09:00:00', N'11:00:00', N'https://meet.example.com/session-6'),
(7, 13, 7, NULL, N'2026-04-24', N'18:00:00', N'19:00:00', N'https://meet.example.com/session-7'),
(8, 15, 8, NULL, N'2026-04-24', N'19:00:00', N'20:00:00', N'https://meet.example.com/session-8'),
(9, 16, 12, 3, N'2026-04-20', N'07:00:00', N'09:00:00', N'https://meet.example.com/session-9'),
(10, 19, 4, 1, N'2026-04-22', N'20:00:00', N'21:00:00', N'https://meet.example.com/session-10'),
(11, 20, 20, NULL, N'2026-04-26', N'10:00:00', N'11:00:00', N'https://meet.example.com/session-11'),
(12, 22, 15, 3, N'2026-04-22', N'20:00:00', N'21:00:00', N'https://meet.example.com/session-12'),
(13, 23, 17, 5, N'2026-04-24', N'18:00:00', N'19:00:00', N'https://meet.example.com/session-13'),
(14, 3, 2, NULL, N'2026-04-24', N'20:00:00', N'21:00:00', N'https://meet.example.com/session-14'),
(15, 5, 10, 3, N'2026-04-26', N'09:00:00', N'10:00:00', N'https://meet.example.com/session-15'),
(16, 6, 14, NULL, N'2026-04-21', N'19:30:00', N'20:30:00', N'https://meet.example.com/session-16'),
(17, 8, 18, 6, N'2026-04-25', N'13:00:00', N'15:00:00', N'https://meet.example.com/session-17'),
(18, 10, 11, NULL, N'2026-04-22', N'19:00:00', N'20:00:00', N'https://meet.example.com/session-18'),
(19, 13, 8, NULL, N'2026-04-20', N'18:00:00', N'19:00:00', N'https://meet.example.com/session-19'),
(20, 15, 19, 6, N'2026-04-25', N'13:00:00', N'14:00:00', N'https://meet.example.com/session-20'),
(21, 16, 13, NULL, N'2026-04-23', N'20:00:00', N'21:00:00', N'https://meet.example.com/session-21'),
(22, 19, 3, 1, N'2026-04-25', N'09:00:00', N'11:00:00', N'https://meet.example.com/session-22'),
(23, 20, 6, 5, N'2026-04-23', N'19:00:00', N'20:00:00', N'https://meet.example.com/session-23'),
(24, 22, 16, 3, N'2026-04-22', N'21:00:00', N'22:00:00', N'https://meet.example.com/session-24');

SET IDENTITY_INSERT [mentee_session] OFF;

GO

SET IDENTITY_INSERT [payment] ON;
INSERT INTO [payment] (payment_id, mentee_session_id, date, total_money, status) VALUES
(1, 1, N'2026-04-18', 15.0, N'Paid'),
(2, 2, N'2026-04-19', 40.0, N'Pending'),
(3, 3, N'2026-04-20', 35.0, N'Failed'),
(4, 4, N'2026-04-19', 22.0, N'Refunded'),
(5, 5, N'2026-04-20', 30.0, N'Paid'),
(6, 6, N'2026-04-21', 35.0, N'Paid'),
(7, 7, N'2026-04-21', 28.0, N'Paid'),
(8, 8, N'2026-04-21', 34.0, N'Pending'),
(9, 9, N'2026-04-20', 30.0, N'Paid'),
(10, 10, N'2026-04-21', 25.0, N'Paid'),
(11, 11, N'2026-04-22', 27.0, N'Paid'),
(12, 12, N'2026-04-22', 25.0, N'Pending'),
(13, 13, N'2026-04-22', 23.0, N'Paid'),
(14, 14, N'2026-04-23', 30.0, N'Paid'),
(15, 15, N'2026-04-23', 26.0, N'Paid'),
(16, 16, N'2026-04-23', 33.0, N'Failed'),
(17, 17, N'2026-04-24', 40.5, N'Paid'),
(18, 18, N'2026-04-24', 29.0, N'Pending'),
(19, 19, N'2026-04-24', 28.0, N'Paid'),
(20, 20, N'2026-04-24', 42.5, N'Refunded'),
(21, 21, N'2026-04-25', 39.0, N'Paid'),
(22, 22, N'2026-04-25', 30.0, N'Paid'),
(23, 23, N'2026-04-25', 24.0, N'Paid'),
(24, 24, N'2026-04-25', 24.0, N'Pending');

SET IDENTITY_INSERT [payment] OFF;

GO

SET IDENTITY_INSERT [wishlist] ON;
INSERT INTO [wishlist] (wishlist_id, mentee_id, wishlist_name) VALUES
(1, 3, N'Internship Prep'),
(2, 5, N'Data Career Plan'),
(3, 6, N'Backend Starter Pack'),
(4, 8, N'QA to Automation'),
(5, 10, N'University Survival Kit'),
(6, 13, N'PM Growth'),
(7, 15, N'Career Comeback'),
(8, 16, N'SDET Path'),
(9, 19, N'Java Interview Pack'),
(10, 20, N'Content Design Move'),
(11, 22, N'Data Science Basics'),
(12, 23, N'Portfolio + Confidence');

SET IDENTITY_INSERT [wishlist] OFF;

GO

INSERT INTO [wishlist_session] (session_id, wishlist_id) VALUES
(1, 1),
(4, 1),
(3, 1),
(9, 2),
(10, 2),
(15, 2),
(4, 3),
(14, 3),
(3, 3),
(12, 4),
(18, 4),
(3, 5),
(11, 5),
(1, 5),
(7, 6),
(8, 6),
(20, 6),
(8, 7),
(19, 7),
(12, 8),
(13, 8),
(4, 9),
(3, 9),
(20, 10),
(5, 10),
(6, 10),
(9, 11),
(15, 11),
(16, 11),
(17, 12),
(20, 12);


GO

SET IDENTITY_INSERT [message] ON;
INSERT INTO [message] (message_id, mentor_id, mentee_id, sender_role, content, sent_at) VALUES
(1, 1, 3, N'mentee', N'Hi anh An, em muốn được review project React của em. I feel my code is messy.', N'2026-04-17 20:00:00'),
(2, 1, 3, N'mentor', N'Được em, gửi anh repo GitHub và mục tiêu em muốn cải thiện nhé. We can focus on component structure first.', N'2026-04-17 20:05:00'),
(3, 2, 5, N'mentee', N'Em muốn được định hướng sang data engineering và MLOps.', N'2026-04-17 21:00:00'),
(4, 2, 5, N'mentor', N'Anh sẽ giúp em lên roadmap từ SQL, Python đến ETL fundamentals. After that we can discuss orchestration.', N'2026-04-17 21:07:00'),
(5, 2, 6, N'mentee', N'Anh có thể mock interview backend cho em không?', N'2026-04-18 09:00:00'),
(6, 2, 6, N'mentor', N'Được, em đặt session Java Backend Mock Interview nhé. Prepare two projects and one failure story.', N'2026-04-18 09:10:00'),
(7, 12, 8, N'mentee', N'I want to move from QA to automation. Nên bắt đầu với tool nào trước ạ?', N'2026-04-18 10:00:00'),
(8, 12, 8, N'mentor', N'Bắt đầu với test strategy trước, then pick one stack like Playwright or Cypress depending on your product.', N'2026-04-18 10:06:00'),
(9, 7, 13, N'mentee', N'Can you help me become a better PM? Em đang bị yếu ở stakeholder communication.', N'2026-04-18 11:00:00'),
(10, 7, 13, N'mentor', N'Sure. We will practice framing updates, prioritization, and saying no politely but clearly.', N'2026-04-18 11:08:00'),
(11, 18, 23, N'mentee', N'Em cần feedback portfolio bằng tiếng Anh vì em sắp phỏng vấn.', N'2026-04-18 13:00:00'),
(12, 18, 23, N'mentor', N'No problem. Send me your case study, and I will comment on clarity, evidence, and storytelling.', N'2026-04-18 13:09:00'),
(13, 24, 20, N'mentee', N'I want to switch from marketing to UX writing. Portfolio của em chưa có case study rõ ràng.', N'2026-04-18 14:10:00'),
(14, 24, 20, N'mentor', N'Mình sẽ bắt đầu bằng việc viết lại 2 flows hiện tại của bạn thành stronger writing samples.', N'2026-04-18 14:16:00'),
(15, 17, 22, N'mentee', N'Anh Quang ơi, data science roadmap nào realistic cho sinh viên năm 2?', N'2026-04-18 15:00:00'),
(16, 17, 22, N'mentor', N'Tập trung vào Python, SQL, statistics, rồi làm 2 project nhỏ. Don’t rush into deep learning too early.', N'2026-04-18 15:07:00'),
(17, 11, 10, N'mentee', N'I am building a Flutter todo app. Có nên dùng Provider hay Riverpod?', N'2026-04-18 16:00:00'),
(18, 11, 10, N'mentor', N'For learning, Provider is fine. If you want better scalability later, Riverpod is a great next step.', N'2026-04-18 16:10:00'),
(19, 21, 15, N'mentee', N'I want to understand cloud basics without getting lost.', N'2026-04-18 18:00:00'),
(20, 21, 15, N'mentor', N'We will start with compute, storage, networking, then map them to a simple product architecture.', N'2026-04-18 18:08:00'),
(21, 2, 19, N'mentee', N'Em hơi run khi trả lời câu hỏi database indexing.', N'2026-04-19 09:00:00'),
(22, 2, 19, N'mentor', N'Không sao, we will practice with examples: B-tree index, composite index, and common trade-offs.', N'2026-04-19 09:05:00'),
(23, 4, 5, N'mentee', N'Could you review my portfolio landing page? Em nghĩ flow chưa mượt.', N'2026-04-19 10:00:00'),
(24, 4, 5, N'mentor', N'Yes. I will look at information hierarchy, CTA clarity, and whether your impact is visible in 10 seconds.', N'2026-04-19 10:06:00');

SET IDENTITY_INSERT [message] OFF;

GO

INSERT INTO [rate] (mentor_id, mentee_id, comment, star, rated_date) VALUES
(1, 3, N'Mentor giải thích rất dễ hiểu, góp ý đúng trọng tâm project. Super practical feedback.', 5, N'2026-04-21'),
(2, 5, N'Roadmap rõ ràng, thực tế và có nhiều tài liệu hữu ích.', 5, N'2026-04-23'),
(2, 6, N'Mock interview sát thực tế, giúp em tự tin hơn.', 4, N'2026-04-24'),
(12, 8, N'Clear guidance for QA to automation transition. Có checklist rất dễ làm theo.', 5, N'2026-04-24'),
(7, 13, N'Very supportive and structured. I now know how to talk to stakeholders better.', 4, N'2026-04-24'),
(18, 23, N'Portfolio feedback chi tiết, helpful for English presentation too.', 5, N'2026-04-25'),
(24, 20, N'Mentor góp ý wording cực kỳ sắc và practical.', 5, N'2026-04-25'),
(17, 22, N'Roadmap hợp lý, không bị overhype.', 4, N'2026-04-25'),
(11, 10, N'Great explanation, patient and beginner-friendly.', 4, N'2026-04-25'),
(21, 15, N'Good cloud overview, but I need one more session for hands-on practice.', 4, N'2026-04-25'),
(4, 5, N'Feedback portfolio rất chi tiết và dễ áp dụng.', 4, N'2026-04-22'),
(2, 19, N'Anh mentor chỉ ra lỗi tư duy khi trả lời interview rất đúng.', 5, N'2026-04-26');

GO
