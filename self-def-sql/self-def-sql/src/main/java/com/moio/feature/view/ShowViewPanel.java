package com.moio.feature.view;

import com.formdev.flatlaf.icons.FlatWindowRestoreIcon;
import com.moio.common.enums.SystemWarnLanguageEnum;
import com.moio.common.enums.ViewEnum;
import com.moio.entity.BasicSql;
import com.moio.entity.Category;
import com.moio.entity.MenuDto;
import com.moio.entity.ResultDto;
import com.moio.manager.CustomizeSqlNewManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author molinchang
 *
 * @description show view panel component
 */
@Slf4j
@Component
public class ShowViewPanel extends JPanel {

    @Autowired
    CustomizeSqlNewManager manager;

    public void refresh(String indexStr, int width, int height) {
        removeAll();
        Map<String, String> map = new HashMap<>();
        String[] resultArr;
        boolean hasNullKey = false;
        String travelSql = "";
        setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_SQL_BELOW.getContent());
        viewLabel.setBounds(5, 10, width * 200 / 1200, 20);

        JButton copyButton = new JButton();
        copyButton.setIcon(new FlatWindowRestoreIcon());
        copyButton.setToolTipText("复制");
        copyButton.setBorderPainted(false);
        copyButton.setContentAreaFilled(false);
        copyButton.setFocusPainted(false);
        copyButton.setBounds(605 + viewLabel.getWidth() + viewLabel.getX(), 10, 30, 20);

        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        // get basic sql list
        ResultDto<MenuDto> dto = manager.initClickViewMenu(indexStr);
        MenuDto menuDto = dto.getData();
        if (menuDto == null) {
            JOptionPane.showMessageDialog(null, dto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
            // clear panel and reset value
            jTextArea.setText(null);
        } else {
            Category category = menuDto.getCategory();
            java.util.List<String> keyList = menuDto.getKeyList();
            if (category != null) {
                java.util.List<BasicSql> basicSqlList = category.getBasicSqlList();
                if (keyList == null || keyList.contains("")) {
                    travelSql = manager.travelSql(basicSqlList);
                    hasNullKey = true;
                    jTextArea.append(travelSql);
                }
            } else {
                JOptionPane.showMessageDialog(null, dto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                // clear panel and reset value
                jTextArea.setText(null);
            }
            scrollPane.setBounds(10, 30, width * 870 / 1200, height * 380 / 605);
            if (keyList != null) {
                int y = scrollPane.getY() + scrollPane.getHeight();
                assert category != null;
                List<BasicSql> basicSqls = category.getBasicSqlList();
                if (hasNullKey) {
                    resultArr = new String[keyList.size() + 1];
                    resultArr[0] = travelSql;
                } else {
                    resultArr = new String[keyList.size()];
                }

                for (int i = 0; i < keyList.size(); i++) {
                    String key = keyList.get(i);
                    if (!"".equals(key)) {
                        // input parameter
                        JLabel nameLabel = new JLabel(SystemWarnLanguageEnum.INPUT_PARAMETER.getContent() + key + SystemWarnLanguageEnum.INPUT_SYMBOL.getContent());
                        nameLabel.setBounds(5, y + i*20, 250, 20);
                        JTextField nameField = new JTextField(20);
                        nameField.setBounds(265, y + i*20, width * 400 / 1200, 20);
                        JButton submitBtn = new JButton("确认");
                        if (hasNullKey) {
                            submitBtn.setName(String.valueOf(i+1));
                        } else {
                            submitBtn.setName(String.valueOf(i));
                        }

                        submitBtn.setBounds(275 + nameField.getWidth(), y + i*20, 65, 20);

                        // get name field value
                        submitBtn.addActionListener(l -> {
                            String value = nameField.getText();

                            if (map.get(submitBtn.getName()) == null) {
                                map.put(submitBtn.getName(), value);
                                String sqls = manager.keyListView(key, value, basicSqls);
                                resultArr[Integer.parseInt(submitBtn.getName())] = sqls;
                                jTextArea.append(sqls);
                            } else {
                                if (!value.equals(map.get(submitBtn.getName()))) {
                                    map.put(submitBtn.getName(), value);
                                    jTextArea.setText(null);
                                    String sqls = manager.keyListView(key, value, basicSqls);
                                    resultArr[Integer.parseInt(submitBtn.getName())] = sqls;
                                    for (String s : resultArr) {
                                        jTextArea.append(s);
                                    }
                                }
                            }
                        });
                        add(nameLabel);
                        add(nameField);
                        add(submitBtn);
                    }
                }
            }


            copyButton.addActionListener(l -> {
                StringSelection stringSelection = new StringSelection(jTextArea.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            });
        }


        add(viewLabel);
        add(copyButton);
        add(scrollPane);

        setVisible(true);
        revalidate();
    }
}
