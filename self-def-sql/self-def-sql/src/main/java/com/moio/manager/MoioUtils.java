package com.moio.manager;

import com.moio.common.constant.ArgsConstant;
import com.moio.manager.handler.ProcessGoBackHandler;

/**
 * @description util collection which implements functions from handler or more interface from manager.
 *
 * @author molinchang
 */
public final class MoioUtils {

    public static ProcessGoBackHandler goBackHandler(String val) {

        return runnable -> {
            if (ArgsConstant.BACK_SIGNAL.equals(val)) {
                runnable.run();
            }
        };
    }
}
