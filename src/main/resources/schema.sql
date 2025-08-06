CREATE TABLE IF NOT EXISTS department (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(100),
    creation_date DATE,
    head_id BINARY(16)
);

CREATE TABLE IF NOT EXISTS employee (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(100),
    dob DATE,
    salary DECIMAL(15, 2),
    joining_date DATE,
    yearly_bonus_percent DOUBLE,
    address TEXT,
    title VARCHAR(100),
    department_id BINARY(16),
    manager_id BINARY(16),
    password VARCHAR(100) ,
    role VARCHAR(50) ,

    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (manager_id) REFERENCES employee(id)
);
CREATE TABLE IF NOT EXISTS attendance(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BINARY(16),
    attendance_date DATE,
    is_present BOOLEAN,
    check_in_time TIME,
    check_out_time TIME,
    status VARCHAR(50), 
    
    CONSTRAINT fk_employee
        FOREIGN KEY (employee_id)
        REFERENCES employee(id),
        
    CONSTRAINT uc_employee_date UNIQUE (employee_id, attendance_date)
);

