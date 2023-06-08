package com.moio.utils;

import com.moio.common.constant.ArgsConstant;
import com.moio.entity.BasicSql;
import com.moio.entity.Category;
import com.moio.entity.CategorySql;

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

        return prefixSQL + middleSQL +suffixSQL;
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
