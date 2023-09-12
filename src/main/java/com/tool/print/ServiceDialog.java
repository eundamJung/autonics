//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.print;

import java.awt.GraphicsConfiguration;
import java.awt.Window;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.attribute.PrintRequestAttributeSet;

public class ServiceDialog extends sun.print.ServiceDialog {
    public ServiceDialog(GraphicsConfiguration gc, int x, int y, PrintService[] services, int defaultServiceIndex, DocFlavor flavor, PrintRequestAttributeSet attributes, Window window) {
        super(gc, x, y, services, defaultServiceIndex, flavor, attributes, window);
    }

    public ServiceDialog(GraphicsConfiguration gc, int x, int y, PrintService ps, DocFlavor flavor, PrintRequestAttributeSet attributes, Window window) {
        super(gc, x, y, ps, flavor, attributes, window);
    }
}
