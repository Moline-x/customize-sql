package com.moio.feature.view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author molinchang
 *
 * @description bind sql table
 */
public class BindSQLTable extends JTable {

    private DefaultTableModel dtm;

    public BindSQLTable(DefaultTableModel dtm) {
        super(dtm);
    }


}
