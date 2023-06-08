package com.moio.feature.view;

import com.moio.common.enums.SystemWarnLanguageEnum;
import com.moio.common.enums.ViewEnum;
import com.moio.entity.Category;
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
 * @description delete category panel component
 */
@Slf4j
@Component
public class DeleteCategoryPanel extends JPanel {

    @Autowired
    CustomizeSqlNewManager manager;

    public void refresh(int width, int height) {
        removeAll();
        setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_CATEGORY_BELOW.getContent());
        viewLabel.setBounds(5, 10, width * 150 / 1200, 20);
        // use JList to instead of JTextarea.
        JList<String> objectJList = new JList<>();
        objectJList.setFont(new Font(null, Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(
                objectJList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        // get category list
        java.util.List<Category> categories = manager.subShowCategoryView();
        String[] categoryArr = new String[categories.size() + 1];

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            categoryArr[i] = category.getIndexC() + ". " + category.getContent();
        }
        objectJList.setListData(categoryArr);
        scrollPane.setBounds(10, 30, width * 850 / 1200, height * 400 / 605);

        JLabel nameLabel = new JLabel("请确认要删除的分类条目的序号: ");
        nameLabel.setBounds(5, 40 + scrollPane.getHeight(), 190, 20);
        JTextField nameField = new JTextField(20);
        nameField.setEditable(false);
        nameField.setBounds(205, 40 + scrollPane.getHeight(), 200, 20);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(415, 40 + scrollPane.getHeight(), 65, 20);

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
            int index;
            try {
                index = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                index = 0;
            }
            boolean delete = manager.initRemoveCategoryView(index);
            if (delete) {
                JOptionPane.showMessageDialog(null, SystemWarnLanguageEnum.DELETE_SUCCEED.getContent(), "结果", JOptionPane.PLAIN_MESSAGE);
                log.info(SystemWarnLanguageEnum.DELETE_SUCCEED.getContent());
                // clear panel and reset value
                manager.reIndex();
                refresh(width, height);
                SwingUtilities.invokeLater(() -> nameField.setText(null));
            } else {
                JOptionPane.showMessageDialog(null, SystemWarnLanguageEnum.DELETE_FAILED.getContent(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(SystemWarnLanguageEnum.DELETE_FAILED.getContent());
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
