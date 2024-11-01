package com.sleeptracker.sleeptracker.services;

import com.sleeptracker.sleeptracker.models.User;

public interface AuthenticationService {
    boolean isSessionValid(String sessionToken);
    User getUserBySessionToken(String sessionToken);
    String getUserIdBySessionToken(String sessionToken);
}
