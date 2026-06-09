package com.trading.diary.exception;

/**
 * Checked exception raised when a company symbol is expected to exist in persistence but no matching record is found.
 */
public class CompanyNotFoundException extends Exception{

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
