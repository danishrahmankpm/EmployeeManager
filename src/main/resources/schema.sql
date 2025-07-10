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
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (manager_id) REFERENCES employee(id)
);
