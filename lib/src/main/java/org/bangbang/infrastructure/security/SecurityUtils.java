package org.bangbang.infrastructure.security;

import java.util.List;
import java.util.Optional;
import org.bangbang.common.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    public static Optional<UserPrincipal> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal principal)) {
            return Optional.empty();
        }
        return Optional.of(principal);
    }

    public static String getCurrentUserIdOrThrow() {
        return getCurrentUser()
            .map(UserPrincipal::userId)
            .orElseThrow(() -> new IllegalStateException("No authenticated user"));
    }

    public static boolean hasRole(String role) {
        return getCurrentUser()
            .map(UserPrincipal::authorities)
            .orElse(List.of())
            .stream()
            .anyMatch(a -> a.getAuthority().equals(role));
    }
}