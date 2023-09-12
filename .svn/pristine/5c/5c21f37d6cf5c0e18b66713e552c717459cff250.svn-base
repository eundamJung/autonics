//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.document;

import com.tool.FileManager;
import com.tool.common.resource.AppConfig;
import com.tool.db.sqlite.config.table.Metadata;
import com.tool.document.services.watch.DownloadService;
import com.tool.document.services.watch.DownloadWorker;
import com.tool.document.services.watch.FileWatchConfig;
import com.tool.document.services.watch.FileWatchService;
import com.tool.document.services.watch.FileWatchUtil;
import com.tool.http.FileHttpUtil;
import com.tool.ui.combobox.TableFilterCombobox;
import com.tool.ui.combobox.TableFilterComboboxListCellRenderer;
import com.tool.ui.combobox.TableFilterComboboxModel;
import com.tool.ui.common.SwingUtils;
import com.tool.ui.table.*;
import com.tool.ui.table.freeze.FreezeTable;
import com.tool.ui.table.selection.CheckboxTable;
import com.tool.ui.textfield.TableFilterTextField;
import com.tool.util.ExceptionUtil;
import com.tool.util.ProcessUtil;
import com.tool.util.StringUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentManager extends JFrame {
    private Logger logger = LoggerFactory.getLogger(DocumentManager.class);
    FileWatchService fileWatchService = null;
    public FileManager fileManager;
    ProcessUtil process;
    TableModel tableModel = null;
    TableRowFilterSet tableRowFilterSet = null;
    public TableModel waitTableModel = null;
    public TableRowFilterSet waitRowFilterSet = null;
    public TableModel uploadTableModel = null;
    public TableRowFilterSet uploadRowFilterSet = null;
    private JPanel topPanel;
    private JPanel topInnerPanel;
    private JPanel workPanel;
    private JLabel workLabel;
    public JTextField workField;
    private JButton workBtn;
    private JPanel InfoPanel;
    private JPanel InfoPanel1;
    private JLabel saveBufferLabel;
    private JLabel waitBufferLabel;
    private JTabbedPane centerTabbedPane;
    private JSplitPane downloadSplitPane;
    private JPanel downLeftPanel;
    private JPanel searchPanel;
    private JPanel searchTopPanel;
    private JPanel searchProgressPanel;
    private JProgressBar searchProgress;
    private JButton searchBtn;
    private DocumentSearchPanel searchFieldPanel;
    private JPanel downRightPane;
    private JPanel listPanel;
    private JPanel listTopPanel;
    private JPanel listProgressPanel;
    public JProgressBar listProgress;
    private JPanel listToolbarSearch;
    private TableFilterCombobox columnCombo;
    private TableFilterTextField searchField;
    private JScrollPane listScrollPanel;
    public FreezeTable freezeTable;
    private JLabel foundLabel;
    private JSplitPane uploadSplitPane;
    private JPanel upTopPaneEmptyBorder;
    private JPanel upTopPane;
    private JScrollPane waitScrollPane;
    public CheckboxTable waitTable;
    private JPanel upBottomPaneEmptyBorder;
    private JPanel upBottomPane;
    private JPanel upBottomTop;
    private TableFilterCombobox uploadCombo;
    private TableFilterTextField uploadSearchField;
    private JScrollPane uploadScrollPane;
    public JTable uploadTable;

    public DocumentManager(FileManager fileManager, String title) {
        this.fileManager = fileManager;
        this.initComponents();
        this.initSearchTable();
        this.initWaitingTable();
        this.initUploadTable();
        this.searchFieldPanel.setDocumentManager(this);
        this.setTitle(title);
        this.setDefaultCloseOperation(0);
        this.setSize(1200, 700);
        Dimension frameSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        Image image = AppConfig.getImageIcon("iconSync.png").getImage();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                DocumentManager.this.setVisible(false);
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    DocumentManager.this.tableRowFilterSet.getTableColumnAdjuster().adjustColumns();
                    DocumentManager.this.freezeTable.updateUI();
                });
            }
        });
        this.setIconImage(image);
        this.setDefaultCloseOperation(0);
        this.setVisible(true);
        this.start();
    }

    void start() {
        this.logger.info(StringUtil.replaceStr(AppConfig.getString("log.start"), new String[]{this.getTitle()}));

        try {
            String systemTempDir = AppConfig.getUserConfig("workspace");
            if(StringUtils.isEmpty(systemTempDir))
                systemTempDir = System.getProperty("java.io.tmpdir");

            Path workspace = Paths.get(systemTempDir + File.separator + "FileManager");
            if (!Files.exists(workspace, new LinkOption[0])) {
                Files.createDirectory(workspace);
            }

            if (!Files.isHidden(workspace)) {
                Files.setAttribute(workspace, "dos:hidden", true);
            }

            this.startWorkspaceFileWatchService(workspace.toString());
        } catch (IOException var3) {
            JOptionPane.showMessageDialog(this, var3.getMessage(), this.getTitle(), 0);
        }

    }

    void initSearchTable() {
        this.tableModel = new TableModel();
        this.freezeTable.setModel(this.tableModel);
        this.freezeTable.setAutoResizeMode(0);
        TableColumnModel tableColumnModel = new TableColumnModel(new TableCellRenderers());
        tableColumnModel.addColumn(new TableColumnMapping("Document Name"));
        tableColumnModel.addColumn(new TableColumnMapping("Rev"));
        tableColumnModel.addColumn(new TableColumnMapping("File"));
        tableColumnModel.addColumn(new TableColumnMapping("Title"));
        tableColumnModel.addColumn(new TableColumnMapping("Owner"));
        tableColumnModel.addColumn(new TableColumnMapping("State"));
        tableColumnModel.addColumn(new TableColumnMapping("Originated"));
        tableColumnModel.addColumn(new TableColumnMapping("Ver"));
        tableColumnModel.addColumn(new TableColumnMapping("Locked"));
        tableColumnModel.addColumn(new TableColumnMapping("Locker"));
        tableColumnModel.addColumn(new TableColumnMapping("File Modified"));
        this.freezeTable.setColumnModel(tableColumnModel);
        this.tableModel.setColumnMap(tableColumnModel.getColumnActualMap());
        this.freezeTable.setRowHeight(35);
        this.freezeTable.setRowSelectionAllowed(true);
        this.freezeTable.setColumnSelectionAllowed(false);
        this.freezeTable.setFixedColumns(2);
        this.tableRowFilterSet = new TableRowFilterSet(this.freezeTable, new TableRowFilter(), new TableRowSorter(this.tableModel), new TableColumnAdjuster(this.freezeTable, 10), this.foundLabel);
        this.freezeTable.setAdjuster(this.tableRowFilterSet.getTableColumnAdjuster());
        this.freezeTable.addMouseRightButtonPopupMenu(new MouseRightButtonPopupMenu());
        String[] filters = new String[]{"Document Name", "Rev", "Owner", "Title", "File"};
        TableColumnMapping[] filterList = SwingUtils.getTableFilterComboboxList(tableColumnModel.getColumnMapplingList(), filters);
        this.columnCombo.setModel(new TableFilterComboboxModel(filterList));
        this.columnCombo.initializeEvent(this.tableRowFilterSet, this.searchField);
        this.columnCombo.setRenderer(new TableFilterComboboxListCellRenderer());
        this.searchField.initializeEvent(this.tableRowFilterSet, this.columnCombo);
        this.downloadSplitPane.setOneTouchExpandable(true);
        this.searchProgress.setStringPainted(false);
        this.listProgress.setStringPainted(false);
    }

    void initWaitingTable() {
        this.waitTable.setAutoResizeMode(0);
        this.waitTableModel = new TableModel();
        this.waitTable.setModel(this.waitTableModel);
        TableColumnModel waitTableColumnModel = new TableColumnModel(new TableCellRenderers());
        waitTableColumnModel.addColumn(new TableColumnMapping("Waiting Time"));
        waitTableColumnModel.addColumn(new TableColumnMapping("Local File Name"));
        waitTableColumnModel.addColumn(new TableColumnMapping("Document Name"));
        waitTableColumnModel.addColumn(new TableColumnMapping("Rev"));
        waitTableColumnModel.addColumn(new TableColumnMapping("File"));
        this.waitTable.setColumnModel(waitTableColumnModel);
        this.waitTable.setCheckAllMouseListener();
        this.waitTableModel.setColumnMap(waitTableColumnModel.getColumnActualMap());
        this.waitRowFilterSet = new TableRowFilterSet(this.waitTable, new TableRowFilter(), new TableRowSorter(this.waitTableModel), new TableColumnAdjuster(this.waitTable, 10), (JLabel)null);
        this.waitTable.setRowHeight(35);
        this.waitTable.setRowSelectionAllowed(true);
        this.waitTable.setColumnSelectionAllowed(true);
    }

    void initUploadTable() {
        this.uploadTable.setAutoResizeMode(0);
        this.uploadTableModel = new TableModel();
        this.uploadTable.setModel(this.uploadTableModel);
        TableColumnModel tableColumnModel = new TableColumnModel(new TableCellRenderers());
        tableColumnModel.addColumn(new TableColumnMapping("Upload Date"));
        tableColumnModel.addColumn(new TableColumnMapping("Local File Name"));
        tableColumnModel.addColumn(new TableColumnMapping("Document Name"));
        tableColumnModel.addColumn(new TableColumnMapping("Rev"));
        tableColumnModel.addColumn(new TableColumnMapping("File"));
        tableColumnModel.addColumn(new TableColumnMapping("State"));
        tableColumnModel.addColumn(new TableColumnMapping("Message"));
        this.uploadTable.setColumnModel(tableColumnModel);
        this.uploadTableModel.setColumnMap(tableColumnModel.getColumnActualMap());
        this.uploadTable.setRowHeight(35);
        this.uploadTable.setRowSelectionAllowed(true);
        this.uploadTable.setColumnSelectionAllowed(true);
        this.uploadRowFilterSet = new TableRowFilterSet(this.uploadTable, new TableRowFilter(), new TableRowSorter(this.uploadTableModel), new TableColumnAdjuster(this.uploadTable, 10), (JLabel)null);
        String[] filters = new String[]{"Local File Name", "Document Name", "Rev", "File", "State"};
        TableColumnMapping[] filterList = SwingUtils.getTableFilterComboboxList(tableColumnModel.getColumnMapplingList(), filters);
        this.uploadCombo.setModel(new TableFilterComboboxModel(filterList));
        this.uploadCombo.initializeEvent(this.uploadRowFilterSet, this.uploadSearchField);
        this.uploadCombo.setRenderer(new TableFilterComboboxListCellRenderer());
        this.uploadSearchField.initializeEvent(this.uploadRowFilterSet, this.uploadCombo);
    }

    private DocumentManager startWorkspaceFileWatchService(String workspace) {
        this.logger.info(workspace + " " + AppConfig.getString("log.workspace.setting"));
        this.workField.setText(workspace);
        JLabel var10000 = this.saveBufferLabel;
        long var10001 = FileWatchConfig.MONITOR_INTERVAL.getTime();
        var10000.setText("*Monitor Interval(" + var10001 / 1000L + ")");
        var10000 = this.waitBufferLabel;
        var10001 = FileWatchConfig.WAIT_BUFFER.getTime();
        var10000.setText("*Wait Buffer(" + var10001 / 1000L + ")");

        try {
            if (this.fileWatchService != null) {
                this.fileWatchService.shutdown();
                this.fileWatchService = null;
            }

            this.fileWatchService = new FileWatchService(this, workspace);
        } catch (Exception var3) {
            JOptionPane.showMessageDialog(this, var3.getMessage(), this.getTitle(), 0);
            var3.printStackTrace();
        }

        return this;
    }

    private void workBtnActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(1);
        int option = chooser.showOpenDialog(this);
        if (option == 0) {
            this.startWorkspaceFileWatchService(chooser.getSelectedFile().getAbsolutePath());
        }

    }

    void download(boolean lock) {
        if (this.fileWatchService == null) {
            JOptionPane.showMessageDialog(this, AppConfig.getString("document.filewatch.notrunning"), this.getTitle(), 2);
        } else {
            Map rowMap = this.tableModel.getRow(this.freezeTable.convertRowIndexToModel(this.freezeTable.getSelectedRow()));
            DownloadService.download(this, new DownloadWorker(this.fileWatchService.getWorkspacePath(), rowMap, lock));
        }
    }

    void lockUnlock(boolean lock) {
        String action = lock ? "Lock" : "Unlock";
        Map rowMap = this.tableModel.getRow(this.freezeTable.convertRowIndexToModel(this.freezeTable.getSelectedRow()));
        FileManager.executors.execute(() -> {
            try {
                Metadata metadata = FileWatchUtil.transMapToMetadata(rowMap);
                JSONObject info = ExceptionUtil.ifResponseErrorThrow(FileHttpUtil.lockUnlock(FileManager.session, metadata, lock));
                rowMap.putAll(FileWatchUtil.transJsonToMap((JSONObject)info.get("data")));
                SwingUtilities.invokeLater(() -> {
                    this.freezeTable.updateUI();
                });
                JOptionPane.showMessageDialog(this, action + " " + AppConfig.getString("success"), action, 1);
            } catch (Exception var9) {
                JOptionPane.showMessageDialog(this, var9.getMessage(), action, 2);
            } finally {
                if (!DownloadService.isDownloading()) {
                    SwingUtils.turnOffProgress(this.listProgress);
                }

            }

        });
    }

    private void searchBtnActionPerformed(ActionEvent e) {
        this.search();
    }

    void search() {
        HashMap<String, String> searchCriteriaMap = this.searchFieldPanel.getSearchCriteria();
        if (this.process.can()) {
            this.process.start(this.searchProgress);
            FileManager var10000 = this.fileManager;
            FileManager.executors.execute(() -> {
                try {
                    TableModel model = (TableModel)this.freezeTable.getModel();
                    model.clearAll();
                    JSONObject resultJson = FileHttpUtil.searchFiles(FileManager.session, searchCriteriaMap);
                    ExceptionUtil.ifResponseErrorThrow(resultJson);
                    JSONArray documents = (JSONArray)resultJson.get("data");
                    Iterator itr = documents.iterator();

                    while(itr.hasNext()) {
                        model.add(FileWatchUtil.transJsonToMap((JSONObject)itr.next()));
                    }

                    SwingUtilities.invokeLater(() -> {
                        this.foundLabel.setText(documents.size() + "  Found");
                        this.tableRowFilterSet.getTableColumnAdjuster().adjustColumns();
                        this.freezeTable.updateUI();
                    });
                } catch (Exception var9) {
                    JOptionPane.showMessageDialog(this, var9.getMessage(), this.getTitle(), 2);
                } finally {
                    this.process.end(this.searchProgress);
                }

            });
        }
    }

    private void initComponents() {
        this.topPanel = new JPanel();
        this.topInnerPanel = new JPanel();
        this.workPanel = new JPanel();
        this.workLabel = new JLabel();
        this.workField = new JTextField();
        this.workBtn = new JButton();
        this.InfoPanel = new JPanel();
        this.InfoPanel1 = new JPanel();
        this.saveBufferLabel = new JLabel();
        this.waitBufferLabel = new JLabel();
        this.centerTabbedPane = new JTabbedPane();
        this.downloadSplitPane = new JSplitPane();
        this.downLeftPanel = new JPanel();
        this.searchPanel = new JPanel();
        this.searchTopPanel = new JPanel();
        this.searchProgressPanel = new JPanel();
        this.searchProgress = new JProgressBar();
        this.searchBtn = new JButton();
        this.searchFieldPanel = new DocumentSearchPanel();
        this.downRightPane = new JPanel();
        this.listPanel = new JPanel();
        this.listTopPanel = new JPanel();
        this.listProgressPanel = new JPanel();
        this.listProgress = new JProgressBar();
        this.listToolbarSearch = new JPanel();
        this.columnCombo = new TableFilterCombobox();
        this.searchField = new TableFilterTextField();
        this.listScrollPanel = new JScrollPane();
        this.freezeTable = new FreezeTable();
        this.foundLabel = new JLabel();
        this.uploadSplitPane = new JSplitPane();
        this.upTopPaneEmptyBorder = new JPanel();
        this.upTopPane = new JPanel();
        this.waitScrollPane = new JScrollPane();
        this.waitTable = new CheckboxTable();
        this.upBottomPaneEmptyBorder = new JPanel();
        this.upBottomPane = new JPanel();
        this.upBottomTop = new JPanel();
        this.uploadCombo = new TableFilterCombobox();
        this.uploadSearchField = new TableFilterTextField();
        this.uploadScrollPane = new JScrollPane();
        this.uploadTable = new JTable();
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        this.topPanel.setLayout(new BorderLayout(15, 0));
        this.topInnerPanel.setLayout(new BorderLayout());
        this.workPanel.setLayout(new FlowLayout(0, 10, 10));
        this.workLabel.setText("작업 공간");
        this.workPanel.add(this.workLabel);
        this.workField.setColumns(25);
        this.workField.setEditable(false);
        this.workField.setPreferredSize(new Dimension(0, 30));
        this.workPanel.add(this.workField);
        this.workBtn.setText("...");
        this.workBtn.setVisible(false);
        this.workBtn.addActionListener((e) -> {
            this.workBtnActionPerformed(e);
        });
        this.workPanel.add(this.workBtn);
        this.topInnerPanel.add(this.workPanel, "Center");
        this.InfoPanel.setLayout(new FlowLayout(2));
        this.InfoPanel1.setBorder(new EmptyBorder(5, 0, 0, 5));
        this.InfoPanel1.setLayout(new FlowLayout(2, 10, 0));
        this.saveBufferLabel.setForeground(new Color(102, 102, 102));
        this.saveBufferLabel.setFont(this.saveBufferLabel.getFont().deriveFont(this.saveBufferLabel.getFont().getStyle() | 1));
        this.InfoPanel1.add(this.saveBufferLabel);
        this.waitBufferLabel.setForeground(new Color(102, 102, 102));
        this.waitBufferLabel.setFont(this.waitBufferLabel.getFont().deriveFont(this.waitBufferLabel.getFont().getStyle() | 1));
        this.InfoPanel1.add(this.waitBufferLabel);
        this.InfoPanel.add(this.InfoPanel1);
        this.topInnerPanel.add(this.InfoPanel, "East");
        this.topPanel.add(this.topInnerPanel, "South");
        contentPane.add(this.topPanel, "North");
        this.downloadSplitPane.setDividerLocation(300);
        this.downLeftPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
        this.downLeftPanel.setLayout(new BorderLayout());
        this.searchPanel.setBorder(new TitledBorder("검색조건"));
        this.searchPanel.setLayout(new BorderLayout(0, 4));
        this.searchTopPanel.setOpaque(false);
        this.searchTopPanel.setPreferredSize(new Dimension(0, 45));
        this.searchTopPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
        this.searchTopPanel.setLayout(new BorderLayout(0, 5));
        this.searchProgressPanel.setPreferredSize(new Dimension(146, 4));
        this.searchProgressPanel.setLayout(new GridLayout());
        this.searchProgress.setPreferredSize(new Dimension(146, 6));
        this.searchProgress.setVisible(false);
        this.searchProgressPanel.add(this.searchProgress);
        this.searchTopPanel.add(this.searchProgressPanel, "North");
        this.searchBtn.setBackground(new Color(66, 162, 218));
        this.searchBtn.setForeground(Color.white);
        this.searchBtn.setText("검색");
        this.searchBtn.addActionListener((e) -> {
            this.searchBtnActionPerformed(e);
        });
        this.searchTopPanel.add(this.searchBtn, "Center");
        this.searchPanel.add(this.searchTopPanel, "North");
        this.searchFieldPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        this.searchPanel.add(this.searchFieldPanel, "Center");
        this.downLeftPanel.add(this.searchPanel, "Center");
        this.downloadSplitPane.setLeftComponent(this.downLeftPanel);
        this.downRightPane.setBorder(new EmptyBorder(10, 5, 5, 5));
        this.downRightPane.setLayout(new BorderLayout());
        this.listPanel.setBorder(new TitledBorder("문서 리스트"));
        this.listPanel.setLayout(new BorderLayout());
        this.listTopPanel.setPreferredSize(new Dimension(0, 50));
        this.listTopPanel.setMinimumSize(new Dimension(240, 45));
        this.listTopPanel.setLayout(new BorderLayout());
        this.listProgressPanel.setPreferredSize(new Dimension(146, 4));
        this.listProgressPanel.setLayout(new GridLayout());
        this.listProgress.setPreferredSize(new Dimension(146, 6));
        this.listProgress.setVisible(false);
        this.listProgressPanel.add(this.listProgress);
        this.listTopPanel.add(this.listProgressPanel, "North");
        this.listToolbarSearch.setLayout(new FlowLayout(1, 5, 4));
        this.columnCombo.setPreferredSize(new Dimension(150, 30));
        this.listToolbarSearch.add(this.columnCombo);
        this.searchField.setColumns(20);
        this.searchField.setPreferredSize(new Dimension(226, 30));
        this.listToolbarSearch.add(this.searchField);
        this.listTopPanel.add(this.listToolbarSearch, "South");
        this.listPanel.add(this.listTopPanel, "North");
        this.listScrollPanel.setViewportView(this.freezeTable);
        this.listPanel.add(this.listScrollPanel, "Center");
        this.foundLabel.setHorizontalAlignment(4);
        this.foundLabel.setText("Found");
        this.listPanel.add(this.foundLabel, "South");
        this.downRightPane.add(this.listPanel, "Center");
        this.downloadSplitPane.setRightComponent(this.downRightPane);
        this.centerTabbedPane.addTab("문서검색", this.downloadSplitPane);
        this.uploadSplitPane.setDividerLocation(200);
        this.uploadSplitPane.setFocusable(false);
        this.uploadSplitPane.setDoubleBuffered(true);
        this.uploadSplitPane.setOrientation(0);
        this.upTopPaneEmptyBorder.setBorder(new EmptyBorder(10, 5, 5, 0));
        this.upTopPaneEmptyBorder.setLayout(new BorderLayout());
        this.upTopPane.setBorder(new TitledBorder("업로드 대기"));
        this.upTopPane.setLayout(new BorderLayout());
        this.waitScrollPane.setViewportView(this.waitTable);
        this.upTopPane.add(this.waitScrollPane, "Center");
        this.upTopPaneEmptyBorder.add(this.upTopPane, "Center");
        this.uploadSplitPane.setTopComponent(this.upTopPaneEmptyBorder);
        this.upBottomPaneEmptyBorder.setBorder(new EmptyBorder(10, 5, 5, 5));
        this.upBottomPaneEmptyBorder.setLayout(new BorderLayout());
        this.upBottomPane.setBorder(new TitledBorder("업로드 결과"));
        this.upBottomPane.setLayout(new BorderLayout());
        this.upBottomTop.setLayout(new FlowLayout());
        this.upBottomTop.add(this.uploadCombo);
        this.uploadSearchField.setColumns(20);
        this.upBottomTop.add(this.uploadSearchField);
        this.upBottomPane.add(this.upBottomTop, "North");
        this.uploadScrollPane.setViewportView(this.uploadTable);
        this.upBottomPane.add(this.uploadScrollPane, "Center");
        this.upBottomPaneEmptyBorder.add(this.upBottomPane, "Center");
        this.uploadSplitPane.setBottomComponent(this.upBottomPaneEmptyBorder);
        this.centerTabbedPane.addTab("자동 업로드", this.uploadSplitPane);
        contentPane.add(this.centerTabbedPane, "Center");
        this.pack();
        this.setLocationRelativeTo(this.getOwner());
        this.process = new ProcessUtil(this);
    }

    class MouseRightButtonPopupMenu extends MouseAdapter {
        MouseRightButtonPopupMenu() {
        }

        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                int clickCount = e.getClickCount();
                if (clickCount == 2) {
                    DocumentManager.this.download(false);
                    e.consume();
                }
            } else if (e.getButton() == 3) {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem downloadItem = new JMenuItem(AppConfig.getString("download"));
                downloadItem.setIcon(AppConfig.getImageIcon("iconActionCheckOut.gif"));
                popupMenu.add(downloadItem);
                downloadItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        DocumentManager.this.download(false);
                    }
                });
                JMenuItem checkoutItem = new JMenuItem(AppConfig.getString("checkout"));
                checkoutItem.setIcon(AppConfig.getImageIcon("iconActionDownload.gif"));
                popupMenu.add(checkoutItem);
                checkoutItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        DocumentManager.this.download(true);
                    }
                });
                JMenuItem lockItem = new JMenuItem(AppConfig.getString("lock"));
                lockItem.setIcon(AppConfig.getImageIcon("iconActionLock.gif"));
                popupMenu.add(lockItem);
                lockItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        DocumentManager.this.lockUnlock(true);
                    }
                });
                JMenuItem unlockItem = new JMenuItem(AppConfig.getString("unlock"));
                unlockItem.setIcon(AppConfig.getImageIcon("iconActionUnlock.gif"));
                popupMenu.add(unlockItem);
                unlockItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        DocumentManager.this.lockUnlock(false);
                    }
                });
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }

        }
    }
}
