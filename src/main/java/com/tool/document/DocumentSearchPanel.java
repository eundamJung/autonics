//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.document;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.jdatepicker.JDatePicker;

public class DocumentSearchPanel extends JPanel {
    DocumentManager documentManager;
    private JPanel searchGrid;
    private JPanel nameBorder;
    private JTextField nameFiled;
    private JPanel titleBorder;
    private JTextField titleField;
    private JPanel fileNameBorder;
    private JTextField fileNameField;
    private JPanel originatedFromBorder;
    private JDatePicker originatedFrom;
    private JPanel originatedToBorder;
    private JDatePicker originatedTo;
    private JPanel currentBorder;
    private JComboBox<String> currentCombo;
    private JPanel ownerBorder;
    private JTextField ownerField;

    public DocumentSearchPanel() {
        this.initComponents();
        this.initDatePicker(this);
    }

    private void initDatePicker(JPanel panel) {
        Component[] var2 = panel.getComponents();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Component component = var2[var4];
            if (component instanceof JPanel) {
                this.initDatePicker((JPanel)component);
            } else if (component instanceof JDatePicker) {
                JDatePicker datePicker = (JDatePicker)component;
                datePicker.getFormattedTextField().setPreferredSize(new Dimension(49, 30));
                datePicker.getButton().setPreferredSize(new Dimension(30, 30));
            }
        }

    }

    private void nameFiledKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            this.documentManager.search();
        }

    }

    private void titleFieldKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            this.documentManager.search();
        }

    }

    private void fileNameFieldKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            this.documentManager.search();
        }

    }

    private void originatedFromKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            this.documentManager.search();
        }

    }

    private void originatedToKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            this.documentManager.search();
        }

    }

    private void initComponents() {
        this.searchGrid = new JPanel();
        this.nameBorder = new JPanel();
        this.nameFiled = new JTextField();
        this.titleBorder = new JPanel();
        this.titleField = new JTextField();
        this.fileNameBorder = new JPanel();
        this.fileNameField = new JTextField();
        this.originatedFromBorder = new JPanel();
        this.originatedFrom = new JDatePicker();
        this.originatedToBorder = new JPanel();
        this.originatedTo = new JDatePicker();
        this.currentBorder = new JPanel();
        this.currentCombo = new JComboBox();
        this.ownerBorder = new JPanel();
        this.ownerField = new JTextField();
        this.setLayout(new BorderLayout());
        this.searchGrid.setLayout(new GridLayout(7, 1, 5, 5));
        this.nameBorder.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "문서번호"));
        this.nameBorder.setLayout(new GridLayout());
        this.nameFiled.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                DocumentSearchPanel.this.nameFiledKeyPressed(e);
            }
        });
        this.nameBorder.add(this.nameFiled);
        this.searchGrid.add(this.nameBorder);
        this.titleBorder.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "제목"));
        this.titleBorder.setLayout(new GridLayout());
        this.titleField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                DocumentSearchPanel.this.titleFieldKeyPressed(e);
            }
        });
        this.titleBorder.add(this.titleField);
        this.searchGrid.add(this.titleBorder);
        this.fileNameBorder.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "파일"));
        this.fileNameBorder.setLayout(new GridLayout());
        this.fileNameField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                DocumentSearchPanel.this.fileNameFieldKeyPressed(e);
            }
        });
        this.fileNameBorder.add(this.fileNameField);
        this.searchGrid.add(this.fileNameBorder);
        this.originatedFromBorder.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "작성일 From"));
        this.originatedFromBorder.setLayout(new GridLayout());
        this.originatedFrom.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                DocumentSearchPanel.this.originatedFromKeyPressed(e);
            }
        });
        this.originatedFromBorder.add(this.originatedFrom);
        this.searchGrid.add(this.originatedFromBorder);
        this.originatedToBorder.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "작성일 To"));
        this.originatedToBorder.setLayout(new GridLayout());
        this.originatedTo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                DocumentSearchPanel.this.originatedToKeyPressed(e);
            }
        });
        this.originatedToBorder.add(this.originatedTo);
        this.searchGrid.add(this.originatedToBorder);
        this.currentBorder.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "State"));
        this.currentBorder.setVisible(false);
        this.currentBorder.setLayout(new GridLayout());
        this.currentCombo.setPreferredSize(new Dimension(82, 30));
        this.currentCombo.setModel(new DefaultComboBoxModel(new String[]{" ", "PRIVATE", "IN_WORK", "FROZEN", "RELEASED"}));
        this.currentBorder.add(this.currentCombo);
        this.searchGrid.add(this.currentBorder);
        this.ownerBorder.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "Owner"));
        this.ownerBorder.setVisible(false);
        this.ownerBorder.setLayout(new GridLayout());
        this.ownerField.setVisible(false);
        this.ownerBorder.add(this.ownerField);
        this.searchGrid.add(this.ownerBorder);
        this.add(this.searchGrid, "North");
    }

    public HashMap<String, String> getSearchCriteria() {
        HashMap<String, String> searchCriteriaMap = new HashMap();
        searchCriteriaMap.put("name", this.nameFiled.getText().trim());
        searchCriteriaMap.put("owner", this.ownerField.getText().trim());
        searchCriteriaMap.put("state", this.currentCombo.getSelectedItem().toString());
        searchCriteriaMap.put("title", this.titleField.getText().trim());
        searchCriteriaMap.put("fileName", this.fileNameField.getText());
        searchCriteriaMap.put("originatedFrom", getDatePickerValue(this.originatedFrom));
        searchCriteriaMap.put("originatedTo", getDatePickerValue(this.originatedTo));
        return searchCriteriaMap;
    }

    public static String getDatePickerValue(JDatePicker datePicker) {
        Calendar calendar = (Calendar)datePicker.getModel().getValue();
        return calendar == null ? "" : (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    public void setDocumentManager(DocumentManager documentManager) {
        this.documentManager = documentManager;
    }
}
