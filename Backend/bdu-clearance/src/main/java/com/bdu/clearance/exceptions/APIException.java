package com.bdu.clearance.exceptions;

public class APIException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public APIException(String message) {
        super(message);
    }

    public APIException(String entity, Long id) {
        super(entity + " not found with id: " + id);
    }
}
