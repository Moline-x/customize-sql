package com.moio.feature.view;

import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.moio.common.enums.SystemWarnLanguageEnum;
import com.moio.common.enums.ViewEnum;
import com.moio.common.listener.JTextFieldHintListener;
import com.moio.entity.BasicSql;
import com.moio.entity.BindSqlDto;
import com.moio.entity.Category;
import com.moio.entity.ResultDto;
import com.moio.manager.CustomizeSqlNewManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author molinchang
 *
 * @description bind sql for category panel component
 */
@Slf4j
@Component
public class BindSQLForCategoryPanel extends JPanel {

    @Autowired
    CustomizeSqlNewManager manager;

    public void refresh(int width, int height) {
        removeAll();
        setLayout(null);
        // create list to contain checkbox select order
        List<String> checkBoxSelectIdQueue = new ArrayList<>();
        JLabel viewLabel = new JLabel(ViewEnum.ALL_CATEGORY_BELOW.getContent());
        viewLabel.setBounds(5, 10, width * 130 / 1200, 20);
        // use JList to instead of JTextarea.
        JList<String> objectJList = new JList<>();
        objectJList.setFont(new Font(null, Font.PLAIN, 12));

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
        scrollPane.setBounds(10, 30, width * 300 / 1200, height * 520 / 605);

        // update index
        JLabel addableLabel = new JLabel(SystemWarnLanguageEnum.LIST_ADDABLE_SQL.getContent());
        addableLabel.setBounds(10 + scrollPane.getX() + scrollPane.getWidth(), 10, width * 180 / 1200, 20);
        // add combo box
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("key");
        comboBox.addItem("sql");
        comboBox.setBounds(addableLabel.getX() + addableLabel.getWidth(), 10, 70, 20);
        // add search text field
        JTextField searchField = new JTextField();
        searchField.setBounds(5 +comboBox.getWidth() + comboBox.getX(), 10, width * 200 / 1200, 20);
        searchField.addFocusListener(new JTextFieldHintListener(searchField, "请根据key或sql的关键字检索"));
        // add search button
        JButton searchBtn = new JButton();
        searchBtn.setIcon(new FlatSearchIcon());
        searchBtn.setBounds(5 + searchField.getWidth() + searchField.getX(), 10, width * 20 / 1200, 20);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setContentAreaFilled(false);
        searchBtn.setToolTipText("检索");
        // bind sql button
        JButton submitBtn = new JButton("绑定");
        submitBtn.setBounds(20 + searchBtn.getX() + searchBtn.getWidth(), 10, 65, 20);

        // use JPanel to simulate JTable to instead of JTextArea
        JPanel addablePanel = new JPanel(new GridBagLayout());

        JScrollPane addableScrollPane = new JScrollPane(
                addablePanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        addableScrollPane.setBounds(10 + scrollPane.getX() + scrollPane.getWidth(), 30, width * 620 / 1200, height * 520 / 605);

        // objectList click event
        objectJList.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {
                String index = objectJList.getSelectedValue().split("\\.")[0];

                BindSqlDto bindSqlDto = new BindSqlDto(index, "", "", "");
                ResultDto<java.util.List<BasicSql>> resultDto = manager.initBindSQLForCategory(bindSqlDto);
                if (!resultDto.isUpdateResult()) {
                    JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                    log.error(resultDto.getMessage());
                } else {
                    checkBoxSelectIdQueue.clear();
                    addablePanel.removeAll();
                    addablePanel.updateUI();
                    java.util.List<BasicSql> dataBasic = resultDto.getData();

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.weightx = 0.2;

                    for (BasicSql basicSql : dataBasic) {
                        JCheckBox checkBox = new JCheckBox();
                        JLabel indexLabel = new JLabel(basicSql.getId().toString());
                        JTextArea sqlTextArea = new JTextArea(basicSql.getSqlValue());
                        JLabel keyLabel = new JLabel(ViewEnum.KEY_SYMBOL_LEFT.getContent()+basicSql.getSqlKey() +"】");
                        sqlTextArea.setEditable(false);
                        sqlTextArea.setLineWrap(true);
                        indexLabel.setEnabled(false);
                        keyLabel.setEnabled(false);

                        checkBox.addActionListener(l -> {
                            if (checkBox.isSelected()) {
                                checkBoxSelectIdQueue.add(indexLabel.getText());
                            } else {
                                checkBoxSelectIdQueue.remove(indexLabel.getText());
                            }
                        });

                        JPanel rowPanel = new JPanel(new BorderLayout());
                        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                        leftPanel.add(checkBox);
                        leftPanel.add(indexLabel);
                        rowPanel.add(leftPanel, BorderLayout.WEST);
                        JPanel rightPanel = new JPanel(new BorderLayout());
                        rightPanel.add(sqlTextArea, BorderLayout.CENTER);
                        rightPanel.add(keyLabel, BorderLayout.EAST);
                        rowPanel.add(rightPanel, BorderLayout.CENTER);

                        gbc.gridy++;
                        addablePanel.add(rowPanel, gbc);
                    }

                    addablePanel.revalidate();
                    addablePanel.repaint();
                    SwingUtilities.invokeLater(() -> addableScrollPane.getVerticalScrollBar().setValue(0));

                }
            }
        });

