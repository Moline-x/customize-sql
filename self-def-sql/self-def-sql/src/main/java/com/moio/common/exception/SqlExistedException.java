package com.moio.common.exception;

/**
 * @author molinchang
 *
 * @description customize sql exception with sql already exist
 */
public class SqlExistedException extends Exception {


    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public SqlExistedException() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public SqlExistedException(String message) {
        super(message);
    }
}
