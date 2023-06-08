package com.moio.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.moio.entity.Category;
import com.moio.feature.view.*;
import com.moio.manager.CustomizeSqlNewManager;
import com.moio.utils.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * @author molinchang
 *
 * @description test new feature for main UI.
 */
@Component
public class NewMainUI extends JFrame {

    @Autowired
    CustomizeSqlNewManager manager;

    @Autowired
    ShowAllCategoryPanel showAllCategoryPanel;

    @Autowired
    AddCategoryPanel addCategoryPanel;

    @Autowired
    DeleteCategoryPanel deleteCategoryPanel;

    @Autowired
    UpdateCategoryPanel updateCategoryPanel;

    @Autowired
    BindSQLForCategoryPanel bindSQLForCategoryPanel;

    @Autowired
    UnbindSQLForCategoryPanel unbindSQLForCategoryPanel;

    @Autowired
    AddSQLPanel addSQLPanel;

    @Autowired
    ShowAllSQLPanel showAllSQLPanel;

    @Autowired
    DeleteSQLPanel deleteSQLPanel;

    @Autowired
    UpdateSQLPanel updateSQLPanel;

    @Autowired
    ShowViewPanel showViewPanel;

    private JTree tree;
    private JPanel leftPanel;
    private JPanel rightPanel;

