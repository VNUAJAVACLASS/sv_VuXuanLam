create database baitoantinchi;
use baitoantinchi;

create table tbl_users (
	user_code varchar(50) PRIMARY KEY,
    fullname nvarchar(100) NULL,
    address nvarchar(150) NULL,
    class varchar(50) NULL,
    password nvarchar(50) NULL,
    user_type int
);

CREATE TABLE tbl_subject (
    subject_code VARCHAR(50) PRIMARY KEY,
    subject_name NVARCHAR(100),
    credit INT,
    hesodiem1 FLOAT,
    hesodiem2 FLOAT,
    hesodiem3 FLOAT,
    hesodiem4 FLOAT,
    hesodiem5 FLOAT
);

CREATE TABLE tbl_usersubject (
    user_code VARCHAR(50),
    subject_code VARCHAR(50),
    attendanceexammark FLOAT DEFAULT 0,
    midexammark1 FLOAT DEFAULT 0,
    midexammark2 FLOAT DEFAULT 0,
    midexammark3 FLOAT DEFAULT 0,
    finalexammark FLOAT DEFAULT 0,
    PRIMARY KEY (user_code, subject_code),
    FOREIGN KEY (user_code) REFERENCES tbl_users(user_code),
    FOREIGN KEY (subject_code) REFERENCES tbl_subject(subject_code)
);
INSERT INTO tbl_users (user_code, fullname, address, class, password, user_type) VALUES
('U001', N'Nguyễn Văn A', N'Hà Nội', 'CNTT01', N'123456', 0),
('U002', N'Trần Thị B', N'Nam Định', 'CNTT02', N'abcdef', 1),
('U003', N'Lâm đẹp trai', N'Nam Định', 'CNTT03', N'anhyeuem', 1);
INSERT INTO tbl_subject (subject_code, subject_name, credit, hesodiem1, hesodiem2, hesodiem3, hesodiem4, hesodiem5) VALUES
('MH001', N'Cơ sở dữ liệu', 3, 0.1, 0.1, 0.2, 0.2, 0.4),
('MH002', N'Lập trình Java', 4, 0.1, 0.15, 0.15, 0.2, 0.4),
('MH003', N'Triết học', 2, 0.1, 0.1, 0.2, 0.2, 0.4);
INSERT INTO tbl_usersubject (user_code, subject_code, attendanceexammark, midexammark1, midexammark2, midexammark3, finalexammark) VALUES
('U001', 'MH001', 8, 7, 6, 7, 8.5),
('U001', 'MH002', 9, 8, 7, 9, 9),
('U002', 'MH002', 6, 6, 6, 6, 7),
('U003', 'MH003', 10, 10, 9, 9, 10);



