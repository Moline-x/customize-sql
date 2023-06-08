package com.moio.feature.view;

import com.moio.common.constant.ArgsConstant;
import com.moio.common.enums.SystemWarnLanguageEnum;
import com.moio.common.enums.ViewEnum;
import com.moio.common.exception.ContentExistedException;
import com.moio.entity.Category;
import com.moio.manager.CustomizeSqlNewManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * @author molinchang
 *
 * @description add category panel component
 */
@Slf4j
@Component
public class AddCategoryPanel extends JPanel {

    @Autowired
    CustomizeSqlNewManager manager;

    public void refresh(int width, int height) {
        removeAll();
        setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_CATEGORY_BELOW.getContent());
        viewLabel.setBounds(5, 10, width * 150 / 1200, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        // get category list
        java.util.List<Category> categories = manager.subShowCategoryView();
        categories.forEach(c -> {
            jTextArea.append(c.getIndexC() + ". " + c.getContent());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, width * 850 / 1200, height * 400 / 605);

        JPanel executePanel = new JPanel();
        executePanel.setLayout(null);

        JLabel nameLabel = new JLabel("请新增分类条目的名称: ");
        nameLabel.setBounds(5, 40 + scrollPane.getHeight(), 130, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(145, 40 + scrollPane.getHeight(), 200, 20);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(355, 40 + scrollPane.getHeight(), 65, 20);
        // get name field value
        confirmBtn.addActionListener(l -> {
            String text = nameField.getText();
            try {
                java.util.List<Category> list = manager.subShowCategoryView();
                boolean add = manager.initAddCategoryView(list, text);
                if (add) {
                    JOptionPane.showMessageDialog(null, SystemWarnLanguageEnum.ADD_SUCCEED.getContent(), "结果", JOptionPane.PLAIN_MESSAGE);
                    log.info(SystemWarnLanguageEnum.ADD_SUCCEED.getContent());
                    // clear panel and reset value
                    jTextArea.setText(null);
                    List<Category> search = manager.subShowCategoryView();
                    search.forEach(c -> {
                        jTextArea.append(c.getIndexC() + ". " + c.getContent());
                        jTextArea.append("\n");
                    });

                } else {
                    JOptionPane.showMessageDialog(null, SystemWarnLanguageEnum.ADD_FAILED.getContent(), "结果", JOptionPane.ERROR_MESSAGE);
                    log.error(SystemWarnLanguageEnum.ADD_FAILED.getContent());
                }
                nameField.setText(null);
                SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
            } catch (DuplicateKeyException e) {
                String[] split = Objects.requireNonNull(e.getRootCause()).getLocalizedMessage().split("SQL statement:");
                JOptionPane.showMessageDialog(null, SystemWarnLanguageEnum.ADD_FAILED.getContent()+ split[ArgsConstant.REMAIN_NUMBER], "结果", JOptionPane.ERROR_MESSAGE);
                log.info(SystemWarnLanguageEnum.ADD_FAILED.getContent() + split[ArgsConstant.REMAIN_NUMBER]);
                nameField.setText(null);
                SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
            } catch (ContentExistedException e) {
                JOptionPane.showMessageDialog(null, SystemWarnLanguageEnum.ADD_FAILED.getContent()+ e.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(SystemWarnLanguageEnum.ADD_FAILED.getContent() + e.getMessage());
                nameField.setText(null);
                SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
            }


        });

        add(viewLabel);
        add(scrollPane);
        add(nameLabel);
        add(nameField);
        add(confirmBtn);

        setVisible(true);
        revalidate();
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
    }
}
