package fr.ut1.m2ipm.dafumarket.utils;

import fr.ut1.m2ipm.dafumarket.models.Compte;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static Compte getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // or throw a custom exception if you prefer
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Compte compte) {
            return compte;
        } else {
            throw new IllegalStateException("Authenticated principal is not a Compte");
        }
    }
}