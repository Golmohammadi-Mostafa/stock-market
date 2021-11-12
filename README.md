# 
This Application use Spring security and jwt for authorization and authentication.
<br />
you can run these commands for running Application:
<br />
1- `mvn clean package`

2- `docker-compose -f docker-compose.yaml up --build` 

After running the program, 
you can use
<br />
`username`: admin & `password`: 123 for ADMIN_ROLE
<br />
and
<br />
`username`: user & `password`: 123 for  USER_ROLE
<br />
When the program is running on port 8080 you can see all Rest APIs: http://localhost:8080/swagger-ui.html#
<br />
APIs:
http://localhost:8080/swagger-ui.html#   //see all Rest APIs

<br />
http://localhost:8080/users/signin?password=123&username=admin  // for get token
<br />
GET: http://localhost:8080/api/stocks  // for get all stock 
<br />
POST: http://localhost:8080/api/stocks // 
<br />
PATCH: http://localhost:8080/api/stocks/1 // 
<br />
DELETE: http://localhost:8080/api/stocks/1 // 



