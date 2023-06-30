package com.moio.feature.view;

import com.moio.common.enums.SystemWarnLanguageEnum;
import com.moio.entity.BasicSql;
import com.moio.entity.ResultDto;
import com.moio.manager.CustomizeSqlNewManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author molinchang
 *
 * @description update sql panel component
 */
@Slf4j
@Component
public class UpdateSQLPanel extends JPanel {

    @Autowired
    CustomizeSqlNewManager manager;

    public void refresh(int width, int height) {
        removeAll();
        setLayout(null);
        JLabel viewLabel = new JLabel("全部SQL语句如下: ");
        viewLabel.setBounds(5, 10, width * 100 / 1200, 20);
        // use JList to instead of JTextarea.
        JList<String> objectJList = new JList<>();
        objectJList.setFont(new Font(null, Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(
                objectJList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        // get basic sql list
        java.util.List<BasicSql> basicSqls = manager.subShowBasicSQLView();
        String[] sqlArr = new String[basicSqls.size() + 1];

        for (int i = 0; i < basicSqls.size(); i++) {
            BasicSql basicSql = basicSqls.get(i);
            sqlArr[i] = basicSql.getId() + ". " + basicSql.getSqlValue();
        }
        objectJList.setListData(sqlArr);
        scrollPane.setBounds(10, 30, width * 870 / 1200, height * 400 / 605);

        // update key
        JLabel keyLabel = new JLabel(SystemWarnLanguageEnum.INPUT_UPDATE_KEY.getContent());
        keyLabel.setBounds(5, 60 + scrollPane.getHeight(), 150, 20);
        JTextField keyField = new JTextField(20);
        keyField.setEditable(false);
        keyField.setBounds(165, 60 + scrollPane.getHeight(), width * 200 / 1200, 20);
        // update sql value
        JLabel sqlLabel = new JLabel(SystemWarnLanguageEnum.INPUT_UPDATE_SQL.getContent());
        sqlLabel.setBounds(5, 80 + scrollPane.getHeight(), 150, 20);
        JTextField sqlField = new JTextField(20);
        sqlField.setEditable(false);
        sqlField.setBounds(165, 80 + scrollPane.getHeight(), width * 600 / 1200, 20);
        JButton submitBtn = new JButton("提交");
        submitBtn.setBounds(175 + sqlField.getWidth(), 80 + scrollPane.getHeight(), 65, 20);
        // get name field value
        objectJList.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {
                String text = objectJList.getSelectedValue().split("\\.")[0];

                ResultDto<BasicSql> resultDto = manager.initUpdateBasicSQLView(text, "@", "@");
                if (!resultDto.isUpdateResult()) {
                    JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                    log.error(resultDto.getMessage());
                } else {
                    keyField.setText(resultDto.getData().getSqlKey());
                    keyField.setEditable(true);
                    sqlField.setText(resultDto.getData().getSqlValue());
                    sqlField.setEditable(true);
                }
            }
        });

        submitBtn.addActionListener(l -> action(objectJList, keyField, sqlField, width, height));

        keyField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    action(objectJList, keyField, sqlField, width, height);
                }
            }
        });

        sqlField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    action(objectJList, keyField, sqlField, width, height);
                }
            }
        });

        add(viewLabel);
        add(scrollPane);
        add(keyLabel);
        add(keyField);
        add(sqlLabel);
        add(sqlField);
        add(submitBtn);

        revalidate();
    }

    /**
     * submit action
     *
     * @param objectJList   object list
     * @param keyField      key field
     * @param sqlField      sql field
     * @param width         width
     * @param height        height
     */
    private void action(JList<String> objectJList, JTextField keyField, JTextField sqlField, int width, int height) {
        String text = objectJList.getSelectedValue().split("\\.")[0];
        String key = keyField.getText();
        String sql = sqlField.getText();
        // execute update
        ResultDto<BasicSql> resultDto = manager.initUpdateBasicSQLView(text, key, sql);
        if (resultDto.isUpdateResult()) {
            JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.PLAIN_MESSAGE);
            log.info(resultDto.getMessage());
            // clear panel and reset value
            refresh(width, height);
        } else {
            JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
            log.error(resultDto.getMessage());
        }
        keyField.setText(null);
        sqlField.setText(null);
    }
}
