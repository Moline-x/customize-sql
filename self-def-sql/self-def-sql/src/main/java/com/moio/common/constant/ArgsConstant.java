package com.moio.common.constant;

/**
 * @description args constant
 *
 * @author molinchang
 */
public final class ArgsConstant {

    public static final String INSERT_ARGS_CATEGORY = "INSERT INTO t_category (id, content, index_c) VALUES (";
    public static final String INSERT_ARGS_SQL = "INSERT INTO t_basic_sql (id, sql_key, sql_value) VALUES (";
    public static final String INSERT_ARGS_CATEGORY_SQL = "INSERT INTO t_category_sql (cid, sid, seq) VALUES (";

    public static final String DATA_FILE_PATH = "C:/h2data/data-h2.sql";

    public static final String OS_NAME = "os.name";
    public static final String WINDOWS = "Windows";
    public static final String CMD = "cmd";
    public static final String LINE_C = "/c";
    public static final String CLS = "cls";
    public static final String CLEAR = "clear";

    public static final String BACK_SIGNAL = "0";
    public static final int REMAIN_NUMBER = 0;
    public static final String REMAIN_SYMBOL = "*";
    public static final int EXIT_NUM = 99;
}
