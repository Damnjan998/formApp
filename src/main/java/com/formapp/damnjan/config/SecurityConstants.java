package com.formapp.damnjan.config;

public class SecurityConstants {

    public static final String AUTH_PATTERN = "/auth/**";
    public static final String FORM_PATTERN = "/form/**";
    public static final String FIELD_PATTERN = "/field/**";
    public static final String ADMIN = "ADMIN";
    public static final String AUTHORIZATION_HEADER_STRING = "AUTHORIZATION";
    public static final long EXPIRATION = 86400000; // 24 hours
    public static final String SECRET_STRING = "5fd5c39d88c496509eae9b49bf2ed888668f8e11ab07ca9db2563a1d1ca73a5c";
    public static final String SECRET_KEY_ALGORITHM = "HmacSHA256";


}
