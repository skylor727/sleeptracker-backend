package com.sleeptracker.sleeptracker.filters;

import com.sleeptracker.sleeptracker.services.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {
    private SessionService sessionService;

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isSessionValid(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

    private boolean isSessionValid(HttpServletRequest request) {
        String sessionToken = null;
        String cookieName = "next-auth.session-token";

        // Extract session token from the request cookies
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName)) {
                    sessionToken = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionToken != null) {
            return sessionService.isSessionValid(sessionToken);
        }
        return false;
    }
}