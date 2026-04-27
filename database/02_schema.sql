USE [mentor_mentee];
GO

-- Drop tables in dependency order for repeatable local setup
IF OBJECT_ID(N'[rate]', N'U') IS NOT NULL DROP TABLE [rate];
IF OBJECT_ID(N'[message]', N'U') IS NOT NULL DROP TABLE [message];
IF OBJECT_ID(N'[wishlist_session]', N'U') IS NOT NULL DROP TABLE [wishlist_session];
IF OBJECT_ID(N'[wishlist]', N'U') IS NOT NULL DROP TABLE [wishlist];
IF OBJECT_ID(N'[payment]', N'U') IS NOT NULL DROP TABLE [payment];
IF OBJECT_ID(N'[mentee_session]', N'U') IS NOT NULL DROP TABLE [mentee_session];
IF OBJECT_ID(N'[discount]', N'U') IS NOT NULL DROP TABLE [discount];
IF OBJECT_ID(N'[session]', N'U') IS NOT NULL DROP TABLE [session];
IF OBJECT_ID(N'[available_time]', N'U') IS NOT NULL DROP TABLE [available_time];
IF OBJECT_ID(N'[work_for]', N'U') IS NOT NULL DROP TABLE [work_for];
IF OBJECT_ID(N'[company]', N'U') IS NOT NULL DROP TABLE [company];
IF OBJECT_ID(N'[mentor_skills]', N'U') IS NOT NULL DROP TABLE [mentor_skills];
IF OBJECT_ID(N'[skill_category]', N'U') IS NOT NULL DROP TABLE [skill_category];
IF OBJECT_ID(N'[skills]', N'U') IS NOT NULL DROP TABLE [skills];
IF OBJECT_ID(N'[category]', N'U') IS NOT NULL DROP TABLE [category];
IF OBJECT_ID(N'[user_notification]', N'U') IS NOT NULL DROP TABLE [user_notification];
IF OBJECT_ID(N'[notification]', N'U') IS NOT NULL DROP TABLE [notification];
IF OBJECT_ID(N'[user_phone_number]', N'U') IS NOT NULL DROP TABLE [user_phone_number];
IF OBJECT_ID(N'[manage]', N'U') IS NOT NULL DROP TABLE [manage];
IF OBJECT_ID(N'[mentor]', N'U') IS NOT NULL DROP TABLE [mentor];
IF OBJECT_ID(N'[mentee]', N'U') IS NOT NULL DROP TABLE [mentee];
IF OBJECT_ID(N'[user]', N'U') IS NOT NULL DROP TABLE [user];
IF OBJECT_ID(N'[admin]', N'U') IS NOT NULL DROP TABLE [admin];
GO

CREATE TABLE [admin] (
    admin_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    email NVARCHAR(255) NOT NULL UNIQUE,
    [password] NVARCHAR(255) NULL,
    first_name NVARCHAR(100) NOT NULL,
    last_name NVARCHAR(100) NOT NULL,
    [role] NVARCHAR(60) NOT NULL,
    is_email_verified BIT NOT NULL DEFAULT 0
);
GO

CREATE TABLE [user] (
    user_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    email NVARCHAR(255) NOT NULL UNIQUE,
    [password] NVARCHAR(255) NOT NULL,
    first_name NVARCHAR(100) NOT NULL,
    last_name NVARCHAR(100) NOT NULL,
    sex NVARCHAR(20) NULL,
    [status] NVARCHAR(40) NOT NULL DEFAULT N'Active',
    current_job NVARCHAR(150) NULL,
    [address] NVARCHAR(255) NULL,
    country NVARCHAR(100) NULL,
    time_zone NVARCHAR(80) NULL,
    avatar NVARCHAR(500) NULL,
    is_email_verified BIT NOT NULL DEFAULT 0
);
GO

CREATE TABLE [manage] (
    user_id INT NOT NULL,
    admin_id INT NOT NULL,
    CONSTRAINT pk_manage PRIMARY KEY (user_id, admin_id),
    CONSTRAINT fk_manage_user FOREIGN KEY (user_id) REFERENCES [user](user_id),
    CONSTRAINT fk_manage_admin FOREIGN KEY (admin_id) REFERENCES [admin](admin_id)
);
GO

