package com.moio.manager;

import com.moio.common.constant.ArgsConstant;
import com.moio.common.enums.SystemWarnLanguageEnum;
import com.moio.common.exception.ContentExistedException;
import com.moio.common.exception.SqlExistedException;
import com.moio.entity.*;
import com.moio.service.BasicSqlService;
import com.moio.service.CategoryService;
import com.moio.service.CategorySqlService;
import com.moio.utils.ConcatSQLUtil;
import com.moio.utils.ConvertUtil;
import com.moio.utils.KeyBoardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description customize sql system manager with category service, category sql service,
 *              basic sql service management function.
 *
 * @author molinchang
 */
@Component
public class CustomizeSqlManager {

    @Autowired
    CategoryService categoryService;
    @Autowired
    BasicSqlService basicSqlService;
    @Autowired
    CategorySqlService categorySqlService;

    Menu menu = new Menu();

    Scanner scanner = new Scanner(System.in);
    boolean isMultipleValue = false;
    Path path = Path.of(ArgsConstant.DATA_FILE_PATH);

    /**
     * manage init first menu.
     *
     */
    public void initFirstMenu() {
        // call menu main menu.
        menu.mainMenu();
        int number = ArgsConstant.REMAIN_NUMBER;
        try {
            number = scanner.nextInt();
        } catch (InputMismatchException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            KeyBoardUtil.enter();
            removeStrBuffer(scanner);
            initFirstMenu();
        }
        switch (number) {
            case 1: initClickSettingsMenu(); break;
            case 2: initClickViewMenu(); break;
            case ArgsConstant.REMAIN_NUMBER:
                reWriteFileBeforeExit();
                System.exit(ArgsConstant.REMAIN_NUMBER);
            default: initFirstMenu(); break;
        }
    }

    /**
     * manage click event for settings menu.
     *
     */
    public void initClickSettingsMenu() {
        // call settings menu
        menu.settingsMenu();
        int index = ArgsConstant.REMAIN_NUMBER;
        try {
            index = scanner.nextInt();
        } catch (InputMismatchException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            KeyBoardUtil.enter();
            removeStrBuffer(scanner);
            initClickSettingsMenu();
        }
        switch (index) {
            case 1: initClickBasicSQLSettingsMenu(); break;
            case 2: initClickCategorySettingsMenu(); break;
            case ArgsConstant.REMAIN_NUMBER: initFirstMenu(); break;
            case ArgsConstant.EXIT_NUM:
                reWriteFileBeforeExit();
                System.exit(ArgsConstant.REMAIN_NUMBER);
            default: initClickSettingsMenu(); break;
        }

    }

    /**
     * manage click event for basic sql settings menu.
     *
     */
    public void initClickBasicSQLSettingsMenu() {
        // call basic sql settings menu.
        menu.basicSQLSettingsMenu();
        int index = ArgsConstant.REMAIN_NUMBER;
        try {
            index = scanner.nextInt();
        } catch (InputMismatchException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            KeyBoardUtil.enter();
            removeStrBuffer(scanner);
            initClickBasicSQLSettingsMenu();
        }
        switch (index) {
            case 1: initAddBasicSQLView(); break;
            case 2: initRemoveBasicSQLView(); break;
            case 3: initUpdateBasicSQLView(); break;
            case 4: initShowBasicSQLView(); break;
            case ArgsConstant.REMAIN_NUMBER: initClickSettingsMenu(); break;
            case ArgsConstant.EXIT_NUM:
                reWriteFileBeforeExit();
                System.exit(ArgsConstant.REMAIN_NUMBER);
            default: initClickBasicSQLSettingsMenu(); break;
        }
    }

