package com.moio.feature.view;

import com.moio.common.enums.SystemWarnLanguageEnum;
import com.moio.common.enums.ViewEnum;
import com.moio.entity.BasicSql;
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
import java.util.List;

/**
 * @author molinchang
 *
 * @description unbind sql for category panel component
 */
@Slf4j
@Component
public class UnbindSQLForCategoryPanel extends JPanel {

    @Autowired
    CustomizeSqlNewManager manager;

    public void refresh(int width, int height) {
        removeAll();
        setLayout(null);
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
        // unbind sql
        JButton submitBtn = new JButton("解绑");
        submitBtn.setBounds(500 + scrollPane.getX() + scrollPane.getWidth(), 10, 65, 20);
        // update index
        JLabel addableLabel = new JLabel(SystemWarnLanguageEnum.LIST_REMOVABLE_SQL.getContent());
        addableLabel.setBounds(10 + scrollPane.getX() + scrollPane.getWidth(), 10, width * 180 / 1200, 20);
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

                ResultDto<java.util.List<BasicSql>> resultDto = manager.initUnbindSQLForCategory(index, "");
                if (!resultDto.isUpdateResult()) {
                    JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                    log.error(resultDto.getMessage());
                } else {
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
            String text = objectJList.getSelectedValue().split("\\.")[0];
            String content = getSelectedItems(addablePanel);
            // execute bind
            ResultDto<List<BasicSql>> resultDto = manager.initUnbindSQLForCategory(text, content);
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
        });

        add(viewLabel);
        add(scrollPane);
        add(addableLabel);
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
