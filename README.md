# Call Center Backend v2.0

[Frontend](https://comcom.at/)
[Frontend Admin/User APP](https://app.comcom.at/)
[API Server](http://91.151.16.36:8001/api/swagger-ui/index.html#/)
A Call Center Software.

To run this software successfully MySQL 8.0 is needed.

Download MYSQL Community Server 8.0.35 ==> [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)

Now install and run the MySQL DB

## LOCALHOST CONFIGURATION

#### ACCESS MYSQL DB

- mysql -u root -p

#### CREATE DATABASE

- CREATE DATABASE comcom_db;

#### CREATE A NEW USER

- CREATE USER 'admin'@'localhost' IDENTIFIED BY 'password';

#### GRANT PERMISSION TO COMCOM DB

- GRANT ALL PRIVILEGES ON comcom_db.\* TO 'admin'@'localhost';

#### FLUSH

- FLUSH PRIVILEGES;

---

### LOCAL DATABASE

**Database Name**: comcom_db

**Username**: admin

**Password**: password

### DEVELOPMENT DATABASE ON THE SERVER

**Database Name**: comcom_database

**Username**: admin

**Password**: password

---

## Running the Project & Accessing Swagger UI

### Using IntelliJ IDEA

Open the api-gateway folder and run the file **ApiGatewayModule.java**

Same for the auth-service folder, run the file **AuthServiceModule.java**

### Using Terminal

When found in the project callcenter_backend

Type:

- mvn clean package

Now
Navigate to the Api-Gateway

Type:

- mvn spring-boot:run

Lastly
Navigate to the Auth-Service
Type:

- mvn spring-boot:run

#### ACCESSING SWAGGER UI

- When the two apps are running successfully, click here ==> [Swagger UI](http://localhost:8001/api/swagger-ui/index.html)

---

### PORTS USAGE

**API GATEWAY**: 8000

**AUTH SERVICE**: 8001

**CORE SERVICE**: 8002
