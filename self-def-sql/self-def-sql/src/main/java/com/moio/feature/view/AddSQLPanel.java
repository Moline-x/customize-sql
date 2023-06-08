package com.moio.feature.view;

import com.moio.common.enums.SystemWarnLanguageEnum;
import com.moio.common.enums.ViewEnum;
import com.moio.entity.BasicSql;
import com.moio.entity.ResultDto;
import com.moio.manager.CustomizeSqlNewManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author molinchang
 *
 * @description add sql panel component
 */
@Slf4j
@Component
public class AddSQLPanel extends JPanel {

    @Autowired
    CustomizeSqlNewManager manager;

    public void refresh(int width, int height) {
        removeAll();
        setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_SQL_BELOW.getContent());
        viewLabel.setBounds(5, 10, width * 100 / 1200, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get basic sql list
        java.util.List<BasicSql> basicSqls = manager.subShowBasicSQLView();
        basicSqls.forEach(s -> {
            jTextArea.append(s.getId() + ". " + s.getSqlValue());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, width * 870 / 1200, height * 400 / 605);

        // input table name
        JLabel nameLabel = new JLabel(SystemWarnLanguageEnum.INPUT_TABLE_NAME.getContent());
        nameLabel.setBounds(5, 30 + scrollPane.getHeight(), 190, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(205, 30 + scrollPane.getHeight(), width * 200 / 1200, 20);
        // input condition
        JLabel conditionLabel = new JLabel(SystemWarnLanguageEnum.INPUT_CONDITION.getContent());
        conditionLabel.setBounds(5, 50 + scrollPane.getHeight(), 190, 20);
        JTextField conditionField = new JTextField(20);
        conditionField.setBounds(205, 50 + scrollPane.getHeight(), width * 200 / 1200, 20);
        // input other conditions
        JLabel otherLabel = new JLabel(SystemWarnLanguageEnum.INPUT_OTHER_CONDITION.getContent());
        otherLabel.setBounds(5, 70 + scrollPane.getHeight(), 190, 20);
        JTextField otherField = new JTextField(20);
        otherField.setBounds(205, 70 + scrollPane.getHeight(), width * 200 / 1200, 20);
        // input key
        JLabel keyLabel = new JLabel(SystemWarnLanguageEnum.INPUT_KEY.getContent());
        keyLabel.setBounds(5, 90 + scrollPane.getHeight(), 190, 20);
        JTextField keyField = new JTextField(20);
        keyField.setBounds(205, 90 + scrollPane.getHeight(), width * 200 / 1200, 20);
        JButton submitBtn = new JButton("提交");
        submitBtn.setBounds(215 + keyField.getWidth(), 90 + scrollPane.getHeight(), 65, 20);
        // get name field value
        submitBtn.addActionListener(l -> {
            String tableName = nameField.getText();
            String condition = conditionField.getText();
            String other = otherField.getText();
            String key = keyField.getText();

            ResultDto resultDto = manager.initAddBasicSQLView(tableName, condition, other, key);
            if (resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.PLAIN_MESSAGE);
                // clear panel and reset value
                jTextArea.setText(null);
                List<BasicSql> search = manager.subShowBasicSQLView();
                search.forEach(s -> {
                    jTextArea.append(s.getId() + ". " + s.getSqlValue());
                    jTextArea.append("\n");
                });
            } else {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
            }
            nameField.setText(null);
            conditionField.setText(null);
            otherField.setText(null);
            keyField.setText(null);
            SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));

        });

        add(viewLabel);
        add(scrollPane);
        add(nameLabel);
        add(nameField);
        add(submitBtn);
        add(conditionLabel);
        add(conditionField);
        add(otherLabel);
        add(otherField);
        add(keyLabel);
        add(keyField);

        revalidate();
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
    }
}
