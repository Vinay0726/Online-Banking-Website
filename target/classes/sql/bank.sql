CREATE DATABASE easybank_db;
USE easybank_db;
-- create user table
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(45) NOT NULL,
    middle_name VARCHAR(45),
    last_name VARCHAR(45) NOT NULL,
    mobile_number VARCHAR(10) NOT NULL,
    email VARCHAR(50) NOT NULL,
    gender CHAR(1) NOT NULL,
    date_of_birth DATE NOT NULL,
    cin VARCHAR(20) NOT NULL,
    adhaar_card VARCHAR(12) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT
);


-- create user credentials
CREATE TABLE user_credential (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    user_name VARCHAR(10) NOT NULL,
    password VARCHAR(200) NOT NULL,
    password_salt VARCHAR(100) NOT NULL,
    login_date_time DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- create account table

CREATE TABLE account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    account_type VARCHAR(10) NOT NULL,
    balance DECIMAL(15,2) NOT NULL,
    account_number VARCHAR(14) NOT NULL,
    rate_of_interest DECIMAL(5,2) NOT NULL,
    branch_id INT NOT NULL,
    opening_date DATE NOT NULL,
    closing_date DATE,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (branch_id) REFERENCES branch(id)
);

CREATE TABLE address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    address TEXT,
    pincode VARCHAR(7) NOT NULL,
    city_id INT NOT NULL,
    district_id INT NOT NULL,
    state_id INT NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (city_id) REFERENCES city(id),
    FOREIGN KEY (district_id) REFERENCES district(id),
    FOREIGN KEY (state_id) REFERENCES state(id)
);
CREATE TABLE state (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    code INT NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT

);
CREATE TABLE district (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    code INT NOT NULL,
    state_id INT NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (state_id) REFERENCES state(id)
);
CREATE TABLE city (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    code INT NOT NULL,
    district_id INT NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (district_id) REFERENCES district(id)
);
CREATE TABLE branch (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    code INT NOT NULL,
    ifsc_code VARCHAR(10) NOT NULL,
    city_id INT NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (city_id) REFERENCES city(id)
);

-- create transaction table
CREATE TABLE transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    account_number VARCHAR(14) NOT NULL,
    remarks TEXT NOT NULL,
    transaction_date_time DATETIME NOT NULL,
    transaction_type ENUM('Credit', 'Debit') NOT NULL,
    transaction_status VARCHAR(12) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Create admin table
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(45) NOT NULL,
    middle_name VARCHAR(45),
    last_name VARCHAR(45) NOT NULL,
    mobile_number VARCHAR(10) NOT NULL,
    email VARCHAR(50) NOT NULL,
    gender CHAR(1) NOT NULL,
    date_of_birth DATE NOT NULL,
    adhaar_card VARCHAR(12) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT
);

-- Create admin_credential table
CREATE TABLE admin_credential (
    id INT AUTO_INCREMENT PRIMARY KEY,
    admin_id INT NOT NULL,
    user_name VARCHAR(10) NOT NULL,
    password VARCHAR(200) NOT NULL,
    password_salt VARCHAR(100) NOT NULL,
    login_date_time DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (admin_id) REFERENCES admin(id)
);

-- Create admin_address table
CREATE TABLE admin_address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    admin_id INT NOT NULL,
    address TEXT,
    state VARCHAR(20) NOT NULL,
    city VARCHAR(20) NOT NULL,
    pincode VARCHAR(7) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (admin_id) REFERENCES admin(id)
);

-- Create employee table
CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(45) NOT NULL,
    middle_name VARCHAR(45),
    last_name VARCHAR(45) NOT NULL,
    mobile_number VARCHAR(10) NOT NULL,
    email VARCHAR(50) NOT NULL,
    gender CHAR(1) NOT NULL,
    date_of_birth DATE NOT NULL,
    adhaar_card VARCHAR(12) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT
);

-- Create employee_credential table
CREATE TABLE employee_credential (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    user_name VARCHAR(10) NOT NULL,
    password VARCHAR(200) NOT NULL,
    password_salt VARCHAR(100) NOT NULL,
    login_date_time DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

-- Create employee_address table
CREATE TABLE employee_address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    address TEXT,
    state VARCHAR(20) NOT NULL,
    city VARCHAR(20) NOT NULL,
    pincode VARCHAR(7) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by INT NOT NULL,
    updated_at DATETIME,
    updated_by INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

--for transfer transaction history
CREATE TABLE transfertransaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL,
    sender_account_number VARCHAR(12) NOT NULL,
    receiver_id INT NOT NULL,
    receiver_account_number VARCHAR(12) NOT NULL,
    transfer_amount DOUBLE NOT NULL,
    sender_balance DOUBLE NOT NULL,
    receiver_balance DOUBLE NOT NULL,
    created_by INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_by INT,
    updated_at DATETIME
);