    /**
     * manage click event for category settings menu.
     *
     */
    public void initClickCategorySettingsMenu() {
        // call category settings menu.
        menu.categorySettingsMenu();
        int index = ArgsConstant.REMAIN_NUMBER;
        try {
            index = scanner.nextInt();
        } catch (InputMismatchException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            KeyBoardUtil.enter();
            removeStrBuffer(scanner);
            initClickCategorySettingsMenu();
        }
        switch (index) {
            case 1: initAddCategoryView(); break;
            case 2: initRemoveCategoryView(); break;
            case 3: initUpdateCategoryView(); break;
            case 4: initShowCategoryView(); break;
            case 5: initUpdateCategorySqlView(); break;
            case ArgsConstant.REMAIN_NUMBER: initClickSettingsMenu(); break;
            case ArgsConstant.EXIT_NUM:
                reWriteFileBeforeExit();
                System.exit(ArgsConstant.REMAIN_NUMBER);
            default: initClickCategorySettingsMenu(); break;
        }
    }

    /**
     * init add basic sql menu view.
     *
     */
    private void initAddBasicSQLView() {
        subShowBasicSQLView();
        // temp button remove string buffer
        removeStrBuffer(scanner);
        BasicSql basicSql = new BasicSql();
        SystemWarnLanguageEnum.INPUT_TABLE_NAME.output();
        String tableName = scanner.next();
        // if click 0 , go back
        MoioUtils.goBackHandler(tableName).processGoBackHandle(this::initClickBasicSQLSettingsMenu);
        SystemWarnLanguageEnum.INPUT_CONDITION.output();
        String condition = scanner.next();
        MoioUtils.goBackHandler(condition).processGoBackHandle(this::initClickBasicSQLSettingsMenu);
        SystemWarnLanguageEnum.INPUT_OTHER_CONDITION.output();
        removeStrBuffer(scanner);
        String others = scanner.nextLine();
        MoioUtils.goBackHandler(others).processGoBackHandle(this::initClickBasicSQLSettingsMenu);
        String value = ConcatSQLUtil.concatSQLwithCondition(condition, tableName, others);
        basicSql.setSqlValue(value);
        removeStrBuffer(scanner);
        SystemWarnLanguageEnum.INPUT_KEY.output();
        String key = scanner.next();
        basicSql.setSqlKey(key);
        try {
            basicSqlService.add(basicSql);
            System.out.println(SystemWarnLanguageEnum.ADD_SUCCEED.getContent() + basicSql);
            KeyBoardUtil.enter();
            initClickBasicSQLSettingsMenu();
        } catch (DuplicateKeyException e) {
            System.out.println(SystemWarnLanguageEnum.ADD_FAILED.getContent() + Objects.requireNonNull(e.getRootCause()).getMessage());
            KeyBoardUtil.enter();
            initClickBasicSQLSettingsMenu();
        } catch (SqlExistedException e) {
            System.out.println(SystemWarnLanguageEnum.ADD_FAILED.getContent() + e.getMessage());
            KeyBoardUtil.enter();
            initClickBasicSQLSettingsMenu();
        }
    }

    /**
     * init remove basic sql menu view.
     *
     */
    private void initRemoveBasicSQLView() {
        subShowBasicSQLView();
        SystemWarnLanguageEnum.INPUT_DELETE_INDEX.output();
        int index = ArgsConstant.REMAIN_NUMBER;
        try {
            index = scanner.nextInt();
        } catch (InputMismatchException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            KeyBoardUtil.enter();
            removeStrBuffer(scanner);
            initRemoveBasicSQLView();
        }
        if (ArgsConstant.REMAIN_NUMBER == index) {
            initClickBasicSQLSettingsMenu();
        } else {
            Boolean delete = basicSqlService.deleteById(index);
            if (delete) {
                SystemWarnLanguageEnum.DELETE_SUCCEED.output();
            } else {
                SystemWarnLanguageEnum.DELETE_FAILED.output();
            }
            KeyBoardUtil.enter();
            initClickBasicSQLSettingsMenu();
        }

    }

