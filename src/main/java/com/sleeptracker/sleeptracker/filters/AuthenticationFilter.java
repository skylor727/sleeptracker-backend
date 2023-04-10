package com.sleeptracker.sleeptracker.filters;

import com.sleeptracker.sleeptracker.models.User;
import com.sleeptracker.sleeptracker.services.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends OncePerRequestFilter {
    private SessionService sessionService;

    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isSessionValid(request)) {
            // Get user by session token
            String sessionToken = getSessionToken(request);
            User user = sessionService.getUserBySessionToken(sessionToken);

            // Set the user in the SecurityContextHolder
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

    private boolean isSessionValid(HttpServletRequest request) {
        String sessionToken = getSessionToken(request);

        if (sessionToken != null) {
            return sessionService.isSessionValid(sessionToken);
        }
        return false;
    }

    private String getSessionToken(HttpServletRequest request) {
        String sessionToken = null;
        String cookieName1 = "__Secure-next-auth.session-token";
        String cookieName2 = "next-auth.session-token";

        // Extract session token from the request cookies
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName1) || cookie.getName().equals(cookieName2)) {
                    sessionToken = cookie.getValue();
                    break;
                }
            }
        }

        return sessionToken;
    }
}
