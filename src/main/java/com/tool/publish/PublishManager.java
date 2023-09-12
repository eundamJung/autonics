//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.publish;

import com.tool.FileManager;
import com.tool.common.resource.AppConfig;
import com.tool.util.StringUtil;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import org.json.simple.JSONObject;

public class PublishManager extends JFrame {
    FileManager fileManager;
    private int tabIndex;
    private JTabbedPane tabbedPane;
    private PublishedPanel publishedPanel;
    private ReceivedPanel receivedPanel;
    private ArchivedPanel archivedPanel;

    public PublishManager(FileManager fileManager, String title) {
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
                PublishManager.this.setVisible(false);
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
        this.publishedPanel = new PublishedPanel(this);
        this.receivedPanel = new ReceivedPanel(this);
        this.archivedPanel = new ArchivedPanel(this);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        this.tabbedPane.addChangeListener((e) -> {
            this.tabbedPaneStateChanged(e);
        });
        this.tabbedPane.addTab("Published", this.publishedPanel);
        this.tabbedPane.addTab("Received", this.receivedPanel);
        this.tabbedPane.addTab("Archived", this.archivedPanel);
        contentPane.add(this.tabbedPane, "Center");
        this.pack();
        this.setLocationRelativeTo(this.getOwner());
    }

    static Map transToMap(JSONObject json) {
        Map data = new HashMap();
        data.put("Drawing No", StringUtil.NVL(json.get("Drawing No")));
        data.put("State", StringUtil.NVL(json.get("State")));
        data.put("File", StringUtil.NVL(json.get("File")));
        data.put("Download", StringUtil.NVL(json.get("Download")));
        data.put("Print", StringUtil.NVL(json.get("Print")));
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
