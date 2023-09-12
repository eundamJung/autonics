//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.printManager;

import com.tool.FileManager;
import com.tool.common.resource.AppConfig;
import com.tool.http.PublishHttpUtil;
import com.tool.ui.combobox.TableFilterCombobox;
import com.tool.ui.combobox.TableFilterComboboxListCellRenderer;
import com.tool.ui.combobox.TableFilterComboboxModel;
import com.tool.ui.table.*;
import com.tool.ui.table.freeze.FreezeCheckboxTable;
import com.tool.ui.table.selection.CheckboxTableColumnModel;
import com.tool.ui.table.selection.CheckboxTableModel;
import com.tool.ui.table.selection.CheckboxTableRowSorter;
import com.tool.ui.textfield.TableFilterTextField;
import com.tool.util.Constants;
import com.tool.util.ExceptionUtil;
import com.tool.util.ProcessUtil;
import com.tool.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.icepdf.core.pobjects.Document;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

public class PrintListPanel extends JPanel implements ActiveLoad {
    private Logger logger = LoggerFactory.getLogger(PrintListPanel.class);
    public ProcessUtil process;
    public PrintManager printManager;
    private JPanel listPanel;
    private JPanel listTopPanel;
    private JPanel listProgressPanle;
    public JProgressBar listProgress;
    private JPanel listToolbarPanel;
    private JPanel listToolbarSearch;
    private TableFilterCombobox columnCombo;
    private TableFilterTextField searchField;
    private JMenuBar menuBar;
    private JButton printButton;
    private JButton ownerChangeButton;
    private JScrollPane listScrollPanel;
    private FreezeCheckboxTable freezeCheckboxTable;
    private JLabel foundLabel;
    private JFileChooser fileChooser = new JFileChooser();
    CheckboxTableModel freezeCheckboxTableModel = null;
    TableRowFilterSet tableRowFilterSet = null;
    private Path downloadPath;

    public PrintListPanel(PrintManager printManager) {
        this.printManager = printManager;
        this.initComponents();
        this.initTable();
    }

    private List<Map> getSelectRow() {
        List<Map> dataList = new ArrayList();
        int selectedRow = this.freezeCheckboxTable.getSelectedRow();
        if (selectedRow > -1) {
            Map data = this.freezeCheckboxTableModel.getRow(this.freezeCheckboxTable.convertRowIndexToModel(this.freezeCheckboxTable.getSelectedRow()));
            dataList.add(data);
        }

        return dataList;
    }

