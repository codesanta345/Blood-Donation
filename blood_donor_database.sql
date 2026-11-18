-- =============================================================
-- Blood Donation - database setup
-- Run this whole file once in MySQL Workbench (or the mysql
-- command line). It is safe to run again: nothing is dropped.
-- =============================================================

CREATE DATABASE IF NOT EXISTS blood_donor_databse;
USE blood_donor_databse;

-- Registered donors
CREATE TABLE IF NOT EXISTS donor_table (
    mobile   VARCHAR(20)  NOT NULL,
    image    VARCHAR(255),
    name     VARCHAR(50),
    gender   VARCHAR(10),
    address  VARCHAR(200),
    city     VARCHAR(50),
    bgroup   VARCHAR(10),
    age      INT,
    disease  VARCHAR(50),
    date     VARCHAR(50),
    PRIMARY KEY (mobile)
);

-- Every donation of one unit
CREATE TABLE IF NOT EXISTS donations (
    mobile          VARCHAR(20),
    bgroup          VARCHAR(10),
    dateofdonation  DATE
);

-- Blood issued to patients
CREATE TABLE IF NOT EXISTS issued (
    name       VARCHAR(50),
    mobile     VARCHAR(20),
    hospital   VARCHAR(100),
    reason     VARCHAR(100),
    issuedate  DATE,
    bgroup     VARCHAR(10),
    units      INT DEFAULT 1
);

-- Units in stock (one row). `On` needs backticks because ON is a MySQL keyword.
CREATE TABLE IF NOT EXISTS total_record (
    Ap   INT DEFAULT 0,
    An   INT DEFAULT 0,
    Bp   INT DEFAULT 0,
    Bn   INT DEFAULT 0,
    ABp  INT DEFAULT 0,
    ABn  INT DEFAULT 0,
    Op   INT DEFAULT 0,
    `On` INT DEFAULT 0
);

-- Create the single stock row only if the table is empty
INSERT INTO total_record (Ap, An, Bp, Bn, ABp, ABn, Op, `On`)
SELECT 0, 0, 0, 0, 0, 0, 0, 0 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM total_record);

-- -------------------------------------------------------------
-- ONLY if you already have the OLD database from the original
-- project, run these lines too (remove the leading "-- " first).
-- They add the columns the updated app needs.
-- -------------------------------------------------------------
-- ALTER TABLE total_record ADD COLUMN Op INT DEFAULT 0, ADD COLUMN `On` INT DEFAULT 0;
-- ALTER TABLE issued ADD COLUMN units INT DEFAULT 1;
-- ALTER TABLE issued ADD COLUMN bgroup VARCHAR(10);   -- skip if it already exists

-- Check everything
SELECT * FROM total_record;