    public NewMainUI() {
        FlatDarkLaf.setup();
        setTitle("customize sql system v2.0");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 605);
        setResizable(true);
        addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes.
             *
             * @param e
             */
            @Override
            public void componentResized(ComponentEvent e) {
                refresh(getWidth(), getHeight());
            }
        });

        // set left and right layout
        setLayout(new BorderLayout());
    }
    /**
     * init fake main ui.
     */
    public void init() {


        rightPanel = new JPanel(new GridLayout(1,1));
        rightPanel.setVisible(true);
        leftPanel = new JPanel(new BorderLayout());

        add(rightPanel, BorderLayout.CENTER);
        refresh(getWidth(), getHeight());

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                // do nothing
            }

            @Override
            public void windowClosing(WindowEvent e) {
                manager.reWriteFileBeforeExit();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                // do nothing
            }

            @Override
            public void windowIconified(WindowEvent e) {
                // do nothing
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // do nothing
            }

            @Override
            public void windowActivated(WindowEvent e) {
                // do nothing
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // do nothing
            }
        });


        setLocationRelativeTo(null);
        // 显示窗口
        setVisible(true);
    }



    public void refresh(int width, int height) {
        leftPanel.removeAll();
        leftPanel.updateUI();
        rightPanel.removeAll();
        rightPanel.updateUI();
        rightPanel.add(new JPanel());
        DefaultMutableTreeNode root = initTreeMenu();
        tree = new JTree(root);

        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setName("treeMenu");
        scrollPane.setPreferredSize(new Dimension(250, 560));
        leftPanel.add(scrollPane);

        // tree menu listener
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
            if (node != null) {
                String name = node.getUserObject().toString();

                if (name.equals("展示全部分类条目")) {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    rightPanel.add(showAllCategoryPanel);
                    showAllCategoryPanel.refresh(width, height);
                } else if (name.equals("新增分类条目")) {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    rightPanel.add(addCategoryPanel);
                    addCategoryPanel.refresh(width, height);
                } else if (name.equals("删除分类条目")) {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    rightPanel.add(deleteCategoryPanel);
                    deleteCategoryPanel.refresh(width, height);
                } else if (name.equals("更新分类条目")) {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    rightPanel.add(updateCategoryPanel);
                    updateCategoryPanel.refresh(width, height);
                } else if (name.equals("绑定")) {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    rightPanel.add(bindSQLForCategoryPanel);
                    bindSQLForCategoryPanel.refresh(width, height);
                } else if (name.equals("解绑")) {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    rightPanel.add(unbindSQLForCategoryPanel);
                    unbindSQLForCategoryPanel.refresh(width, height);
                } else if (name.equals("新增SQL语句")) {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    rightPanel.add(addSQLPanel);
                    addSQLPanel.refresh(width, height);
                } else if (name.equals("展示全部SQL语句")) {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    rightPanel.add(showAllSQLPanel);
                    showAllSQLPanel.refresh(width, height);
                } else if (name.equals("删除SQL语句")) {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    rightPanel.add(deleteSQLPanel);
                    deleteSQLPanel.refresh(width, height);
                } else if (name.equals("更新SQL语句")) {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    rightPanel.add(updateSQLPanel);
                    updateSQLPanel.refresh(width, height);
                } else if (name.split("\\.").length != 0 && ValidUtil.isContainNumber(name.split("\\.")[0])) {
                    rightPanel.removeAll();
                    rightPanel.repaint();
                    showViewPanel.refresh(name.split("\\.")[0], width, height);
                    rightPanel.add(showViewPanel);
                    rightPanel.updateUI();
                } else if (name.equals("显示菜单")) {
                    refresh(width, height);
                }
            }

        });
        leftPanel.setVisible(true);
        add(leftPanel, BorderLayout.WEST);

    }

    /**
     * create tree menu in left layout
     *
     * @return tree node
     */
    private DefaultMutableTreeNode initTreeMenu() {

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Menu");
        DefaultMutableTreeNode customNode = new DefaultMutableTreeNode("自定义设置");
        DefaultMutableTreeNode sqlNode = new DefaultMutableTreeNode("自定义SQL语句");
        DefaultMutableTreeNode addSqlNode = new DefaultMutableTreeNode("新增SQL语句");
        DefaultMutableTreeNode deleteSqlNode = new DefaultMutableTreeNode("删除SQL语句");
        DefaultMutableTreeNode updateSqlNode = new DefaultMutableTreeNode("更新SQL语句");
        DefaultMutableTreeNode showAllSqlNode = new DefaultMutableTreeNode("展示全部SQL语句");
        DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode("自定义分类条目");
        DefaultMutableTreeNode addCategoryNode = new DefaultMutableTreeNode("新增分类条目");
        DefaultMutableTreeNode deleteCategoryNode = new DefaultMutableTreeNode("删除分类条目");
        DefaultMutableTreeNode updateCategoryNode = new DefaultMutableTreeNode("更新分类条目");
        DefaultMutableTreeNode showAllCategoryNode = new DefaultMutableTreeNode("展示全部分类条目");
        DefaultMutableTreeNode bindOrUnbindCategoryNode = new DefaultMutableTreeNode("为当前分类条目绑定/解绑SQL语句");
        DefaultMutableTreeNode bindSQLNode = new DefaultMutableTreeNode("绑定");
        DefaultMutableTreeNode unbindSQLNode = new DefaultMutableTreeNode("解绑");
        DefaultMutableTreeNode menuNode = new DefaultMutableTreeNode("显示菜单");
        List<Category> categories = manager.subShowCategoryView();
        for (Category category : categories) {
            DefaultMutableTreeNode subMenuNode = new DefaultMutableTreeNode(category.getIndexC() + "." +category.getContent());
            menuNode.add(subMenuNode);
        }
        root.add(customNode);
        customNode.add(categoryNode);
        categoryNode.add(addCategoryNode);
        categoryNode.add(deleteCategoryNode);
        categoryNode.add(updateCategoryNode);
        categoryNode.add(showAllCategoryNode);
        categoryNode.add(bindOrUnbindCategoryNode);
        bindOrUnbindCategoryNode.add(bindSQLNode);
        bindOrUnbindCategoryNode.add(unbindSQLNode);
        customNode.add(sqlNode);
        sqlNode.add(addSqlNode);
        sqlNode.add(deleteSqlNode);
        sqlNode.add(updateSqlNode);
        sqlNode.add(showAllSqlNode);
        root.add(menuNode);

        return root;
    }


}
