# Setup To Run the CallSign (Ticket) App

## Pre-Requisites
1. Java 8+ should be installed on the system
2. MySQL server should be installed and available if not use docker steps to up the mysql server. 

Use the following properties in `application.properties` file to configure the application.
   `spring.datasource.url`
   `spring.datasource.username`
   `spring.datasource.password`
   
   Install docker cli and to start MySQL container, open the project, goto `docker -> mysql` directory and execute `docker-compose up`

### Options to Run the app

#### 1. Open in the IDE and download all the dependencies and run the app. 
#### 2. Run using Docker. 
#### 3. Run from CLI using maven command.
