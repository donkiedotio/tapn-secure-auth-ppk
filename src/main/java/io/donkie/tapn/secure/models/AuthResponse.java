package io.donkie.tapn.secure.models;

import java.util.Set;

public class AuthResponse {
    private String userName;
    private Set<String> authorities;
    private long accessExpiration;
    private long refreshExpiration;
}
