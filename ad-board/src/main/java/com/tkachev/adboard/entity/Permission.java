package com.tkachev.adboard.entity;

public enum Permission {
    USERS_READ("users:read"),
    USERS_WRITE("users:write"),
    USERS_DELETE("users:delete"),
    CHATS_READ("chats:read"),
    CHATS_WRITE("chats:write"),
    CHATS_DELETE("chats:delete"),
    MESSAGES_READ("messages:read"),
    MESSAGES_WRITE("messages:write"),
    MESSAGES_DELETE("messages:delete"),
    ADS_READ("ads:read"),
    ADS_WRITE("ads:write"),
    ADS_DELETE("ads:delete"),
    CATEGORIES_READ("categories:read"),
    CATEGORIES_WRITE("categories:write"),
    CATEGORIES_DELETE("categories:delete"),
    REVIEWS_READ("reviews:read"),
    REVIEWS_WRITE("reviews:write"),
    REVIEWS_DELETE("reviews:delete");

    private final String value;

    Permission(String permission) {
        this.value = permission;
    }

    public String getValue() {
        return value;
    }
}
