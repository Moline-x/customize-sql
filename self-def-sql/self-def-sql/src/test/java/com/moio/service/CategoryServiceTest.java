package com.moio.service;

import com.moio.entity.Category;
import com.moio.mapper.CategorySqlMapper;
import com.moio.service.CategoryService;
import com.moio.utils.ConcatSQLUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CategoryServiceTest {

    @Autowired
    CategoryService service;

    @Autowired
    BasicSqlService basicSqlService;

    @Autowired
    CategorySqlMapper mapper;

    @Test
    public void testGetCategoryList() {
        log.info(service.list().toString());
        Category ca = service.getByIndex(1);
        log.info(ca.toString());
        String sql = ConcatSQLUtil.concatSQLForCategorySql(mapper.list().get(0));
        log.info(sql);
//        log.info(mapper.list().toString());
//        Boolean delete = basicSqlService.deleteById(5);
//        if (delete) {
//            ca = service.getById(1);
//            log.info(ca.toString());
//        }
    }

    @Test
    public void testOverWriteFile() throws IOException {
        String readString = Files.readString(Path.of("C:\\h2data\\data-h2.sql"));
        log.info(readString);

        String sql = ConcatSQLUtil.concatSQLForCategorySql(mapper.list().get(0));
        log.info(sql);
        Path path = Files.writeString(Path.of("C:\\h2data\\data-h2.sql"), sql);
    }
}
