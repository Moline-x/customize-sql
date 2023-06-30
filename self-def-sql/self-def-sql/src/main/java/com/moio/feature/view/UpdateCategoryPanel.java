package com.moio.feature.view;

import com.moio.common.enums.SystemWarnLanguageEnum;
import com.moio.common.enums.ViewEnum;
import com.moio.entity.Category;
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
import java.util.Optional;

/**
 * @author molinchang
 *
 * @description update category panel component
 */
@Slf4j
@Component
public class UpdateCategoryPanel extends JPanel {

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
        scrollPane.setBounds(10, 30, width * 850 / 1200, height * 420 / 605);

        // update index
        JLabel indexLabel = new JLabel(SystemWarnLanguageEnum.INPUT_UPDATE_INDEX_FOR_CATEGORY.getContent());
        indexLabel.setBounds(5, 50 + scrollPane.getHeight(), 190, 20);
        JTextField indexField = new JTextField(20);
        indexField.setEditable(false);
        indexField.setBounds(205, 50 + scrollPane.getHeight(), 200, 20);
        // update content
        JLabel contentLabel = new JLabel(SystemWarnLanguageEnum.INPUT_UPDATE_CONTENT.getContent());
        contentLabel.setBounds(5, 70 + scrollPane.getHeight(), 190, 20);
        JTextField contentField = new JTextField(20);
        contentField.setEditable(false);
        contentField.setBounds(205, 70 + scrollPane.getHeight(), 200, 20);
        JButton submitBtn = new JButton("提交");
        submitBtn.setBounds(415, 70 + scrollPane.getHeight(), 65, 20);
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

                ResultDto<Category> resultDto = manager.initUpdateCategoryView(text, "", "");
                if (!resultDto.isUpdateResult()) {
                    JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                    log.error(resultDto.getMessage());
                } else {
                    indexField.setText(resultDto.getData().getIndexC().toString());
                    indexField.setEditable(true);
                    contentField.setText(resultDto.getData().getContent());
                    contentField.setEditable(true);
                }
            }
        });

        submitBtn.addActionListener(l -> action(objectJList, indexField, contentField, width, height));

        indexField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    action(objectJList, indexField, contentField, width, height);
                }
            }
        });

        contentField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    action(objectJList, indexField, contentField, width, height);
                }
            }
        });

        add(viewLabel);
        add(scrollPane);
        add(indexLabel);
        add(indexField);
        add(contentLabel);
        add(contentField);
        add(submitBtn);

        revalidate();
    }

    /**
     * submit event.
     *
     * @param objectJList       object list
     * @param indexField        index field
     * @param contentField      content field
     * @param width             width
     * @param height            height
     */
    private void action(JList<String> objectJList, JTextField indexField, JTextField contentField, int width, int height) {
        String text = Optional.ofNullable(objectJList.getSelectedValue()).isPresent()?objectJList.getSelectedValue().split("\\.")[0]:"";
        String index = indexField.getText();
        String content = contentField.getText();
        // execute update
        ResultDto<Category> resultDto = manager.initUpdateCategoryView(text, index, content);
        if (resultDto.isUpdateResult()) {
            JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.PLAIN_MESSAGE);
            log.info(resultDto.getMessage());
            // clear panel and reset value
            refresh(width, height);
        } else {
            JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
            log.error(resultDto.getMessage());
        }
        indexField.setText(null);
        contentField.setText(null);
    }
}
