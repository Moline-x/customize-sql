package com.moio.entity;

import com.moio.common.constant.ArgsConstant;
import com.moio.common.enums.MenuEnum;
import com.moio.common.enums.SystemWarnLanguageEnum;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * @description menu entity
 *
 * @author molinchang
 */
@NoArgsConstructor
public class Menu {

    /**
     * init top banner.
     */
    private void initTopBanner() {
        MenuEnum.TOP_BANNER.output();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * init first menu.
     */
    public void mainMenu() {
        viewMenu();
        MenuEnum.CUSTOMIZE_SETTING.output();
        MenuEnum.VIEW_MENU.output();
        MenuEnum.EXIT_BUTTON.output();
        System.out.print(SystemWarnLanguageEnum.INPUT_INDEX.getContent());

    }

    /**
     * init view menu.
     */
    public void viewMenu() {
        clearConsole();
        initTopBanner();
    }

    /**
     * init menu which click customize settings
     */
    public void settingsMenu() {
        viewMenu();
        MenuEnum.BASIC_SQL_SETTING.output();
        MenuEnum.CATEGORY_SETTING.output();
        System.out.println(SystemWarnLanguageEnum.BACK_BUTTON.getContent());
        System.out.println(SystemWarnLanguageEnum.SUB_EXIT_BUTTON.getContent());
        System.out.print(SystemWarnLanguageEnum.INPUT_INDEX.getContent());

    }

    /**
     * init menu which click basic sql settings
     */
    public void basicSQLSettingsMenu() {
        viewMenu();
        MenuEnum.ADD_BASIC_SQL.output();
        MenuEnum.REMOVE_BASIC_SQL.output();
        MenuEnum.UPDATE_BASIC_SQL.output();
        MenuEnum.SHOW_BASIC_SQL.output();
        System.out.println(SystemWarnLanguageEnum.BACK_BUTTON.getContent());
        System.out.println(SystemWarnLanguageEnum.SUB_EXIT_BUTTON.getContent());
        System.out.print(SystemWarnLanguageEnum.INPUT_INDEX.getContent());
    }


    /**
     * init menu which click category settings
     */
    public void categorySettingsMenu() {
        viewMenu();
        MenuEnum.ADD_CATEGORY.output();
        MenuEnum.REMOVE_CATEGORY.output();
        MenuEnum.UPDATE_CATEGORY.output();
        MenuEnum.SHOW_CATEGORY.output();
        MenuEnum.BIND_SQL_TO_CATEGORY.output();
        System.out.println(SystemWarnLanguageEnum.BACK_BUTTON.getContent());
        System.out.println(SystemWarnLanguageEnum.SUB_EXIT_BUTTON.getContent());
        System.out.print(SystemWarnLanguageEnum.INPUT_INDEX.getContent());

    }

    /**
     * clear console residue.
     */
    private void clearConsole() {
        try {
            String os = System.getProperty(ArgsConstant.OS_NAME);
            if (os.contains(ArgsConstant.WINDOWS)) {
                ProcessBuilder pb = new ProcessBuilder(ArgsConstant.CMD, ArgsConstant.LINE_C, ArgsConstant.CLS);
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder(ArgsConstant.CLEAR);
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
