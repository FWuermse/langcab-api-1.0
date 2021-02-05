package com.flowcode.langcab.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class LoginService {
    public void login() throws Exception {

String s = "MyInformation"
stream = new ByteArrayInputStream(s.getBytes());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .build();

        FirebaseApp.initializeApp(options);
    }
}

