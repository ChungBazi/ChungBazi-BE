package chungbazi.chungbazi_be.domain.auth.jwt;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationToken implements Authentication {
    private final String principal; // 사용자 ID (userId)
    private final Object credentials;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated = true;

    public JwtAuthenticationToken(String principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        this.principal = principal;
        this.credentials = credentials;
        this.authorities = Collections.unmodifiableCollection(authorities); // 불변 컬렉션
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return principal; // 사용자 ID 반환
    }

    public Long getId() {
        try {
            return Long.parseLong(principal);
        } catch (NumberFormatException e) {
            throw new BadRequestHandler(ErrorStatus.INVALID_USER_ID_FORMAT);
        }
    }
}
