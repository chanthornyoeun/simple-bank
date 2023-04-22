package com.example.bank.controllers;

import com.example.bank.dto.UserDTO;
import com.example.bank.models.User;
import com.example.bank.services.UserService;
import com.example.bank.utils.ResponseDTO;
import com.example.bank.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity<ResponseDTO> list() {
        Iterable<User> users = this.userService.getAllUsers();
        long totalRecord = this.userService.count();
        return ResponseEntity.ok(ResponseUtil.list(users, totalRecord, true, "List all users"));
    }

    @GetMapping("{id}")
    ResponseDTO get(@PathVariable long id) {
        try {
            User user = this.userService.getUserById(id);
            return ResponseUtil.object(user, true, "Get user by id: " + id);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("Error while getting user: " + id + " " + ex.getMessage());
            return ResponseUtil.object(null, false, ex.getMessage());
        }
    }

    @PostMapping("/register")
    ResponseDTO register(@RequestBody UserDTO payload) {
        try {
            User user = this.userService.register(payload);
            return ResponseUtil.object(user, true, "User has been registered.");
        } catch (Exception ex) {
            LOGGER.error("Error while registering user: " + ex.getMessage());
            return ResponseUtil.object(null, false, ex.getMessage());
        }
    }

    @GetMapping("/current-user")
    ResponseDTO getCurrentUser() {
        try {
            User user = this.userService.getCurrentUser();
            return ResponseUtil.object(user, true, "Get current user");
        } catch (Exception ex) {
            LOGGER.error("Error while getting current user: " + ex.getMessage());
            return ResponseUtil.object(null, false, ex.getMessage());
        }
    }

}
