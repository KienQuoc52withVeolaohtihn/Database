USE [mentor_mentee];
GO

-- Run this if you already ran database scripts before notification BE was updated.
-- It keeps old data and adds the column/index/demo notification needed by the new API.

IF COL_LENGTH('notification', 'target_role') IS NULL
BEGIN
    ALTER TABLE [notification] ADD target_role NVARCHAR(20) NOT NULL CONSTRAINT df_notification_target_role DEFAULT N'ALL';
END;
GO

DECLARE @demoAdminId INT = (SELECT TOP 1 admin_id FROM [admin] ORDER BY admin_id);
DECLARE @demoMentorId INT = (SELECT user_id FROM [user] WHERE email = N'mentor@mentorhub.dev');
DECLARE @demoMenteeId INT = (SELECT user_id FROM [user] WHERE email = N'mentee@mentorhub.dev');
DECLARE @demoNotificationId INT;

IF @demoAdminId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM [notification] WHERE title = N'Admin announcement demo')
BEGIN
    INSERT INTO [notification] (admin_id, target_role, title, content, sent_date, sent_time)
    VALUES (@demoAdminId, N'ALL', N'Admin announcement demo', N'Đây là thông báo demo từ Admin. Mentor và Mentee đều sẽ thấy thông báo này trong Dashboard.', CONVERT(date, SYSDATETIME()), CONVERT(time(0), SYSDATETIME()));
    SET @demoNotificationId = SCOPE_IDENTITY();

    IF @demoMentorId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM [user_notification] WHERE user_id = @demoMentorId AND notification_id = @demoNotificationId)
        INSERT INTO [user_notification] (user_id, notification_id, is_read) VALUES (@demoMentorId, @demoNotificationId, 0);
    IF @demoMenteeId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM [user_notification] WHERE user_id = @demoMenteeId AND notification_id = @demoNotificationId)
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
