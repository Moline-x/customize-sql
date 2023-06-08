package com.moio.common.enums;

/**
 * @description this is an enum for system warning language.
 *
 * @author molinchang
 */
public enum SystemWarnLanguageEnum {
    /**
     * system warning sign.
     */
    ADD_SUCCEED("添加成功! ") {
        @Override
        public void output() {
            System.out.println("添加成功! ");
        }
    },
    ADD_FAILED("添加失败...") {
        @Override
        public void output() {
            System.out.println("添加失败...");
        }
    },
    DELETE_SUCCEED("删除成功!") {
        @Override
        public void output() {
            System.out.println("删除成功!");
        }
    },
    DELETE_FAILED("删除失败... 请输入合法值 :(") {
        @Override
        public void output() {
            System.out.println("删除失败... 请输入合法值 :(");
        }
    },
    UPDATE_SUCCEED("更新成功!") {
        @Override
        public void output() {
            System.out.println("更新成功!");
        }
    },
    UPDATE_FAILED("更新失败 ... 请核查并输入合法参数") {
        @Override
        public void output() {
            System.out.println("更新失败 ... 请核查并输入合法参数");
        }
    },
    INVALID_INDEX("抱歉, 无效索引号...") {
        @Override
        public void output() {
            System.out.println("抱歉, 无效索引号...");
        }
    },
    INPUT_TABLE_NAME("请输入表名: ") {
        @Override
        public void output() {
            System.out.print("请输入表名: ");
        }
    },
    INPUT_CONDITION("请输入条件: ") {
        @Override
        public void output() {
            System.out.print("请输入条件: ");
        }
    },
    INPUT_OTHER_CONDITION("请输入后置语句(and/or/order by..):"){
        @Override
        public void output() {
            System.out.print("请输入其他条件:");
        }
    },
    INPUT_KEY("请输入key值来统一多个条件"){
        @Override
        public void output() {
            System.out.print("请输入key值分组");
        }
    },
    INPUT_DELETE_INDEX("请确认您想删除的编号:") {
        @Override
        public void output() {
            System.out.print("请输入您想删除的编号:");
        }
    },
    INPUT_UPDATE_INDEX("请输入您想更新的索引编号:") {
        @Override
        public void output() {
            System.out.print("请输入您想更新的索引编号或按数字0返回上一级 >");
        }
    },
    SEARCH_RESULT("检索结果:") {
        @Override
        public void output() {
            System.out.println("检索结果:");
        }
    },
    ID("sql语句编号: ") {
        @Override
        public void output() {
            System.out.println("sql语句编号: ");
        }
    },
    KEY("分组Key值: ") {
        @Override
        public void output() {
            System.out.println("分组Key值: ");
        }
    },
    SQL("sql语句: ") {
        @Override
        public void output() {
            System.out.println("sql语句: ");
        }
    },
    INDEX("条目索引号: ") {
        @Override
        public void output() {
            System.out.println("条目索引号: ");
        }
    },
    CONTENT("条目名称: ") {
        @Override
        public void output() {
            System.out.println("条目名称: ");
        }
    },
    SPLIT_SYMBOL("-------------------------------------------------------------------") {
        @Override
        public void output() {
            System.out.println("-------------------------------------------------------------------");
        }
    },
    INPUT_UPDATE_KEY("请更新分组KEY: ") {
        @Override
        public void output() {
            System.out.print("请更新分组KEY: ");
        }
    },
    INPUT_UPDATE_SQL("请更新sql语句:") {
        @Override
        public void output() {
            System.out.print("请更新sql语句:");
        }
    },
    INPUT_UPDATE_INDEX_FOR_CATEGORY("请更改索引编号:") {
        @Override
        public void output() {
            System.out.print("请更改索引编号:");
        }
    },
    INPUT_UPDATE_CONTENT("请更改分类条目名称:") {
        @Override
        public void output() {
            System.out.print("请更改分类条目名称:");
        }
    },
    INPUT_CATEGORY_INDEX("请输入分类条目编号:") {
        @Override
        public void output() {
            System.out.print("请输入分类条目编号:");
        }
    },
    INPUT_CATEGORY_CONTENT("please input category content or click 0 to go back >") {
        @Override
        public void output() {
            System.out.print("请输入分类条目的名称或按数字0返回上一级 >");
        }
    },
    CATEGORY_SYMBOL_LEFT("--------分类条目 【") {
        @Override
        public void output() {
            System.out.println("--------分类条目 【");
        }
    },
    CATEGORY_SYMBOL_RIGHT("】 目前已经绑定的sql语句如下:") {
        @Override
        public void output() {
            System.out.println("】 目前已经绑定的sql语句如下:");
        }
    },
    WILL_NOT_BIND_LEFT("不会绑定索引号为 [") {
        @Override
        public void output() {
            System.out.println("不会绑定索引号为 [");
        }
    },
    WILL_NOT_BIND_RIGHT("] 因为该索引编号不存在") {
        @Override
        public void output() {
            System.out.println("] 因为该索引编号不存在");
        }
    },
    BIND_SQL_TO_CATEGORY("Do you want to bind sql to this category? [y/n]:") {
        @Override
        public void output() {
            System.out.print("您是否想为当前分类条目绑定一些sql语句? [y/n]:");
        }
    },
    UNBIND_SQL_FROM_CATEGORY("Do you want to remove binding sql from this category? [y/n]:") {
        @Override
        public void output() {
            System.out.print("您是否想为当前分类条目解绑一些sql语句? [y/n]:");
        }
    },
    LIST_ADDABLE_SQL("以下是您可以绑定的sql语句列表:") {
        @Override
        public void output() {
            System.out.println("以下是您可以绑定的sql语句列表:");
        }
    },
    LIST_REMOVABLE_SQL("以下是您可以解绑的sql语句列表:") {
        @Override
        public void output() {
            System.out.println("以下是您可以解绑的sql语句列表:");
        }
    },
    INPUT_SQL_INDEX("请输入sql编号(多条用','分隔):") {
        @Override
        public void output() {
            System.out.print("请输入sql编号(多条用','分隔):");
        }
    },
    INPUT_INDEX("请输入索引编号 >") {
        @Override
        public void output() {
            System.out.print("请输入索引编号 >");
        }
    },
    EMPTY_CATEGORY_LIST("EMPTY LIST...... please go back to customize category first :(") {
        @Override
        public void output() {
            System.out.println("空列表...... 请先返回上一级新增一条分类条目吧 :(");
        }
    },
    EMPTY_SQL_LIST("空SQL列表...请返回上一级为当前分类绑定一些SQL语句吧 :(") {
        @Override
        public void output() {
            System.out.println("空SQL列表...请返回上一级为当前分类绑定一些SQL语句吧 :(");
        }
    },
    BACK_BUTTON("0. 返回上一级") {
        @Override
        public void output() {
            System.out.println("0. 返回上一级");
        }
    },
    SUB_EXIT_BUTTON("99. 退出") {
        @Override
        public void output() {
            System.out.println("99. 退出");
        }
    },
    ENTER_BUTTON("line.separator") {
        @Override
        public void output() {
            System.out.println("line.separator");
        }
    },
    ENTER_CONTINUE("please click enter button to continue ...") {
        @Override
        public void output() {
            System.out.println("请按回车键继续...");
        }
    },
    INPUT_PARAMETER("请输入参数(多条用','分隔) ") {
        @Override
        public void output() {
            System.out.println("请输入参数(多条用','分隔) ");
        }
    },
    INPUT_SYMBOL(": ") {
        @Override
        public void output() {
            System.out.println(": ");
        }
    },
    SAVED_FILE("saved file at ") {
        @Override
        public void output() {
            System.out.println("saved file at ");
        }
    },

    ;

    private final String content;
    public abstract void output();

    SystemWarnLanguageEnum(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
