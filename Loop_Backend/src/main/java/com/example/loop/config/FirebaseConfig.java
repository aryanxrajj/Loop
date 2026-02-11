package com.example.loop.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FirebaseConfig {
    @PostConstruct
    public void initialize() {
        System.out.println("🔍 FirebaseConfig initialization started...");
        try {
            InputStream serviceAccount =
                    getClass().getResourceAsStream("/firebase/serviceAccountKey.json");

            if (serviceAccount == null) {
                System.err.println("Firebase serviceAccountKey.json not found in resources!");
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase has been initialized successfully!");
            } else {
                System.out.println("⚙️ Firebase already initialized.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("🔥 Failed to initialize Firebase: " + e.getMessage());
        }
    }
}
