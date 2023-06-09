package com.moio.utils;

import com.moio.entity.sta.BasicGateLane;
import com.moio.entity.sta.BasicQC;
import com.moio.entity.sta.BasicYardBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * converter util
 *
 * @author molinchang
 */
public final class ConvertUtil {

    private ConvertUtil() {
        throw new IllegalStateException("util class can not be init");
    }

    /**
     * convert container add one blank if container with no blank in the middle
     *
     * @param cntrN container number
     * @return convert container number
     */
    public static String convertCntrNumber(String cntrN) {

        String result = cntrN;
        if (cntrN != null) {
            if (cntrN.length() < 5) {
                return result;
            }
            if (!cntrN.substring(4,5).isBlank()) {
                String subCntr = cntrN.substring(0, 4);
                result = subCntr + " " + cntrN.substring(4);
            }
        }

        return result;
    }

    /**
     * convert block name to block id
     *
     * @param blockName  block name
     * @return block id
     */
    public static String convertBlockMtoBlockId(String blockName) {

        return BasicYardBlock.basicYardBlockMap().get(blockName);
    }

    /**
     * convert gate lane number to gate lane id
     *
     * @param laneNumber  gate lane number
     * @return gate lane id
     */
    public static String convertLaneNtoGateLaneId(String laneNumber) {

        return BasicGateLane.basicGateLaneMap().get(laneNumber);
    }

    /**
     * convert qc name to qc id
     *
     * @param qcName qc name
     * @return qc id
     */
    public static String convertQCMtoQCId(String qcName) {

        return BasicQC.basicQC().get(qcName);
    }

    /**
     * multiple rules of single parameter
     *
     * @param key    key
     * @param value  value
     */
    public static String convertRules(String key, String value) {

        if (key.equals("cntr_n")) {
            value = convertCntrNumber(value);
        }
        if (key.equals("block_m")) {
            value = convertBlockMtoBlockId(value);
        }
        if (key.equals("lane_n")) {
            value = convertLaneNtoGateLaneId(value);
        }
        if (key.equals("qc_m")) {
            value = convertQCMtoQCId(value);
        }
        return value;
    }

    /**
     * confirm if parameter is a group
     *
     * @param parameter parameter
     * @return true or false
     */
    public static boolean isMultipleParameter(String parameter) {

        return parameter.contains(",");
    }



    /**
     * convert multiple parameters to list
     *
     * @param parameters parameters
     * @param key        key word
     * @return parameter list
     */
    public static List<String> convertMultiParameterToList(String parameters, String key) {

        List<String> list = new ArrayList<>();

        if (parameters != null) {
            if (parameters.contains(" ")) {
                if (parameters.contains(",")) {
                    String all = parameters.replaceAll(" ", "");
                    String regex = regexInit(key);
                    String replaceAll = judgeRegex(regex, all, parameters);
                    String[] split = replaceAll.split(" ");
                    travelParameters(split, key, list);
                }
            } else {
                // drop all blank symbols
                String replaceAll = parameters.replaceAll(" ", "");
                if (replaceAll.contains(",")) {
                    String[] split = replaceAll.split(",");
                    travelParameters(split, key, list);
                }
            }
        }

        return list;
    }

    /**
     * travel split parameters array.
     *
     * @param split     split parameters array
     * @param key       key
     * @param list      result list
     */
    private static void travelParameters(String[] split, String key, List<String> list) {
        for (String parameter : split) {
            parameter = convertRules(key, parameter);
            list.add(parameter);
        }
    }

    /**
     * init regex.
     *
     * @param key key
     * @return regex
     */
    private static String regexInit(String key) {
        String regex;
        if ("cntr_n".equals(key)) {
            regex = "(.{11})";
        } else {
            regex = "\\s{1,}";
        }

        return regex;
    }

    /**
     * judge regex.
     *
     * @param regex         regex
     * @param all           new turned parameters
     * @param parameters    original parameters
     * @return replace parameters
     */
    private static String judgeRegex(String regex, String all, String parameters) {
        String replaceAll;
        if (!"\\s{1,}".equals(regex)) {
            replaceAll = all.replaceAll(regex, "$1 ");
        } else {
            replaceAll = parameters.replaceAll(regex, " ");
        }

        return replaceAll;
    }

    /**
     * convert list to string.
     *
     * @param list  list
     * @return string
     */
    public static String convertListToString(List<String> list) {

        return String.join(",", list);
    }

    /**
     * convert string to list.
     *
     * @param str   string
     * @return list
     */
    public static List<String> convertStringToList(String str) {

        List<String> list = new ArrayList<>();

        if (str != null && !"".equals(str)) {
            String[] split = str.split(",");
            Collections.addAll(list, split);
        }

        return list;
    }
}
