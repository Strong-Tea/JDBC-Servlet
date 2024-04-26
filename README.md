## Video Link:
https://drive.google.com/file/d/1u-Zf2WLv9hndgYExVPihV8foNJN6C6Tx/view?usp=sharing

## Description:

This project is a Java web application built using JDBC, Servlet, PostgreSQL, and Tomcat. It serves as a practical demonstration of performing CRUD (Create, Read, Update, Delete) operations on a relational database within a web environment. By leveraging these technologies, the application provides a robust framework for managing data stored in a PostgreSQL database.

## Created with:

- PostgreSQL 16.2
- Tomсat 10.1.20


## Database example
![Untitled22](https://github.com/Strong-Tea/JDBC-Servlet/assets/135996451/858266cf-a753-4c61-ba63-96e2b4f32548)

## How to use

1) You need to manually create the database you want to connect to. 
JDBC does not support direct database creation.
Because if database name is not specified it will use the username as default database. [PostgreSQL](https://jdbc.postgresql.org/documentation/use/)



2) Set Database Credentials: Set the database username and password in the db.properties file.
~~~
db.url=jdbc:postgresql://localhost:5432/test
db.username=root
db.password=root
~~~

3) Deploy Application: Deploy the application on a Tomcat server or use alternatives.
4) Start Using: Once deployed, you can start using the application to perform CRUD operations.
