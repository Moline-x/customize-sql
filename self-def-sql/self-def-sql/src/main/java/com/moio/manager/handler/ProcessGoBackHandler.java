package com.moio.manager.handler;


/**
 * @description this is a handler to process event which click go back button.
 *
 * @author molinchang
 */
public interface ProcessGoBackHandler {

    /**
     * when go back will process.
     *
     * @param action go back function
     */
    void processGoBackHandle(Runnable action);
}
