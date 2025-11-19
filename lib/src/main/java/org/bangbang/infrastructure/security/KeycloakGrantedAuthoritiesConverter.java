package org.bangbang.infrastructure.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public record KeycloakGrantedAuthoritiesConverter(String clientId) implements
    Converter<Jwt, List<SimpleGrantedAuthority>> {

    @Override
    public List<SimpleGrantedAuthority> convert(Jwt jwt) {
        Set<String> roles = new HashSet<>();

        // realm roles
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.get("roles") instanceof Collection<?> realmRoles) {
            realmRoles.forEach(r -> roles.add("ROLE_" + r));
        }

        // client roles
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null && resourceAccess.get(
            clientId) instanceof Map<?, ?> clientAccess) {
            Object rolesObj = clientAccess.get("roles");
            if (rolesObj instanceof Collection<?> clientRoles) {
                clientRoles.forEach(r -> roles.add("ROLE_" + r));
            }
        }

        return roles.stream()
            .map(SimpleGrantedAuthority::new)
            .toList();
    }
}