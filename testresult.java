@Slf4j
@Component
public class NewMainUI extends JFrame {

    @Autowired
    private CustomizeSqlNewManager manager;

    private JTree tree;


    public void init() {
        //Must be called first of all Swing code as this sets the look and feel to FlatIntelliJ.
        FlatDarkLaf.setup();

        setTitle("customize sql system v2.0");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 605);

        // set left and right layout
        setLayout(new BorderLayout());
        // create tree menu
        DefaultMutableTreeNode root = initTreeMenu();
        tree = new JTree(root);

        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setName("treeMenu");
        scrollPane.setPreferredSize(new Dimension(250, 400));
        add(scrollPane, BorderLayout.WEST);
        boolean isVisible = true;
        // create right layout
        initRightPanel(isVisible);

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

    }

    private void initRightPanel(boolean isVisible) {
        // create right panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,1));
        JPanel cards = new JPanel(new CardLayout());
        // create empty panel
        JPanel emptyPanel = initEmptyPanel();
        // create add category panel
        JPanel addCategoryPanel = initAddCategoryPanel();
        // create delete category panel
        JPanel deleteCategoryPanel = initDeleteCategoryPanel();
        // create update category panel
        JPanel updateCategoryPanel = initUpdateCategoryPanel();
        // create show all categories panel
        JPanel showAllCategoryPanel = initShowAllCategoryPanel();
        // create add sql panel
        JPanel addSQLPanel = initAddSQLPanel();
        // create delete sql panel
        JPanel deleteSQLPanel = initDeleteSQLPanel();
        // create update sql panel
        JPanel updateSQLPanel = initUpdateSQLPanel();
        // create show all sql panel
        JPanel showAllSQLPanel = initShowAllSQLPanel();
        // create bind sql for category panel
        JPanel bindSQLForCategoryPanel = initBindSQLForCategoryPanel();
        // create unbind sql for category panel
        JPanel unbindSQLForCategoryPanel = initUnbindSQLForCategoryPanel();

        cards.add(emptyPanel, "empty");
        cards.add(addCategoryPanel, "新增分类条目");
        cards.add(deleteCategoryPanel, "删除分类条目");
        cards.add(updateCategoryPanel, "更新分类条目");
        cards.add(showAllCategoryPanel, "展示全部分类条目");
        cards.add(addSQLPanel, "新增SQL语句");
        cards.add(deleteSQLPanel, "删除SQL语句");
        cards.add(updateSQLPanel, "更新SQL语句");
        cards.add(showAllSQLPanel, "展示全部SQL语句");
        cards.add(bindSQLForCategoryPanel, "绑定");
        cards.add(unbindSQLForCategoryPanel, "解绑");
        List<Category> categories = manager.subShowCategoryView();
        for (Category category : categories) {
            // create menu view panel
            JPanel executePanel = initShowViewPanel(category.getIndexC().toString());
            cards.add(executePanel, category.getIndexC() + "." + category.getContent());
        }

        panel.add(cards);
        add(panel, BorderLayout.CENTER);
        CardLayout cl = (CardLayout)cards.getLayout();

        // tree menu listener
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
            if (node != null) {

                cl.show(cards, node.getUserObject().toString());

            }
        });

        setLocationRelativeTo(null);
        // 显示窗口
        setVisible(isVisible);
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

    /**
     * create empty panel when right main panel idle.
     *
     * @return emptyPanel
     */
    private JPanel initEmptyPanel() {

        return new JPanel();
    }

    /**
     * init show all categories panel.
     *
     * @return show all categories panel
     */
    private JPanel initShowAllCategoryPanel() {

        JPanel executePanel = new JPanel();
        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_CATEGORY_BELOW.getContent());
        viewLabel.setBounds(5, 10, 150, 20);

        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get category list
        List<Category> categories = manager.subShowCategoryView();
        categories.forEach(c -> {
            jTextArea.append(c.getIndexC() + ". " + c.getContent());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, 850, 500);
        JButton refreshBtn = generateCategoryRefreshBtn(jTextArea, 150);

        executePanel.add(viewLabel);
        executePanel.add(refreshBtn);
        executePanel.add(scrollPane);



        return executePanel;
    }

    private JButton generateCategoryRefreshBtn(JTextArea jTextArea, int width) {
        JButton refreshBtn = new JButton();
        refreshBtn.setIcon(new FlatRadioButtonIcon());
        refreshBtn.setBounds(width + 5, 10, 60, 20);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setContentAreaFilled(false);
        refreshBtn.setToolTipText("刷新");

        refreshBtn.addActionListener(l -> {
            // clear panel and reset value
            jTextArea.setText(null);
            List<Category> search = manager.subShowCategoryView();
            search.forEach(c -> {
                jTextArea.append(c.getIndexC() + ". " + c.getContent());
                jTextArea.append("\n");
            });
        });

        return refreshBtn;
    }

    /**
     * init add category panel.
     *
     * @return add category panel
     */
    private JPanel initAddCategoryPanel() {

        JPanel executePanel = new JPanel();

        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_CATEGORY_BELOW.getContent());
        viewLabel.setBounds(5, 10, 150, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get category list
        List<Category> categories = manager.subShowCategoryView();
        categories.forEach(c -> {
            jTextArea.append(c.getIndexC() + ". " + c.getContent());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, 850, 400);

        JButton refreshBtn = generateCategoryRefreshBtn(jTextArea, 150);

        JLabel nameLabel = new JLabel("请新增分类条目的名称: ");
        nameLabel.setBounds(5, 440, 130, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(145, 440, 200, 20);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(355, 440, 65, 20);
        // get name field value
        confirmBtn.addActionListener(l -> {
            String text = nameField.getText();
            try {
                List<Category> list = manager.subShowCategoryView();
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
                    nameField.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, SystemWarnLanguageEnum.ADD_FAILED.getContent(), "结果", JOptionPane.ERROR_MESSAGE);
                    log.error(SystemWarnLanguageEnum.ADD_FAILED.getContent());
                    nameField.setText(null);
                }
            } catch (DuplicateKeyException e) {
                String[] split = Objects.requireNonNull(e.getRootCause()).getLocalizedMessage().split("SQL statement:");
                JOptionPane.showMessageDialog(null, SystemWarnLanguageEnum.ADD_FAILED.getContent()+ split[ArgsConstant.REMAIN_NUMBER], "结果", JOptionPane.ERROR_MESSAGE);
                log.info(SystemWarnLanguageEnum.ADD_FAILED.getContent() + split[ArgsConstant.REMAIN_NUMBER]);
                nameField.setText(null);
            } catch (ContentExistedException e) {
                JOptionPane.showMessageDialog(null, SystemWarnLanguageEnum.ADD_FAILED.getContent()+ e.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(SystemWarnLanguageEnum.ADD_FAILED.getContent() + e.getMessage());
                nameField.setText(null);
            }


        });

        executePanel.add(viewLabel);
        executePanel.add(scrollPane);
        executePanel.add(refreshBtn);
        executePanel.add(nameLabel);
        executePanel.add(nameField);
        executePanel.add(confirmBtn);

        return executePanel;
    }

    /**
     * init delete category panel.
     *
     * @return delete category panel
     */
    private JPanel initDeleteCategoryPanel() {

        JPanel executePanel = new JPanel();

        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_CATEGORY_BELOW.getContent());
        viewLabel.setBounds(5, 10, 150, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get category list
        List<Category> categories = manager.subShowCategoryView();
        categories.forEach(c -> {
            jTextArea.append(c.getIndexC() + ". " + c.getContent());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, 850, 400);
        JButton refreshBtn = generateCategoryRefreshBtn(jTextArea, 150);

        JLabel nameLabel = new JLabel("请输入要删除的分类条目的序号: ");
        nameLabel.setBounds(5, 440, 190, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(205, 440, 200, 20);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(415, 440, 65, 20);
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
                jTextArea.setText(null);
                List<Category> search = manager.subShowCategoryView();
                search.forEach(c -> {
                    jTextArea.append(c.getIndexC() + ". " + c.getContent());
                    jTextArea.append("\n");
                });
                nameField.setText(null);
            } else {
                JOptionPane.showMessageDialog(null, SystemWarnLanguageEnum.DELETE_FAILED.getContent(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(SystemWarnLanguageEnum.DELETE_FAILED.getContent());
                nameField.setText(null);
            }


        });

        executePanel.add(viewLabel);
        executePanel.add(scrollPane);
        executePanel.add(refreshBtn);
        executePanel.add(nameLabel);
        executePanel.add(nameField);
        executePanel.add(confirmBtn);

        return executePanel;
    }

    /**
     * init update category panel.
     *
     * @return update category panel
     */
    private JPanel initUpdateCategoryPanel() {

        JPanel executePanel = new JPanel();

        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_CATEGORY_BELOW.getContent());
        viewLabel.setBounds(5, 10, 150, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get category list
        List<Category> categories = manager.subShowCategoryView();
        categories.forEach(c -> {
            jTextArea.append(c.getIndexC() + ". " + c.getContent());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, 850, 400);
        JButton refreshBtn = generateCategoryRefreshBtn(jTextArea, 150);
        // get content
        JLabel nameLabel = new JLabel(SystemWarnLanguageEnum.INPUT_UPDATE_INDEX.getContent());
        nameLabel.setBounds(5, 450, 190, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(205, 450, 200, 20);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(415, 450, 65, 20);
        // update index
        JLabel indexLabel = new JLabel(SystemWarnLanguageEnum.INPUT_UPDATE_INDEX_FOR_CATEGORY.getContent());
        indexLabel.setBounds(5, 470, 190, 20);
        JTextField indexField = new JTextField(20);
        indexField.setBounds(205, 470, 200, 20);
        // update content
        JLabel contentLabel = new JLabel(SystemWarnLanguageEnum.INPUT_UPDATE_CONTENT.getContent());
        contentLabel.setBounds(5, 490, 190, 20);
        JTextField contentField = new JTextField(20);
        contentField.setBounds(205, 490, 200, 20);
        JButton submitBtn = new JButton("提交");
        submitBtn.setBounds(415, 490, 65, 20);
        // get name field value
        confirmBtn.addActionListener(l -> {
            String text = nameField.getText();

            ResultDto<Category> resultDto = manager.initUpdateCategoryView(text, "", "");
            if (!resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
                nameField.setText(null);
            } else {
                indexField.setText(resultDto.getData().getIndexC().toString());
                contentField.setText(resultDto.getData().getContent());
            }
        });

        submitBtn.addActionListener(l -> {
            String text = nameField.getText();
            String index = indexField.getText();
            String content = contentField.getText();
            // execute update
            ResultDto<Category> resultDto = manager.initUpdateCategoryView(text, index, content);
            if (resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.PLAIN_MESSAGE);
                log.info(resultDto.getMessage());
                // clear panel and reset value
                jTextArea.setText(null);
                List<Category> search = manager.subShowCategoryView();
                search.forEach(c -> {
                    jTextArea.append(c.getIndexC() + ". " + c.getContent());
                    jTextArea.append("\n");
                });
            } else {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
            }
            nameField.setText(null);
            indexField.setText(null);
            contentField.setText(null);
        });

        executePanel.add(viewLabel);
        executePanel.add(scrollPane);
        executePanel.add(refreshBtn);
        executePanel.add(nameLabel);
        executePanel.add(nameField);
        executePanel.add(confirmBtn);
        executePanel.add(indexLabel);
        executePanel.add(indexField);
        executePanel.add(contentLabel);
        executePanel.add(contentField);
        executePanel.add(submitBtn);

        return executePanel;
    }

    /**
     * init bind sql for category panel.
     *
     * @return bind sql for category panel
     */
    private JPanel initBindSQLForCategoryPanel() {

        JPanel executePanel = new JPanel();

        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_CATEGORY_BELOW.getContent());
        viewLabel.setBounds(5, 10, 130, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get category list
        List<Category> categories = manager.subShowCategoryView();
        categories.forEach(c -> {
            jTextArea.append(c.getIndexC() + ". " + c.getContent());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, 300, 400);
        JButton refreshBtn = generateCategoryRefreshBtn(jTextArea, 130);
        // input category index
        JLabel nameLabel = new JLabel(SystemWarnLanguageEnum.INPUT_CATEGORY_INDEX.getContent());
        nameLabel.setBounds(5, 440, 190, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(205, 440, 200, 20);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(415, 440, 65, 20);
        // update index
        JLabel addableLabel = new JLabel(SystemWarnLanguageEnum.LIST_ADDABLE_SQL.getContent());
        addableLabel.setBounds(310, 10, 180, 20);
        // add combo box
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("key");
        comboBox.addItem("sql");
        comboBox.setBounds(490, 10, 70, 20);
        // add search text field
        JTextField searchField = new JTextField();
        searchField.setBounds(565, 10, 200, 20);
        searchField.addFocusListener(new JTextFieldHintListener(searchField, "请根据key或sql的关键字检索"));
        // add search button
        JButton searchBtn = new JButton();
        searchBtn.setIcon(new FlatSearchIcon());
        searchBtn.setBounds(770, 10, 20, 20);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setContentAreaFilled(false);
        searchBtn.setToolTipText("检索");

        JTextArea addableTextArea = new JTextArea();
        addableTextArea.setEditable(false);
        addableTextArea.setLineWrap(true);
        addableTextArea.setFont(new Font(null, Font.PLAIN, 11));

        JScrollPane addableScrollPane = new JScrollPane(
                addableTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        addableScrollPane.setBounds(315, 30, 580, 400);
        // input bind sql index
        JLabel contentLabel = new JLabel(SystemWarnLanguageEnum.INPUT_SQL_INDEX.getContent());
        contentLabel.setBounds(5, 470, 190, 20);
        JTextField contentField = new JTextField(20);
        contentField.setBounds(205, 470, 400, 20);
        JButton submitBtn = new JButton("绑定");
        submitBtn.setBounds(615, 470, 65, 20);
        // get name field value
        confirmBtn.addActionListener(l -> {
            String text = nameField.getText();
            BindSqlDto bindSqlDto = new BindSqlDto(text, "", "", "");
            ResultDto<List<BasicSql>> resultDto = manager.initBindSQLForCategory(bindSqlDto);
            if (!resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
                nameField.setText(null);
            } else {
                List<BasicSql> data = resultDto.getData();
                addableTextArea.setText(null);
                data.forEach(s -> {
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.setIcon(new FlatCheckBoxIcon());
                    addableTextArea.append(s.getId() + ". " + s.getSqlValue() + ViewEnum.KEY_SYMBOL_LEFT.getContent()+s.getSqlKey() +"】");
                    addableTextArea.append("\n");

                });
            }
        });

        submitBtn.addActionListener(l -> {
            String text = nameField.getText();
            String content = contentField.getText();
            BindSqlDto bindSqlDto = new BindSqlDto(text, content, "", "");
            // execute bind
            ResultDto<List<BasicSql>> resultDto = manager.initBindSQLForCategory(bindSqlDto);
            if (resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.PLAIN_MESSAGE);
                log.info(resultDto.getMessage());
                // clear panel and reset value
                addableTextArea.setText(null);
            } else {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
            }
            nameField.setText(null);
            contentField.setText(null);
            searchField.setText(null);
        });

        searchBtn.addActionListener(l -> {
            String searchText = searchField.getText();
            String categoryIndex = nameField.getText();
            String comBoxKey = Optional.ofNullable(comboBox.getSelectedItem()).orElse("").toString();

            BindSqlDto bindSqlDto = new BindSqlDto(categoryIndex, "", searchText, comBoxKey);

            addableTextArea.setText(null);

            ResultDto<List<BasicSql>> resultDto = manager.initBindSQLForCategory(bindSqlDto);
            if (!resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
                nameField.setText(null);
            } else {
                List<BasicSql> data = resultDto.getData();
                addableTextArea.setText(null);
                data.forEach(s -> {
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.setIcon(new FlatCheckBoxIcon());
                    addableTextArea.append(s.getId() + ". " + s.getSqlValue() + ViewEnum.KEY_SYMBOL_LEFT.getContent()+s.getSqlKey() +"】");
                    addableTextArea.append("\n");
                });
            }

        });

        executePanel.add(viewLabel);
        executePanel.add(scrollPane);
        executePanel.add(refreshBtn);
        executePanel.add(addableLabel);
        executePanel.add(comboBox);
        executePanel.add(searchField);
        executePanel.add(searchBtn);
        executePanel.add(addableScrollPane);
        executePanel.add(nameLabel);
        executePanel.add(nameField);
        executePanel.add(confirmBtn);
        executePanel.add(contentLabel);
        executePanel.add(contentField);
        executePanel.add(submitBtn);

        return executePanel;
    }

    /**
     * init unbind sql for category panel.
     *
     * @return unbind sql for category panel
     */
    private JPanel initUnbindSQLForCategoryPanel() {

        JPanel executePanel = new JPanel();

        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_CATEGORY_BELOW.getContent());
        viewLabel.setBounds(5, 10, 130, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get category list
        List<Category> categories = manager.subShowCategoryView();
        categories.forEach(c -> {
            jTextArea.append(c.getIndexC() + ". " + c.getContent());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, 300, 400);
        JButton refreshBtn = generateCategoryRefreshBtn(jTextArea, 130);
        // input category index
        JLabel nameLabel = new JLabel(SystemWarnLanguageEnum.INPUT_CATEGORY_INDEX.getContent());
        nameLabel.setBounds(5, 440, 190, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(205, 440, 200, 20);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(415, 440, 65, 20);
        // update index
        JLabel addableLabel = new JLabel(SystemWarnLanguageEnum.LIST_REMOVABLE_SQL.getContent());
        addableLabel.setBounds(310, 10, 180, 20);
        JTextArea addableTextArea = new JTextArea();
        addableTextArea.setEditable(false);
        addableTextArea.setLineWrap(true);
        addableTextArea.setFont(new Font(null, Font.PLAIN, 11));
        JScrollPane addableScrollPane = new JScrollPane(
                addableTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        addableScrollPane.setBounds(315, 30, 580, 400);
        // input unbind sql index
        JLabel contentLabel = new JLabel(SystemWarnLanguageEnum.INPUT_SQL_INDEX.getContent());
        contentLabel.setBounds(5, 470, 190, 20);
        JTextField contentField = new JTextField(20);
        contentField.setBounds(205, 470, 400, 20);
        JButton submitBtn = new JButton("解绑");
        submitBtn.setBounds(615, 470, 65, 20);
        // get name field value
        confirmBtn.addActionListener(l -> {
            String text = nameField.getText();

            ResultDto<List<BasicSql>> resultDto = manager.initUnbindSQLForCategory(text, "");
            if (!resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
                nameField.setText(null);
            } else {
                List<BasicSql> data = resultDto.getData();
                addableTextArea.setText(null);
                data.forEach(s -> {
                    addableTextArea.append(s.getId() + ". " + s.getSqlValue()+ "   key:【"+s.getSqlKey() +"】");
                    addableTextArea.append("\n");
                });
            }
        });

        submitBtn.addActionListener(l -> {
            String text = nameField.getText();
            String content = contentField.getText();
            // execute bind
            ResultDto<List<BasicSql>> resultDto = manager.initUnbindSQLForCategory(text, content);
            if (resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.PLAIN_MESSAGE);
                log.info(resultDto.getMessage());
                // clear panel and reset value
                addableTextArea.setText(null);
            } else {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
            }
            nameField.setText(null);
            contentField.setText(null);
        });

        executePanel.add(viewLabel);
        executePanel.add(scrollPane);
        executePanel.add(refreshBtn);
        executePanel.add(addableLabel);
        executePanel.add(addableScrollPane);
        executePanel.add(nameLabel);
        executePanel.add(nameField);
        executePanel.add(confirmBtn);
        executePanel.add(contentLabel);
        executePanel.add(contentField);
        executePanel.add(submitBtn);

        return executePanel;
    }

    /**
     * init add sql panel.
     *
     * @return add sql panel
     */
    private JPanel initAddSQLPanel() {

        JPanel executePanel = new JPanel();

        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_SQL_BELOW.getContent());
        viewLabel.setBounds(5, 10, 100, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get basic sql list
        List<BasicSql> basicSqls = manager.subShowBasicSQLView();
        basicSqls.forEach(s -> {
            jTextArea.append(s.getId() + ". " + s.getSqlValue());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, 870, 400);
        JButton refreshBtn = generateSqlRefreshBtn(jTextArea);

        // input table name
        JLabel nameLabel = new JLabel(SystemWarnLanguageEnum.INPUT_TABLE_NAME.getContent());
        nameLabel.setBounds(5, 430, 190, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(205, 430, 200, 20);
        // input condition
        JLabel conditionLabel = new JLabel(SystemWarnLanguageEnum.INPUT_CONDITION.getContent());
        conditionLabel.setBounds(5, 450, 190, 20);
        JTextField conditionField = new JTextField(20);
        conditionField.setBounds(205, 450, 200, 20);
        // input other conditions
        JLabel otherLabel = new JLabel(SystemWarnLanguageEnum.INPUT_OTHER_CONDITION.getContent());
        otherLabel.setBounds(5, 470, 190, 20);
        JTextField otherField = new JTextField(20);
        otherField.setBounds(205, 470, 200, 20);
        // input key
        JLabel keyLabel = new JLabel(SystemWarnLanguageEnum.INPUT_KEY.getContent());
        keyLabel.setBounds(5, 490, 190, 20);
        JTextField keyField = new JTextField(20);
        keyField.setBounds(205, 490, 200, 20);
        JButton submitBtn = new JButton("提交");
        submitBtn.setBounds(415, 490, 65, 20);
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


        });

        executePanel.add(viewLabel);
        executePanel.add(scrollPane);
        executePanel.add(refreshBtn);
        executePanel.add(nameLabel);
        executePanel.add(nameField);
        executePanel.add(submitBtn);
        executePanel.add(conditionLabel);
        executePanel.add(conditionField);
        executePanel.add(otherLabel);
        executePanel.add(otherField);
        executePanel.add(keyLabel);
        executePanel.add(keyField);


        return executePanel;
    }

    /**
     * init show all sqls panel.
     *
     * @return show all sqls panel
     */
    private JPanel initShowAllSQLPanel() {

        JPanel executePanel = new JPanel();
        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_SQL_BELOW.getContent());
        viewLabel.setBounds(5, 10, 100, 20);

        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get category list
        List<BasicSql> basicSqls = manager.subShowBasicSQLView();
        basicSqls.forEach(s -> {
            jTextArea.append(s.getId() + ". " + s.getSqlValue());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, 870, 450);
        JButton refreshBtn = generateSqlRefreshBtn(jTextArea);

        // add search text field
        JTextField searchField = new JTextField();
        searchField.setBounds(565, 10, 200, 20);
        searchField.addFocusListener(new JTextFieldHintListener(searchField, "请输入关键字检索"));
        // add search button
        JButton searchBtn = new JButton();
        searchBtn.setIcon(new FlatSearchWithHistoryIcon());
        searchBtn.setBounds(770, 10, 20, 20);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setContentAreaFilled(false);
        searchBtn.setToolTipText("检索");

        searchBtn.addActionListener(l -> {
            jTextArea.setText(null);
            String text = searchField.getText();
            List<BasicSql> sqlList = manager.subShowBasicSQLViewWithSearch(text);
            sqlList.forEach(s -> {
                jTextArea.append(s.getId() + ". " + s.getSqlValue());
                jTextArea.append("\n");
            });
        });

        executePanel.add(viewLabel);
        executePanel.add(refreshBtn);
        executePanel.add(searchField);
        executePanel.add(searchBtn);
        executePanel.add(scrollPane);



        return executePanel;
    }

    private JButton generateSqlRefreshBtn(JTextArea jTextArea) {
        JButton refreshBtn = new JButton();
        refreshBtn.setIcon(new FlatRadioButtonIcon());
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setContentAreaFilled(false);
        refreshBtn.setToolTipText("刷新");
        refreshBtn.setBounds(100 + 5, 10, 60, 20);

        refreshBtn.addActionListener(l -> {
            // clear panel and reset value
            jTextArea.setText(null);
            List<BasicSql> search = manager.subShowBasicSQLView();
            search.forEach(s -> {
                jTextArea.append(s.getId() + ". " + s.getSqlValue());
                jTextArea.append("\n");
            });
        });

        return refreshBtn;
    }

    /**
     * init delete sql panel.
     *
     * @return delete sql panel
     */
    private JPanel initDeleteSQLPanel() {

        JPanel executePanel = new JPanel();

        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_SQL_BELOW.getContent());
        viewLabel.setBounds(5, 10, 100, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get basic sql list
        List<BasicSql> basicSqls = manager.subShowBasicSQLView();
        basicSqls.forEach(s -> {
            jTextArea.append(s.getId() + ". " + s.getSqlValue());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, 870, 400);
        JButton refreshBtn = generateSqlRefreshBtn(jTextArea);

        JLabel nameLabel = new JLabel(SystemWarnLanguageEnum.INPUT_DELETE_INDEX.getContent());
        nameLabel.setBounds(5, 440, 190, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(205, 440, 200, 20);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(415, 440, 65, 20);
        // get name field value
        confirmBtn.addActionListener(l -> {
            String text = nameField.getText();
            // execute delete
            ResultDto resultDto = manager.initRemoveBasicSQLView(text);
            if (resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.PLAIN_MESSAGE);
                // clear panel and reset value
                jTextArea.setText(null);
                List<BasicSql> search = manager.subShowBasicSQLView();
                search.forEach(s -> {
                    jTextArea.append(s.getId() + ". " + s.getSqlValue());
                    jTextArea.append("\n");
                });
                nameField.setText(null);
            } else {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                nameField.setText(null);
            }


        });

        executePanel.add(viewLabel);
        executePanel.add(scrollPane);
        executePanel.add(refreshBtn);
        executePanel.add(nameLabel);
        executePanel.add(nameField);
        executePanel.add(confirmBtn);

        return executePanel;
    }

    /**
     * init update sql panel.
     *
     * @return update sql panel
     */
    private JPanel initUpdateSQLPanel() {

        JPanel executePanel = new JPanel();

        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel("全部SQL语句如下: ");
        viewLabel.setBounds(5, 10, 100, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get category list
        List<BasicSql> basicSqls = manager.subShowBasicSQLView();
        basicSqls.forEach(s -> {
            jTextArea.append(s.getId() + ". " + s.getSqlValue());
            jTextArea.append("\n");
        });
        scrollPane.setBounds(10, 30, 870, 400);
        JButton refreshBtn = generateSqlRefreshBtn(jTextArea);
        // get content
        JLabel nameLabel = new JLabel(SystemWarnLanguageEnum.INPUT_UPDATE_INDEX.getContent());
        nameLabel.setBounds(5, 440, 190, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(205, 440, 200, 20);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(415, 440, 65, 20);
        // update key
        JLabel keyLabel = new JLabel(SystemWarnLanguageEnum.INPUT_UPDATE_KEY.getContent());
        keyLabel.setBounds(5, 460, 190, 20);
        JTextField keyField = new JTextField(20);
        keyField.setBounds(205, 460, 200, 20);
        // update sql value
        JLabel sqlLabel = new JLabel(SystemWarnLanguageEnum.INPUT_UPDATE_SQL.getContent());
        sqlLabel.setBounds(5, 480, 190, 20);
        JTextField sqlField = new JTextField(20);
        sqlField.setBounds(205, 480, 600, 20);
        JButton submitBtn = new JButton("提交");
        submitBtn.setBounds(815, 480, 65, 20);
        // get name field value
        confirmBtn.addActionListener(l -> {
            String text = nameField.getText();

            ResultDto<BasicSql> resultDto = manager.initUpdateBasicSQLView(text, "@", "@");
            if (!resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
                nameField.setText(null);
            } else {
                keyField.setText(resultDto.getData().getSqlKey());
                sqlField.setText(resultDto.getData().getSqlValue());
            }
        });

        submitBtn.addActionListener(l -> {
            String text = nameField.getText();
            String key = keyField.getText();
            String sql = sqlField.getText();
            // execute update
            ResultDto<BasicSql> resultDto = manager.initUpdateBasicSQLView(text, key, sql);
            if (resultDto.isUpdateResult()) {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.PLAIN_MESSAGE);
                log.info(resultDto.getMessage());
                // clear panel and reset value
                jTextArea.setText(null);
                List<BasicSql> search = manager.subShowBasicSQLView();
                search.forEach(s -> {
                    jTextArea.append(s.getId() + ". " + s.getSqlValue());
                    jTextArea.append("\n");
                });
            } else {
                JOptionPane.showMessageDialog(null, resultDto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
                log.error(resultDto.getMessage());
            }
            nameField.setText(null);
            keyField.setText(null);
            sqlField.setText(null);
        });

        executePanel.add(viewLabel);
        executePanel.add(scrollPane);
        executePanel.add(refreshBtn);
        executePanel.add(nameLabel);
        executePanel.add(nameField);
        executePanel.add(confirmBtn);
        executePanel.add(keyLabel);
        executePanel.add(keyField);
        executePanel.add(sqlLabel);
        executePanel.add(sqlField);
        executePanel.add(submitBtn);

        return executePanel;
    }

    /**
     * create show view panel.
     *
     * @return showViewPanel
     */
    private JPanel initShowViewPanel(String indexStr) {

        JPanel executePanel = new JPanel();

        executePanel.setLayout(null);
        JLabel viewLabel = new JLabel(ViewEnum.ALL_SQL_BELOW.getContent());
        viewLabel.setBounds(5, 10, 200, 20);
        JButton button = new JButton();
        JButton copyButton = new JButton();
        copyButton.setIcon(new FlatWindowRestoreIcon());
        copyButton.setToolTipText("复制");
        copyButton.setBorderPainted(false);
        copyButton.setContentAreaFilled(false);
        copyButton.setFocusPainted(false);
        copyButton.setBounds(810, 10, 30, 20);

        button.setIcon(new FlatClearIcon());
        button.setToolTipText("重置");
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBounds(830, 10, 70, 20);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(new Font(null, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(
                jTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // get basic sql list
        ResultDto<MenuDto> dto = manager.initClickViewMenu(indexStr);
        MenuDto menuDto = dto.getData();
        Category category = menuDto.getCategory();
        List<String> keyList = menuDto.getKeyList();
        if (category != null) {
            List<BasicSql> basicSqlList = category.getBasicSqlList();
            if (keyList == null || keyList.contains("")) {
                String travelSql = manager.travelSql(basicSqlList);
                jTextArea.append(travelSql);
            }
        } else {
            JOptionPane.showMessageDialog(null, dto.getMessage(), "结果", JOptionPane.ERROR_MESSAGE);
            // clear panel and reset value
            jTextArea.setText(null);
        }
        scrollPane.setBounds(10, 30, 870, 380);
        if (keyList != null) {
            int y = 410;
            assert category != null;
            List<BasicSql> basicSqls = category.getBasicSqlList();
            for (int i = 0; i < keyList.size(); i++) {
                String key = keyList.get(i);
                if (!"".equals(key)) {
                    // input parameter
                    JLabel nameLabel = new JLabel(SystemWarnLanguageEnum.INPUT_PARAMETER.getContent() + key + SystemWarnLanguageEnum.INPUT_SYMBOL.getContent());
                    nameLabel.setBounds(5, y + i*20, 250, 20);
                    JTextField nameField = new JTextField(20);
                    nameField.setBounds(265, y + i*20, 400, 20);
                    JButton submitBtn = new JButton("确认");
                    submitBtn.setBounds(675, y + i*20, 65, 20);

                    // get name field value
                    submitBtn.addActionListener(l -> {
                        String value = nameField.getText();
                        String sqls = manager.keyListView(key, value, basicSqls);
                        jTextArea.append(sqls);
                    });
                    executePanel.add(nameLabel);
                    executePanel.add(nameField);
                    executePanel.add(submitBtn);
                }
            }
        }

        button.addActionListener(l -> jTextArea.setText(null));

        copyButton.addActionListener(l -> {
            StringSelection stringSelection = new StringSelection(jTextArea.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });

        executePanel.add(viewLabel);
        executePanel.add(button);
        executePanel.add(copyButton);
        executePanel.add(scrollPane);


        return executePanel;
    }

    public JTree getTree() {
        return tree;
    }

}