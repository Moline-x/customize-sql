package com.moio.entity.sta;

import java.util.HashMap;
import java.util.Map;

/**
 * @description basic gate lane map
 *              lane_n -> gate_lane_id
 *
 * @author molinchang
 */
public final class BasicGateLane {

    private static final int DEFAULT_CAPACITY = 16;
    private static final Map<String, String> basicMap = new HashMap<>(DEFAULT_CAPACITY);

    static {
        basicMap.put("1", "1702800019");
        basicMap.put("2", "1702800020");
        basicMap.put("3", "1702800015");
        basicMap.put("4", "1702800016");
        basicMap.put("5O", "1702800017");
        basicMap.put("6", "1702800018");
        basicMap.put("7", "1702800002");
        basicMap.put("8", "1702800003");
        basicMap.put("9", "1702800004");
        basicMap.put("10", "1702800005");
        basicMap.put("11", "1702800006");
        basicMap.put("12", "1702800007");
        basicMap.put("13", "1702800008");
        basicMap.put("14", "1702800009");
        basicMap.put("15", "1702800010");
        basicMap.put("16", "1702800011");
        basicMap.put("17", "1702800012");
        basicMap.put("18", "1702800013");
        basicMap.put("5I", "1702800001");
        basicMap.put("20", "1702800014");

    }

    public static Map<String, String> basicGateLaneMap() {
        return basicMap;
    }
}
