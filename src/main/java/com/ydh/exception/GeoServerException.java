package com.ydh.exception;

public class GeoServerException extends RuntimeException {

    public GeoServerException() {
        super();
    }

    public GeoServerException(String message) {
        super(message);
    }

    public GeoServerException(Throwable cause) {
        super(cause);
    }

    public GeoServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
