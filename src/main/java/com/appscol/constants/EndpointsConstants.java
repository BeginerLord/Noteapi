package com.appscol.constants;

public class EndpointsConstants {
    private EndpointsConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ENDPOINT_BASE_API = "/api/v1/school";
    public static final String ENDPOINT_LOGIN = ENDPOINT_BASE_API + "/login";
    public static final String ENDPOINT_SIGNUP = ENDPOINT_BASE_API + "/signup";
    public static final String ENDPOINT_STUDENT = ENDPOINT_BASE_API + "/student";
    public static final String ENDPOINT_PROFESSOR = ENDPOINT_BASE_API + "/professor";
    public static final String ENDPOINT_ADMIN = ENDPOINT_BASE_API + "/admin";
    public static final String ENDPOINT_SCHEDULE = ENDPOINT_BASE_API + "/schedule";
    public static final String ENDPOINT_GRADE = ENDPOINT_BASE_API + "/grade";
    public static final String ENDPOINT_NOTE = ENDPOINT_BASE_API + "/note";

}
