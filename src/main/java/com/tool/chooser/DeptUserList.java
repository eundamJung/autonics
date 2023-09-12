//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.chooser;

import com.tool.FileManager;
import com.tool.common.resource.AppConfig;
import com.tool.http.PnOHttpUtil;
import com.tool.ui.combobox.TableFilterCombobox;
import com.tool.ui.combobox.TableFilterComboboxListCellRenderer;
import com.tool.ui.combobox.TableFilterComboboxModel;
import com.tool.ui.table.*;
import com.tool.ui.table.selection.CheckboxTable;
import com.tool.ui.table.selection.CheckboxTableColumnModel;
import com.tool.ui.table.selection.CheckboxTableModel;
import com.tool.ui.table.selection.CheckboxTableRowSorter;
import com.tool.ui.textfield.TableFilterTextField;
import com.tool.util.ExceptionUtil;
import com.tool.util.ProcessUtil;
import com.tool.util.StringUtil;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DeptUserList extends JPanel {
    UserChooser userChooser;
    public ProcessUtil process;
    HashMap<String, List<Map>> memberMap = new HashMap();
    CheckboxTableModel freezeCheckboxTableModel = null;
    TableRowFilterSet tableRowFilterSet = null;
    private JPanel listPanel;
    private JPanel listTopPanel;
    private JPanel listProgressPanle;
    public JProgressBar listProgress;
    private JPanel listToolbarPanel;
    private JPanel listToolbarSearch;
    private TableFilterCombobox columnCombo;
    private TableFilterTextField searchField;
    private JScrollPane listScrollPanel;
    public CheckboxTable freezeCheckboxTable;
    public JLabel foundLabel;

    public DeptUserList(UserChooser userChooser) {
        this.userChooser = userChooser;
        this.initComponents();
        this.initTable();
    }

    private void initTable() {
        this.freezeCheckboxTableModel = new CheckboxTableModel();
        this.freezeCheckboxTable.setModel(this.freezeCheckboxTableModel);
        CheckboxTableColumnModel checkboxTableColumnModel;
        checkboxTableColumnModel = new CheckboxTableColumnModel(new TableCellRenderers());
        checkboxTableColumnModel.addColumn(new TableColumnMapping("User Dept"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("User Id"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("User Name"));
        checkboxTableColumnModel.addColumn(new TableColumnMapping("State"));
        this.freezeCheckboxTable.setColumnModel(checkboxTableColumnModel);
        this.freezeCheckboxTable.setCheckAllMouseListener();
        this.freezeCheckboxTableModel.setColumnMap(checkboxTableColumnModel.getColumnActualMap());
        this.freezeCheckboxTable.setRowHeight(35);
        this.freezeCheckboxTable.setRowSelectionAllowed(true);
        this.tableRowFilterSet = new TableRowFilterSet(this.freezeCheckboxTable, new TableRowFilter(), new CheckboxTableRowSorter(this.freezeCheckboxTableModel), new TableColumnAdjuster(this.freezeCheckboxTable, 10), this.foundLabel);
        this.columnCombo.initializeEvent(this.tableRowFilterSet, this.searchField);
        List<TableColumnMapping> columnList = checkboxTableColumnModel.getColumnMapplingList();
        columnList.remove(0);
        this.columnCombo.setModel(new TableFilterComboboxModel((TableColumnMapping[])columnList.toArray(new TableColumnMapping[0])));
        this.columnCombo.setRenderer(new TableFilterComboboxListCellRenderer());
        this.searchField.initializeEvent(this.tableRowFilterSet, this.columnCombo);
    }

    public void loadData(String orgId) {
        this.process.start();
        FileManager.executors.execute(() -> {
            try {
                this.freezeCheckboxTableModel.clearAll();
                if (!this.memberMap.containsKey(orgId)) {
                    List<Map> list = new ArrayList();
                    JSONObject info = ExceptionUtil.ifResponseErrorThrow(PnOHttpUtil.getMember(FileManager.session, orgId));
                    JSONArray members = (JSONArray)info.get("data");
                    Iterator itr = members.iterator();

                    while(true) {
                        if (!itr.hasNext()) {
                            this.memberMap.put(orgId, list);
                            break;
                        }

                        list.add(this.transJsonToMap((JSONObject)itr.next()));
                    }
                }

                this.freezeCheckboxTableModel.addAll((List)this.memberMap.get(orgId));
                JLabel var10000 = this.foundLabel;
                int var10001 = this.freezeCheckboxTable.getRowCount();
                var10000.setText(var10001 + " " + AppConfig.getString("found"));
                SwingUtilities.invokeLater(() -> {
                    this.freezeCheckboxTable.updateUI();
                    this.tableRowFilterSet.getTableColumnAdjuster().adjustColumns();
                });
            } catch (Exception var9) {
                var9.printStackTrace();
            } finally {
                this.process.end();
            }

        });
    }

    HashMap transJsonToMap(JSONObject json) {
        HashMap rowMap = new HashMap();
        rowMap.put("User Dept", StringUtil.NVL(json.get("User Dept")));
        rowMap.put("User Id", StringUtil.NVL(json.get("name")));
        rowMap.put("User Name", StringUtil.NVL(json.get("Full Name")));
        rowMap.put("State", StringUtil.NVL(json.get("current")));
        return rowMap;
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
        this.listScrollPanel = new JScrollPane();
        this.freezeCheckboxTable = new CheckboxTable();
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
        this.listTopPanel.add(this.listToolbarPanel, "South");
        this.listPanel.add(this.listTopPanel, "First");
        this.listScrollPanel.setViewportView(this.freezeCheckboxTable);
        this.listPanel.add(this.listScrollPanel, "Center");
        this.foundLabel.setHorizontalAlignment(4);
        this.foundLabel.setText("객체");
        this.listPanel.add(this.foundLabel, "Last");
        this.add(this.listPanel, "Center");
        this.process = new ProcessUtil(this, this.listProgress);
    }
}
