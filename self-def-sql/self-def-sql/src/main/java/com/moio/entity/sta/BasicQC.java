package com.moio.entity.sta;

import java.util.HashMap;
import java.util.Map;

/**
 * @description basic qc map
 *              qc_m -> qc_id
 *
 * @author molinchang
 */
public final class BasicQC {

    private static final int DEFAULT_CAPACITY = 16;
    private static final Map<String, String> basicMap = new HashMap<>(DEFAULT_CAPACITY);

    static {
        basicMap.put("QC2201", "1900100001");
        basicMap.put("QC2202", "1900100002");
        basicMap.put("QC2203", "1900100003");
        basicMap.put("QC2204", "1900100004");
        basicMap.put("QC2205", "1900100005");
        basicMap.put("QC2206", "1900100006");
        basicMap.put("QC2207", "1900100007");
        basicMap.put("QC2208", "1900100008");
        basicMap.put("QC2209", "1900100009");
        basicMap.put("QC2210", "1900100010");
        basicMap.put("QC2211", "1900100011");
        basicMap.put("QC2212", "1900100012");
        basicMap.put("QC2213", "1900100013");
        basicMap.put("QC2214", "1900100014");
        basicMap.put("QC2215", "1900100015");
        basicMap.put("QC2216", "1900100016");
        basicMap.put("QC2217", "1900100017");
        basicMap.put("QC2218", "1900100018");
        basicMap.put("QC2219", "1900100019");
        basicMap.put("QC2220", "1900100020");
        basicMap.put("QC2221", "1900100021");
        basicMap.put("QC2222", "1900100022");
        basicMap.put("QC2223", "1900100023");
        basicMap.put("QC2224", "1900100024");
        basicMap.put("2201", "1900100001");
        basicMap.put("2202", "1900100002");
        basicMap.put("2203", "1900100003");
        basicMap.put("2204", "1900100004");
        basicMap.put("2205", "1900100005");
        basicMap.put("2206", "1900100006");
        basicMap.put("2207", "1900100007");
        basicMap.put("2208", "1900100008");
        basicMap.put("2209", "1900100009");
        basicMap.put("2210", "1900100010");
        basicMap.put("2211", "1900100011");
        basicMap.put("2212", "1900100012");
        basicMap.put("2213", "1900100013");
        basicMap.put("2214", "1900100014");
        basicMap.put("2215", "1900100015");
        basicMap.put("2216", "1900100016");
        basicMap.put("2217", "1900100017");
        basicMap.put("2218", "1900100018");
        basicMap.put("2219", "1900100019");
        basicMap.put("2220", "1900100020");
        basicMap.put("2221", "1900100021");
        basicMap.put("2222", "1900100022");
        basicMap.put("2223", "1900100023");
        basicMap.put("2224", "1900100024");
    }

    public static Map<String, String> basicQC() {
        return basicMap;
    }
}