    /**
     * init update basic sql menu view.
     *
     */
    private void initUpdateBasicSQLView() {
        subShowBasicSQLView();
        SystemWarnLanguageEnum.INPUT_UPDATE_INDEX.output();
        int index = ArgsConstant.REMAIN_NUMBER;
        try {
            index = scanner.nextInt();
        } catch (InputMismatchException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            KeyBoardUtil.enter();
            removeStrBuffer(scanner);
            initUpdateBasicSQLView();
        }
        if (ArgsConstant.REMAIN_NUMBER == index) {
            initClickBasicSQLSettingsMenu();
        } else {
            BasicSql basicSql = basicSqlService.getById(index);
            if (basicSql == null) {
                SystemWarnLanguageEnum.INVALID_INDEX.output();
                KeyBoardUtil.enter();
                initUpdateBasicSQLView();
            }
            SystemWarnLanguageEnum.SEARCH_RESULT.output();
            System.out.println(SystemWarnLanguageEnum.ID.getContent() + basicSql.getId());
            System.out.println(SystemWarnLanguageEnum.KEY.getContent() + basicSql.getSqlKey());
            System.out.println(SystemWarnLanguageEnum.SQL.getContent() + basicSql.getSqlValue());
            SystemWarnLanguageEnum.SPLIT_SYMBOL.output();
            // temp button remove string buffer
            removeStrBuffer(scanner);
            SystemWarnLanguageEnum.INPUT_UPDATE_KEY.output();
            String key = scanner.nextLine();
            if (!ArgsConstant.REMAIN_SYMBOL.equals(key)) {
                basicSql.setSqlKey(key);
            }
            // temp button remove string buffer
            removeStrBuffer(scanner);
            SystemWarnLanguageEnum.INPUT_UPDATE_SQL.output();
            String sql = scanner.nextLine();
            if (!ArgsConstant.REMAIN_SYMBOL.equals(sql)) {
                basicSql.setSqlValue(sql);
            }
            try {
                Boolean update = basicSqlService.update(basicSql);
                if (update) {
                    SystemWarnLanguageEnum.UPDATE_SUCCEED.output();
                } else {
                    SystemWarnLanguageEnum.UPDATE_FAILED.output();
                }
            } catch (SqlExistedException e) {

            }

            KeyBoardUtil.enter();
            initClickBasicSQLSettingsMenu();
        }
    }

    /**
     * init show basic sql menu view.
     *
     */
    private void initShowBasicSQLView() {
        subShowBasicSQLView();
        KeyBoardUtil.enter();
        initClickBasicSQLSettingsMenu();
    }

    /**
     * sub show basic sql view.
     */
    private List<Integer> subShowBasicSQLView() {
        List<BasicSql> list = basicSqlService.list();
        list.forEach(s -> System.out.println(s.getId() + ". " + s.getSqlValue()));

        return list.stream().map(BasicSql::getId).collect(Collectors.toList());
    }

