USE [mentor_mentee];
GO

-- Backend alignment script for the Java API in backend-java.
-- Run after: 01_create_database.sql, 02_schema.sql, 03_seed_full_testcase.sql, 05_course_tables.sql.

IF COL_LENGTH('payment', 'payment_method') IS NULL
BEGIN
    ALTER TABLE [payment] ADD payment_method NVARCHAR(40) NULL;
END;
GO

IF COL_LENGTH('payment', 'paid_at') IS NULL
BEGIN
    ALTER TABLE [payment] ADD paid_at DATETIME2(0) NULL;
END;
GO

IF COL_LENGTH('notification', 'target_role') IS NULL
BEGIN
    ALTER TABLE [notification] ADD target_role NVARCHAR(20) NOT NULL CONSTRAINT df_notification_target_role DEFAULT N'ALL';
END;
GO

IF OBJECT_ID(N'[mentor_wishlist]', N'U') IS NULL
BEGIN
    CREATE TABLE [mentor_wishlist] (
        mentee_id INT NOT NULL,
        mentor_id INT NOT NULL,
        created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
        CONSTRAINT pk_mentor_wishlist PRIMARY KEY (mentee_id, mentor_id),
        CONSTRAINT fk_mentor_wishlist_mentee FOREIGN KEY (mentee_id) REFERENCES [mentee](user_id),
        CONSTRAINT fk_mentor_wishlist_mentor FOREIGN KEY (mentor_id) REFERENCES [mentor](user_id)
    );
END;
GO

-- Demo accounts matching the FE login cards.
IF NOT EXISTS (SELECT 1 FROM [admin] WHERE email = N'admin@mentorhub.dev')
BEGIN
    INSERT INTO [admin] (email, [password], first_name, last_name, [role], is_email_verified)
    VALUES (N'admin@mentorhub.dev', N'123456', N'System', N'Admin', N'ADMIN', 1);
END;
GO

DECLARE @mentorUserId INT;
SELECT @mentorUserId = user_id FROM [user] WHERE email = N'mentor@mentorhub.dev';
IF @mentorUserId IS NULL
BEGIN
    INSERT INTO [user] (email, [password], first_name, last_name, sex, [status], current_job, country, time_zone, is_email_verified)
    VALUES (N'mentor@mentorhub.dev', N'123456', N'Bình', N'Trần', N'Female', N'Active', N'Backend Team Lead', N'Vietnam', N'Asia/Ho_Chi_Minh', 1);
    SET @mentorUserId = SCOPE_IDENTITY();
END;
IF NOT EXISTS (SELECT 1 FROM [mentor] WHERE user_id = @mentorUserId)
BEGIN
    INSERT INTO [mentor] (user_id, expertise, [language], experience_years, avg_star)
    VALUES (@mentorUserId, N'Java, Spring Boot, SQL Server, backend career mentoring', N'Vietnamese, English', 8, 4.95);
END;
GO

DECLARE @menteeUserId INT;
SELECT @menteeUserId = user_id FROM [user] WHERE email = N'mentee@mentorhub.dev';
IF @menteeUserId IS NULL
BEGIN
    INSERT INTO [user] (email, [password], first_name, last_name, sex, [status], current_job, country, time_zone, is_email_verified)
    VALUES (N'mentee@mentorhub.dev', N'123456', N'Mai', N'Võ', N'Female', N'Active', N'Junior Developer', N'Vietnam', N'Asia/Ho_Chi_Minh', 1);
    SET @menteeUserId = SCOPE_IDENTITY();
END;
IF NOT EXISTS (SELECT 1 FROM [mentee] WHERE user_id = @menteeUserId)
BEGIN
    INSERT INTO [mentee] (user_id, learning_goal, [level])
    VALUES (@menteeUserId, N'Prepare backend internship interview and build portfolio.', N'Beginner');
END;
GO

-- Demo session/course/message for the FE demo accounts.
DECLARE @mentorId INT = (SELECT user_id FROM [user] WHERE email = N'mentor@mentorhub.dev');
DECLARE @menteeId INT = (SELECT user_id FROM [user] WHERE email = N'mentee@mentorhub.dev');

