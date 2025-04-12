package io.donkie.tapn.secure.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Utility class for accessing current authenticated user details from the Spring
 * SecurityContext.
 */
public class CurrentUser {

    private CurrentUser() {
        // prevent instantiation
    }

    public static Optional<Authentication> getAuthentication() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(auth);
    }

    public static Optional<String> getPrincipal() {
        return getAuthentication().map(Authentication::getName);
    }

    public static List<String> getAuthorities() {
        return getAuthentication().map(Authentication::getAuthorities)
                .map(auth -> auth.stream()
                        .map(GrantedAuthority::getAuthority).toList()).orElseGet(List::of);
    }

    public static boolean hasAuthority(String authority) {
        return getAuthorities().contains(authority);
    }

    public static boolean hasAllAuthorities(String... authorities) {
        var allAuthorities = getAuthorities();
        return Stream.of(authorities).allMatch(allAuthorities::contains);
    }

    public static boolean hasAnyAuthorities(String... authorities) {
        var allAuthorities = getAuthorities();
        return Stream.of(authorities).anyMatch(allAuthorities::contains);
    }
}