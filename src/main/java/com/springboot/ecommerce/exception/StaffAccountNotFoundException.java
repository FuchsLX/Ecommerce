package com.springboot.ecommerce.exception;

public class StaffAccountNotFoundException extends RuntimeException {

    public StaffAccountNotFoundException(String message) {super(message);}
    public StaffAccountNotFoundException(String message, Throwable cause) {super(message, cause);}
    public StaffAccountNotFoundException(Throwable cause) {super(cause);}
}
