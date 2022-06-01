package com.trustedservices.navigator.exception;

class NoInternetConnectionException extends Exception {
    private final String ERR_MESSAGE= "No internet connection";
    public NoInternetConnectionException (String s) {
        super (s);
    }
    public void getError() {
        System.out.println(ERR_MESSAGE);
    }
}
