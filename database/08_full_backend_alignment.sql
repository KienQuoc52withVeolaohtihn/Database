USE [mentor_mentee];
GO

-- Full backend alignment for v5. Run after 06/07 if you already created the database.
-- Safe to run multiple times.

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

UPDATE [payment]
SET payment_method = CASE WHEN [status] = N'Paid' THEN N'Card' ELSE N'Pending' END
WHERE payment_method IS NULL;
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

-- Make demo login work with the seeded users, so FE mentor id 2 and mentee id 3 match chat/wishlist/session data.
UPDATE [user] SET [password] = N'123456' WHERE user_id IN (2, 3);
GO

IF NOT EXISTS (SELECT 1 FROM [admin] WHERE email = N'admin@mentorhub.dev')
BEGIN
    INSERT INTO [admin] (email, [password], first_name, last_name, [role], is_email_verified)
    VALUES (N'admin@mentorhub.dev', N'123456', N'System', N'Admin', N'ADMIN', 1);
END;
GO

-- Add a role-wide notification demo and attach it to all current mentors/mentees.
DECLARE @demoAdminId INT = (SELECT TOP 1 admin_id FROM [admin] ORDER BY admin_id);
DECLARE @demoNotificationId INT;

IF @demoAdminId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM [notification] WHERE title = N'Backend v5 is connected')
BEGIN
    INSERT INTO [notification] (admin_id, target_role, title, content, sent_date, sent_time)
    VALUES (@demoAdminId, N'ALL', N'Backend v5 is connected', N'Admin notification đã được lưu database. Mentor/Mentee dashboard sẽ fetch từ API /api/notifications/users/{userId}.', CONVERT(date, SYSDATETIME()), CONVERT(time(0), SYSDATETIME()));
    SET @demoNotificationId = SCOPE_IDENTITY();

    INSERT INTO [user_notification] (user_id, notification_id, is_read)
    SELECT u.user_id, @demoNotificationId, 0
    FROM [user] u
    WHERE (EXISTS (SELECT 1 FROM [mentor] m WHERE m.user_id = u.user_id)
       OR EXISTS (SELECT 1 FROM [mentee] me WHERE me.user_id = u.user_id))
      AND NOT EXISTS (SELECT 1 FROM [user_notification] un WHERE un.user_id = u.user_id AND un.notification_id = @demoNotificationId);
END;
GO

-- Useful indexes for the new endpoints.
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'ix_message_mentor_mentee_sent_at_v5' AND object_id = OBJECT_ID(N'[message]'))
BEGIN
    CREATE INDEX ix_message_mentor_mentee_sent_at_v5 ON [message](mentor_id, mentee_id, sent_at DESC, message_id DESC);
END;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'ix_payment_status_date_v5' AND object_id = OBJECT_ID(N'[payment]'))
BEGIN
    CREATE INDEX ix_payment_status_date_v5 ON [payment]([status], [date] DESC);
END;
GO

IF OBJECT_ID(N'[course_enrollment]', N'U') IS NOT NULL
BEGIN
    IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'ix_course_enrollment_mentee_v5' AND object_id = OBJECT_ID(N'[course_enrollment]'))
    BEGIN
        CREATE INDEX ix_course_enrollment_mentee_v5 ON [course_enrollment](mentee_id, enrolled_date DESC);
    END;
END;
GO
