package com.example.server.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

public class FireBaseInitializer {

    @PostConstruct
    public static void initialize() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/serviceAccountKey.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://memo-ce15c-default-rtdb.firebaseio.com") // Firebase Console에서 확인 가능한 Realtime Database URL
                    .setStorageBucket("memo-ce15c.appspot.com") // Firebase Console에서 확인 가능한 Storage 버킷 URL
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
