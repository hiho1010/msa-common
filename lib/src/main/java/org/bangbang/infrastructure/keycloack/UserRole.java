package org.bangbang.infrastructure.keycloack;

public enum UserRole {
    USER,
    ADMIN,
    MANAGER;

    public String asAuthority() {
        return "ROLE_" + name();
    }
}
