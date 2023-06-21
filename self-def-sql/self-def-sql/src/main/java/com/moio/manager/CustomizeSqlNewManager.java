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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
 *              new feature to use GUI.
 *
 * @author molinchang
 */
@Slf4j
@Component
public class CustomizeSqlNewManager {

    @Autowired
    CategoryService categoryService;
    @Autowired
    BasicSqlService basicSqlService;
    @Autowired
    CategorySqlService categorySqlService;

    boolean isMultipleValue = false;
    Path path = Path.of(ArgsConstant.DATA_FILE_PATH);


    /**
     * init add basic sql menu view.
     *
     * @param tableName     table name
     * @param condition     sql condition
     * @param others        other condition
     * @param key           key condition
     * @return result dto
     */
    public ResultDto initAddBasicSQLView(String tableName, String condition, String others, String key) {

        BasicSql basicSql = new BasicSql();
        ResultDto resultDto = new ResultDto();
        String value = ConcatSQLUtil.concatSQLwithCondition(condition, tableName, others);
        basicSql.setSqlValue(value);
        basicSql.setSqlKey(key);
        // execute add
        try {
            Boolean add = basicSqlService.add(basicSql);
            resultDto.setUpdateResult(add);
            if (Boolean.TRUE.equals(add)) {
                resultDto.setMessage(SystemWarnLanguageEnum.ADD_SUCCEED.getContent());
            } else {
                resultDto.setMessage(SystemWarnLanguageEnum.ADD_FAILED.getContent());
            }
            log.debug(resultDto.getMessage() + basicSql);
        } catch (DuplicateKeyException e) {
            resultDto.setMessage(SystemWarnLanguageEnum.ADD_FAILED.getContent() + Objects.requireNonNull(e.getRootCause()).getMessage());
            resultDto.setUpdateResult(false);
        } catch (SqlExistedException e) {
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.ADD_FAILED.getContent() + e.getMessage());
        }
        return resultDto;
    }

    /**
     * init remove basic sql menu view.
     *
     * @param text index
     * @return result dto
     */
    public ResultDto initRemoveBasicSQLView(String text) {

        ResultDto resultDto = new ResultDto();
        int index;
        try {
            index = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.DELETE_FAILED.getContent());
            return resultDto;
        }
        Boolean delete = basicSqlService.deleteById(index);
        resultDto.setUpdateResult(delete);
        if (Boolean.TRUE.equals(delete)) {
            resultDto.setMessage(SystemWarnLanguageEnum.DELETE_SUCCEED.getContent());
            SystemWarnLanguageEnum.DELETE_SUCCEED.output();
        } else {
            resultDto.setMessage(SystemWarnLanguageEnum.DELETE_FAILED.getContent());
            SystemWarnLanguageEnum.DELETE_FAILED.output();
        }

        return resultDto;

    }

    /**
     * init update basic sql menu view.
     *
     * @param text  index
     * @param key   sql key
     * @param sql   sql value
     * @return result dto with basic sql
     */
    public ResultDto<BasicSql> initUpdateBasicSQLView(String text, String key, String sql) {

        ResultDto<BasicSql> resultDto = new ResultDto<>();
        int index;
        try {
            index = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            return resultDto;
        }
        BasicSql basicSql = basicSqlService.getById(index);
        if (basicSql == null) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            return resultDto;
        }
        SystemWarnLanguageEnum.SEARCH_RESULT.output();
        log.info(SystemWarnLanguageEnum.ID.getContent() + basicSql.getId());
        log.info(SystemWarnLanguageEnum.KEY.getContent() + basicSql.getSqlKey());
        log.info(SystemWarnLanguageEnum.SQL.getContent() + basicSql.getSqlValue());

        if (!"@".equals(key)) {
            basicSql.setSqlKey(key);
        }
        if (!"@".equals(sql)) {
            basicSql.setSqlValue(sql);
        }
        try {
            Boolean update = basicSqlService.update(basicSql);
            resultDto.setUpdateResult(update);
            if (Boolean.TRUE.equals(update)) {
                resultDto.setMessage(SystemWarnLanguageEnum.UPDATE_SUCCEED.getContent());
                SystemWarnLanguageEnum.UPDATE_SUCCEED.output();
            } else {
                resultDto.setMessage(SystemWarnLanguageEnum.UPDATE_FAILED.getContent());
                SystemWarnLanguageEnum.UPDATE_FAILED.output();
            }
        } catch (SqlExistedException e) {
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.UPDATE_FAILED.getContent() + e.getMessage());
        }

        resultDto.setData(basicSql);

        return resultDto;
    }


    /**
     * sub show basic sql view.
     */
    public List<BasicSql> subShowBasicSQLView() {
        List<BasicSql> list = basicSqlService.list();
        list.forEach(s -> log.debug(s.getId() + ". " + s.getSqlValue()));

        return list;
    }

    /**
     * sub show basic sql view.
     *
     * @param search search key word
     */
    public List<BasicSql> subShowBasicSQLViewWithSearch(String search) {
        List<BasicSql> list = basicSqlService.list();
        if (!"".equals(search) && search != null) {
            List<BasicSql> collect = list.stream().filter(basicSql -> basicSql.getSqlValue().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
            collect.forEach(s -> log.debug(s.getId() + ". " + s.getSqlValue()));
            return collect;
        }

        return list;
    }

    /**
     * sub show category view.
     *
     * @param search search key word
     */
    public List<Category> subShowCategoryViewWithSearch(String search) {
        List<Category> list = categoryService.list();
        if (!"".equals(search) && search != null) {
            List<Category> collect = list.stream().filter(category -> category.getContent().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
            collect.forEach(c -> log.debug(c.getIndexC() + ". " + c.getContent()));
            return collect;
        }

        return list;
    }

    /**
     * init bind sql for category.
     *
     * @param bindSqlDto bind sql dto
     * @return result dto with list for basic sql
     */
    public ResultDto<List<BasicSql>> initBindSQLForCategory(final BindSqlDto bindSqlDto) {
        final String categoryIndex = bindSqlDto.getCategoryIndex();
        final String sqlIndex = bindSqlDto.getContent();
        final String searchText = bindSqlDto.getSearchText();
        final String searchKey = bindSqlDto.getSearchKey();
        final ResultDto<List<BasicSql>> resultDto = new ResultDto<>();
        final int index;
        try {
            index = Integer.parseInt(categoryIndex);
        } catch (NumberFormatException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            return resultDto;
        }

        // get category by index
        final Category category = categoryService.getByIndex(index);
        if (category == null) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            return resultDto;
        }
        log.debug("{}{}{}", SystemWarnLanguageEnum.CATEGORY_SYMBOL_LEFT.getContent(), category.getContent(), SystemWarnLanguageEnum.CATEGORY_SYMBOL_RIGHT.getContent());

        // get sql for category
        List<BasicSql> sortBasicSqlList = sortBasicSqlList(category);
        final List<Integer> idList = sortBasicSqlList.stream().map(BasicSql::getId).collect(Collectors.toList());
        sortBasicSqlList.forEach(basicSql -> log.debug("{}. {}", basicSql.getId(), basicSql.getSqlValue()));

        SystemWarnLanguageEnum.BIND_SQL_TO_CATEGORY.output();

        // filter sql
        final List<BasicSql> list = basicSqlService.list();
        final List<BasicSql> sqlList = list.stream()
                .filter(basicSql -> !idList.contains(basicSql.getId()))
                .filter(basicSql -> checkSqlKeyword(basicSql, searchText, searchKey))
                .collect(Collectors.toList());
        log.debug(SystemWarnLanguageEnum.LIST_ADDABLE_SQL.getContent());
        sqlList.forEach(s -> log.debug("{}. {}", s.getId(), s.getSqlValue()));
        resultDto.setData(sqlList);
        log.debug(SystemWarnLanguageEnum.INPUT_SQL_INDEX.getContent());
        if (!"".equals(sqlIndex) && sqlIndex != null) {
            try {
                int num;
                if (idList.isEmpty()) {
                    num = 0;
                } else {
                    num = categorySqlService.getMaxSeq(category.getId(), idList) + 1;
                }
                if (ConvertUtil.isMultipleParameter(sqlIndex)) {
                    final List<String> toList = ConvertUtil.convertStringToList(sqlIndex);
                    for (int i = num; i < toList.size() + num; i++) {
                        String id = toList.get(i-num);
                        categorySqlService.add(category.getId(), Integer.valueOf(id), i);
                    }
                } else {
                    categorySqlService.add(category.getId(), Integer.valueOf(sqlIndex), num);
                }
                SystemWarnLanguageEnum.ADD_SUCCEED.output();
                resultDto.setUpdateResult(true);
                resultDto.setMessage("绑定成功");
            } catch (NumberFormatException e) {
                SystemWarnLanguageEnum.INVALID_INDEX.output();
                resultDto.setUpdateResult(false);
                resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            } catch (DataIntegrityViolationException e) {
                SystemWarnLanguageEnum.ADD_FAILED.output();
                resultDto.setUpdateResult(false);
                resultDto.setMessage("绑定失败");
            }
        } else {
            resultDto.setUpdateResult(true);
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
        }

        return resultDto;
    }

    /**
     * check sql key word.
     *
     * @param basicSql      basic sql
     * @param searchText    search text
     * @param searchKey     search key
     * @return true or false
     */
    private boolean checkSqlKeyword(BasicSql basicSql, String searchText, String searchKey) {
        if ("key".equals(searchKey)) {
            if (searchText != null) {
                return basicSql.getSqlKey().toLowerCase().contains(searchText.toLowerCase());
            }
        } else if ("sql".equals(searchKey) && (searchText != null)) {
                return basicSql.getSqlValue().toLowerCase().contains(searchText.toLowerCase());
        }
        return true;
    }

    /**
     * init unbind sql for category.
     *
     * @param text      index
     * @param sqlIndex  sql index string
     * @return result dto with list for basic sql
     */
    public ResultDto<List<BasicSql>> initUnbindSQLForCategory(String text, String sqlIndex) {

        ResultDto<List<BasicSql>> resultDto = new ResultDto<>();
        int index;
        try {
            index = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            return resultDto;
        }

        Category category = categoryService.getByIndex(index);
        if (category == null) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            return resultDto;
        }

        List<BasicSql> sortBasicSqlList = sortBasicSqlList(category);
        log.debug(SystemWarnLanguageEnum.LIST_REMOVABLE_SQL.getContent());
        sortBasicSqlList.forEach(s -> log.debug("{}. {}", s.getId(), s.getSqlValue()));

        resultDto.setData(sortBasicSqlList);
        if (!"".equals(sqlIndex) && sqlIndex != null) {
            boolean deleted = false;
            List<Integer> collectIdList;
            try {
                if (ConvertUtil.isMultipleParameter(sqlIndex)) {
                    List<Integer> toList = ConvertUtil.convertStringToList(sqlIndex).stream().map(Integer::valueOf).collect(Collectors.toList());
                    deleted = categorySqlService.delete(category.getId(), toList);
                    collectIdList = sortBasicSqlList.stream().map(BasicSql::getId).filter(id -> !toList.contains(id)).collect(Collectors.toList());
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(Integer.valueOf(sqlIndex));
                    deleted = categorySqlService.delete(category.getId(), list);
                    collectIdList = sortBasicSqlList.stream().map(BasicSql::getId).filter(id -> !list.contains(id)).collect(Collectors.toList());
                }
                for (int i = 0; i < collectIdList.size(); i++) {
                    categorySqlService.update(new CategorySql(category.getId(), collectIdList.get(i), i));
                }
            } catch (NumberFormatException e) {
                SystemWarnLanguageEnum.INVALID_INDEX.output();
                resultDto.setUpdateResult(false);
                resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            } catch (DataIntegrityViolationException e) {
                SystemWarnLanguageEnum.DELETE_FAILED.output();
                resultDto.setUpdateResult(false);
                resultDto.setMessage("解绑失败");
            }
            resultDto.setUpdateResult(deleted);
            if (deleted) {
                SystemWarnLanguageEnum.DELETE_SUCCEED.output();
                resultDto.setMessage("解绑成功");
            } else {
                SystemWarnLanguageEnum.DELETE_FAILED.output();
                resultDto.setMessage("解绑失败");
            }
        } else {
            resultDto.setUpdateResult(true);
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
        }

        return resultDto;
    }

    /**
     * init add category view.
     *
     * @param categories category list
     * @param content    content
     * @return true - add succeed , false - add failed
     * @throws ContentExistedException content already exist will throw this exception
     */
    public boolean initAddCategoryView(List<Category> categories, String content) throws ContentExistedException {

        if ("".equals(content) || content == null) {
            return false;
        }
        int indexC = categories.get(categories.size() - 1).getIndexC() + 1;
        Category category = new Category();
        category.setIndexC(indexC);
        category.setContent(content);

        return categoryService.add(category);
    }

    /**
     * init remove category view.
     *
     * @param index index
     * @return true - delete succeed , false - delete failed
     */
    public boolean initRemoveCategoryView(int index) {

        return categoryService.deleteById(
                Optional.ofNullable(categoryService.getByIndex(index)).orElse(new Category()).getId()
        );

    }


    /**
     * init update category view.
     *
     * @param text          index
     * @param newIndexStr   update index
     * @param newContent    update content
     * @return ResultDto
     */
    public ResultDto<Category> initUpdateCategoryView(String text, String newIndexStr, String newContent) {

        int index;
        try {
            index = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            index = 0;
        }
        Category category = categoryService.getByIndex(index);
        ResultDto<Category> resultDto = new ResultDto<>();
        if (category == null) {
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            resultDto.setUpdateResult(false);
            return resultDto;
        }

        int indexC;
        try {
            if (!"".equals(newIndexStr) && newIndexStr != null) {
                indexC = Integer.parseInt(newIndexStr);
                category.setIndexC(indexC);
            }
        } catch (NumberFormatException e) {
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            resultDto.setUpdateResult(false);
            return resultDto;
        }

        if (!"".equals(newContent) && newContent != null) {
            category.setContent(newContent);
        }
        Boolean update;
        try {
            update = categoryService.update(category);
        } catch (DuplicateKeyException e) {
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.UPDATE_FAILED.getContent() + ", 重复索引号: [" + newIndexStr + "]");
            return resultDto;
        }

        resultDto.setUpdateResult(update);
        if (Boolean.TRUE.equals(update)) {
            resultDto.setMessage(SystemWarnLanguageEnum.UPDATE_SUCCEED.getContent());
        } else {
            resultDto.setMessage(SystemWarnLanguageEnum.UPDATE_FAILED.getContent());
        }
        resultDto.setData(category);

        return resultDto;
    }

    /**
     * sub show category view.
     */
    public List<Category> subShowCategoryView() {
        List<Category> list = categoryService.list();
        list.forEach(c -> log.debug(c.getIndexC() + ". " + c.getContent()));

        return list;
    }

    /**
     * manage click event for view menu.
     *
     * @param indexStr category index
     * @return result dto
     */
    public ResultDto<MenuDto> initClickViewMenu(String indexStr) {

        ResultDto<MenuDto> resultDto = new ResultDto<>();
        int index;
        try {
            index = Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            resultDto.setData(new MenuDto());
            return resultDto;
        }

        return showBasicSQLByCategoryIndex(index);
    }

    /**
     * show sql group by category you chose
     *
     * @param index index number
     */
    private ResultDto<MenuDto> showBasicSQLByCategoryIndex(int index) {
        ResultDto<MenuDto> resultDto = new ResultDto<>();
        MenuDto menuDto = new MenuDto();
        Category category = categoryService.getByIndex(index);

        if (category == null) {
            SystemWarnLanguageEnum.INVALID_INDEX.output();
            resultDto.setUpdateResult(false);
            resultDto.setMessage(SystemWarnLanguageEnum.INVALID_INDEX.getContent());
            return resultDto;
        } else {

            List<BasicSql> basicSqlList = category.getBasicSqlList();
            List<BasicSql> sortBasicSqlList = sortBasicSqlList(category);
            category.setBasicSqlList(sortBasicSqlList);
            menuDto.setCategory(category);
            if (basicSqlList == null || basicSqlList.isEmpty()) {
                SystemWarnLanguageEnum.EMPTY_SQL_LIST.output();
                resultDto.setUpdateResult(false);
                resultDto.setMessage(SystemWarnLanguageEnum.EMPTY_SQL_LIST.getContent());
                resultDto.setData(menuDto);
                return resultDto;
            } else {
                List<String> keyList = generateKeyList(sortBasicSqlList);
                resultDto.setUpdateResult(true);
                menuDto.setKeyList(keyList);
                resultDto.setData(menuDto);
            }
        }

        return resultDto;
    }

    /**
     * convert basic sql list sorted by sequence.
     *
     * @param category category
     * @return sorted basic sql list
     */
    private List<BasicSql> sortBasicSqlList(Category category) {

        List<BasicSql> basicSqlList = category.getBasicSqlList();
        List<BasicSql> newList = new ArrayList<>(basicSqlList.size());
        if (!basicSqlList.isEmpty()) {
            newList.addAll(basicSqlList);
            for (BasicSql basicSql : basicSqlList) {
                Integer seq = categorySqlService.getSeq(category.getId(), basicSql.getId());
                newList.set(seq, basicSql);
            }
        }
        return newList;
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
    public String keyListView(String key,String value, List<BasicSql> sqlList) {

        if (ConvertUtil.isMultipleParameter(value)) {
            isMultipleValue = true;
            value = ConvertUtil.convertListToString(ConvertUtil.convertMultiParameterToList(value, key));
        }else {
            value = ConvertUtil.convertRules(key, value);
        }
        return autoSql(key, value, sqlList);
    }


    /**
     * no condition, just travel sql.
     *
     * @param sqlList sql list
     */
    public String travelSql(List<BasicSql> sqlList) {

        StringBuilder sb = new StringBuilder();
        for (BasicSql basicSQL : sqlList) {
            if ("".equals(basicSQL.getSqlKey())) {
                log.info(basicSQL.getSqlValue());
                sb.append(basicSQL.getSqlValue());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * auto create sql with parameter and sql list
     *
     * @param key             key
     * @param value           value from key map
     * @param sqlList         category sql list
     */
    private String autoSql(String key, String value , List<BasicSql> sqlList) {

        String sql;
        StringBuilder sb = new StringBuilder();
        for (BasicSql basicSQL : sqlList) {
            sql = basicSQL.getSqlValue();
            if (basicSQL.getSqlKey().equals(key)) {
                if (isMultipleValue) {
                    sql = ConcatSQLUtil.concatSQLwithMultipleParameters(sql, ConvertUtil.convertStringToList(value));
                } else {
                    sql = ConcatSQLUtil.concatSQLwithSingleParamter(sql, value);
                }
                sb.append(sql);
                sb.append("\n");
            }
        }
        log.info(sb.toString());
        return sb.toString();

    }

    /**
     * re-index for category.
     */
    public void reIndex() {
        List<Category> categories = subShowCategoryView();
        for (int i = 1; i <= categories.size(); i++) {
            Category category = categories.get(i - 1);
            category.setIndexC(i);
            Boolean update = categoryService.update(category);
            if (Boolean.FALSE.equals(update)) {
                log.error("update index failed....");
            }
        }
    }

    /**
     * saved file before system exit.
     *
     */
    public void reWriteFileBeforeExit() {
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
        log.info(SystemWarnLanguageEnum.SAVED_FILE.getContent() + path.toString());
    }
}
