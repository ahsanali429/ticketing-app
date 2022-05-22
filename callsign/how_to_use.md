### How to use the API's?
The application by default runs on port 8080 and exposes following endpoints

1. POST `/authenticate` Expects 2 parameters in the request body, 
    
    a. `username`
    b. `password`
    
    It returns the JWT in the response and with this jwt anyone can use the others API.
    
 2. GET `/tickets` Expects parameter for authorization in the header parameters like 
 `Authorization` : `Bearer {jwt token}`. If the token is invalid/expired, the request will not be served, otherwise it will return tickets by priority order
