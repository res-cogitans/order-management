package org.programmers.ordermanagementsystem.exception;

public class ProhibitedItemException extends RuntimeException {
    public ProhibitedItemException(String message) {
        super(message);
    }
}
