package com.example.loop.controller;

import com.example.loop.model.User;
import com.example.loop.security.FirebaseTokenVerifier;
import com.example.loop.service.UserService;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private FirebaseTokenVerifier tokenVerifier;
    @Autowired
    private UserService userService;

    public UserController(FirebaseTokenVerifier tokenVerifier, UserService userService){
        this.userService = userService;
        this.tokenVerifier = tokenVerifier;
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestHeader(value = "Authorization", required = false) String authHeader){
        try{
            FirebaseToken decodedToken = tokenVerifier.verifyIdToken(authHeader);
            String uid = decodedToken.getUid();
            String name = decodedToken.getName();
            String email = decodedToken.getEmail();

            User saved = userService.saveOrUpdateFirebaseUser(name, email, uid);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid Token: " + e.getMessage());
        }
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
