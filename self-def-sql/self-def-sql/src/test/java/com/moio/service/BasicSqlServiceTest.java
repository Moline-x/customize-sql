package com.moio.service;

import com.moio.common.exception.SqlExistedException;
import com.moio.entity.BasicSql;
import com.moio.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BasicSqlServiceTest {

    @Autowired
    BasicSqlService service;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategorySqlService categorySqlService;

    @Test
    public void testCategoryByIndex() {
        categorySqlService.add(1, 1, 0);
        categorySqlService.add(1, 2, 1);
        categorySqlService.add(1, 3, 2);

        Category category = categoryService.getByIndex(1);
        log.info(category.toString());
    }

    @Test
    public void testBasicSqlList() {
        log.info(service.list().toString());
        BasicSql sq = service.getById(1);
        log.info(sq.toString());
        sq.setSqlValue("select * from vos_plan_cntr where cntr_n in (') order by cntr_n;");
        Boolean update = null;
        try {
            update = service.update(sq);
        } catch (SqlExistedException e) {
            e.printStackTrace();
        }
        if (update) {
            log.info(service.list().toString());
        }
    }
}
