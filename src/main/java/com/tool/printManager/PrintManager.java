//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.printManager;

import com.tool.FileManager;
import com.tool.common.resource.AppConfig;
import com.tool.publish.PublishedPanel;
import com.tool.util.StringUtil;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class PrintManager extends JFrame {
    FileManager fileManager;
    private int tabIndex;
    private JTabbedPane tabbedPane;
    private PrintListPanel printListPanel;

    public PrintManager(FileManager fileManager, String title) {
        this.fileManager = fileManager;
        this.initComponents();
        this.setTitle(title);
        this.setDefaultCloseOperation(0);
        this.setSize(1200, 700);
        Dimension frameSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        Image image = AppConfig.getImageIcon("iconSync.png").getImage();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                PrintManager.this.setVisible(false);
            }
        });
        this.setIconImage(image);
        this.setDefaultCloseOperation(0);
        this.setVisible(true);
    }

    private void tabbedPaneStateChanged(ChangeEvent e) {
        JTabbedPane tab = (JTabbedPane)e.getSource();
        Component component = tab.getComponent(tab.getSelectedIndex());
        if (component instanceof ActiveLoad) {
            ((ActiveLoad)component).load();
        }

    }

    private void initComponents() {
        this.tabbedPane = new JTabbedPane();
        this.printListPanel = new PrintListPanel(this);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        this.tabbedPane.addChangeListener((e) -> {
            this.tabbedPaneStateChanged(e);
        });
        this.tabbedPane.addTab("PrintList", this.printListPanel);
        contentPane.add(this.tabbedPane, "Center");
        this.pack();
        this.setLocationRelativeTo(this.getOwner());
    }

    static Map transToMap(JSONObject json) {
        Map data = new HashMap();
        data.put("File Name", StringUtil.NVL(json.get("State")));
        data.put("Revision", StringUtil.NVL(json.get("State")));
        data.put("DBP No", StringUtil.NVL(json.get("File")));
        data.put("Print Due Date", StringUtil.NVL(json.get("Download")));
        data.put("Owner", StringUtil.NVL(json.get("Print")));
        data.put("View", StringUtil.NVL(json.get("View")));
        data.put("Published Date", StringUtil.NVL(json.get("Published Date")));
        data.put("Publisher", StringUtil.NVL(json.get("Publisher")));
        data.put("Publish No", StringUtil.NVL(json.get("Publish No")));
        data.put("Received Date", StringUtil.NVL(json.get("Received Date")));
        data.put("Approver", StringUtil.NVL(json.get("Approver")));
        data.put("Approver Dept", StringUtil.NVL(json.get("Approver Dept")));
        data.put("Id", StringUtil.NVL(json.get("Id")));
        return data;
    }
}
