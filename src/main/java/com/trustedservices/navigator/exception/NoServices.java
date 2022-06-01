package com.trustedservices.navigator.exception;

class NoServices extends Exception {
    private final static String ERR_MESSAGE="This research field returned zero results";
    private String searchInput;
    public NoServices (String s) {
        super(s);
        searchInput=s;
    }
    public void getError() {
        System.out.println(ERR_MESSAGE+ " " +searchInput);
    }
}