    private void initComponents() {
        this.listPanel = new JPanel();
        this.listTopPanel = new JPanel();
        this.listProgressPanle = new JPanel();
        this.listProgress = new JProgressBar();
        this.listToolbarPanel = new JPanel();
        this.listToolbarSearch = new JPanel();
        this.columnCombo = new TableFilterCombobox();
        this.searchField = new TableFilterTextField();
        this.menuBar = new JMenuBar();
        this.printButton = new JButton();
        this.ownerChangeButton = new JButton();
        this.listScrollPanel = new JScrollPane();
        this.freezeCheckboxTable = new FreezeCheckboxTable();
        this.foundLabel = new JLabel();
        this.setLayout(new BorderLayout());
        this.listPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
        this.listPanel.setLayout(new BorderLayout());
        this.listTopPanel.setPreferredSize(new Dimension(0, 50));
        this.listTopPanel.setMinimumSize(new Dimension(240, 45));
        this.listTopPanel.setLayout(new BorderLayout());
        this.listProgressPanle.setPreferredSize(new Dimension(146, 4));
        this.listProgressPanle.setLayout(new GridLayout());
        this.listProgress.setPreferredSize(new Dimension(146, 6));
        this.listProgress.setVisible(false);
        this.listProgressPanle.add(this.listProgress);
        this.listTopPanel.add(this.listProgressPanle, "North");
        this.listToolbarPanel.setLayout(new BorderLayout());
        this.listToolbarSearch.setLayout(new FlowLayout(1, 5, 4));
        this.columnCombo.setPreferredSize(new Dimension(150, 30));
        this.listToolbarSearch.add(this.columnCombo);
        this.searchField.setColumns(20);
        this.searchField.setPreferredSize(new Dimension(226, 30));
        this.listToolbarSearch.add(this.searchField);
        this.listToolbarPanel.add(this.listToolbarSearch, "Center");
        this.menuBar.setBackground(new Color(242, 242, 242));
        this.printButton.setBackground(new Color(242, 242, 242));
        this.printButton.addActionListener((e) -> {
            this.printButtonActionPerformed(e);
        });
        this.menuBar.add(this.printButton);
        this.listToolbarPanel.add(this.menuBar, "West");
        this.listTopPanel.add(this.listToolbarPanel, "South");
        this.listPanel.add(this.listTopPanel, "First");
        this.listScrollPanel.setViewportView(this.freezeCheckboxTable);
        this.listPanel.add(this.listScrollPanel, "Center");
        this.foundLabel.setHorizontalAlignment(4);
        this.foundLabel.setText("객체");
        this.listPanel.add(this.foundLabel, "Last");
        this.add(this.listPanel, "Center");
        this.fileChooser.setApproveButtonText(AppConfig.getString("select"));
        this.fileChooser.setFileSelectionMode(1);
        this.printButton.setIcon(AppConfig.getImageIcon("iconActionPrint.png", "Print"));
        this.ownerChangeButton.setIcon(AppConfig.getImageIcon("iconActionTransferOwnership.png", "Transfer Ownership"));
        this.process = new ProcessUtil(this, this.listProgress);
    }

