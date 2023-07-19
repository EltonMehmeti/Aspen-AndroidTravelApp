package com.elton.androidtouristapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        User user = repository.findByEmailAndPassword(email, password);

        if (user != null) {
            // Successful login
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("user", user); // Include the user details in the response
            return ResponseEntity.ok(response);
        } else {
            // Invalid email or password
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }



    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        // Check if the email is already registered
        if (repository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already registered.");
        }
        Map<String, Object> response = new HashMap<>();
        // Save the user in the database
        repository.save(user);

        response.put("success", true);
        response.put("message", "Login successful");

        return ResponseEntity.ok(response.toString());

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = repository.findAll();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable int id) {

        repository.deleteById(id);
    }

}
