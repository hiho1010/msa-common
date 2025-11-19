package org.bangbang.infrastructure.keycloack;

import java.util.Collection;
import org.bangbang.common.security.UserPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public record KeycloakJwtAuthenticationConverter(
    KeycloakGrantedAuthoritiesConverter authoritiesConverter) implements
    Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String userId = jwt.getClaim("sub"); // 혹은 preferred_username/사용자 지정 claim
        String username = jwt.getClaimAsString("preferred_username");

        Collection<? extends GrantedAuthority> authorities = authoritiesConverter.convert(jwt);

        UserPrincipal principal = new UserPrincipal(
            userId,
            username,
            null,       // 패스워드는 필요 없음
            authorities
        );

        return new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
    }

    @Override
    public <U> Converter<Jwt, U> andThen(
        Converter<? super AbstractAuthenticationToken, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
