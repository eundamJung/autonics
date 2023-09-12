//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.print;

import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.DialogOwner;
import javax.print.attribute.standard.Fidelity;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import sun.print.SunAlternateMedia;

public class ServiceUI {
    public ServiceUI() {
    }

    public static PrintService printDialog(GraphicsConfiguration gc, int x, int y, PrintService[] services, PrintService defaultService, DocFlavor flavor, PrintRequestAttributeSet attributes) throws HeadlessException {
        int defaultIndex = -1;
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        } else if (services != null && services.length != 0) {
            if (attributes == null) {
                throw new IllegalArgumentException("attributes must be non-null");
            } else {
                if (defaultService != null) {
                    for(int i = 0; i < services.length; ++i) {
                        if (services[i].equals(defaultService)) {
                            defaultIndex = i;
                            break;
                        }
                    }

                    if (defaultIndex < 0) {
                        throw new IllegalArgumentException("services must contain defaultService");
                    }
                } else {
                    defaultIndex = 0;
                }

                DialogOwner dlgOwner = (DialogOwner)attributes.get(DialogOwner.class);
                Window owner = dlgOwner != null ? dlgOwner.getOwner() : null;
                boolean setOnTop = dlgOwner != null && owner == null;
                Rectangle gcBounds = gc == null ? GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds() : gc.getBounds();
                x += gcBounds.x;
                y += gcBounds.y;
                final ServiceDialog dialog = new ServiceDialog(gc, x, y, services, defaultIndex, flavor, attributes, owner);
                ArrayList<JTabbedPane> tabbedPanes = findTabbedPane(dialog, new ArrayList());
                if (tabbedPanes.size() > 0) {
                    try {
                        JTabbedPane tabbedPane = (JTabbedPane)tabbedPanes.get(0);
                        Component component = tabbedPane.getComponents()[0];
                        ArrayList<JComboBox> comboBoxes = findComboBox((Container)component, new ArrayList());
                        Iterator var17 = comboBoxes.iterator();

                        while(var17.hasNext()) {
                            JComboBox comboBox = (JComboBox)var17.next();
                            comboBox.addFocusListener(new FocusListener() {
                                public void focusGained(FocusEvent e) {
                                    ServiceUI.setDisabled(dialog);
                                }

                                public void focusLost(FocusEvent e) {
                                    ServiceUI.setDisabled(dialog);
                                }
                            });
                        }
                    } catch (Exception var21) {
                    }
                }

                setDisabled(dialog);
                if (setOnTop) {
                    try {
                        dialog.setAlwaysOnTop(true);
                    } catch (SecurityException var20) {
                    }
                }

                Rectangle dlgBounds = dialog.getBounds();
                if (!gcBounds.contains(dlgBounds)) {
                    if (dlgBounds.x + dlgBounds.width > gcBounds.x + gcBounds.width) {
                        if (gcBounds.x + gcBounds.width - dlgBounds.width > gcBounds.x) {
                            x = gcBounds.x + gcBounds.width - dlgBounds.width;
                        } else {
                            x = gcBounds.x;
                        }
                    }

                    if (dlgBounds.y + dlgBounds.height > gcBounds.y + gcBounds.height) {
                        if (gcBounds.y + gcBounds.height - dlgBounds.height > gcBounds.y) {
                            y = gcBounds.y + gcBounds.height - dlgBounds.height;
                        } else {
                            y = gcBounds.y;
                        }
                    }

                    dialog.setBounds(x, y, dlgBounds.width, dlgBounds.height);
                }

                dialog.show();
                if (dialog.getStatus() == 1) {
                    PrintRequestAttributeSet newas = dialog.getAttributes();
                    Class<?> dstCategory = Destination.class;
                    Class<?> amCategory = SunAlternateMedia.class;
                    Class<?> fdCategory = Fidelity.class;
                    if (attributes.containsKey(dstCategory) && !newas.containsKey(dstCategory)) {
                        attributes.remove(dstCategory);
                    }

                    if (attributes.containsKey(amCategory) && !newas.containsKey(amCategory)) {
                        attributes.remove(amCategory);
                    }

                    attributes.addAll(newas);
                    Fidelity fd = (Fidelity)attributes.get(fdCategory);
                    if (fd != null && fd == Fidelity.FIDELITY_TRUE) {
                        removeUnsupportedAttributes(dialog.getPrintService(), flavor, attributes);
                    }
                }

                return dialog.getPrintService();
            }
        } else {
            throw new IllegalArgumentException("services must be non-null and non-empty");
        }
    }

    private static void removeUnsupportedAttributes(PrintService ps, DocFlavor flavor, AttributeSet aset) {
        AttributeSet asUnsupported = ps.getUnsupportedAttributes(flavor, aset);
        if (asUnsupported != null) {
            Attribute[] usAttrs = asUnsupported.toArray();

            for(int i = 0; i < usAttrs.length; ++i) {
                Class<? extends Attribute> category = usAttrs[i].getCategory();
                if (ps.isAttributeCategorySupported(category)) {
                    Attribute attr = (Attribute)ps.getDefaultAttributeValue(category);
                    if (attr != null) {
                        aset.add(attr);
                    } else {
                        aset.remove(category);
                    }
                } else {
                    aset.remove(category);
                }
            }
        }

    }

    private static ArrayList<JTabbedPane> findTabbedPane(Container container, ArrayList list) {
        Component[] var2 = container.getComponents();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Component component = var2[var4];
            if (component instanceof JTabbedPane) {
                list.add(component);
            } else if (component instanceof Container) {
                findTabbedPane((Container)component, list);
            }
        }

        return list;
    }

    private static ArrayList<JComboBox> findComboBox(Container container, ArrayList list) {
        Component[] var2 = container.getComponents();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Component component = var2[var4];
            if (component instanceof JComboBox) {
                list.add(component);
            } else if (component instanceof Container) {
                findComboBox((Container)component, list);
            }
        }

        return list;
    }

    private static ArrayList<JButton> findButton(Container container, ArrayList list) {
        Component[] var2 = container.getComponents();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Component component = var2[var4];
            if (component instanceof JButton) {
                list.add(component);
            } else if (component instanceof Container) {
                findButton((Container)component, list);
            }
        }

        return list;
    }

    private static ArrayList<JCheckBox> findCheckBox(Container container, ArrayList list) {
        Component[] var2 = container.getComponents();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Component component = var2[var4];
            if (component instanceof JCheckBox) {
                list.add(component);
            } else if (component instanceof Container) {
                findCheckBox((Container)component, list);
            }
        }

        return list;
    }

    private static ArrayList<JSpinner> findSpinner(Container container, ArrayList list) {
        Component[] var2 = container.getComponents();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Component component = var2[var4];
            if (component instanceof JSpinner) {
                list.add(component);
            } else if (component instanceof Container) {
                findSpinner((Container)component, list);
            }
        }

        return list;
    }

    private static void setDisabled(ServiceDialog dialog) {
        ArrayList<JTabbedPane> tabbedPanes = findTabbedPane(dialog, new ArrayList());
        if (tabbedPanes.size() > 0) {
            try {
                JTabbedPane tabbedPane = (JTabbedPane)tabbedPanes.get(0);
                Component component = tabbedPane.getComponents()[0];
                ArrayList<JButton> buttons = findButton((Container)component, new ArrayList());
                Iterator var5 = buttons.iterator();

                while(var5.hasNext()) {
                    JButton button = (JButton)var5.next();
                    if (button.getText().equals(ServiceDialog.getMsg("button.properties"))) {
                        button.setEnabled(false);
                        break;
                    }
                }

                ArrayList<JCheckBox> checkboxies = findCheckBox((Container)component, new ArrayList());
                Iterator var11 = checkboxies.iterator();

                while(var11.hasNext()) {
                    JCheckBox checkbox = (JCheckBox)var11.next();
                    if (checkbox.getText().equals(ServiceDialog.getMsg("checkbox.printtofile"))) {
                        checkbox.setEnabled(false);
                        break;
                    }
                }

                ArrayList<JSpinner> spinners = findSpinner((Container)component, new ArrayList());
                Iterator var13 = spinners.iterator();

                while(var13.hasNext()) {
                    JSpinner spinner = (JSpinner)var13.next();
                    spinner.setEnabled(false);
                }
            } catch (Exception var9) {
            }
        }

    }
}