    /**
     * init update category sql menu view.
     *
     */
    private void initUpdateCategorySqlView() {
        subShowCategoryView();
        SystemWarnLanguageEnum.INPUT_CATEGORY_INDEX.output();
        int index = ArgsConstant.REMAIN_NUMBER;
        try {
            index = scanner.nextInt();
        } catch (InputMismatchException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            KeyBoardUtil.enter();
            removeStrBuffer(scanner);
            initUpdateCategorySqlView();
        }
        if (index == ArgsConstant.REMAIN_NUMBER) {
            initClickCategorySettingsMenu();
        } else {
            Category category = categoryService.getByIndex(index);
            if (category == null) {
                SystemWarnLanguageEnum.INVALID_INDEX.output();
                KeyBoardUtil.enter();
                initUpdateCategorySqlView();
            }
            System.out.println(SystemWarnLanguageEnum.CATEGORY_SYMBOL_LEFT.getContent() + category.getContent() + SystemWarnLanguageEnum.CATEGORY_SYMBOL_RIGHT.getContent());
            List<BasicSql> basicSqlList = category.getBasicSqlList();
            List<Integer> idList = basicSqlList.stream().map(BasicSql::getId).collect(Collectors.toList());
            basicSqlList.forEach(b -> System.out.println(b.getId() + ". " + b.getSqlValue()));
            SystemWarnLanguageEnum.SPLIT_SYMBOL.output();
            SystemWarnLanguageEnum.BIND_SQL_TO_CATEGORY.output();
            removeStrBuffer(scanner);
            String add = scanner.next();
            if ("y".equals(add) || "Y".equals(add)) {
                List<BasicSql> list = basicSqlService.list();
                List<BasicSql> sqlList = list.stream().filter(basicSql -> !idList.contains(basicSql.getId())).collect(Collectors.toList());
                SystemWarnLanguageEnum.LIST_ADDABLE_SQL.output();
                sqlList.forEach(s -> System.out.println(s.getId() + ". " + s.getSqlValue()));
                SystemWarnLanguageEnum.SPLIT_SYMBOL.output();
                SystemWarnLanguageEnum.INPUT_SQL_INDEX.output();
                removeStrBuffer(scanner);
                String sqlIndex;
                try {
                    sqlIndex = scanner.nextLine();
                    if (ConvertUtil.isMultipleParameter(sqlIndex)) {
                        List<String> toList = ConvertUtil.convertStringToList(sqlIndex);
                        toList.forEach(id -> categorySqlService.add(category.getId(), Integer.valueOf(id), 0));
                    } else {
                        categorySqlService.add(index, Integer.valueOf(sqlIndex), 0);
                    }
                    SystemWarnLanguageEnum.ADD_SUCCEED.output();
                } catch (NumberFormatException e) {
                    SystemWarnLanguageEnum.INVALID_INDEX.output();
                    KeyBoardUtil.enter();
                    removeStrBuffer(scanner);
                    initUpdateCategorySqlView();
                }
            }
            SystemWarnLanguageEnum.UNBIND_SQL_FROM_CATEGORY.output();
//            temp = scanner.nextLine();
            String delete = scanner.next();
            if ("y".equals(delete) || "Y".equals(delete)) {
                Category byIndex = categoryService.getByIndex(index);
                if (byIndex == null) {
                    SystemWarnLanguageEnum.INVALID_INDEX.output();
                    KeyBoardUtil.enter();
                    initUpdateCategorySqlView();
                }
                List<BasicSql> basicSqls = byIndex.getBasicSqlList();
                SystemWarnLanguageEnum.LIST_REMOVABLE_SQL.output();
                basicSqls.forEach(s -> System.out.println(s.getId() + ". " + s.getSqlValue()));
                SystemWarnLanguageEnum.SPLIT_SYMBOL.output();
                SystemWarnLanguageEnum.INPUT_SQL_INDEX.output();
                removeStrBuffer(scanner);
                boolean deleted = false;
                String sqlIndex;
                try {
                    sqlIndex = scanner.nextLine();
                    if (ConvertUtil.isMultipleParameter(sqlIndex)) {
                        List<Integer> toList = ConvertUtil.convertStringToList(sqlIndex).stream().map(Integer::valueOf).collect(Collectors.toList());
                        deleted = categorySqlService.delete(byIndex.getId(), toList);
                    } else {
                        List<Integer> list = new ArrayList<>();
                        list.add(Integer.valueOf(sqlIndex));
                        deleted = categorySqlService.delete(byIndex.getId(), list);
                    }
                } catch (NumberFormatException e) {
                    SystemWarnLanguageEnum.INVALID_INDEX.output();
                    KeyBoardUtil.enter();
                    removeStrBuffer(scanner);
                    initUpdateCategorySqlView();
                }
                if (deleted) {
                    SystemWarnLanguageEnum.DELETE_SUCCEED.output();
                } else {
                    SystemWarnLanguageEnum.DELETE_FAILED.output();
                }
            }
            KeyBoardUtil.enter();
            initClickCategorySettingsMenu();
        }

    }

