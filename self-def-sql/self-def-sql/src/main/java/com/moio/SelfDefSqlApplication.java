package com.moio;

import com.moio.controller.CategoryController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author molinchang
 *
 * @description boot app
 */
@SpringBootApplication
@MapperScan("com.moio.mapper")
public class SelfDefSqlApplication {

    public static void main(String[] args) {

        try {
            System.setProperty("javax.xml.parsers.DocumentBuilderFactory","com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
            SpringApplicationBuilder builder = new SpringApplicationBuilder(SelfDefSqlApplication.class);
            ConfigurableApplicationContext context = builder.headless(false).web(WebApplicationType.NONE).run(args);

            CategoryController controller = context.getBean(CategoryController.class);
            controller.run();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}