IF @mentorId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM [session] WHERE mentor_id = @mentorId AND session_name = N'Java Backend Mock Interview')
BEGIN
    INSERT INTO [session] (mentor_id, description, session_name, duration, price)
    VALUES (@mentorId, N'Mock interview for Java, REST API, SQL Server and OOP fundamentals.', N'Java Backend Mock Interview', 60, 42.00);
END;

IF OBJECT_ID(N'[course]', N'U') IS NOT NULL AND @mentorId IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM [course] WHERE mentor_id = @mentorId AND title = N'Java Backend Career Track')
    BEGIN
        INSERT INTO [course] (mentor_id, title, description, [level], price, [status])
        VALUES (@mentorId, N'Java Backend Career Track', N'Java, Spring Boot, REST API, SQL Server and mock interview roadmap.', N'Beginner to Intermediate', 79.00, N'Active');
    END;
END;

IF @mentorId IS NOT NULL AND @menteeId IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM [message] WHERE mentor_id = @mentorId AND mentee_id = @menteeId)
    BEGIN
        INSERT INTO [message] (mentor_id, mentee_id, sender_role, content)
        VALUES
        (@mentorId, @menteeId, N'MENTEE', N'Em muốn hỏi trước về buổi Java Backend Mock Interview ạ.'),
        (@mentorId, @menteeId, N'MENTOR', N'Được em, trước buổi học em gửi CV và repo GitHub cho anh nhé.');
    END;

    IF NOT EXISTS (SELECT 1 FROM [mentor_wishlist] WHERE mentor_id = @mentorId AND mentee_id = @menteeId)
    BEGIN
        INSERT INTO [mentor_wishlist] (mentee_id, mentor_id) VALUES (@menteeId, @mentorId);
    END;
END;
GO

-- Useful indexes for message and approval screens.
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'ix_message_mentor_mentee_sent_at' AND object_id = OBJECT_ID(N'[message]'))
BEGIN
    CREATE INDEX ix_message_mentor_mentee_sent_at ON [message](mentor_id, mentee_id, sent_at DESC);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'ix_user_status' AND object_id = OBJECT_ID(N'[user]'))
BEGIN
    CREATE INDEX ix_user_status ON [user]([status]);
END;
GO


-- Demo admin notification recipients for the FE notification panel.
DECLARE @demoAdminId INT = (SELECT TOP 1 admin_id FROM [admin] ORDER BY admin_id);
DECLARE @demoMentorId INT = (SELECT user_id FROM [user] WHERE email = N'mentor@mentorhub.dev');
DECLARE @demoMenteeId INT = (SELECT user_id FROM [user] WHERE email = N'mentee@mentorhub.dev');
DECLARE @demoNotificationId INT;

IF @demoAdminId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM [notification] WHERE title = N'Admin announcement demo')
BEGIN
    INSERT INTO [notification] (admin_id, target_role, title, content, sent_date, sent_time)
    VALUES (@demoAdminId, N'ALL', N'Admin announcement demo', N'Đây là thông báo demo từ Admin. Mentor và Mentee đều sẽ thấy thông báo này trong Dashboard.', CONVERT(date, SYSDATETIME()), CONVERT(time(0), SYSDATETIME()));
    SET @demoNotificationId = SCOPE_IDENTITY();

    IF @demoMentorId IS NOT NULL
        INSERT INTO [user_notification] (user_id, notification_id, is_read) VALUES (@demoMentorId, @demoNotificationId, 0);
    IF @demoMenteeId IS NOT NULL
        INSERT INTO [user_notification] (user_id, notification_id, is_read) VALUES (@demoMenteeId, @demoNotificationId, 0);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'ix_user_notification_user_read' AND object_id = OBJECT_ID(N'[user_notification]'))
BEGIN
    CREATE INDEX ix_user_notification_user_read ON [user_notification](user_id, is_read, notification_id DESC);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'ix_notification_target_role' AND object_id = OBJECT_ID(N'[notification]'))
BEGIN
    CREATE INDEX ix_notification_target_role ON [notification](target_role, sent_date DESC, sent_time DESC);
END;
GO