    /**
     * init add category view.
     *
     */
    private void initAddCategoryView() {
        List<Category> categories = subShowCategoryView();

        int indexC = categories.get(categories.size() - 1).getIndexC() + 1;
        Category category = new Category();
        category.setIndexC(indexC);
        removeStrBuffer(scanner);
        SystemWarnLanguageEnum.INPUT_CATEGORY_CONTENT.output();
        String content = scanner.nextLine();
        if (ArgsConstant.BACK_SIGNAL.equals(content)) {
            initClickCategorySettingsMenu();
        } else {
            category.setContent(content);
            try {
                categoryService.add(category);
                System.out.println(SystemWarnLanguageEnum.ADD_SUCCEED.getContent() + category);
                KeyBoardUtil.enter();
                List<Integer> sqlIdList = subShowBasicSQLView();
                SystemWarnLanguageEnum.INPUT_SQL_INDEX.output();
                String categorySql;
                try {
                    categorySql = scanner.nextLine();
                    boolean multipleParameter = ConvertUtil.isMultipleParameter(categorySql);
                    Category categoryNew = categoryService.getByIndex(indexC);
                    if (multipleParameter) {
                        List<String> strList = ConvertUtil.convertStringToList(categorySql);
                        strList.forEach(s -> {
                            if (sqlIdList.contains(Integer.valueOf(s))) {
                                categorySqlService.add(categoryNew.getId(), Integer.valueOf(s), 0);
                            } else {
                                System.out.println(SystemWarnLanguageEnum.WILL_NOT_BIND_LEFT.getContent() + s + SystemWarnLanguageEnum.WILL_NOT_BIND_RIGHT.getContent());
                            }
                        });
                    } else {
                        if (sqlIdList.contains(Integer.valueOf(categorySql))) {
                            categorySqlService.add(categoryNew.getId(), Integer.valueOf(categorySql), 0);
                        } else {
                            System.out.println(SystemWarnLanguageEnum.WILL_NOT_BIND_LEFT.getContent() + categorySql + SystemWarnLanguageEnum.WILL_NOT_BIND_RIGHT.getContent());
                        }
                    }
                } catch (NumberFormatException e) {
                    SystemWarnLanguageEnum.INVALID_INDEX.output();
                    KeyBoardUtil.enter();
                    removeStrBuffer(scanner);
                    initClickCategorySettingsMenu();
                }
                SystemWarnLanguageEnum.ADD_SUCCEED.output();
                KeyBoardUtil.enter();
                initClickCategorySettingsMenu();
            } catch (DuplicateKeyException e) {
                String[] split = Objects.requireNonNull(e.getRootCause()).getLocalizedMessage().split("SQL statement:");
                System.out.println(SystemWarnLanguageEnum.ADD_FAILED.getContent() + split[ArgsConstant.REMAIN_NUMBER]);
                KeyBoardUtil.enter();
                initClickCategorySettingsMenu();
            } catch (ContentExistedException e) {
                System.out.println(SystemWarnLanguageEnum.ADD_FAILED.getContent() + e.getMessage());
                KeyBoardUtil.enter();
                initClickCategorySettingsMenu();
            }
        }
    }

    /**
     * init remove category view.
     *
     */
    private void initRemoveCategoryView() {
        subShowCategoryView();
        SystemWarnLanguageEnum.INPUT_DELETE_INDEX.output();
        int index = ArgsConstant.REMAIN_NUMBER;
        try {
            index = scanner.nextInt();
        } catch (InputMismatchException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            KeyBoardUtil.enter();
            removeStrBuffer(scanner);
            initRemoveCategoryView();
        }
        if (ArgsConstant.REMAIN_NUMBER == index) {
            initClickCategorySettingsMenu();
        } else {
            Boolean delete = categoryService.deleteById(
                    Optional.ofNullable(categoryService.getByIndex(index)).orElse(new Category()).getId()
            );
            if (delete) {
                SystemWarnLanguageEnum.DELETE_SUCCEED.output();
            } else {
                SystemWarnLanguageEnum.DELETE_FAILED.output();
            }
            KeyBoardUtil.enter();
            initClickCategorySettingsMenu();
        }
    }

    /**
     * init show category menu view.
     *
     */
    private void initShowCategoryView() {
        subShowCategoryView();
        KeyBoardUtil.enter();
        initClickCategorySettingsMenu();
    }

