# 🧑‍💼 Employee Management System

A RESTful Spring Boot application to manage employees and departments in an organization. 

---

### Features

- Create, update, delete employees and departments
- Assign employees to departments and managers
- View paginated lists of employees and departments

---

### Tech Stack

- **Java 17**
- **Spring Boot 3.5.3**
- **MySQL**
- **Gradle 8.14.2**
- **JUnit + Mockito**

---

### Setup Instructions

#### 1. Clone the repository
```bash
git clone https://github.com/yourusername/EmployeeManager.git
```
#### 2. Setup your local MySQL DB and configure it in application.properties (src/main/resources/application.properties):
```bash
spring.application.name=EmployeeManager
spring.datasource.url=jdbc:mysql://localhost:3306/employeedb
spring.datasource.username=root
spring.datasource.password=your_password
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.jpa.hibernate.ddl-auto=none
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```
Create MySQL DB.
```bash
CREATE DATABASE employeedb;
```
Please note that the default name of the db is employeedb.If you are changing please remeber to do so in application.properties too:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/employeedb
```
Please remember to give your username and password for succesful configuration.
```bash
spring.datasource.username=root
spring.datasource.password=your_password
```
#### 3. Run the application
```bash
./gradlew bootRun
```
### API Endpoints
#### Employee
| Method | Endpoint                     | Description                         | Params                           |
| ------ | ---------------------------- | -------------------------------     |--------------------------------- |
| POST   | `/employees/create`          | Create a new employee               |                                  |
| PUT    | `/employees/{id}`            | Update employee                     |                                  |
| PATCH  | `/employees/{id}/department` | Set/change department for employee  | UUID departmentId                |
| GET    | `/employees/all`             | Get paginated list of employees     |                                  | 
| GET    | `/employees/id-name-list`    | Get paginated list of names and ids | Boolean lookup (should be true)  |

#### Department
| Method | Endpoint                      | Description                   | Params                           |
| ------ | --------------------------    | ----------------------------- |-------------------------         |
| POST   | `/departments/create`         | Create a new department       |                                  |
| PUT    | `/departments/{id}/`          | Update department             |                                  |
| DELETE | `/departments/{id}`           | Delete department             |                                  |
| GET    | `/departments/all`            | Paginated list of departments |                                  |
| GET    | `/departments/{id}/employees` | Employees in a department     | Boolean expand (should be true)  |

### Request JSON Schema:
#### Employee
For POST`/employees/create`(create) and PUT`/employees/{id}`(update)
```
{
  "name": "John Doe",
  "dob": "1990-05-15",
  "salary": 65000.00,
  "joiningDate": "2022-01-10",
  "yearlyBonusPercent": 5.5,
  "address": "123 Main Street, Springfield, IL",
  "title": "Software Engineer"
}
```
#### Department
For POST `/departments/create`(create) and PUT `/departments/{id}`(update)
```
{
  "name": "Engineering",
  "creationDate": "2023-04-01"
}
```




