package com.enershare.controller.user;

import com.enershare.dto.response.SuccessResponse;
import com.enershare.dto.user.UserDTO;
import com.enershare.exception.EmailAlreadyExistsException;
import com.enershare.exception.EmailNotFoundException;
import com.enershare.service.auth.JwtService;
import com.enershare.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final JwtService jwtService;

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

    @GetMapping("/total-users")
    public long getTotalUsers() {
        return userService.getTotalUsers();
    }

    @GetMapping(path = "/all-users")
    Page<UserDTO> getUsers(@RequestParam int page, @RequestParam int size) {
        return userService.getUsers(page, size);
    }

    @PostMapping("/create")
    public void createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
    }

    @DeleteMapping
    public void deleteObject(@RequestParam("id") Long id) {
        userService.deleteObject(id);
    }

    @GetMapping(path = "/by-id")
    UserDTO getObject(@RequestParam("id") Long id) {
        return userService.getObject(id);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
    }

    @PostMapping("/register-user")
    public ResponseEntity<SuccessResponse> registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }



}