    void initTable() {
        this.freezeCheckboxTableModel = new CheckboxTableModel();
        this.freezeCheckboxTable.setModel(this.freezeCheckboxTableModel);
        this.freezeCheckboxTable.setAutoResizeMode(0);
        CheckboxTableColumnModel checkbgoxTableColumnModel = new CheckboxTableColumnModel(new TableCellRenderers());
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("File Name"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("Revision"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("DBP No"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("Print Due Date"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("Owner"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("View"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("Received Date"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("Published Date"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("Publisher"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("Approver"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("Approver Dept"));
        checkbgoxTableColumnModel.addColumn(new TableColumnMapping("Publish No"));
        this.freezeCheckboxTable.setColumnModel(checkbgoxTableColumnModel);
        this.freezeCheckboxTable.setCheckAllMouseListener();
        this.freezeCheckboxTableModel.setColumnMap(checkbgoxTableColumnModel.getColumnActualMap());
        this.freezeCheckboxTable.setRowHeight(35);
        this.freezeCheckboxTable.setRowSelectionAllowed(true);
        this.freezeCheckboxTable.setFixedColumns(2);
        this.tableRowFilterSet = new TableRowFilterSet(this.freezeCheckboxTable,
                                                       new TableRowFilter(),
                                                       new CheckboxTableRowSorter(this.freezeCheckboxTableModel),
                                                       new TableColumnAdjuster(this.freezeCheckboxTable, 10),
                                                       this.foundLabel);
        this.freezeCheckboxTable.addMouseRightButtonPopupMenu(new MouseRightButtonPopupMenu());
        this.columnCombo.initializeEvent(this.tableRowFilterSet, this.searchField);
        List<TableColumnMapping> columnList = checkbgoxTableColumnModel.getColumnMapplingList();
        columnList.remove(0);
        this.columnCombo.setModel(new TableFilterComboboxModel((TableColumnMapping[])columnList.toArray(new TableColumnMapping[0])));
        this.columnCombo.setRenderer(new TableFilterComboboxListCellRenderer());
        this.searchField.initializeEvent(this.tableRowFilterSet, this.columnCombo);
        this.listProgress.setStringPainted(false);
    }

    public void load() {
        this.freezeCheckboxTable.clearSelection();
        this.process.start();
        FileManager.executors.execute(() -> {
            try {
                this.loadData();
            } catch (Exception var5) {
                JOptionPane.showMessageDialog(this, var5.getMessage(), AppConfig.getString("dataLoad"), 2);
            } finally {
                this.process.end();
            }

        });
    }

    private void viewOrPrintMouseAction(int action) {
        this.viewOrPrintAction(this.getSelectRow(), action);
    }

    private void downButtonActionPerformed(ActionEvent e) {
        this.download(this.freezeCheckboxTable.getCheckedList());
    }

    private void printButtonActionPerformed(ActionEvent e) {
        this.viewOrPrintAction(this.freezeCheckboxTable.getCheckedList(), 1);
    }

    private void viewButtonActionPerformed(ActionEvent e) {
        this.viewOrPrintAction(this.freezeCheckboxTable.getCheckedList(), 2);
    }

    private void viewOrPrintAction(List<Map> dataList, int action) {
        String strAction = "";
        String strServiceUrl = "";
        switch(action) {
            case 1:
                strAction = "print";
                strServiceUrl = Constants.SERVICE_PUBLISH_PRINT;
                break;
            case 2:
                strAction = "view";
                strServiceUrl = Constants.SERVICE_PUBLISH_VIEW;
        }

        if (this.process.can()) {
            if (dataList.size() == 0) {
                JOptionPane.showMessageDialog(this, AppConfig.getString("message.selection." + strAction), AppConfig.getString(strAction), 2);
            } else {
                this.process.start();
                String finalStrServiceUrl = strServiceUrl;
                String finalStrAction     = strAction;
                FileManager.executors.execute(() -> {
                    try {
                        this.viewOrPrintAction(this.getActionPropertiesManager(action), finalStrServiceUrl, dataList);
                        this.writeLogAndIncreaseCount(dataList, action);
                    } catch (Exception var9) {
                        JOptionPane.showMessageDialog(this, var9.getMessage(), AppConfig.getString(finalStrAction), 2);
                    } finally {
                        this.process.end();
                    }

                });
            }
        }
    }

    public void viewOrPrintAction(PropertiesManager properties, String url, List<Map> dataList) throws Exception {
        File file = null;

        try {
            file = Files.createTempFile("FileManager", (String)null).toFile();
            List<String> rowIdList = (List)dataList.stream().map((m) -> {
                return (String)m.get("Id");
            }).collect(Collectors.toList());
            ExceptionUtil.ifResponseErrorThrow(PublishHttpUtil.checkout(FileManager.session, url, rowIdList, file));
            Map dataMap = (Map)dataList.get(0);
            JFrame applicationFrame = new JFrame();
            applicationFrame.setIconImage(AppConfig.getImageIcon("iconActionPDF.gif").getImage());
            applicationFrame.setTitle((String)dataMap.get("File"));
            SwingController controller = new SwingController(applicationFrame, dataMap);
            SwingViewBuilder factory = new SwingViewBuilder(controller, properties);
            JPanel viewerComponentPanel = factory.buildViewerPanel();
            applicationFrame.getContentPane().add(viewerComponentPanel);
            Document document = new Document();
            document.setFile(file.getAbsolutePath());
            controller.openDocument(document, file.getName());
            applicationFrame.pack();
            Dimension frameSize = applicationFrame.getSize();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            applicationFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
            applicationFrame.setVisible(true);
        } catch (Exception var21) {
            throw var21;
        } finally {
            if (file != null && Files.exists(file.toPath(), new LinkOption[0])) {
                try {
                    Files.delete(file.toPath());
                } catch (IOException var20) {
                    var20.printStackTrace();
                }
            }

        }

    }

    private PropertiesManager getActionPropertiesManager(int action) {
        PropertiesManager properties = new PropertiesManager(System.getProperties(), ResourceBundle.getBundle("org.icepdf.ri.resources.MessageBundle"));
        if (action == 1) {
            properties.setBoolean("application.toolbar.show.utility.save", Boolean.FALSE);
            properties.setBoolean("application.toolbar.show.utility.open", Boolean.FALSE);
            properties.setBoolean("application.toolbar.show.utility.search", Boolean.FALSE);
            properties.setBoolean("application.toolbar.show.utility.upane", Boolean.FALSE);
            properties.setBoolean("application.toolbar.show.annotation", Boolean.FALSE);
        } else {
            properties.setBoolean("application.toolbar.show.utility", Boolean.FALSE);
            properties.setBoolean("application.toolbar.show.annotation", Boolean.FALSE);
        }

        return properties;
    }

    private void download(List<Map> dataList) {
        if (this.process.can()) {
            if (dataList.size() == 0) {
                JOptionPane.showMessageDialog(this, AppConfig.getString("message.selection.download"), AppConfig.getString("download"), 2);
            } else {
                final List<String> checkedIds = (List)dataList.stream().map((m) -> {
                    return (String)m.get("Id");
                }).collect(Collectors.toList());
                FutureTask<String> future = new FutureTask(new Callable<String>() {
                    public String call() {
                        String result = null;

                        try {
                            PrintListPanel.this.process.start();
                            ExceptionUtil.ifResponseErrorThrow(PublishHttpUtil.downloadPreCheck(FileManager.session, checkedIds));
                        } catch (Exception var6) {
                            result = var6.getMessage();
                        } finally {
                            PrintListPanel.this.process.end();
                        }

                        return result;
                    }
                });
                FileManager.executors.submit(future);

                try {
                    String message = (String)future.get();
                    if (StringUtils.isNotEmpty(message)) {
                        throw new Exception(message);
                    }
                } catch (Exception var5) {
                    JOptionPane.showMessageDialog(this, var5.getMessage(), AppConfig.getString("download"), 1);
                    return;
                }

                if (this.downloadPath != null) {
                    this.fileChooser.setCurrentDirectory(this.downloadPath.toFile());
                }

                int option = this.fileChooser.showOpenDialog(this);
                if (option == 0) {
                    this.downloadPath = Paths.get(this.fileChooser.getSelectedFile().getAbsolutePath());
                    if (!Files.isWritable(this.downloadPath)) {
                        JOptionPane.showMessageDialog(this, StringUtil.replaceStr(AppConfig.getString("message.folder.permission"), new String[0]), AppConfig.getString("download"), 1);
                        return;
                    }

                    this.process.start();
                    FileManager.executors.execute(() -> {
                        try {
                            ExceptionUtil.ifResponseErrorThrow(PublishHttpUtil.download(FileManager.session, checkedIds, this.fileChooser.getSelectedFile().getAbsolutePath()));
                            this.writeLogAndIncreaseCount(dataList, 0);
                            JOptionPane.showMessageDialog(this, AppConfig.getString("message.complete.download"), AppConfig.getString("download"), 1);
                        } catch (Exception var7) {
                            JOptionPane.showMessageDialog(this, var7.getMessage(), AppConfig.getString("download"), 2);
                        } finally {
                            this.process.end();
                        }

                    });
                }

            }
        }
    }

    private void loadData() throws Exception {
        this.freezeCheckboxTable.checkedAll.set(false);
        CheckboxTableModel model = (CheckboxTableModel)this.freezeCheckboxTable.getModel();
        model.clearAll();
        JSONObject resultJson = ExceptionUtil.ifResponseErrorThrow(PublishHttpUtil.search(FileManager.session, "Received"));
        JSONArray publishList = (JSONArray)resultJson.get("data");
        Iterator itr = publishList.iterator();

        while(itr.hasNext()) {
            model.add(PrintManager.transToMap((JSONObject)itr.next()));
        }

        SwingUtilities.invokeLater(() -> {
            JLabel var10000 = this.foundLabel;
            int var10001 = publishList.size();
            var10000.setText(var10001 + " " + AppConfig.getString("found"));
            this.freezeCheckboxTable.updateUI();
            this.tableRowFilterSet.getTableColumnAdjuster().adjustColumns();
        });
    }

    public void postTransferOwnership(List<Map> dataList) {
        this.freezeCheckboxTable.clearSelection();
        this.freezeCheckboxTableModel.removeAll(dataList);
        SwingUtilities.invokeLater(() -> {
            JLabel var10000 = this.foundLabel;
            int var10001 = this.freezeCheckboxTable.getRowCount();
            var10000.setText(var10001 + " " + AppConfig.getString("found"));
            this.freezeCheckboxTable.updateUI();
            this.tableRowFilterSet.getTableColumnAdjuster().adjustColumns();
        });
    }

    private void writeLogAndIncreaseCount(List<Map> dataList, int action) {
        List<String> rowIdList = (List)dataList.stream().map((m) -> {
            return (String)m.get("Id");
        }).collect(Collectors.toList());
        String strAction;
        switch(action) {
            case 0:
                strAction = "download";
                break;
            case 1:
                strAction = "print";
                break;
            default:
                strAction = "view";
        }

        strAction = AppConfig.getString(strAction);

        try {
            HashMap<String, Map> dataListMap = new HashMap();
            Iterator var6 = dataList.iterator();

            while(var6.hasNext()) {
                Map dataMap = (Map)var6.next();
                dataListMap.put((String)dataMap.get("Id"), dataMap);
            }

            JSONObject resultJson = ExceptionUtil.ifResponseErrorThrow(PublishHttpUtil.writeLogAndIncreaseCount(FileManager.session, rowIdList, action));
            JSONArray publishList = (JSONArray)resultJson.get("data");
            Iterator itr = publishList.iterator();

            while(itr.hasNext()) {
                Map rowMap = PrintManager.transToMap((JSONObject)itr.next());
                String id = (String)rowMap.get("Id");
                if (dataListMap.containsKey(id)) {
                    ((Map)dataListMap.get(id)).putAll(rowMap);
                }
            }

            SwingUtilities.invokeLater(() -> {
                this.freezeCheckboxTable.updateUI();
            });
        } catch (Exception var11) {
            this.logger.error(AppConfig.getString("message.updatecount.fail1"), strAction, var11.getMessage());
            this.logger.error(AppConfig.getString("message.updatecount.fail2"), String.join(", ", rowIdList));
        }

    }

    class MouseRightButtonPopupMenu extends MouseAdapter {
        MouseRightButtonPopupMenu() {
        }

        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                int clickCount = e.getClickCount();
                if (clickCount == 2) {
                    PrintListPanel.this.viewOrPrintMouseAction(2);
                }
            } else if (e.getButton() == 3) {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem downItem = new JMenuItem(AppConfig.getString("download"));
                downItem.setIcon(AppConfig.getScaledImageIcon("iconActionDownload.png", 25, 25));
                downItem.addActionListener((e12) -> {
                    PrintListPanel.this.download(PrintListPanel.this.getSelectRow());
                });
                popupMenu.add(downItem);
                JMenuItem printItem = new JMenuItem(AppConfig.getString("print"));
                printItem.setIcon(AppConfig.getScaledImageIcon("iconActionPrint.png", 25, 25));
                printItem.addActionListener((e1) -> {
                    PrintListPanel.this.viewOrPrintMouseAction(1);
                });
                popupMenu.add(printItem);
                JMenuItem viewItem = new JMenuItem(AppConfig.getString("view"));
                viewItem.setIcon(AppConfig.getScaledImageIcon("iconActionView.png", 25, 25));
                viewItem.addActionListener((e12) -> {
                    PrintListPanel.this.viewOrPrintMouseAction(2);
                });
                popupMenu.add(viewItem);

                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }

        }
    }
}
