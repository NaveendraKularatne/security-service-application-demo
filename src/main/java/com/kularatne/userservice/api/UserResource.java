package com.kularatne.userservice.api;

import com.kularatne.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //To make the class controller
@RequestMapping("/api")
@RequiredArgsConstructor //So that we can inject below userService field into the constructor
public class UserResource {
    //injecting the service
    private final UserService userService;

    //creating a method to return a list of users
}
