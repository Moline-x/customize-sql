package com.moio.common.enums;

/**
 * @description menu enum.
 *
 * @author molinchang
 */
public enum MenuEnum {
    /**
     * menu component.
     */
    TOP_BANNER("================ CUSTOMIZE-SQL-SYSTEM V1.0 =================") {
        @Override
        public void output() {
            System.out.println("================ CUSTOMIZE-SQL-SYSTEM V1.0 =================");
        }
    },
    CUSTOMIZE_SETTING("1. Customize Settings") {
        @Override
        public void output() {
            System.out.println("1. 自定义设置");
        }
    },
    VIEW_MENU("2. Menu View") {
        @Override
        public void output() {
            System.out.println("2. 生成界面显示");
        }
    },
    EXIT_BUTTON("0. Exit") {
        @Override
        public void output() {
            System.out.println("0. 退出");
        }
    },
    BASIC_SQL_SETTING("1. Basic SQL Settings") {
        @Override
        public void output() {
            System.out.println("1. SQL语句自定义设置");
        }
    },
    CATEGORY_SETTING("2. Category Settings") {
        @Override
        public void output() {
            System.out.println("2. 分类条目自定义设置");
        }
    },
    ADD_BASIC_SQL("1. Add Basic SQL") {
        @Override
        public void output() {
            System.out.println("1. 新增SQL语句");
        }
    },
    REMOVE_BASIC_SQL("2. Remove Basic SQL") {
        @Override
        public void output() {
            System.out.println("2. 删除SQL语句");
        }
    },
    UPDATE_BASIC_SQL("3. Update Basic SQL") {
        @Override
        public void output() {
            System.out.println("3. 更新SQL语句");
        }
    },
    SHOW_BASIC_SQL("4. Show Basic SQL") {
        @Override
        public void output() {
            System.out.println("4. 展示全部SQL语句");
        }
    },
    ADD_CATEGORY("1. Add Category") {
        @Override
        public void output() {
            System.out.println("1. 新增分类目录");
        }
    },
    REMOVE_CATEGORY("2. Remove Category") {
        @Override
        public void output() {
            System.out.println("2. 删除分类目录");
        }
    },
    UPDATE_CATEGORY("3. Update Category") {
        @Override
        public void output() {
            System.out.println("3. 更新分类目录");
        }
    },
    SHOW_CATEGORY("4. Show Category") {
        @Override
        public void output() {
            System.out.println("4. 展示全部分类目录");
        }
    },
    BIND_SQL_TO_CATEGORY("5. Update Sql For My Category") {
        @Override
        public void output() {
            System.out.println("5. 为当前分类目录绑定SQL语句");
        }
    },
    ;

    private final String content;
    public abstract void output();

    MenuEnum(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
