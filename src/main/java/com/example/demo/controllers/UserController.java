package com.example.demo.controllers;
import com.example.demo.dto.UserDto;
import com.example.demo.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(path = "/health-check")
    public String healthCheck(){
        return "Hello";
    }

    @GetMapping()
    public List<UserDto> getAllUser(){
        return userService.fetchAllUsers();
    }

    @PostMapping()
    public UserDto addUser(@RequestBody UserDto userDto){
        return userService.addNewUser(userDto);
    }

    @PutMapping(path = "/{userId}")
    public UserDto updateUser(@PathVariable Long userId , @RequestBody UserDto userDetails){
        return userService.updateUser(userId,userDetails);
    }

    @DeleteMapping(path = "/{userId}")
    public boolean deleteUser(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }

    @PatchMapping(path = "/{userId}")
        public UserDto updatePartially(@RequestBody Map<String,Object> update, @PathVariable Long userId){
        return  userService.updateUserPartially(userId,update);
    }


}
