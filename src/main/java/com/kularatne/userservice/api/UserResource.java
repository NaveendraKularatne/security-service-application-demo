package com.kularatne.userservice.api;

import com.kularatne.userservice.domain.Role;
import com.kularatne.userservice.domain.User;
import com.kularatne.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        log.info("UserController: createUser: Creating user");
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    //Save a role
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        log.info("UserResponse(UserController: saveRole: Saving a role");
        return ResponseEntity.ok().body(userService.saveRole(role));
    }
}


