//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.chooser;

import com.tool.ui.textfield.TableFilterTextField;
import com.tool.util.ProcessUtil;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class DeptTreePanel extends JPanel {
    UserChooser userChooser;
    ProcessUtil process;
    private JPanel listPanel;
    private JPanel listTopPanel;
    private JPanel listProgressPanle;
    public JProgressBar listProgress;
    private JPanel listToolbarPanel;
    private JPanel listToolbarSearch;
    private TableFilterTextField filterField;
    private JButton expandAllBtn;
    private JScrollPane listScrollPanel;
    public DeptTree tree;

    public DeptTreePanel(UserChooser userChooser) {
        this.initComponents();
        this.initEvent();
        this.userChooser = userChooser;
    }

    private void expandAllBtnActionPerformed(ActionEvent e) {
        this.tree.expandAll();
    }

    private void initComponents() {
        this.listPanel = new JPanel();
        this.listTopPanel = new JPanel();
        this.listProgressPanle = new JPanel();
        this.listProgress = new JProgressBar();
        this.listToolbarPanel = new JPanel();
        this.listToolbarSearch = new JPanel();
        this.filterField = new TableFilterTextField();
        this.expandAllBtn = new JButton();
        this.listScrollPanel = new JScrollPane();
        this.tree = new DeptTree(this);
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
        this.listToolbarSearch.setLayout(new BorderLayout());
        this.filterField.setColumns(20);
        this.filterField.setPreferredSize(new Dimension(226, 30));
        this.listToolbarSearch.add(this.filterField, "Center");
        this.expandAllBtn.setText("Expand All");
        this.expandAllBtn.addActionListener((e) -> {
            this.expandAllBtnActionPerformed(e);
        });
        this.listToolbarSearch.add(this.expandAllBtn, "East");
        this.listToolbarPanel.add(this.listToolbarSearch, "North");
        this.listTopPanel.add(this.listToolbarPanel, "South");
        this.listPanel.add(this.listTopPanel, "First");
        this.listScrollPanel.setViewportView(this.tree);
        this.listPanel.add(this.listScrollPanel, "Center");
        this.add(this.listPanel, "Center");
        this.process = new ProcessUtil(this, this.listProgress);
    }

    public void initEvent() {
        this.filterField.getDocument().addDocumentListener(new DocumentListener() {
            protected void changeFilter(DocumentEvent event) {
                Document document = event.getDocument();

                try {
                    DeptTreePanel.this.tree.filter(document.getText(0, document.getLength()).trim());
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            }

            public void changedUpdate(DocumentEvent e) {
                this.changeFilter(e);
            }

            public void insertUpdate(DocumentEvent e) {
                this.changeFilter(e);
            }

            public void removeUpdate(DocumentEvent e) {
                this.changeFilter(e);
            }
        });
    }
}
