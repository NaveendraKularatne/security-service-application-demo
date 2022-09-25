package com.kularatne.userservice.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kularatne.userservice.domain.Role;
import com.kularatne.userservice.domain.User;
import com.kularatne.userservice.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController //To make the class controller
@RequestMapping("/api")
@RequiredArgsConstructor //So that we can inject below userService field into the constructor
@Slf4j
public class UserResource {
    //injecting the service
    private final UserService userService;

    //creating a method to return a list of all the users
    @GetMapping("/Users")
    public ResponseEntity<List<User>> getUsers() {
        //Call the service and have it return the users from the database
        return ResponseEntity.ok().body(userService.getUsers()); /*How did you return the data from controller to
        client(browser/postman/another app)? I used a response entity, and sent the response (user list) inside its body */
    }
    /* How did you create an api/REST api / whole process/ procedure?
        Called the service from the controller and passed the required parameters if there were any,
        In service layer, I handled the business logic
        (ex: validations (i.e. name should be not null, length should not exceed 50 characters)) and
        then I did the database transactions through the repository layer.*/


    //Save a user
    @PostMapping("/user/save")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
        log.info("UserController: createUser: Creating user");

        //it's more precise if we can send 201 instead of sending 200 (success),
        // because these methods are creating something (resources are created) inside the server
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    //Save a role
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
        log.info("UserResponse(UserController): saveRole: Saving a role");
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    //Add a role to a specific user
    @PostMapping("/role/addroletouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        log.info("UserResponse(UserController): addRoleToUser: Assigning role to user:");
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh Token is missing");
        }
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}












