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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author molinchang
 *
 * @description delete sql panel component
 */
@Slf4j
@Component
public class DeleteSQLPanel extends JPanel {

    @Autowired
    CustomizeSqlNewManager manager;

    public void refresh(int width, int height) {
        removeAll();
        setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_SQL_BELOW.getContent());
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

        JLabel nameLabel = new JLabel(SystemWarnLanguageEnum.INPUT_DELETE_INDEX.getContent());
        nameLabel.setBounds(5, 40 + scrollPane.getHeight(), 190, 20);
        JTextField nameField = new JTextField(20);
        nameField.setEditable(false);
        nameField.setBounds(205, 40 + scrollPane.getHeight(), width * 200 / 1200, 20);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(215 + nameField.getWidth(), 40 + scrollPane.getHeight(), 65, 20);

        objectJList.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {
                String index = objectJList.getSelectedValue().split("\\.")[0];
                nameField.setText(index);
            }
        });

        // get name field value
        confirmBtn.addActionListener(l -> {
            String text = nameField.getText();
            // execute delete
            ResultDto resultDto = manager.initRemoveBasicSQLView(text);
            if (resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.PLAIN_MESSAGE);
                // clear panel and reset value
                refresh(width, height);
                SwingUtilities.invokeLater(() -> nameField.setText(null));
            } else {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                nameField.setText(null);
            }


        });

        add(viewLabel);
        add(scrollPane);
        add(nameLabel);
        add(nameField);
        add(confirmBtn);

        revalidate();
    }
}
