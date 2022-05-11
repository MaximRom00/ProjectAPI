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
POST localhost:8081/api/board?name=new project&desc=this is my project
POST localhost:8081/api/card/3?name=new card
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
# Swagger UI
<p align="center">
<img  src="https://user-images.githubusercontent.com/95149324/167943443-18ed1e7e-fb47-46e8-ac96-f0f0703bc433.png" width="750" height="400"> 
                                                                                                                   </p> 
