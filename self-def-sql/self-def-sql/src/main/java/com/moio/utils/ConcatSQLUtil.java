package com.moio.utils;

import com.moio.common.constant.ArgsConstant;
import com.moio.entity.BasicSql;
import com.moio.entity.Category;
import com.moio.entity.CategorySql;

import java.util.ArrayList;
import java.util.List;

/**
 * concat sql string util
 *
 * @author molinchang
 */
public final class ConcatSQLUtil {

    /**
     * concat sql with condition key, tableName and other conditions.
     *
     * @param key         condition
     * @param tableName   table name
     * @param others      other condition
     * @return  concat sql
     */
    public static String concatSQLwithCondition(String key, String tableName, String others) {

        String prefix = "select * from ";
        String middle = " where ";
        String suffix = " in (') ";
        StringBuilder sb = new StringBuilder(prefix);
        sb.append(tableName).append(middle).append(key).append(suffix);
        if (!"".equals(others) && others != null) {
            sb.append(others);
        }
        sb.append(";");

        return sb.toString();
    }

    /**
     * concat sql with single parameter
     *
     * @param sql   sql string
     * @param value single parameter
     * @return concat sql
     */
    public static String concatSQLwithSingleParamter(String sql, String value) {

        int prefixIndex = sql.lastIndexOf("(") + 1;
        String prefixSQL = sql.substring(0, prefixIndex + 1);
        String suffixSQL = "'" + sql.substring(prefixIndex + 1);

        return prefixSQL + value + suffixSQL;
    }

    /**
     * concat sql with multiple parameters
     *
     * @param sql    sql string
     * @param values value list
     * @return concat sql
     */
    public static String concatSQLwithMultipleParameters(String sql, List<String> values) {
        // init result
        String result = "";

        if (containsTarget(values, "[")) {
            result = concatSQLwithGroupData(sql, values);
        } else {
            int prefixIndex = sql.lastIndexOf("(") + 1;
            int suffixIndex = sql.lastIndexOf(")");
            String middleStr = "','";
            String prefixSQL = sql.substring(0, prefixIndex + 1);
            String suffixSQL = sql.substring(suffixIndex);

            StringBuilder middleSQL = new StringBuilder();
            for (int i = 0; i < values.size(); i++) {
                String value = values.get(i);
                if (i == values.size() - 1) {
                    middleSQL.append(value).append("'");
                    break;
                }
                middleSQL.append(value).append(middleStr);
            }
            result = prefixSQL + middleSQL +suffixSQL;
        }

        return result;
    }

    /**
     * check if list contained target string.
     *
     * @param source    source list
     * @param target    target string
     * @return true - contain, false - un-contain
     */
    public static boolean containsTarget(List<String> source, String target) {

        for (String str : source) {
            if (str.contains(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * concat sql with group data.
     *
     * @param sql       sql
     * @param values    values
     * @return concat sql
     */
    public static String concatSQLwithGroupData(String sql, List<String> values) {

        // get index list
        List<Integer> preIndexList = computeIndex(sql, '(', 1);
        List<Integer> sufIndexList = computeIndex(sql, ')', 0);

        // convert to same group
        List<List<String>> sameGroup = convertListToSameGroup(values);
        int count = sameGroup.get(0).size();

        // 2 () = 1 segment
        String prefixSQL = sql.substring(0, preIndexList.get(0) + 1);
        String suffixSQL = sql.substring(sufIndexList.get(sufIndexList.size() - 1));
        String middleStr = "','";

        StringBuilder result = new StringBuilder(prefixSQL);

        for (int i = 0; i < sameGroup.size(); i++) {
            List<String> list = sameGroup.get(i);
            for (int j = 0; j < count; j++) {
                String value = list.get(j);
                if (j == count - 1) {
                    result.append(value).append("'");
                    break;
                }
                result.append(value).append(middleStr);
            }
            if (i != sameGroup.size() - 1) {
                prefixSQL = sql.substring(sufIndexList.get(i), preIndexList.get(i+1) + 1);
                result.append(prefixSQL);
            }
        }

        result.append(suffixSQL);

        return result.toString();
    }

    /**
     * convert list to same group.
     *
     * @param values value list
     * @return same group list
     */
    public static List<List<String>> convertListToSameGroup(List<String> values) {

        List<List<String>> result = new ArrayList<>();

        String valueStr = ConvertUtil.convertListToString(values);
        String[] split = valueStr.contains("],") ? valueStr.split("],") : valueStr.split("]");
        int col = split[0].split(",").length;
        String[][] resultMap = new String[split.length][col];
        for (int j = 0; j < split.length; j++) {
            String s = split[j];
            String s1 = s.replaceAll("\\[", "");
            String s2 = s1.replaceAll("]", "");
            String[] innerSplit = s2.split(",");
            System.arraycopy(innerSplit, 0, resultMap[j], 0, innerSplit.length);
        }
        for (int i = 0; i < col; i++) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < split.length; j++) {
                list.add(resultMap[j][i]);
            }
            result.add(list);
        }


        return result;
    }

    /**
     * compute index for target in source.
     *
     * @param source    source string
     * @param target    target character
     * @param offset    move distance
     * @return index list
     */
    public static List<Integer> computeIndex(String source, char target, int offset) {

        List<Integer> list = new ArrayList<>();

        char[] chars = source.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == target) {
                list.add(i + offset);
            }
        }

        return list;
    }

    /**
     * concat sql for t_category
     *
     * @param category category
     * @return concat sql
     */
    public static String concatSQLForCategory(Category category) {
        String prefix = ArgsConstant.INSERT_ARGS_CATEGORY;
        return prefix + category.getId() + ",'" + category.getContent() + "'," + category.getIndexC() + ");";
    }

    /**
     * concat sql for t_basic_sql
     *
     * @param basicSql basic sql
     * @return concat sql
     */
    public static String concatSQLForSql(BasicSql basicSql) {
        String prefix = ArgsConstant.INSERT_ARGS_SQL;
        String sqlValue = basicSql.getSqlValue();
        // additional logic : if there is ' in sqlValue, then need to turn it
        char[] chars = sqlValue.toCharArray();
        String replaceOld = sqlValue;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            char j = i == chars.length - 1 ? c : chars[i+1];
            if (c == '\'') {
                if (j != ')') {
                    replaceOld = sqlValue.replace(String.valueOf(sqlValue.charAt(i)), "''");
                }
            }
        }
        String replace = replaceOld.replace("(')", "('')");
        return prefix + basicSql.getId() + ",'" + basicSql.getSqlKey() + "','" + replace + "');";
    }

    /**
     * concat sql for t_category_sql
     *
     * @param categorySql category sql
     * @return concat sql
     */
    public static String concatSQLForCategorySql(CategorySql categorySql) {
        String prefix = ArgsConstant.INSERT_ARGS_CATEGORY_SQL;
        return prefix + categorySql.getCid() + "," + categorySql.getSid() + "," + categorySql.getSeq() + ");";
    }
}
