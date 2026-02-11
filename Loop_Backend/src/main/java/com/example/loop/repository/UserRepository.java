package com.example.loop.repository;

import com.example.loop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //Finding User by email and FirebaseUid.
    Optional<User> findByEmail(String email);
    Optional<User> findByFirebaseUid(String firebaseUid);
    //Check if user exits or not.
    boolean existsByEmail(String email);
}