    /**
     * init update category view.
     *
     */
    private void initUpdateCategoryView() {
        subShowCategoryView();
        SystemWarnLanguageEnum.INPUT_UPDATE_INDEX.output();
        int index = ArgsConstant.REMAIN_NUMBER;
        try {
            index = scanner.nextInt();
        } catch (InputMismatchException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            KeyBoardUtil.enter();
            removeStrBuffer(scanner);
            initUpdateCategoryView();
        }
        if (ArgsConstant.REMAIN_NUMBER == index) {
            initClickCategorySettingsMenu();
        } else {
            Category category = categoryService.getByIndex(index);
            if (category == null) {
                SystemWarnLanguageEnum.INVALID_INDEX.output();
                KeyBoardUtil.enter();
                initUpdateCategoryView();
            }
            SystemWarnLanguageEnum.SEARCH_RESULT.output();
            System.out.println(SystemWarnLanguageEnum.INDEX.getContent() + category.getIndexC());
            System.out.println(SystemWarnLanguageEnum.CONTENT.getContent() + category.getContent());
            SystemWarnLanguageEnum.SPLIT_SYMBOL.output();
            SystemWarnLanguageEnum.INPUT_UPDATE_INDEX_FOR_CATEGORY.output();
            int indexC = ArgsConstant.REMAIN_NUMBER;
            try {
                indexC = scanner.nextInt();
            } catch (InputMismatchException e) {
                SystemWarnLanguageEnum.INVALID_INDEX.output();
                KeyBoardUtil.enter();
                removeStrBuffer(scanner);
                initUpdateCategoryView();
            }
            if (ArgsConstant.REMAIN_NUMBER != indexC) {
                category.setIndexC(indexC);
            }
            // temp button remove string buffer
            removeStrBuffer(scanner);
            SystemWarnLanguageEnum.INPUT_UPDATE_CONTENT.output();
            String content = scanner.nextLine();
            if (!ArgsConstant.REMAIN_SYMBOL.equals(content)) {
                category.setContent(content);
            }
            Boolean update = categoryService.update(category);
            if (update) {
                SystemWarnLanguageEnum.UPDATE_SUCCEED.output();
            } else {
                SystemWarnLanguageEnum.UPDATE_FAILED.output();
            }
            KeyBoardUtil.enter();
            initClickCategorySettingsMenu();
        }
    }

    /**
     * sub show category view.
     */
    private List<Category> subShowCategoryView() {
        List<Category> list = categoryService.list();
        list.forEach(c -> System.out.println(c.getIndexC() + ". " + c.getContent()));

        return list;
    }

    /**
     * manage click event for view menu.
     *
     */
    public void initClickViewMenu() {
        // call view menu
        menu.viewMenu();
        // query all category from DB.
        List<Category> list = categoryService.list();
        if (list.isEmpty()) {
            SystemWarnLanguageEnum.EMPTY_CATEGORY_LIST.output();
            KeyBoardUtil.enter();
            initFirstMenu();
        } else {
            list.forEach(c -> System.out.println(c.getIndexC() + ". " + c.getContent()));
        }
        SystemWarnLanguageEnum.BACK_BUTTON.output();
        SystemWarnLanguageEnum.SUB_EXIT_BUTTON.output();
        SystemWarnLanguageEnum.INPUT_INDEX.output();
        int index = ArgsConstant.REMAIN_NUMBER;
        try {
            index = scanner.nextInt();
        } catch (InputMismatchException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            KeyBoardUtil.enter();
            removeStrBuffer(scanner);
            initClickViewMenu();
        }
        if (index == ArgsConstant.REMAIN_NUMBER) {
            // go back
            initFirstMenu();
        } else if (index == ArgsConstant.EXIT_NUM) {
            reWriteFileBeforeExit();
            System.exit(ArgsConstant.REMAIN_NUMBER);
        } else {
            showBasicSQLByCategoryIndex(index);
        }

    }

