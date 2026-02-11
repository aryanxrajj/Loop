package com.example.loop.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Component;

@Component
public class FirebaseTokenVerifier {
    public FirebaseToken verifyIdToken(String bearerHeader) throws FirebaseAuthException {
        if(bearerHeader == null || !bearerHeader.startsWith("Bearer ")){
            throw new IllegalArgumentException("Missing Authorization header: Bearer <token>");
        }

        String token = bearerHeader.substring(7).trim();
        return FirebaseAuth.getInstance().verifyIdToken(token);
    }
}
