package com.enershare.controller.user;

import com.enershare.model.user.User;

import com.enershare.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<String> testJwt(HttpServletRequest request) {
        String token = jwtService.getJwt(request);
        if (token != null) {
            String userId = jwtService.getUserIdByToken(token);
            return ResponseEntity.ok("User ID: " + userId);
        } else {
            return ResponseEntity.badRequest().body("No JWT token found in the request.");
        }
    }
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestBody User user) {
//        // Perform user registration and save to the database
//        User createdUser = userService.createUser(user);
//
//        if (createdUser != null) {
//            // Return a 201 Created response with the created user
//            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//        } else {
//            // Return a 400 Bad Request response if registration fails
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
////    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
////    @Transactional
////    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO) {
////        return userService.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
////    }
//
//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> userList = userService.getAllUsers();
//        if (!userList.isEmpty()) {
//            // Return a 200 OK response with the list of users
//            return new ResponseEntity<>(userList, HttpStatus.OK);
//        } else {
//            // Return a 404 Not Found response if no users are found
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
//        // Attempt to delete the user by ID
//        if (userService.getUserById(id) != null) {
//            userService.deleteUser(id);
//            // Return a 204 No Content response if the user is successfully deleted
//            log.info("User with ID {} deleted successfully at {}.", id, LocalDateTime.now());
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            // Return a 404 Not Found response if the user with the given ID is not found
//            log.warn("Attempt to delete non-existent user with ID {} at {}.", id, LocalDateTime.now());
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}