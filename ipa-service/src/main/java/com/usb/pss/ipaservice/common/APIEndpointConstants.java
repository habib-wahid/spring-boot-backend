package com.usb.pss.ipaservice.common;

public class APIEndpointConstants {

    private APIEndpointConstants() {
    }

    private static final String API_VERSION = "/api/v1";
    public static final String AUTHENTICATION_ENDPOINT = API_VERSION + "/auth";
    public static final String USER_ENDPOINT = API_VERSION + "/users";
    public static final String GROUP_ENDPOINT = API_VERSION + "/groups";
    public static final String MENU_ENDPOINT = API_VERSION + "/menus";

    public static final String MODULE_ENDPOINT = API_VERSION + "/modules";
    public static final String ACTION_ENDPOINT = API_VERSION + "/actions";
    public static final String DEPARTMENT_ENDPOINT = API_VERSION + "/departments";
    public static final String DESIGNATION_ENDPOINT = API_VERSION + "/designations";
    public static final String CURRENCY_ENDPOINT = API_VERSION + "/currencies";
    public static final String POINT_OF_SALE_ENDPOINT
        = API_VERSION + "/pointOfSales";
    public static final String AIRCRAFT_ENDPOINT = API_VERSION + "/aircraft";
    public static final String PASSWORD_POLICY_ENDPOINT = API_VERSION + "/passwordPolicy";
}
