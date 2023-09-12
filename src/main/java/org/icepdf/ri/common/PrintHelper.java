//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.icepdf.ri.common;

import com.tool.FileManager;
import com.tool.http.PublishHttpUtil;
import com.tool.print.ServiceUI;
import com.tool.util.ExceptionUtil;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Window;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.print.CancelablePrintJob;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUIFactory;
import javax.print.SimpleDoc;
import javax.print.DocFlavor.SERVICE_FORMATTED;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PageRanges;
import javax.print.attribute.standard.PrintQuality;
import javax.print.event.PrintServiceAttributeListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.icepdf.core.pobjects.PDimension;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.pobjects.PageTree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PrintHelper implements Printable {
    private static final Logger logger = Logger.getLogger(PrintHelper.class.toString());
    private PageTree pageTree;
    private Container container;
    private float userRotation;
    private boolean printFitToMargin;
    private int printingCurrentPage;
    private int totalPagesToPrint;
    private boolean paintAnnotation;
    private boolean paintSearchHighlight;
    private static PrintService[] services;
    private PrintService printService;
    private HashDocAttributeSet docAttributeSet;
    private HashPrintRequestAttributeSet printRequestAttributeSet;
    private SwingController swingController;

    public PrintHelper(Container container, PageTree pageTree, int rotation) {
        this(container, pageTree, (float)rotation, MediaSizeName.NA_LETTER, PrintQuality.DRAFT);
    }

    public PrintHelper(Container container, PageTree pageTree, float rotation, MediaSizeName paperSizeName, PrintQuality printQuality) {
        this.paintAnnotation = true;
        this.paintSearchHighlight = true;
        this.container = container;
        this.pageTree = pageTree;
        this.userRotation = rotation;
        services = this.lookForPrintServices();
        this.printRequestAttributeSet = new HashPrintRequestAttributeSet();
        this.docAttributeSet = new HashDocAttributeSet();
        this.printRequestAttributeSet.add(printQuality);
        this.printRequestAttributeSet.add(paperSizeName);
        this.docAttributeSet.add(paperSizeName);
        MediaSize mediaSize = MediaSize.getMediaSizeForName(paperSizeName);
        float[] size = mediaSize.getSize(25400);
        this.printRequestAttributeSet.add(new MediaPrintableArea(0.0F, 0.0F, size[0], size[1], 25400));
        this.docAttributeSet.add(new MediaPrintableArea(0.0F, 0.0F, size[0], size[1], 25400));
        this.setupPrintService(0, this.pageTree.getNumberOfPages(), 1, true, false);
        if (logger.isLoggable(Level.FINE)) {
            Logger var10000 = logger;
            String var10001 = paperSizeName.getName();
            var10000.fine("Paper Size: " + var10001 + " " + size[0] + " x " + size[1]);
        }

    }

    public PrintHelper(Container container, PageTree pageTree, float userRotation, HashDocAttributeSet docAttributeSet, HashPrintRequestAttributeSet printRequestAttributeSet) {
        this.paintAnnotation = true;
        this.paintSearchHighlight = true;
        this.container = container;
        this.pageTree = pageTree;
        this.userRotation = userRotation;
        this.docAttributeSet = docAttributeSet;
        this.printRequestAttributeSet = printRequestAttributeSet;
        services = this.lookForPrintServices();
        this.setupPrintService(0, this.pageTree.getNumberOfPages(), 1, true, false);
    }

    public boolean setupPrintService(int startPage, int endPage, int copies, boolean shrinkToPrintableArea, boolean showPrintDialog) {
        this.printFitToMargin = shrinkToPrintableArea;
        this.printRequestAttributeSet.add(new PageRanges(startPage + 1, endPage + 1));
        this.printRequestAttributeSet.add(new Copies(copies));
        if (showPrintDialog) {
            this.printService = this.getSetupDialog();
            return this.printService != null;
        } else {
            return true;
        }
    }

    public void setupPrintService(PrintService printService, int startPage, int endPage, int copies, boolean shrinkToPrintableArea) {
        this.printFitToMargin = shrinkToPrintableArea;
        this.printRequestAttributeSet.add(new PageRanges(startPage + 1, endPage + 1));
        this.printRequestAttributeSet.add(new Copies(copies));
        this.printService = printService;
    }

    public void setupPrintService(PrintService printService, HashPrintRequestAttributeSet printRequestAttributeSet, boolean shrinkToPrintableArea) {
        this.printFitToMargin = shrinkToPrintableArea;
        this.printRequestAttributeSet = printRequestAttributeSet;
        this.printService = printService;
    }

    public void showPrintSetupDialog() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        if (this.printService == null && services != null && services.length > 0 && services[0] != null) {
            this.printService = services[0];
        }

        try {
            pj.setPrintService(this.printService);
            pj.pageDialog(this.printRequestAttributeSet);
        } catch (Throwable var3) {
            logger.log(Level.FINE, "Error creating page setup dialog.", var3);
        }

    }

    public int getCurrentPage() {
        return this.printingCurrentPage;
    }

    public int getNumberOfPages() {
        return this.totalPagesToPrint;
    }

    public boolean isPrintFitToMargin() {
        return this.printFitToMargin;
    }

    public float getUserRotation() {
        return this.userRotation;
    }

    public HashDocAttributeSet getDocAttributeSet() {
        return this.docAttributeSet;
    }

    public HashPrintRequestAttributeSet getPrintRequestAttributeSet() {
        return this.printRequestAttributeSet;
    }

    public PrintService getPrintService() {
        return this.printService;
    }

    public int print(Graphics printGraphics, PageFormat pageFormat, int pageIndex) {
        if (this.printingCurrentPage != pageIndex) {
            this.printingCurrentPage = pageIndex + 1;
        }

        if (pageIndex >= 0 && pageIndex < this.pageTree.getNumberOfPages()) {
            try {
                Page currentPage = this.pageTree.getPage(pageIndex);
                currentPage.init();
                PDimension pageDim = currentPage.getSize(this.userRotation);
                float pageWidth = (float)pageDim.getWidth();
                float pageHeight = (float)pageDim.getHeight();
                float zoomFactor = 1.0F;
                Point imageablePrintLocation = new Point();
                float rotation = this.userRotation;
                boolean isDefaultRotation = true;
                if (pageWidth > pageHeight && pageFormat.getOrientation() == 1) {
                    isDefaultRotation = false;
                    rotation -= 90.0F;
                }

                if (this.printFitToMargin) {
                    Dimension imageablePrintSize;
                    if (isDefaultRotation) {
                        imageablePrintSize = new Dimension((int)pageFormat.getImageableWidth(), (int)pageFormat.getImageableHeight());
                    } else {
                        imageablePrintSize = new Dimension((int)pageFormat.getImageableHeight(), (int)pageFormat.getImageableWidth());
                    }

                    float zw = (float)imageablePrintSize.width / pageWidth;
                    float zh = (float)imageablePrintSize.height / pageHeight;
                    zoomFactor = Math.min(zw, zh);
                    imageablePrintLocation.x = (int)pageFormat.getImageableX();
                    imageablePrintLocation.y = (int)pageFormat.getImageableY();
                }

                printGraphics.translate(imageablePrintLocation.x, imageablePrintLocation.y);
                currentPage.paint(printGraphics, 2, 2, rotation, zoomFactor, this.paintAnnotation, this.paintSearchHighlight);
            } catch (InterruptedException var15) {
                Thread.currentThread().interrupt();
                logger.log(Level.SEVERE, "Printing: Page initialization and painting was interrupted", var15);
            }

            return 0;
        } else {
            return 1;
        }
    }

    public void print() throws PrintException {
        if (this.printService == null && services != null && services.length > 0 && services[0] != null) {
            this.printService = services[0];
        }

        if (this.printService != null) {
            this.calculateTotalPagesToPrint();
            this.printService.createPrintJob().print(new SimpleDoc(this, SERVICE_FORMATTED.PRINTABLE, (DocAttributeSet)null), this.printRequestAttributeSet);
        } else {
            logger.fine("No print could be found to print to.");
        }

    }

    public CancelablePrintJob cancelablePrint() throws PrintException {
        if (this.printService == null && services != null && services.length > 0 && services[0] != null) {
            this.printService = services[0];
        }

        if (this.printService != null) {
            if ("No allowed printer".equals(this.printService.getName())) {
                JOptionPane.showMessageDialog(this.container, "No allowed printer", "Print", 2);
                return null;
            } else {
                this.calculateTotalPagesToPrint();
                DocPrintJob printerJob = this.printService.createPrintJob();
                printerJob.print(new SimpleDoc(this, SERVICE_FORMATTED.PRINTABLE, (DocAttributeSet)null), this.printRequestAttributeSet);
                return (CancelablePrintJob)printerJob;
            }
        } else {
            return null;
        }
    }

    public void print(PrintJobWatcher printJobWatcher) throws PrintException {
        if (this.printService == null && services != null && services.length > 0 && services[0] != null) {
            this.printService = services[0];
        }

        if (this.printService != null) {
            this.calculateTotalPagesToPrint();
            DocPrintJob printerJob = this.printService.createPrintJob();
            printJobWatcher.setPrintJob(printerJob);
            printerJob.print(new SimpleDoc(this, SERVICE_FORMATTED.PRINTABLE, (DocAttributeSet)null), this.printRequestAttributeSet);
            printJobWatcher.waitForDone();
        } else {
            logger.fine("No print could be found to print to.");
        }

    }

    private PrintService getSetupDialog() {
        Window window = SwingUtilities.getWindowAncestor(this.container);
        GraphicsConfiguration graphicsConfiguration = window == null ? null : window.getGraphicsConfiguration();
        return ServiceUI.printDialog(graphicsConfiguration, this.container.getX() + 50, this.container.getY() + 50, services, services[0], SERVICE_FORMATTED.PRINTABLE, this.printRequestAttributeSet);
    }

    private void calculateTotalPagesToPrint() {
        PageRanges pageRanges = (PageRanges)this.printRequestAttributeSet.get(PageRanges.class);
        this.totalPagesToPrint = 0;
        int[][] arr$ = pageRanges.getMembers();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            int[] ranges = arr$[i$];
            int start = ranges[0];
            int end = ranges[1];
            if (start < 1) {
                start = 1;
            }

            if (end > this.pageTree.getNumberOfPages()) {
                end = this.pageTree.getNumberOfPages();
            }

            this.totalPagesToPrint += end - start + 1;
        }

    }

    private PrintService[] lookForPrintServices() {
        JSONArray printServices = new JSONArray();

        try {
            JSONObject functionsObj = PublishHttpUtil.getPrintService(FileManager.session);
            ExceptionUtil.ifResponseErrorThrow(functionsObj);
            printServices = (JSONArray)functionsObj.get("data");
        } catch (Exception var12) {
        }

        List<PrintService> serviceList = new ArrayList();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(SERVICE_FORMATTED.PRINTABLE, (AttributeSet)null);
        PrintService[] var4 = services;
        int serviceLength = services.length;

        int max;
        PrintService printService;
        for(max = 0; max < serviceLength; ++max) {
            printService = var4[max];
            String serviceName = printService.getName();
            boolean isAdd = false;

            for(int i = 0; i < printServices.size(); ++i) {
                String printRegex = (String)printServices.get(i);
                if (Pattern.matches(printRegex, serviceName)) {
                    isAdd = true;
                    break;
                }
            }

            if (isAdd) {
                serviceList.add(printService);
            }
        }

        services = (PrintService[])serviceList.toArray(new PrintService[0]);
        if (services.length == 0) {
            services = new PrintService[]{new PrintService() {
                public String getName() {
                    return "No allowed printer";
                }

                public DocPrintJob createPrintJob() {
                    return null;
                }

                public void addPrintServiceAttributeListener(PrintServiceAttributeListener listener) {
                }

                public void removePrintServiceAttributeListener(PrintServiceAttributeListener listener) {
                }

                public PrintServiceAttributeSet getAttributes() {
                    return null;
                }

                public <T extends PrintServiceAttribute> T getAttribute(Class<T> category) {
                    return null;
                }

                public DocFlavor[] getSupportedDocFlavors() {
                    return new DocFlavor[0];
                }

                public boolean isDocFlavorSupported(DocFlavor flavor) {
                    return false;
                }

                public Class<?>[] getSupportedAttributeCategories() {
                    return new Class[0];
                }

                public boolean isAttributeCategorySupported(Class<? extends Attribute> category) {
                    return false;
                }

                public Object getDefaultAttributeValue(Class<? extends Attribute> category) {
                    return null;
                }

                public Object getSupportedAttributeValues(Class<? extends Attribute> category, DocFlavor flavor, AttributeSet attributes) {
                    return null;
                }

                public boolean isAttributeValueSupported(Attribute attrval, DocFlavor flavor, AttributeSet attributes) {
                    return false;
                }

                public AttributeSet getUnsupportedAttributes(DocFlavor flavor, AttributeSet attributes) {
                    return null;
                }

                public ServiceUIFactory getServiceUIFactory() {
                    return null;
                }
            }};
        }

        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        if (defaultService != null && services.length > 1) {
            serviceLength = 1;

            for(max = services.length; serviceLength < max; ++serviceLength) {
                printService = services[serviceLength];
                if (printService.equals(defaultService)) {
                    PrintService tmp = services[0];
                    services[0] = defaultService;
                    services[serviceLength] = tmp;
                    break;
                }
            }
        }

        return services;
    }

    public boolean isPaintAnnotation() {
        return this.paintAnnotation;
    }

    public void setPaintAnnotation(boolean paintAnnotation) {
        this.paintAnnotation = paintAnnotation;
    }

    public boolean isPaintSearchHighlight() {
        return this.paintSearchHighlight;
    }

    public void setPaintSearchHighlight(boolean paintSearchHighlight) {
        this.paintSearchHighlight = paintSearchHighlight;
    }

    public void setSwingController(SwingController swingController) {
        this.swingController = swingController;
    }

    public SwingController getSwingController() {
        return this.swingController;
    }
}
