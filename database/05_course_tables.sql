USE [mentor_mentee];
GO

-- Extra tables for the new FE feature: after register/login, users can see courses.
-- Run this after 01_create_database.sql, 02_schema.sql and seed files if you want course support in SQL Server.

IF OBJECT_ID(N'[course_enrollment]', N'U') IS NOT NULL DROP TABLE [course_enrollment];
IF OBJECT_ID(N'[course_lesson]', N'U') IS NOT NULL DROP TABLE [course_lesson];
IF OBJECT_ID(N'[course]', N'U') IS NOT NULL DROP TABLE [course];
GO

CREATE TABLE [course] (
    course_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    mentor_id INT NOT NULL,
    title NVARCHAR(180) NOT NULL,
    description NVARCHAR(MAX) NULL,
    [level] NVARCHAR(80) NULL,
    price DECIMAL(10,2) NOT NULL DEFAULT 0,
    created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME(),
    [status] NVARCHAR(40) NOT NULL DEFAULT N'Active',
    CONSTRAINT fk_course_mentor FOREIGN KEY (mentor_id) REFERENCES [mentor](user_id)
);
GO

CREATE TABLE [course_lesson] (
    lesson_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    course_id INT NOT NULL,
    lesson_title NVARCHAR(180) NOT NULL,
    lesson_order INT NOT NULL,
    video_url NVARCHAR(500) NULL,
    content NVARCHAR(MAX) NULL,
    CONSTRAINT fk_course_lesson_course FOREIGN KEY (course_id) REFERENCES [course](course_id)
);
GO

CREATE TABLE [course_enrollment] (
    enrollment_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    course_id INT NOT NULL,
    mentee_id INT NOT NULL,
    enrolled_date DATE NOT NULL DEFAULT CONVERT(date, SYSUTCDATETIME()),
    progress INT NOT NULL DEFAULT 0 CHECK (progress BETWEEN 0 AND 100),
    [status] NVARCHAR(40) NOT NULL DEFAULT N'In Progress',
    CONSTRAINT fk_course_enrollment_course FOREIGN KEY (course_id) REFERENCES [course](course_id),
    CONSTRAINT fk_course_enrollment_mentee FOREIGN KEY (mentee_id) REFERENCES [mentee](user_id),
    CONSTRAINT uq_course_enrollment UNIQUE (course_id, mentee_id)
);
GO

INSERT INTO [course] (mentor_id, title, description, level, price, status) VALUES
(2, N'Java Backend Career Track', N'Java, Spring Boot, REST API, SQL Server and mock interview roadmap.', N'Beginner to Intermediate', 79.00, N'Active'),
(1, N'React Portfolio Sprint', N'React/Vite project with routing, auth flow, dashboard and deployment checklist.', N'Beginner', 59.00, N'Active'),
(9, N'Data Engineering Roadmap', N'SQL, Python, ETL and warehouse modeling with practical projects.', N'Intermediate', 69.00, N'Active');
GO

INSERT INTO [course_lesson] (course_id, lesson_title, lesson_order, content) VALUES
(1, N'Backend roadmap overview', 1, N'Understand backend fundamentals and project structure.'),
(1, N'Spring Boot REST API', 2, N'Create controllers, services and repositories.'),
(1, N'SQL Server schema review', 3, N'Review MentorHub schema and relationship design.'),
(2, N'React routing', 1, N'Use react-router-dom for separate pages.'),
(2, N'Role protected dashboard', 2, N'Create protected routes for Mentee, Mentor and Admin.'),
(3, N'Data roadmap', 1, N'Select SQL, Python and ETL milestones.');
GO

-- Demo enrollments for existing mentees in Testcase_sqlserver(1).sql
INSERT INTO [course_enrollment] (course_id, mentee_id, progress, status) VALUES
(1, 3, 45, N'In Progress'),
(2, 3, 30, N'In Progress'),
(3, 5, 15, N'In Progress'),
(1, 19, 60, N'In Progress');
GO
