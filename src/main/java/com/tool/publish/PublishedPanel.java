//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.publish;

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
import com.tool.util.ExceptionUtil;
import com.tool.util.ProcessUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PublishedPanel extends JPanel implements ActiveLoad {
    PublishManager publishManager;
    private JPanel listPanel;
    private JPanel listTopPanel;
    private JPanel listProgressPanle;
    public JProgressBar listProgress;
    private JPanel listToolbarPanel;
    private JPanel listToolbarSearch;
    private TableFilterCombobox columnCombo;
    private TableFilterTextField searchField;
    private JMenuBar menuBar;
    private JButton acceptButton;
    private JScrollPane listScrollPanel;
    private FreezeCheckboxTable freezeCheckboxTable;
    private JLabel foundLabel;
    private ProcessUtil process;
    CheckboxTableModel freezeCheckboxTableModel = null;
    TableRowFilterSet tableRowFilterSet = null;

    public PublishedPanel(PublishManager publishManager) {
        this.publishManager = publishManager;
        this.initComponents();
        this.initTable();
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
        this.acceptButton = new JButton();
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
        this.acceptButton.setBackground(new Color(242, 242, 242));
        this.acceptButton.addActionListener((e) -> {
            this.acceptButtonActionPerformed(e);
        });
        this.menuBar.add(this.acceptButton);
        this.listToolbarPanel.add(this.menuBar, "West");
        this.listTopPanel.add(this.listToolbarPanel, "South");
        this.listPanel.add(this.listTopPanel, "North");
        this.listScrollPanel.setViewportView(this.freezeCheckboxTable);
        this.listPanel.add(this.listScrollPanel, "Center");
        this.foundLabel.setHorizontalAlignment(4);
        this.foundLabel.setText("객체");
        this.listPanel.add(this.foundLabel, "South");
        this.add(this.listPanel, "Center");
        this.acceptButton.setIcon(AppConfig.getImageIcon("iconActionAccept.png", AppConfig.getString("accept")));
        this.process = new ProcessUtil(this, this.listProgress);
    }

    void initTable() {
        this.freezeCheckboxTableModel = new CheckboxTableModel();
        this.freezeCheckboxTable.setModel(this.freezeCheckboxTableModel);
        this.freezeCheckboxTable.setAutoResizeMode(0);
        CheckboxTableColumnModel checkboxTableColumnModel = new CheckboxTableColumnModel(new TableCellRenderers());
        checkboxTableColumnModel.addColumn(new TableColumnMapping("Drawing No"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("State"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("File"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("Download"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("Print"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("View"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("Published Date"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("Publisher"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("Approver"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("Approver Dept"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("Publish No"));
        this.freezeCheckboxTable.setColumnModel(checkboxTableColumnModel);
        this.freezeCheckboxTable.setCheckAllMouseListener();
        this.freezeCheckboxTableModel.setColumnMap(checkboxTableColumnModel.getColumnActualMap());
        this.freezeCheckboxTable.setRowHeight(35);
        this.freezeCheckboxTable.setRowSelectionAllowed(true);
        this.freezeCheckboxTable.setFixedColumns(2);
        this.tableRowFilterSet = new TableRowFilterSet(this.freezeCheckboxTable,
                                                       new TableRowFilter(),
                                                       new CheckboxTableRowSorter(this.freezeCheckboxTableModel),
                                                       new TableColumnAdjuster(this.freezeCheckboxTable, 10),
                                                       this.foundLabel);
        this.columnCombo.initializeEvent(this.tableRowFilterSet, this.searchField);
        List<TableColumnMapping> columnList = checkboxTableColumnModel.getColumnMapplingList();
        columnList.remove(0);
        this.columnCombo.setModel(new TableFilterComboboxModel((TableColumnMapping[])columnList.toArray(new TableColumnMapping[0])));
        this.columnCombo.setRenderer(new TableFilterComboboxListCellRenderer());
        this.searchField.initializeEvent(this.tableRowFilterSet, this.columnCombo);
        this.listProgress.setStringPainted(false);
    }

    private void acceptButtonActionPerformed(ActionEvent e) {
        if (this.process.can()) {
            List<Map> checkedList = this.freezeCheckboxTable.getCheckedList();
            if (checkedList.size() == 0) {
                JOptionPane.showMessageDialog(this, AppConfig.getString("message.selection.accept"), AppConfig.getString("accept"), 2);
            } else {
                if (JOptionPane.showConfirmDialog(this, AppConfig.getString("confirm.selection.accept"), AppConfig.getString("accept"), 2) == 0) {
                    this.process.start();
                    FileManager.executors.execute(() -> {
                        try {
                            List<String> checkedIds = (List)checkedList.stream().map((m) -> {
                                return (String)m.get("Id");
                            }).collect(Collectors.toList());
                            ExceptionUtil.ifResponseErrorThrow(PublishHttpUtil.accept(FileManager.session, checkedIds));
                            this.freezeCheckboxTableModel.removeAll(checkedList);
                            SwingUtilities.invokeLater(() -> {
                                this.freezeCheckboxTable.clearSelection();
                                JLabel var10000 = this.foundLabel;
                                int var10001 = this.freezeCheckboxTable.getRowCount();
                                var10000.setText(var10001 + " " + AppConfig.getString("found"));
                                this.freezeCheckboxTable.updateUI();
                                this.tableRowFilterSet.getTableColumnAdjuster().adjustColumns();
                            });
                            JOptionPane.showMessageDialog(this, AppConfig.getString("message.complete.accept"), AppConfig.getString("accept"), 1);
                        } catch (Exception var6) {
                            JOptionPane.showMessageDialog(this, var6.getMessage(), AppConfig.getString("accept"), 2);
                        } finally {
                            this.process.end();
                        }

                    });
                }

            }
        }
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

    private void loadData() throws Exception {
        this.freezeCheckboxTable.checkedAll.set(false);
        CheckboxTableModel model = (CheckboxTableModel)this.freezeCheckboxTable.getModel();
        model.clearAll();
        JSONObject resultJson = PublishHttpUtil.search(FileManager.session, "Published");
        ExceptionUtil.ifResponseErrorThrow(resultJson);
        JSONArray documents = (JSONArray)resultJson.get("data");
        Iterator itr = documents.iterator();

        while(itr.hasNext()) {
            model.add(PublishManager.transToMap((JSONObject)itr.next()));
        }

        SwingUtilities.invokeLater(() -> {
            JLabel var10000 = this.foundLabel;
            int var10001 = documents.size();
            var10000.setText(var10001 + " " + AppConfig.getString("found"));
            this.freezeCheckboxTable.updateUI();
            this.tableRowFilterSet.getTableColumnAdjuster().adjustColumns();
        });
    }
}