        submitBtn.addActionListener(l -> {
            String text = Optional.ofNullable(objectJList.getSelectedValue()).isPresent()? objectJList.getSelectedValue().split("\\.")[0]:"";
//            String content = getSelectedItems(addablePanel);
            StringBuilder sb = new StringBuilder();
            checkBoxSelectIdQueue.forEach(id -> {
                sb.append(id);
                sb.append(",");
            });
            String content = sb.deleteCharAt(sb.length() - 1).toString();
            BindSqlDto bindSqlDto = new BindSqlDto(text, content, "", "");
            // execute bind
            ResultDto<java.util.List<BasicSql>> resultDto = manager.initBindSQLForCategory(bindSqlDto);
            if (resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.PLAIN_MESSAGE);
                log.info(resultDto.getMessage());
                // clear panel and reset value
                addablePanel.removeAll();
                addablePanel.updateUI();
            } else {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
            }
            searchField.setText(null);
        });

        searchBtn.addActionListener(l -> {
            String searchText = searchField.getText();
            String categoryIndex = Optional.ofNullable(objectJList.getSelectedValue()).isPresent() ? objectJList.getSelectedValue().split("\\.")[0]:"";
            String comBoxKey = Optional.ofNullable(comboBox.getSelectedItem()).orElse("").toString();

            BindSqlDto bindSqlDto = new BindSqlDto(categoryIndex, "", searchText, comBoxKey);

            addablePanel.removeAll();
            addablePanel.updateUI();

            ResultDto<java.util.List<BasicSql>> resultDto = manager.initBindSQLForCategory(bindSqlDto);
            if (!resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
            } else {
                List<BasicSql> data = resultDto.getData();
                addablePanel.removeAll();
                addablePanel.updateUI();

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.weightx = 0.2;

                for (BasicSql basicSql : data) {
                    JCheckBox checkBox = new JCheckBox();
                    JLabel indexLabel = new JLabel(basicSql.getId().toString());
                    JTextArea sqlTextArea = new JTextArea(basicSql.getSqlValue());
                    JLabel keyLabel = new JLabel(ViewEnum.KEY_SYMBOL_LEFT.getContent()+basicSql.getSqlKey() +"】");
                    sqlTextArea.setEditable(false);
                    sqlTextArea.setLineWrap(true);
                    indexLabel.setEnabled(false);
                    keyLabel.setEnabled(false);

                    JPanel rowPanel = new JPanel(new BorderLayout());
                    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    leftPanel.add(checkBox);
                    leftPanel.add(indexLabel);
                    rowPanel.add(leftPanel, BorderLayout.WEST);
                    JPanel rightPanel = new JPanel(new BorderLayout());
                    rightPanel.add(sqlTextArea, BorderLayout.CENTER);
                    rightPanel.add(keyLabel, BorderLayout.EAST);
                    rowPanel.add(rightPanel, BorderLayout.CENTER);

                    gbc.gridy++;
                    addablePanel.add(rowPanel, gbc);
                }

                addablePanel.revalidate();
                addablePanel.repaint();
                SwingUtilities.invokeLater(() -> addableScrollPane.getVerticalScrollBar().setValue(0));

            }

        });

        add(viewLabel);
        add(scrollPane);
        add(addableLabel);
        add(comboBox);
        add(searchField);
        add(searchBtn);
        add(addableScrollPane);
        add(submitBtn);

        revalidate();
    }

    /**
     * get checkbox selected items then concat a string value.
     *
     * @param panel zone
     * @return string split by ','
     */
    private String getSelectedItems(JPanel panel) {

        StringBuilder selectedIds = new StringBuilder();
        java.awt.Component[] components = panel.getComponents();
        for (java.awt.Component component : components) {
            if (component instanceof JPanel) {
                JPanel rowPanel = (JPanel) component;
                java.awt.Component[] rowComponents = rowPanel.getComponents();
                for (java.awt.Component rowComponent : rowComponents) {
                    if (rowComponent instanceof JPanel) {
                        JPanel innerPanel = (JPanel) rowComponent;
                        java.awt.Component[] innerComponents = innerPanel.getComponents();
                        for (java.awt.Component innerComponent : innerComponents) {
                            if (innerComponent instanceof JCheckBox) {
                                JCheckBox checkBox = (JCheckBox) innerComponent;
                                if (checkBox.isSelected()) {
                                    JLabel indexLabel = (JLabel) innerPanel.getComponent(1);
                                    String id = indexLabel.getText();
                                    selectedIds.append(id).append(",");
                                }
                            }
                        }
                    }
                }
            }
        }

        if (selectedIds.length() > 0) {
            selectedIds.deleteCharAt(selectedIds.length() - 1);
        }

        return selectedIds.toString();
    }


}
