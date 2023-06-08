package com.moio.controller;


import com.moio.view.NewMainUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//implements CommandLineRunner
@Component
public class CategoryController  {

    @Autowired
    NewMainUI mainUI;

    public void run() {
        mainUI.init();
    }

//    @Override
//    public void run(String... args) throws Exception {
////        manager.initFirstMenu();
//        MainUI mainUI = new MainUI();
//
//    }
}
