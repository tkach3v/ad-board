package com.tkachev.adboard.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(
            Permission.USERS_READ,
            Permission.USERS_WRITE,
            Permission.MESSAGES_READ,
            Permission.MESSAGES_WRITE,
            Permission.CHATS_READ,
            Permission.CHATS_WRITE,
            Permission.ADS_READ,
            Permission.ADS_WRITE,
            Permission.CATEGORIES_READ,
            Permission.REVIEWS_READ,
            Permission.REVIEWS_WRITE
    )),
    ADMIN(Set.of(
            Permission.USERS_READ,
            Permission.USERS_WRITE,
            Permission.USERS_DELETE,
            Permission.MESSAGES_READ,
            Permission.MESSAGES_WRITE,
            Permission.MESSAGES_DELETE,
            Permission.CHATS_READ,
            Permission.CHATS_WRITE,
            Permission.CHATS_DELETE,
            Permission.ADS_READ,
            Permission.ADS_WRITE,
            Permission.ADS_DELETE,
            Permission.CATEGORIES_READ,
            Permission.CATEGORIES_WRITE,
            Permission.CATEGORIES_DELETE,
            Permission.REVIEWS_READ,
            Permission.REVIEWS_WRITE,
            Permission.REVIEWS_DELETE
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toSet());
    }
}
