package com.moio.entity.sta;

import java.util.HashMap;
import java.util.Map;

/**
 * basic yard block map ,
 * block_m -> block_id
 *
 * @author molinchang
 */
public final class BasicYardBlock {

    private static final int DEFAULT_CAPACITY = 16;
    private static final Map<String, String> basicMap = new HashMap<>(DEFAULT_CAPACITY);

    static {
        basicMap.put("N201","1900800001");
        basicMap.put("N202","1900800002");
        basicMap.put("N203","1900800003");
        basicMap.put("N204","1900800004");
        basicMap.put("N205","1900800005");
        basicMap.put("N206","1900800006");
        basicMap.put("N208","1900800007");
        basicMap.put("N209","1900800008");
        basicMap.put("N210","1900800009");
        basicMap.put("N211","1900800010");
        basicMap.put("N212","1900800011");
        basicMap.put("N213","1900800012");
        basicMap.put("N214","1900800013");
        basicMap.put("N215","1900800014");
        basicMap.put("N216","1900800015");
        basicMap.put("N217","1900800016");
        basicMap.put("N290","1900800017");
        basicMap.put("N291","1900800018");
        basicMap.put("N218","1900800019");
        basicMap.put("N219","1900800020");
        basicMap.put("N221","1900800021");
        basicMap.put("N222","1900800022");
        basicMap.put("N223","1900800023");
        basicMap.put("N224","1900800024");
        basicMap.put("N225","1900800025");
        basicMap.put("N226","1900800026");
        basicMap.put("N227","1900800027");
        basicMap.put("N228","1900800028");
        basicMap.put("N229","1900800029");
        basicMap.put("N230","1900800030");
        basicMap.put("N231","1900800031");
        basicMap.put("N232","1900800032");
        basicMap.put("N233","1900800033");
        basicMap.put("N234","1900800034");
        basicMap.put("N236","1900800035");
        basicMap.put("N292","1900800036");
        basicMap.put("N293","1900800037");
        basicMap.put("N237","1900800038");
        basicMap.put("N238","1900800039");
        basicMap.put("N239","1900800040");
        basicMap.put("N240","1900800041");
        basicMap.put("N241","1900800042");
        basicMap.put("N242","1900800043");
        basicMap.put("N243","1900800044");
        basicMap.put("N244","1900800045");
        basicMap.put("N245","1900800046");
        basicMap.put("N246","1900800047");
        basicMap.put("N247","1900800048");
        basicMap.put("N294","1900800049");
        basicMap.put("N295","1900800050");
    }

    public static Map<String, String> basicYardBlockMap() {
        return basicMap;
    }



}
