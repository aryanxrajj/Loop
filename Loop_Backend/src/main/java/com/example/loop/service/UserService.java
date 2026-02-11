package com.example.loop.service;

import com.example.loop.model.User;
import com.example.loop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveOrUpdateFirebaseUser(String name, String email, String firebaseUid){
        Optional<User> existingUser = userRepository.findByFirebaseUid(firebaseUid);
        User user;

        if(existingUser.isPresent()){
            user = existingUser.get();
            user.setName(name != null ? name : user.getName());
            user.setEmail(email != null ? email : user.getEmail());
        }else{
            user = new User();
            user.setFirebaseUid(firebaseUid);
            user.setName(name != null ? name : "Anonymous");
            user.setEmail(email != null ? email : "no-email@firebase.com");
        }

        return userRepository.save(user);
    }

    public Optional<User> findByFirebaseUid(String firebaseUid){
        return userRepository.findByFirebaseUid(firebaseUid);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers(){
       return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
