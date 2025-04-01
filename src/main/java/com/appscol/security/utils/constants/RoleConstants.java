package com.appscol.security.utils.constants;

public class RoleConstants {
    // Constructor privado para evitar la instanciación
    private RoleConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String ROLE_PROFESSOR = "ROLE_PROFESSOR";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_STUDENT = "ROLE_STUDENT";
}
