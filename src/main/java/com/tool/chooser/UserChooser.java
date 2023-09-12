//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.chooser;

import com.tool.FileManager;
import com.tool.common.resource.AppConfig;
import com.tool.http.PublishHttpUtil;
import com.tool.publish.ReceivedPanel;
import com.tool.ui.common.SwingUtils;
import com.tool.util.ExceptionUtil;
import com.tool.util.ProcessUtil;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class UserChooser extends JDialog {
    ReceivedPanel receivedPanel;
    List<Map> dataList;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel mainPanel;
    private JSplitPane splitPane;
    public DeptTreePanel treePanel;
    public DeptUserList userListPanel;
    private JPanel buttonBar;
    public JButton okButton;
    public JButton cancelButton;

    public UserChooser(String titie, ReceivedPanel receivedpanel, List<Map> dataList) {
        super(receivedpanel.publishManager, true);
        this.receivedPanel = receivedpanel;
        this.dataList = dataList;
        this.initComponents();
        this.initEvent();
        this.setTitle(titie);
        this.initialze();
    }

    void initEvent() {
        Component[] components = (Component[])SwingUtils.getChildAllComponents(this, new ArrayList()).toArray(new Component[0]);
        KeyAdapter keyAdapter = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    if (!UserChooser.this.userListPanel.process.can()) {
                        return;
                    }

                    UserChooser.this.dispose();
                }

            }
        };
        Component[] var3 = components;
        int var4 = components.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Component component = var3[var5];
            component.addKeyListener(keyAdapter);
        }

        this.cancelButton.addActionListener((e) -> {
            if (this.userListPanel.process.can()) {
                this.dispose();
            }
        });
        this.okButton.addActionListener((e) -> {
            ProcessUtil userListProcess = this.userListPanel.process;
            if (userListProcess.can()) {
                List userList = this.userListPanel.freezeCheckboxTable.getCheckedList();
                if (userList.size() == 0) {
                    JOptionPane.showMessageDialog(this, AppConfig.getString("message.selection.tansowner.chooser"), AppConfig.getString("tansowner"), 2);
                } else if (userList.size() > 1) {
                    JOptionPane.showMessageDialog(this, AppConfig.getString("message.selection.tansowner.chooser.onlyone"), AppConfig.getString("tansowner"), 2);
                } else {
                    Map userMap = (Map)userList.get(0);
                    String userId = (String)userMap.get("User Id");
                    if (FileManager.session.getId().equals(userId)) {
                        JOptionPane.showMessageDialog(this, AppConfig.getString("message.selection.tansowner.chooser.same"), AppConfig.getString("tansowner"), 2);
                    } else {
                        if (JOptionPane.showConfirmDialog(this, AppConfig.getString("confirm.selection.tansowner"), AppConfig.getString("tansowner"), 2) == 0) {
                            userListProcess.start();
                            FileManager.executors.execute(() -> {
                                try {
                                    ExceptionUtil.ifResponseErrorThrow(PublishHttpUtil.transferOwnership(FileManager.session, (List)this.dataList.stream().map((m) -> {
                                        return (String)m.get("Id");
                                    }).collect(Collectors.toList()), userId));
                                    this.receivedPanel.postTransferOwnership(this.dataList);
                                    Thread.sleep(200L);
                                    this.dispose();
                                } catch (Exception var7) {
                                    JOptionPane.showMessageDialog(this, var7.getMessage(), AppConfig.getString("tansowner"), 1);
                                } finally {
                                    userListProcess.end();
                                }

                            });
                        }

                    }
                }
            }
        });
    }

    void initialze() {
        this.setSize(800, 500);
        this.treePanel.tree.loadData();
        this.setVisible(true);
    }

    private void createUIComponents() {
    }

    private void initComponents() {
        this.dialogPane = new JPanel();
        this.contentPanel = new JPanel();
        this.mainPanel = new JPanel();
        this.splitPane = new JSplitPane();
        this.treePanel = new DeptTreePanel(this);
        this.userListPanel = new DeptUserList(this);
        this.buttonBar = new JPanel();
        this.okButton = new JButton();
        this.cancelButton = new JButton();
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        this.dialogPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.dialogPane.setLayout(new BorderLayout());
        this.contentPanel.setLayout(new BorderLayout());
        this.mainPanel.setBorder(new TitledBorder(""));
        this.mainPanel.setLayout(new BorderLayout());
        this.splitPane.setDividerLocation(250);
        this.splitPane.setDividerSize(5);
        this.splitPane.setLeftComponent(this.treePanel);
        this.splitPane.setRightComponent(this.userListPanel);
        this.mainPanel.add(this.splitPane, "Center");
        this.contentPanel.add(this.mainPanel, "Center");
        this.dialogPane.add(this.contentPanel, "Center");
        this.buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
        this.buttonBar.setLayout(new GridBagLayout());
        ((GridBagLayout)this.buttonBar.getLayout()).columnWidths = new int[]{0, 85, 80};
        ((GridBagLayout)this.buttonBar.getLayout()).columnWeights = new double[]{1.0D, 0.0D, 0.0D};
        this.okButton.setText("OK");
        this.buttonBar.add(this.okButton, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 5), 0, 0));
        this.cancelButton.setText("Cancel");
        this.buttonBar.add(this.cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        this.dialogPane.add(this.buttonBar, "South");
        contentPane.add(this.dialogPane, "Center");
        this.pack();
        this.setLocationRelativeTo(this.getOwner());
    }
}
