# ProjectAPI
Project shows how to work with Trello API. App has 3 entity(Board, List, Card) and you can create, update and etc. with these entities. All trello api's functionality you can see in TrelloClient class. Below is the api documentation. App has JWT authorization.
# Technologies used
### Stack
- Java 11
- Spring: SpringBoot, MVC, Data JPA, Security, Validation
- Maven
- Lombok
- Mysql
- JWT
- Swagger2
# REST APIs requests
```
POST localhost:8081/api/board?name=new project&desc=this is my project - an example endpoint that is restricted to authorized users with the authority 'ROLE_ADMIN' (a valid JWT token must be present in the request header)
POST localhost:8081/api/card/3?name=new card - an example endpoint that is restricted to authorized users with the authority 'ROLE_USER' (a valid JWT token must be present in the request header)
POST localhost:8081/api/task/1?name=new card&desc=this is my new task;

GET localhost:8081/api/board?name=te (get all boards which start with 'te')
GET localhost:8081/api/board (get all boards)
GET localhost:8081/api/card?id=3 (get all cards from board with id 3)
GET localhost:8081/api/task?name=task (get task by name);

PUT localhost:8081/api/card/13?name=hello
PUT localhost:8081/api/board/10?name=training
PUT localhost:8081/api/task/1?name=simple task&desc=my task;

DEL localhost:8081/api/board/2
DEL localhost:8081/api/card/13
DEL localhost:8081/api/task/3
```
# Documentation
https://developer.atlassian.com/cloud/trello/rest/api-group-actions/
# Security
There are three user accounts present to demonstrate the different levels of access to the endpoints in the API:
- Admin,
- User.

There are two endpoints that are reasonable for the demo:
```
- localhost:8081/api/register
{
    "login": "user",
    "password": "12345"
};
{
    "login": "user",
    "password": "12345",
    "role": "ROLE_ADMIN"
}
- localhost:8081/api/auth - authentication endpoint
{
    "login": "user",
    "password": "12345"
}
```
# Run app
1. Clone the application or download the zip file.
```
git clone https://github.com/MaximRom00/ProjectAPI.git
```
2. Create MySQL database
```
create database tracker
```
3. Change MySQL username and password as per your MySQL installation
- open src/main/resources/application.properties file.
- change spring.datasource.username and spring.datasource.password properties as per your mysql installation
4. Run the app
You can run the spring boot app by typing the following command -
```
mvn spring-boot:run
```
The server will start on port 8081.

5. Defaults Roles
The app uses role based authorization powered by spring security. You need to add the default roles in the database. Example
```
insert into role (name) values ('ROLE_ADMIN'), ('ROLE_USER')
```
# Swagger UI
<p align="center">
<img  src="https://user-images.githubusercontent.com/95149324/167943443-18ed1e7e-fb47-46e8-ac96-f0f0703bc433.png" width="750" height="400"> 
                                                                                                                   </p> 
                                                                                                                   
                                                                                                                   
