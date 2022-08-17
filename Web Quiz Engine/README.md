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
