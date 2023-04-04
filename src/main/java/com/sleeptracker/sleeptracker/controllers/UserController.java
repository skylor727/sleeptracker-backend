package com.sleeptracker.sleeptracker.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
public class UserController {
    String googleId = System.getenv("GOOGLE_ID");

    @PostMapping(value = "/sleep-api/get-user")
    public boolean validateAccessToken(String accessToken, String userEmail) {
        JsonFactory jsonFactory = new JacksonFactory();
        NetHttpTransport transport = new NetHttpTransport();

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(googleId))
                    .build();
            GoogleIdToken idToken = verifier.verify(accessToken);
            if (idToken != null) {
                String email = idToken.getPayload().getEmail();
                return userEmail.equals(email);
            }
        } catch (IOException | GeneralSecurityException e) {
            System.out.println(e);
        }
        return false;
    }
}