CREATE TABLE [user_phone_number] (
    user_id INT NOT NULL,
    phone_number NVARCHAR(30) NOT NULL,
    CONSTRAINT pk_user_phone_number PRIMARY KEY (user_id, phone_number),
    CONSTRAINT fk_phone_user FOREIGN KEY (user_id) REFERENCES [user](user_id)
);
GO

CREATE TABLE [mentee] (
    user_id INT NOT NULL PRIMARY KEY,
    learning_goal NVARCHAR(MAX) NULL,
    [level] NVARCHAR(60) NULL,
    CONSTRAINT fk_mentee_user FOREIGN KEY (user_id) REFERENCES [user](user_id)
);
GO

CREATE TABLE [mentor] (
    user_id INT NOT NULL PRIMARY KEY,
    expertise NVARCHAR(MAX) NOT NULL,
    [language] NVARCHAR(200) NULL,
    experience_years INT NOT NULL DEFAULT 0,
    avg_star DECIMAL(3,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_mentor_user FOREIGN KEY (user_id) REFERENCES [user](user_id)
);
GO

CREATE TABLE [notification] (
    notification_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    admin_id INT NOT NULL,
    target_role NVARCHAR(20) NOT NULL DEFAULT N'ALL',
    title NVARCHAR(200) NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    sent_date DATE NOT NULL,
    sent_time TIME(0) NOT NULL,
    CONSTRAINT fk_notification_admin FOREIGN KEY (admin_id) REFERENCES [admin](admin_id)
);
GO

CREATE TABLE [user_notification] (
    user_id INT NOT NULL,
    notification_id INT NOT NULL,
    is_read BIT NOT NULL DEFAULT 0,
    CONSTRAINT pk_user_notification PRIMARY KEY (user_id, notification_id),
    CONSTRAINT fk_user_notification_user FOREIGN KEY (user_id) REFERENCES [user](user_id),
    CONSTRAINT fk_user_notification_notification FOREIGN KEY (notification_id) REFERENCES [notification](notification_id)
);
GO

CREATE TABLE [category] (
    category_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    category_name NVARCHAR(120) NOT NULL,
    category_super_id INT NULL,
    CONSTRAINT fk_category_parent FOREIGN KEY (category_super_id) REFERENCES [category](category_id)
);
GO

CREATE TABLE [skills] (
    skill_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    skill_name NVARCHAR(120) NOT NULL UNIQUE
);
GO

CREATE TABLE [skill_category] (
    skill_id INT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT pk_skill_category PRIMARY KEY (skill_id, category_id),
    CONSTRAINT fk_skill_category_skill FOREIGN KEY (skill_id) REFERENCES [skills](skill_id),
    CONSTRAINT fk_skill_category_category FOREIGN KEY (category_id) REFERENCES [category](category_id)
);
GO

CREATE TABLE [mentor_skills] (
    mentor_id INT NOT NULL,
    skill_id INT NOT NULL,
    CONSTRAINT pk_mentor_skills PRIMARY KEY (mentor_id, skill_id),
    CONSTRAINT fk_mentor_skills_mentor FOREIGN KEY (mentor_id) REFERENCES [mentor](user_id),
    CONSTRAINT fk_mentor_skills_skill FOREIGN KEY (skill_id) REFERENCES [skills](skill_id)
);
GO

CREATE TABLE [company] (
    company_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    company_name NVARCHAR(160) NOT NULL UNIQUE
);
GO

CREATE TABLE [work_for] (
    mentor_id INT NOT NULL,
    company_id INT NOT NULL,
    job_title NVARCHAR(160) NOT NULL,
    start_day DATE NOT NULL,
    end_day DATE NULL,
    CONSTRAINT pk_work_for PRIMARY KEY (mentor_id, company_id, job_title, start_day),
    CONSTRAINT fk_work_for_mentor FOREIGN KEY (mentor_id) REFERENCES [mentor](user_id),
    CONSTRAINT fk_work_for_company FOREIGN KEY (company_id) REFERENCES [company](company_id)
);
GO

CREATE TABLE [available_time] (
    available_time_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    mentor_id INT NOT NULL,
    start_day DATE NOT NULL,
    end_day DATE NOT NULL,
    start_time TIME(0) NOT NULL,
    end_time TIME(0) NOT NULL,
    CONSTRAINT fk_available_time_mentor FOREIGN KEY (mentor_id) REFERENCES [mentor](user_id)
);
GO

CREATE TABLE [session] (
    session_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    mentor_id INT NOT NULL,
    description NVARCHAR(MAX) NULL,
    session_name NVARCHAR(180) NOT NULL,
    duration INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_session_mentor FOREIGN KEY (mentor_id) REFERENCES [mentor](user_id)
);
GO

CREATE TABLE [discount] (
    discount_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    discount_name NVARCHAR(160) NOT NULL,
    discount_value DECIMAL(10,2) NOT NULL,
    start_day DATETIME2(0) NOT NULL,
    end_day DATETIME2(0) NOT NULL,
    code NVARCHAR(60) NOT NULL UNIQUE,
    [status] NVARCHAR(40) NOT NULL
);
GO

CREATE TABLE [mentee_session] (
    mentee_session_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    mentee_id INT NOT NULL,
    session_id INT NOT NULL,
    discount_id INT NULL,
    [date] DATE NOT NULL,
    start_time TIME(0) NOT NULL,
    end_time TIME(0) NOT NULL,
    link NVARCHAR(500) NULL,
    CONSTRAINT fk_mentee_session_mentee FOREIGN KEY (mentee_id) REFERENCES [mentee](user_id),
    CONSTRAINT fk_mentee_session_session FOREIGN KEY (session_id) REFERENCES [session](session_id),
    CONSTRAINT fk_mentee_session_discount FOREIGN KEY (discount_id) REFERENCES [discount](discount_id)
);
GO

CREATE TABLE [payment] (
    payment_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    mentee_session_id INT NOT NULL,
    [date] DATE NOT NULL,
    total_money DECIMAL(10,2) NOT NULL,
    [status] NVARCHAR(40) NOT NULL,
    CONSTRAINT fk_payment_mentee_session FOREIGN KEY (mentee_session_id) REFERENCES [mentee_session](mentee_session_id)
);
GO

CREATE TABLE [wishlist] (
    wishlist_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    mentee_id INT NOT NULL,
    wishlist_name NVARCHAR(160) NOT NULL,
    CONSTRAINT fk_wishlist_mentee FOREIGN KEY (mentee_id) REFERENCES [mentee](user_id)
);
GO

CREATE TABLE [wishlist_session] (
    session_id INT NOT NULL,
    wishlist_id INT NOT NULL,
    CONSTRAINT pk_wishlist_session PRIMARY KEY (session_id, wishlist_id),
    CONSTRAINT fk_wishlist_session_session FOREIGN KEY (session_id) REFERENCES [session](session_id),
    CONSTRAINT fk_wishlist_session_wishlist FOREIGN KEY (wishlist_id) REFERENCES [wishlist](wishlist_id)
);
GO

CREATE TABLE [message] (
    message_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    mentor_id INT NOT NULL,
    mentee_id INT NOT NULL,
    sender_role NVARCHAR(30) NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    sent_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_message_mentor FOREIGN KEY (mentor_id) REFERENCES [mentor](user_id),
    CONSTRAINT fk_message_mentee FOREIGN KEY (mentee_id) REFERENCES [mentee](user_id)
);
GO

CREATE TABLE [rate] (
    mentor_id INT NOT NULL,
    mentee_id INT NOT NULL,
    comment NVARCHAR(MAX) NULL,
    star INT NOT NULL CHECK (star BETWEEN 1 AND 5),
    rated_date DATE NOT NULL,
    CONSTRAINT pk_rate PRIMARY KEY (mentor_id, mentee_id, rated_date),
    CONSTRAINT fk_rate_mentor FOREIGN KEY (mentor_id) REFERENCES [mentor](user_id),
    CONSTRAINT fk_rate_mentee FOREIGN KEY (mentee_id) REFERENCES [mentee](user_id)
);
GO

CREATE INDEX ix_mentor_avg_star ON [mentor](avg_star DESC);
CREATE INDEX ix_session_mentor_price ON [session](mentor_id, price);
CREATE INDEX ix_mentee_session_date ON [mentee_session]([date], start_time);
CREATE INDEX ix_message_pair_sent_at ON [message](mentor_id, mentee_id, sent_at DESC);
GO
