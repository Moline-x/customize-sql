package com.moio.feature.view;

import com.formdev.flatlaf.icons.FlatSearchWithHistoryIcon;
import com.moio.common.constant.GuiConstant;
import com.moio.common.enums.ViewEnum;
import com.moio.common.listener.JTextFieldHintListener;
import com.moio.entity.Category;
import com.moio.manager.CustomizeSqlNewManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * @author molinchang
 *
 * @description show all category panel component
 */
@Component
public class ShowAllCategoryPanel extends JPanel {

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
        List<Category> categories = manager.subShowCategoryView();
        categories.forEach(c -> {
            jTextArea.append(c.getIndexC() + ". " + c.getContent());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, width * 850 / 1200, height * 500 / 605);

        // add search text field
        JTextField searchField = new JTextField();
        searchField.setBounds(415 + viewLabel.getWidth(), 10, width * 200 / 1200, 20);
        searchField.addFocusListener(new JTextFieldHintListener(searchField, "请输入关键字检索"));
        // add search button
        JButton searchBtn = new JButton();
        searchBtn.setIcon(new FlatSearchWithHistoryIcon());
        searchBtn.setBounds(5 + (searchField.getX() + searchField.getWidth()), 10, 20, 20);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setContentAreaFilled(false);
        searchBtn.setToolTipText("检索");

        searchBtn.addActionListener(l -> {
            jTextArea.setText(null);
            String text = searchField.getText();
            List<Category> categoryList = manager.subShowCategoryViewWithSearch(text);
            categoryList.forEach(c -> {
                jTextArea.append(c.getIndexC() + ". " + c.getContent());
                jTextArea.append("\n");
            });
            SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
        });

        add(viewLabel);
        add(searchField);
        add(searchBtn);
        add(scrollPane);

        setVisible(true);
        revalidate();

        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
    }
}
