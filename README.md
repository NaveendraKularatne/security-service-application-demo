# security-service-application-demo
This application was created to secure backend using JWT, Spring Boot and Spring Security. I have implemented JWT access and refresh tokens.

JWT (Jason Web Token) is just a way for an applications to transmit information. Basically it is a way for application A to talk to application B to transmit information.
And JWT s very small and self-contained and it's also very secure because it's small.
We can pass it and the request bodd or in the headers. 

The way JWT works is: 
Application is going to create the token, take the user information, and then sign the token. That signature is digital. 
And whenever the user needs to access the information, the user will send the token to check their authorization.

Another important concept is the concept of authentication and authorization.

•  Authentication - Basically, authentication is verifying who you are. So let's say you want to access an application, you need to provide some email or username and password. 
So this is verifying who you are. 

•	 Authorization can be recognize as everybody has access to the application, but they don't have the same access to the resources.
Let's say we have some organization, and they have simple users. They have managers, they have admins.
So they can all access the application. That means all authenticate with the application because they need them to be able to access the application to do whatever they need to do in the company. 
But they can't do the same things. Or they can access the same resources. 

A good example for Authentication and Authorization is a building. So can you enter the building? That's authentication. 
After entering the building, but can you access a specific room, or can you access a specific floor? That's authorization.

Created both Access Token and Refresh Token, therefore users have access to the application even if their access token is expired,
but if their refresh token is still not expired.

![3](https://user-images.githubusercontent.com/44170368/192162785-1569ef90-8422-4be8-b4a1-cae993492231.png)

![6](https://user-images.githubusercontent.com/44170368/192162620-9270b159-f18a-4221-b655-6d3735165b8f.png)