    /**
     * show sql group by category you chose
     *
     * @param index index number
     */
    private void showBasicSQLByCategoryIndex(int index) {
        Category category = categoryService.getByIndex(index);
        if (category == null) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
        } else {
            List<BasicSql> basicSqlList = category.getBasicSqlList();
            if (basicSqlList == null || basicSqlList.isEmpty()) {
                SystemWarnLanguageEnum.EMPTY_SQL_LIST.output();
            } else {
                List<String> keyList = generateKeyList(basicSqlList);
                keyListView(keyList, basicSqlList);
            }
        }
        KeyBoardUtil.enter();
        initClickViewMenu();
    }

    /**
     * generate key list by sql list.
     *
     * @param sqlList basic sql list
     * @return key list
     */
    private List<String> generateKeyList(List<BasicSql> sqlList) {
        return sqlList.stream().map(BasicSql::getSqlKey).distinct().collect(Collectors.toList());
    }

    /**
     * reflect key list & value list to a map
     * key -> value
     *
     */
    private void keyListView(List<String> keyList, List<BasicSql> sqlList) {

        if (keyList != null) {
            for (String key : keyList) {
                if ("".equals(key)) {
                    travelSql(sqlList);
                } else {
                    // temp button remove string buffer
                    removeStrBuffer(scanner);
                    System.out.print(SystemWarnLanguageEnum.INPUT_PARAMETER.getContent() + key + SystemWarnLanguageEnum.INPUT_SYMBOL.getContent());
                    String value = scanner.nextLine();
                    if (ConvertUtil.isMultipleParameter(value)) {
                        isMultipleValue = true;
                        value = ConvertUtil.convertListToString(ConvertUtil.convertMultiParameterToList(value, key));
                    }else {
                        value = ConvertUtil.convertRules(key, value);
                    }
                    autoSql(key, value, sqlList);
                }
            }
        } else {
            travelSql(sqlList);
        }
    }

    /**
     * remove string buffer in console.
     *
     * @param scanner scanner
     */
    private void removeStrBuffer(Scanner scanner) {
        scanner.nextLine();
    }

    /**
     * no condition, just travel sql.
     *
     * @param sqlList sql list
     */
    private void travelSql(List<BasicSql> sqlList) {

        for (BasicSql basicSQL : sqlList) {
            if ("".equals(basicSQL.getSqlKey())) {
                System.out.println(basicSQL.getSqlValue());
            }
        }
    }

    /**
     * auto create sql with parameter and sql list
     *
     * @param value           value from key map
     * @param sqlList         category sql list
     */
    private void autoSql(String key, String value , List<BasicSql> sqlList) {

        String sql;
        for (BasicSql basicSQL : sqlList) {
            sql = basicSQL.getSqlValue();
            if (basicSQL.getSqlKey().equals(key)) {
                if (isMultipleValue) {
                    sql = ConcatSQLUtil.concatSQLwithMultipleParameters(sql, ConvertUtil.convertStringToList(value));
                } else {
                    sql = ConcatSQLUtil.concatSQLwithSingleParamter(sql, value);
                }
                System.out.println(sql);
            }
        }

    }

    /**
     * saved file before system exit.
     *
     */
    private void reWriteFileBeforeExit() {
//        String readString = Files.readString(path);
        StringBuilder sb = new StringBuilder();
        String enter = System.getProperty(SystemWarnLanguageEnum.ENTER_BUTTON.getContent());
        // search category
        List<Category> list = categoryService.list();
        list.forEach(c -> {
            sb.append(ConcatSQLUtil.concatSQLForCategory(c));
            sb.append(enter);
        });
        // search basic sql
        List<BasicSql> sqlList = basicSqlService.list();
        sqlList.forEach(s -> {
            sb.append(ConcatSQLUtil.concatSQLForSql(s));
            sb.append(enter);
        });
        // search category sql list
        List<CategorySql> categorySqlList = categorySqlService.list();
        categorySqlList.forEach(cs -> {
            sb.append(ConcatSQLUtil.concatSQLForCategorySql(cs));
            sb.append(enter);
        });
        try {
            Files.writeString(path, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(SystemWarnLanguageEnum.SAVED_FILE.getContent() + path.toString());
    }
}
