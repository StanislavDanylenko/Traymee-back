package com.traymee.traymeeback.exception;

import java.io.IOException;

public class InvalidJwtAuthenticationException extends IOException {
    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}
