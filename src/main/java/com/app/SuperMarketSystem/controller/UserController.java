package com.app.SuperMarketSystem.controller;

import com.app.SuperMarketSystem.dto.ApiResponse;
import com.app.SuperMarketSystem.model.User;
import com.app.SuperMarketSystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public List<User> list() {
        return userService.findAllUsers();
    }

    @PostMapping("/save")
    public ApiResponse save(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable(name = "id") Integer userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/getBy/{id}")
    public User getById(@PathVariable(name = "id") Integer userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/getOrdersByUserId/{id}")
    public ApiResponse getOrdersByUserId(@PathVariable(name = "id") Integer userId) {
        return userService.getOrdersByUserId(userId);
    }
}