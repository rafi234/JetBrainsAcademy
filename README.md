# JetBrainsAcademy

## Web Quiz Engine
This project uses Spring Boot, Spring Security, Hibernate and is connected to H2 databse. 
### REST API request in Web Quiz Engine

#### With authorization
1. POST /api/quizzes - create new quiz in database and user who created it.
2. GET /api/quizzes/{id} - get quiz with given id.
3. GET /api/quizzes - return Slice instance with 10 quizzes on given page - @ParamRequest.
4. POST /api/quizzes/{id}/solve - solve quiz with given id as @PathVariable and answer as @BodyRequest. User which solve it is saved to database.
5. GET /api/quizzes/completed - return Slice just like in GET /api/quizzes and in his content are quizzes solved by logged in user. 
6. DELETE /api/quizzes/{id} - you can delete quiz with given id only if you create it.

#### Without authorization
1. POST /api/register - you can register new user and saved him in database.


## Cinema Room REST service
It uses Spring Framework.

## Car Sharing
This aplication is a simple simulation of car sharing aplication it uses H2 database to hold the data.

## Bulls and cows
It is a simple game where program draws a random string and user have to guess it. 
When user answer isn't correct but given string contains symbol program writes cow and for every correct symbol user gets one cow.
Also, when given input isn't correct but some symbols matches the places then user gets one bull for every symbol.
