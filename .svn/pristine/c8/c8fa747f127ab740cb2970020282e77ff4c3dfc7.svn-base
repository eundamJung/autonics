//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.chooser;

import com.tool.FileManager;
import com.tool.common.resource.AppConfig;
import com.tool.http.PnOHttpUtil;
import com.tool.ui.tree.TreeItem;
import com.tool.ui.tree.TreeNode;
import com.tool.util.ExceptionUtil;
import com.tool.util.ProcessUtil;
import com.tool.util.StringUtil;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DeptTree extends JTree {
    DeptTreePanel treePanel;
    HashMap<String, List<TreeItem>> childMap = new HashMap();

    public DeptTree(DeptTreePanel treePanel) {
        super(new DefaultTreeModel(new TreeNode(), false));
        this.treePanel = treePanel;
        this.setRowHeight(27);
    }

    public void loadData() {
        ProcessUtil process = this.treePanel.process;
        process.start();
        FileManager.executors.execute(() -> {
            try {
                DefaultTreeModel mode = (DefaultTreeModel)this.getModel();
                this.setCellRenderer(new DeptTreeCellRenderer());
                this.getSelectionModel().addTreeSelectionListener(new DeptTreeSelectionListener());
                JSONObject info = ExceptionUtil.ifResponseErrorThrow(PnOHttpUtil.getOrganization(FileManager.session));
                JSONObject data = (JSONObject)info.get("data");
                JSONObject company = (JSONObject)data.get("company");
                TreeItem rootItem = this.transJsonToTreeItem(company);
                this.childMap.put(rootItem.getId(), new ArrayList());
                JSONArray deptList = (JSONArray)data.get("department");
                int size = deptList.size();

                for(int i = 0; i < size; ++i) {
                    JSONObject dept = (JSONObject)deptList.get(i);
                    String deptId = (String)dept.get("id");
                    String parentId = (String)StringUtils.defaultIfEmpty((String)dept.get("to[Company Department].from.id"), (String)dept.get("to[Division].from.id"));
                    ((List)this.childMap.get(parentId)).add(this.transJsonToTreeItem(dept));
                    if (!this.childMap.containsKey(deptId)) {
                        this.childMap.put(deptId, new ArrayList());
                    }
                }

                TreeNode root = (TreeNode)mode.getRoot();
                root.setUserObject(rootItem);
                this.setSelectionRow(0);
                SwingUtilities.invokeLater(() -> {
                    this.updateUI();
                });
            } catch (Exception var16) {
                JOptionPane.showMessageDialog(this, var16.getMessage(), AppConfig.getString("dataLoad"), 2);
            } finally {
                process.end();
            }

        });
    }

    public TreeItem transJsonToTreeItem(JSONObject json) {
        String orgType = StringUtil.NVL(json.get("type"));
        String orgName = StringUtil.NVL(json.get("attribute[Title]"));
        if (StringUtils.isEmpty(orgName) || "Unknown".equals(orgName)) {
            orgName = StringUtil.NVL(json.get("attribute[Organization Name]"));
            if (StringUtils.isEmpty(orgName)) {
                orgName = StringUtil.NVL(json.get("name"));
            }
        }

        String id = StringUtil.NVL(json.get("id"));
        TreeItem item = new TreeItem(orgType, orgName, id, 0, true);
        return item;
    }

    public void filter(String word) {
        TreeNode root = (TreeNode)this.getModel().getRoot();
        root.removeAllChildren();
        if (StringUtils.isEmpty(word)) {
            this.expand(root);
            this.treePanel.userChooser.userListPanel.loadData(((TreeItem)root.getUserObject()).getId());
        } else {
            List<TreeItem> filterList = new ArrayList();
            Iterator itr = this.childMap.keySet().iterator();

            label30:
            while(true) {
                List childItemList;
                do {
                    if (!itr.hasNext()) {
                        this.addChildNode(root, filterList);
                        break label30;
                    }

                    String id = (String)itr.next();
                    childItemList = (List)this.childMap.get(id);
                } while(childItemList == null);

                Iterator var7 = childItemList.iterator();

                while(var7.hasNext()) {
                    TreeItem item = (TreeItem)var7.next();
                    if (item.getName().toLowerCase().contains(word.toLowerCase())) {
                        filterList.add(item);
                    }
                }
            }
        }

        SwingUtilities.invokeLater(() -> {
            this.updateUI();
        });
    }

    private void expand(TreeNode node) {
        TreeItem item = (TreeItem)node.getUserObject();
        List<TreeItem> childItemList = (List)this.childMap.get(item.getId());
        if (childItemList != null) {
            this.addChildNode(node, childItemList);
        }

        this.expandPath(new TreePath(node.getPath()));
    }

    public void expandAll() {
        this.expandAll(new TreePath(this.getModel().getRoot()));
    }

    public void expandAll(TreePath parentNode) {
        TreeNode node = (TreeNode)parentNode.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            Enumeration e = node.children();

            while(e.hasMoreElements()) {
                TreeNode childNode = (TreeNode)e.nextElement();
                this.expand(childNode);
                TreePath path = parentNode.pathByAddingChild(childNode);
                this.expandAll(path);
            }
        }

    }

    private void addChildNode(TreeNode treeNode, List<TreeItem> treeItems) {
        Iterator var3 = treeItems.iterator();

        while(var3.hasNext()) {
            TreeItem treeItem = (TreeItem)var3.next();
            TreeNode childTreeNode = new TreeNode(treeItem);
            childTreeNode.setAllowsChildren(treeItem.isAllowsChildren());
            treeNode.add(childTreeNode);
        }

    }

    class DeptTreeCellRenderer extends DefaultTreeCellRenderer {
        DeptTreeCellRenderer() {
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Component retValue = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            TreeNode node = (TreeNode)value;
            TreeItem item = (TreeItem)node.getUserObject();
            if (item != null) {
                String var10001 = item.getType();
                this.setIcon(AppConfig.getImageIcon("icon" + var10001.replaceAll(" ", "") + ".png"));
                this.setText(item.getName());
            }

            return retValue;
        }
    }

    class DeptTreeSelectionListener implements TreeSelectionListener {
        DeptTreeSelectionListener() {
        }

        public void valueChanged(TreeSelectionEvent e) {
            TreeNode node = (TreeNode)DeptTree.this.getLastSelectedPathComponent();
            DeptTree.this.expand(node);
            DeptTree.this.treePanel.userChooser.userListPanel.loadData(((TreeItem)node.getUserObject()).getId());
        }
    }
}
