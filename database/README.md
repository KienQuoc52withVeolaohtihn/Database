# Database scripts

Run SQL Server scripts in this order:

1. `01_create_database.sql`
2. `02_schema.sql`
3. `03_seed_full_testcase.sql`
4. `05_course_tables.sql`
5. `06_backend_alignment.sql`
6. `07_notifications_backend_alignment.sql` nếu bạn đã từng chạy database bản cũ và muốn cập nhật notification không mất dữ liệu.
7. `08_full_backend_alignment.sql` bản v5, chạy thêm để payment/admin notification/demo login/index khớp backend mới nhất.

`06_backend_alignment.sql` adds the mentor wishlist table, demo FE login accounts, notification target role, and indexes used by the Java backend.
`07_notifications_backend_alignment.sql` is an incremental patch for existing local databases.
`08_full_backend_alignment.sql` is safe to run multiple times and aligns the completed backend v5.
