package com.springboot.ecommerce.exception;

import lombok.NoArgsConstructor;

public class RoleAlreadyExistsException extends RuntimeException{

    public RoleAlreadyExistsException() {super();}
    public RoleAlreadyExistsException(String message) {super(message);}
    public RoleAlreadyExistsException(String message, Throwable cause) {super(message, cause);}
    public RoleAlreadyExistsException(Throwable cause) {super(cause);}
}
