//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.icepdf.ri.common;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.icepdf.core.SecurityCallback;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.io.SizeInputStream;
import org.icepdf.core.pobjects.Catalog;
import org.icepdf.core.pobjects.Destination;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Name;
import org.icepdf.core.pobjects.NameTree;
import org.icepdf.core.pobjects.NamedDestinations;
import org.icepdf.core.pobjects.OptionalContent;
import org.icepdf.core.pobjects.OutlineItem;
import org.icepdf.core.pobjects.Outlines;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.pobjects.PageTree;
import org.icepdf.core.pobjects.StringObject;
import org.icepdf.core.pobjects.ViewerPreferences;
import org.icepdf.core.pobjects.actions.Action;
import org.icepdf.core.pobjects.actions.GoToAction;
import org.icepdf.core.pobjects.actions.URIAction;
import org.icepdf.core.pobjects.fonts.FontFactory;
import org.icepdf.core.pobjects.security.Permissions;
import org.icepdf.core.pobjects.security.SecurityManager;
import org.icepdf.core.search.DocumentSearchController;
import org.icepdf.core.util.Library;
import org.icepdf.core.util.Utils;
import org.icepdf.ri.common.fonts.FontDialog;
import org.icepdf.ri.common.search.DocumentSearchControllerImpl;
import org.icepdf.ri.common.utility.annotation.AnnotationPanel;
import org.icepdf.ri.common.utility.attachment.AttachmentPanel;
import org.icepdf.ri.common.utility.layers.LayersPanel;
import org.icepdf.ri.common.utility.outline.OutlineItemTreeNode;
import org.icepdf.ri.common.utility.search.SearchPanel;
import org.icepdf.ri.common.utility.signatures.SignaturesPanel;
import org.icepdf.ri.common.utility.thumbs.ThumbnailsPanel;
import org.icepdf.ri.common.views.AbstractDocumentView;
import org.icepdf.ri.common.views.AnnotationComponent;
import org.icepdf.ri.common.views.Controller;
import org.icepdf.ri.common.views.DocumentView;
import org.icepdf.ri.common.views.DocumentViewController;
import org.icepdf.ri.common.views.DocumentViewControllerImpl;
import org.icepdf.ri.common.views.DocumentViewModelImpl;
import org.icepdf.ri.common.views.annotations.AnnotationState;
import org.icepdf.ri.util.BareBonesBrowserLaunch;
import org.icepdf.ri.util.PropertiesManager;
import org.icepdf.ri.util.Resources;
import org.icepdf.ri.util.SVG;
import org.icepdf.ri.util.TextExtractionTask;
import org.icepdf.ri.util.URLAccess;

public class SwingController implements Controller, ActionListener, FocusListener, ItemListener, TreeSelectionListener, WindowListener, DropTargetListener, KeyListener, PropertyChangeListener {
    protected static final Logger logger = Logger.getLogger(SwingController.class.toString());
    public static final int CURSOR_OPEN_HAND = 1;
    public static final int CURSOR_CLOSE_HAND = 2;
    public static final int CURSOR_ZOOM_IN = 3;
    public static final int CURSOR_ZOOM_OUT = 4;
    public static final int CURSOR_WAIT = 6;
    public static final int CURSOR_SELECT = 7;
    public static final int CURSOR_DEFAULT = 8;
    protected static final int MAX_SELECT_ALL_PAGE_COUNT = 250;
    private JMenuItem openFileMenuItem;
    private JMenuItem openURLMenuItem;
    private JMenuItem closeMenuItem;
    private JMenuItem saveAsFileMenuItem;
    private JMenuItem exportTextMenuItem;
    private JMenuItem exportSVGMenuItem;
    private JMenuItem permissionsMenuItem;
    private JMenuItem informationMenuItem;
    private JMenuItem fontInformationMenuItem;
    private JMenuItem printSetupMenuItem;
    private JMenuItem printMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem deleteMenuItem;
    private JMenuItem selectAllMenuItem;
    private JMenuItem deselectAllMenuItem;
    private JMenuItem fitActualSizeMenuItem;
    private JMenuItem fitPageMenuItem;
    private JMenuItem fitWidthMenuItem;
    private JMenuItem zoomInMenuItem;
    private JMenuItem zoomOutMenuItem;
    private JMenuItem rotateLeftMenuItem;
    private JMenuItem rotateRightMenuItem;
    private JMenuItem showHideToolBarMenuItem;
    private JMenuItem showHideUtilityPaneMenuItem;
    private JMenuItem firstPageMenuItem;
    private JMenuItem previousPageMenuItem;
    private JMenuItem nextPageMenuItem;
    private JMenuItem lastPageMenuItem;
    private JMenuItem searchMenuItem;
    private JMenuItem goToPageMenuItem;
    private JMenuItem minimiseAllMenuItem;
    private JMenuItem bringAllToFrontMenuItem;
    private List windowListMenuItems;
    private JMenuItem aboutMenuItem;
    private JButton openFileButton;
    private JButton saveAsFileButton;
    private JButton printButton;
    private JButton searchButton;
    private JToggleButton showHideUtilityPaneButton;
    private JButton firstPageButton;
    private JButton previousPageButton;
    private JButton nextPageButton;
    private JButton lastPageButton;
    private JTextField currentPageNumberTextField;
    private JLabel numberOfPagesLabel;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JComboBox zoomComboBox;
    private JToggleButton fitActualSizeButton;
    private JToggleButton fitHeightButton;
    private JToggleButton fitWidthButton;
    private JToggleButton fontEngineButton;
    private JToggleButton facingPageViewContinuousButton;
    private JToggleButton singlePageViewContinuousButton;
    private JToggleButton facingPageViewNonContinuousButton;
    private JToggleButton singlePageViewNonContinuousButton;
    private JButton rotateLeftButton;
    private JButton rotateRightButton;
    private JToggleButton panToolButton;
    private JToggleButton textSelectToolButton;
    private JToggleButton zoomInToolButton;
    private JToggleButton zoomDynamicToolButton;
    private JToggleButton selectToolButton;
    private JToggleButton highlightAnnotationToolButton;
    private JToggleButton textAnnotationToolButton;
    private JToggleButton formHighlightButton;
    private JToggleButton linkAnnotationToolButton;
    private JToggleButton highlightAnnotationUtilityToolButton;
    private JToggleButton strikeOutAnnotationToolButton;
    private JToggleButton underlineAnnotationToolButton;
    private JToggleButton lineAnnotationToolButton;
    private JToggleButton lineArrowAnnotationToolButton;
    private JToggleButton squareAnnotationToolButton;
    private JToggleButton circleAnnotationToolButton;
    private JToggleButton inkAnnotationToolButton;
    private JToggleButton freeTextAnnotationToolButton;
    private JToggleButton textAnnotationUtilityToolButton;
    private JToolBar completeToolBar;
    private ProgressMonitor printProgressMonitor;
    private Timer printActivityMonitor;
    private JTree outlinesTree;
    private JScrollPane outlinesScrollPane;
    private SearchPanel searchPanel;
    private AttachmentPanel attachmentPanel;
    private ThumbnailsPanel thumbnailsPanel;
    private LayersPanel layersPanel;
    private SignaturesPanel signaturesPanel;
    private AnnotationPanel annotationPanel;
    private JTabbedPane utilityTabbedPane;
    private JSplitPane utilityAndDocumentSplitPane;
    private int utilityAndDocumentSplitPaneLastDividerLocation;
    private JLabel statusLabel;
    private JFrame viewer;
    protected WindowManagementCallback windowManagementCallback;
    protected ViewModel viewModel;
    protected DocumentViewControllerImpl documentViewController;
    protected DocumentSearchController documentSearchController;
    protected Document document;
    protected boolean disposed;
    protected static ResourceBundle messageBundle = null;
    protected PropertiesManager propertiesManager;
    private boolean reflectingZoomInZoomComboBox;
    public JFrame applicationFrame;
    public Map data;

    public SwingController() {
        this((ResourceBundle)null);
    }

    public SwingController(JFrame applicationFrame, Map data) {
        this((ResourceBundle)null);
        this.applicationFrame = applicationFrame;
        this.data = data;
    }

    public SwingController(ResourceBundle messageBundle) {
        this.reflectingZoomInZoomComboBox = false;
        this.viewModel = new ViewModel();
        this.documentViewController = new DocumentViewControllerImpl(this);
        this.documentSearchController = new DocumentSearchControllerImpl(this);
        this.documentViewController.addPropertyChangeListener(this);
        if (messageBundle != null) {
            SwingController.messageBundle = messageBundle;
        } else {
            SwingController.messageBundle = ResourceBundle.getBundle("org.icepdf.ri.resources.MessageBundle");
        }

    }

    public void setDocumentViewController(DocumentViewControllerImpl documentViewController) {
        if (this.documentViewController != null) {
            this.documentViewController.removePropertyChangeListener(this);
        }

        this.documentViewController = documentViewController;
        documentViewController.addPropertyChangeListener(this);
    }

    public DocumentViewController getDocumentViewController() {
        return this.documentViewController;
    }

    public DocumentSearchController getDocumentSearchController() {
        return this.documentSearchController;
    }

    public ResourceBundle getMessageBundle() {
        return messageBundle;
    }

    public void setWindowManagementCallback(WindowManagementCallback wm) {
        this.windowManagementCallback = wm;
    }

    public WindowManagementCallback getWindowManagementCallback() {
        return this.windowManagementCallback;
    }

    public void setPropertiesManager(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    public PropertiesManager getPropertiesManager() {
        return this.propertiesManager;
    }

    public void setOpenFileMenuItem(JMenuItem mi) {
        this.openFileMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setOpenURLMenuItem(JMenuItem mi) {
        this.openURLMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setCloseMenuItem(JMenuItem mi) {
        this.closeMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setSaveAsFileMenuItem(JMenuItem mi) {
        this.saveAsFileMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setExportTextMenuItem(JMenuItem mi) {
        this.exportTextMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setExportSVGMenuItem(JMenuItem mi) {
        this.exportSVGMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setPermissionsMenuItem(JMenuItem mi) {
        this.permissionsMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setInformationMenuItem(JMenuItem mi) {
        this.informationMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setFontInformationMenuItem(JMenuItem mi) {
        this.fontInformationMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setPrintSetupMenuItem(JMenuItem mi) {
        this.printSetupMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setPrintMenuItem(JMenuItem mi) {
        this.printMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setExitMenuItem(JMenuItem mi) {
        this.exitMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setUndoMenuItem(JMenuItem mi) {
        this.undoMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setReduMenuItem(JMenuItem mi) {
        this.redoMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setCopyMenuItem(JMenuItem mi) {
        this.copyMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setDeleteMenuItem(JMenuItem mi) {
        this.deleteMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setSelectAllMenuItem(JMenuItem mi) {
        this.selectAllMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setDselectAllMenuItem(JMenuItem mi) {
        this.deselectAllMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setFitActualSizeMenuItem(JMenuItem mi) {
        this.fitActualSizeMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setFitPageMenuItem(JMenuItem mi) {
        this.fitPageMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setFitWidthMenuItem(JMenuItem mi) {
        this.fitWidthMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setZoomInMenuItem(JMenuItem mi) {
        this.zoomInMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setZoomOutMenuItem(JMenuItem mi) {
        this.zoomOutMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setRotateLeftMenuItem(JMenuItem mi) {
        this.rotateLeftMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setRotateRightMenuItem(JMenuItem mi) {
        this.rotateRightMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setShowHideToolBarMenuItem(JMenuItem mi) {
        this.showHideToolBarMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setShowHideUtilityPaneMenuItem(JMenuItem mi) {
        this.showHideUtilityPaneMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setFirstPageMenuItem(JMenuItem mi) {
        this.firstPageMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setPreviousPageMenuItem(JMenuItem mi) {
        this.previousPageMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setNextPageMenuItem(JMenuItem mi) {
        this.nextPageMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setLastPageMenuItem(JMenuItem mi) {
        this.lastPageMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setSearchMenuItem(JMenuItem mi) {
        this.searchMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setGoToPageMenuItem(JMenuItem mi) {
        this.goToPageMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setMinimiseAllMenuItem(JMenuItem mi) {
        this.minimiseAllMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setBringAllToFrontMenuItem(JMenuItem mi) {
        this.bringAllToFrontMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setWindowListMenuItems(List menuItems) {
        this.windowListMenuItems = menuItems;
        int count = this.windowListMenuItems != null ? this.windowListMenuItems.size() : 0;

        for(int i = 0; i < count; ++i) {
            JMenuItem mi = (JMenuItem)this.windowListMenuItems.get(i);
            mi.addActionListener(this);
        }

    }

    public void setAboutMenuItem(JMenuItem mi) {
        this.aboutMenuItem = mi;
        mi.addActionListener(this);
    }

    public void setOpenFileButton(JButton btn) {
        this.openFileButton = btn;
        btn.addActionListener(this);
    }

    public void setSaveAsFileButton(JButton btn) {
        this.saveAsFileButton = btn;
        btn.addActionListener(this);
    }

    public void setPrintButton(JButton btn) {
        this.printButton = btn;
        btn.addActionListener(this);
    }

    public void setSearchButton(JButton btn) {
        this.searchButton = btn;
        btn.addActionListener(this);
    }

    public void setShowHideUtilityPaneButton(JToggleButton btn) {
        this.showHideUtilityPaneButton = btn;
        btn.addActionListener(this);
    }

    public void setFirstPageButton(JButton btn) {
        this.firstPageButton = btn;
        btn.addActionListener(this);
    }

    public void setPreviousPageButton(JButton btn) {
        this.previousPageButton = btn;
        btn.addActionListener(this);
    }

    public void setNextPageButton(JButton btn) {
        this.nextPageButton = btn;
        btn.addActionListener(this);
    }

    public void setLastPageButton(JButton btn) {
        this.lastPageButton = btn;
        btn.addActionListener(this);
    }

    public void setCurrentPageNumberTextField(JTextField textField) {
        this.currentPageNumberTextField = textField;
        this.currentPageNumberTextField.addActionListener(this);
        this.currentPageNumberTextField.addFocusListener(this);
        this.currentPageNumberTextField.addKeyListener(this);
    }

    public void setNumberOfPagesLabel(JLabel lbl) {
        this.numberOfPagesLabel = lbl;
    }

    public void setZoomOutButton(JButton btn) {
        this.zoomOutButton = btn;
        btn.addActionListener(this);
    }

    public void setZoomComboBox(JComboBox zcb, float[] zl) {
        this.zoomComboBox = zcb;
        this.documentViewController.setZoomLevels(zl);
        this.zoomComboBox.setSelectedItem(NumberFormat.getPercentInstance().format(1.0D));
        this.zoomComboBox.addItemListener(this);
    }

    public void setZoomInButton(JButton btn) {
        this.zoomInButton = btn;
        btn.addActionListener(this);
    }

    public void setFitActualSizeButton(JToggleButton btn) {
        this.fitActualSizeButton = btn;
        btn.addItemListener(this);
    }

    public void setFitHeightButton(JToggleButton btn) {
        this.fitHeightButton = btn;
        btn.addItemListener(this);
    }

    public void setFontEngineButton(JToggleButton btn) {
        this.fontEngineButton = btn;
        btn.addItemListener(this);
    }

    public void setFitWidthButton(JToggleButton btn) {
        this.fitWidthButton = btn;
        btn.addItemListener(this);
    }

    public void setRotateLeftButton(JButton btn) {
        this.rotateLeftButton = btn;
        btn.addActionListener(this);
    }

    public void setRotateRightButton(JButton btn) {
        this.rotateRightButton = btn;
        btn.addActionListener(this);
    }

    public void setPanToolButton(JToggleButton btn) {
        this.panToolButton = btn;
        btn.addItemListener(this);
    }

    public void setZoomInToolButton(JToggleButton btn) {
        this.zoomInToolButton = btn;
        btn.addItemListener(this);
    }

    public void setTextSelectToolButton(JToggleButton btn) {
        this.textSelectToolButton = btn;
        btn.addItemListener(this);
    }

    public void setSelectToolButton(JToggleButton btn) {
        this.selectToolButton = btn;
        btn.addItemListener(this);
    }

    public void setLinkAnnotationToolButton(JToggleButton btn) {
        this.linkAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setHighlightAnnotationToolButton(JToggleButton btn) {
        this.highlightAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setHighlightAnnotationUtilityToolButton(JToggleButton btn) {
        this.highlightAnnotationUtilityToolButton = btn;
        btn.addItemListener(this);
    }

    public void setStrikeOutAnnotationToolButton(JToggleButton btn) {
        this.strikeOutAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setUnderlineAnnotationToolButton(JToggleButton btn) {
        this.underlineAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setLineAnnotationToolButton(JToggleButton btn) {
        this.lineAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setLineArrowAnnotationToolButton(JToggleButton btn) {
        this.lineArrowAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setSquareAnnotationToolButton(JToggleButton btn) {
        this.squareAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setCircleAnnotationToolButton(JToggleButton btn) {
        this.circleAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setInkAnnotationToolButton(JToggleButton btn) {
        this.inkAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setFreeTextAnnotationToolButton(JToggleButton btn) {
        this.freeTextAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setTextAnnotationToolButton(JToggleButton btn) {
        this.textAnnotationToolButton = btn;
        btn.addItemListener(this);
    }

    public void setFormHighlightButton(JToggleButton btn) {
        this.formHighlightButton = btn;
        btn.addActionListener(this);
    }

    public void setTextAnnotationUtilityToolButton(JToggleButton btn) {
        this.textAnnotationUtilityToolButton = btn;
        btn.addItemListener(this);
    }

    public void setZoomDynamicToolButton(JToggleButton btn) {
        this.zoomDynamicToolButton = btn;
        btn.addItemListener(this);
    }

    public void setCompleteToolBar(JToolBar toolbar) {
        this.completeToolBar = toolbar;
    }

    public void setOutlineComponents(JTree tree, JScrollPane scroll) {
        this.outlinesTree = tree;
        this.outlinesScrollPane = scroll;
        this.outlinesTree.addTreeSelectionListener(this);
    }

    public void setSearchPanel(SearchPanel sp) {
        this.searchPanel = sp;
    }

    public void setAttachmentPanel(AttachmentPanel sp) {
        this.attachmentPanel = sp;
    }

    public void setThumbnailsPanel(ThumbnailsPanel tn) {
        this.thumbnailsPanel = tn;
    }

    public void setLayersPanel(LayersPanel tn) {
        this.layersPanel = tn;
    }

    public void setSignaturesPanel(SignaturesPanel tn) {
        this.signaturesPanel = tn;
    }

    public void setAnnotationPanel(AnnotationPanel lp) {
        this.annotationPanel = lp;
    }

    public void setUtilityTabbedPane(JTabbedPane util) {
        this.utilityTabbedPane = util;
    }

    public void setIsEmbeddedComponent(boolean embeddableComponent) {
        if (embeddableComponent) {
            this.documentViewController.setViewKeyListener(this);
            this.documentViewController.getViewContainer().addKeyListener(this);
        }

    }

    public void setUtilityAndDocumentSplitPane(JSplitPane splitPane) {
        this.utilityAndDocumentSplitPane = splitPane;
        this.setUtilityPaneVisible(false);
        this.utilityAndDocumentSplitPane.addPropertyChangeListener(this);
    }

    public void setStatusLabel(JLabel lbl) {
        this.statusLabel = lbl;
    }

    public void setViewerFrame(JFrame v) {
        this.viewer = v;
        this.viewer.addWindowListener(this);
        new DropTarget(this.viewer, 3, this);
        this.reflectStateInComponents();
    }

    public JFrame getViewerFrame() {
        return this.viewer;
    }

    public boolean isPdfCollection() {
        Catalog catalog = this.document.getCatalog();
        HashMap collection = catalog.getCollection();
        if (collection != null && catalog.getEmbeddedFilesNameTree() != null) {
            NameTree embeddedFilesNameTree = catalog.getEmbeddedFilesNameTree();
            List filePairs = embeddedFilesNameTree.getNamesAndValues();
            boolean found = false;
            if (filePairs != null) {
                Library library = catalog.getLibrary();
                int i = 0;

                for(int max = filePairs.size(); i < max; i += 2) {
                    String fileName = Utils.convertStringObject(library, (StringObject)filePairs.get(i));
                    if (fileName != null && fileName.toLowerCase().endsWith(".pdf")) {
                        found = true;
                        break;
                    }
                }
            }

            return found;
        } else {
            return false;
        }
    }

    private void reflectStateInComponents() {
        boolean opened = this.document != null;
        boolean pdfCollection = opened && this.isPdfCollection();
        int nPages = this.getPageTree() != null ? this.getPageTree().getNumberOfPages() : 0;
        boolean canPrint = this.havePermissionToPrint();
        boolean canExtract = this.havePermissionToExtractContent();
        boolean canModify = this.havePermissionToModifyDocument();
        this.reflectPageChangeInComponents();
        this.setEnabled(this.closeMenuItem, opened);
        this.setEnabled(this.saveAsFileMenuItem, opened);
        this.setEnabled(this.exportTextMenuItem, opened && canExtract && !pdfCollection);
        this.setEnabled(this.exportSVGMenuItem, opened && canPrint && !pdfCollection);
        this.setEnabled(this.permissionsMenuItem, opened);
        this.setEnabled(this.informationMenuItem, opened);
        this.setEnabled(this.fontInformationMenuItem, opened);
        this.setEnabled(this.printSetupMenuItem, opened && canPrint && !pdfCollection);
        this.setEnabled(this.printMenuItem, opened && canPrint && !pdfCollection);
        this.setEnabled(this.undoMenuItem, false);
        this.setEnabled(this.redoMenuItem, false);
        this.setEnabled(this.copyMenuItem, false);
        this.setEnabled(this.deleteMenuItem, false);
        this.setEnabled(this.selectAllMenuItem, opened && canExtract && !pdfCollection);
        this.setEnabled(this.deselectAllMenuItem, false);
        this.setEnabled(this.fitActualSizeMenuItem, opened && !pdfCollection);
        this.setEnabled(this.fitPageMenuItem, opened && !pdfCollection);
        this.setEnabled(this.fitWidthMenuItem, opened && !pdfCollection);
        this.setEnabled(this.zoomInMenuItem, opened && !pdfCollection);
        this.setEnabled(this.zoomOutMenuItem, opened && !pdfCollection);
        this.setEnabled(this.rotateLeftMenuItem, opened && !pdfCollection);
        this.setEnabled(this.rotateRightMenuItem, opened && !pdfCollection);
        this.setEnabled(this.fitPageMenuItem, opened && !pdfCollection);
        this.setEnabled(this.fitWidthMenuItem, opened && !pdfCollection);
        boolean vis;
        if (this.showHideToolBarMenuItem != null) {
            vis = this.completeToolBar != null && this.completeToolBar.isVisible();
            this.showHideToolBarMenuItem.setText(vis ? messageBundle.getString("viewer.toolbar.hideToolBar.label") : messageBundle.getString("viewer.toolbar.showToolBar.label"));
        }

        this.setEnabled(this.showHideToolBarMenuItem, this.completeToolBar != null);
        if (this.showHideUtilityPaneMenuItem != null) {
            vis = this.isUtilityPaneVisible();
            this.showHideUtilityPaneMenuItem.setText(opened && vis ? messageBundle.getString("viewer.toolbar.hideUtilityPane.label") : messageBundle.getString("viewer.toolbar.showUtilityPane.label"));
        }

        this.setEnabled(this.showHideUtilityPaneMenuItem, opened && this.utilityTabbedPane != null);
        this.setEnabled(this.searchMenuItem, opened && this.searchPanel != null && !pdfCollection);
        this.setEnabled(this.goToPageMenuItem, opened && nPages > 1 && !pdfCollection);
        this.setEnabled(this.saveAsFileButton, opened);
        this.setEnabled(this.printButton, opened && canPrint && !pdfCollection);
        this.setEnabled(this.searchButton, opened && this.searchPanel != null && !pdfCollection);
        this.setEnabled(this.showHideUtilityPaneButton, opened && this.utilityTabbedPane != null);
        this.setEnabled(this.currentPageNumberTextField, opened && nPages > 1 && !pdfCollection);
        if (this.numberOfPagesLabel != null) {
            Object[] messageArguments = new Object[]{String.valueOf(nPages)};
            MessageFormat formatter = new MessageFormat(messageBundle.getString("viewer.toolbar.pageIndicator"));
            String numberOfPages = formatter.format(messageArguments);
            this.numberOfPagesLabel.setText(opened ? numberOfPages : "");
        }

        this.setEnabled(this.zoomInButton, opened && !pdfCollection);
        this.setEnabled(this.zoomOutButton, opened && !pdfCollection);
        this.setEnabled(this.zoomComboBox, opened && !pdfCollection);
        this.setEnabled(this.fitActualSizeButton, opened && !pdfCollection);
        this.setEnabled(this.fitHeightButton, opened && !pdfCollection);
        this.setEnabled(this.fitWidthButton, opened && !pdfCollection);
        this.setEnabled(this.rotateLeftButton, opened && !pdfCollection);
        this.setEnabled(this.rotateRightButton, opened && !pdfCollection);
        this.setEnabled(this.panToolButton, opened && !pdfCollection);
        this.setEnabled(this.zoomInToolButton, opened && !pdfCollection);
        this.setEnabled(this.zoomDynamicToolButton, opened && !pdfCollection);
        this.setEnabled(this.textSelectToolButton, opened && canExtract && !pdfCollection);
        this.setEnabled(this.selectToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.linkAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.highlightAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.highlightAnnotationUtilityToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.strikeOutAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.underlineAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.lineAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.lineArrowAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.squareAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.circleAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.inkAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.freeTextAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.textAnnotationToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.textAnnotationUtilityToolButton, opened && canModify && !pdfCollection);
        this.setEnabled(this.formHighlightButton, opened && !pdfCollection && this.hasForms());
        this.setEnabled(this.fontEngineButton, opened && !pdfCollection);
        this.setEnabled(this.facingPageViewContinuousButton, opened && !pdfCollection);
        this.setEnabled(this.singlePageViewContinuousButton, opened && !pdfCollection);
        this.setEnabled(this.facingPageViewNonContinuousButton, opened && !pdfCollection);
        this.setEnabled(this.singlePageViewNonContinuousButton, opened && !pdfCollection);
        if (opened) {
            this.reflectZoomInZoomComboBox();
            this.reflectFitInFitButtons();
            this.reflectDocumentViewModeInButtons();
            this.reflectToolInToolButtons();
            this.reflectFormHighlightButtons();
        }

    }

    private boolean hasForms() {
        if (this.document == null) {
            return false;
        } else {
            return this.document.getCatalog().getInteractiveForm() != null && this.document.getCatalog().getInteractiveForm().getFields() != null && this.document.getCatalog().getInteractiveForm().getFields().size() != 0;
        }
    }

    private void reflectPageChangeInComponents() {
        boolean opened = this.document != null;
        int nPages = this.getPageTree() != null ? this.getPageTree().getNumberOfPages() : 0;
        int currentPage = this.isCurrentPage() ? this.documentViewController.getCurrentPageDisplayValue() : 0;
        this.setEnabled(this.firstPageMenuItem, opened && currentPage != 1);
        this.setEnabled(this.previousPageMenuItem, opened && currentPage != 1);
        this.setEnabled(this.nextPageMenuItem, opened && currentPage != nPages);
        this.setEnabled(this.lastPageMenuItem, opened && currentPage != nPages);
        this.setEnabled(this.firstPageButton, opened && currentPage != 1);
        this.setEnabled(this.previousPageButton, opened && currentPage != 1);
        this.setEnabled(this.nextPageButton, opened && currentPage != nPages);
        this.setEnabled(this.lastPageButton, opened && currentPage != nPages);
        if (this.currentPageNumberTextField != null) {
            this.currentPageNumberTextField.setText(opened ? Integer.toString(currentPage) : "");
        }

    }

    public boolean havePermissionToPrint() {
        if (this.document == null) {
            return false;
        } else {
            SecurityManager securityManager = this.document.getSecurityManager();
            if (securityManager == null) {
                return true;
            } else {
                Permissions permissions = securityManager.getPermissions();
                return permissions == null || permissions.getPermissions(0);
            }
        }
    }

    public boolean havePermissionToExtractContent() {
        if (this.document == null) {
            return false;
        } else {
            SecurityManager securityManager = this.document.getSecurityManager();
            if (securityManager == null) {
                return true;
            } else {
                Permissions permissions = securityManager.getPermissions();
                return permissions == null || permissions.getPermissions(3);
            }
        }
    }

    public boolean havePermissionToModifyDocument() {
        if (this.document == null) {
            return false;
        } else {
            SecurityManager securityManager = this.document.getSecurityManager();
            if (securityManager == null) {
                return true;
            } else {
                Permissions permissions = securityManager.getPermissions();
                return permissions == null || permissions.getPermissions(2);
            }
        }
    }

    private void setEnabled(JComponent comp, boolean ena) {
        if (comp != null) {
            comp.setEnabled(ena);
        }

    }

    private void setZoomFromZoomComboBox() {
        if (!this.reflectingZoomInZoomComboBox) {
            int selIndex = this.zoomComboBox.getSelectedIndex();
            float[] zoomLevels = this.documentViewController.getZoomLevels();
            if (selIndex >= 0 && selIndex < zoomLevels.length) {
                float zoom = 1.0F;

                try {
                    zoom = zoomLevels[selIndex];
                } catch (IndexOutOfBoundsException var11) {
                    logger.log(Level.FINE, "Error apply zoom levels");
                } finally {
                    if (zoom != this.documentViewController.getZoom()) {
                        this.setZoom(zoom);
                    }

                }
            } else {
                boolean success = false;

                try {
                    Object selItem = this.zoomComboBox.getSelectedItem();
                    if (selItem != null) {
                        String str = selItem.toString();
                        str = str.replace('%', ' ');
                        str = str.trim();
                        float zoom = Float.parseFloat(str);
                        zoom /= 100.0F;
                        if (zoom != this.documentViewController.getZoom()) {
                            this.setZoom(zoom);
                        }

                        success = true;
                    }
                } catch (Exception var10) {
                    success = false;
                }

                if (!success) {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }

    }

    public void reflectUndoCommands() {
        UndoCaretaker undoCaretaker = ((DocumentViewModelImpl)this.documentViewController.getDocumentViewModel()).getAnnotationCareTaker();
        this.setEnabled(this.undoMenuItem, undoCaretaker.isUndo());
        this.setEnabled(this.redoMenuItem, undoCaretaker.isRedo());
    }

    private void reflectZoomInZoomComboBox() {
        if (!this.reflectingZoomInZoomComboBox && this.document != null) {
            int index = -1;
            float zoom = this.documentViewController.getZoom();
            float belowZoom = zoom * 0.99F;
            float aboveZoom = zoom * 1.01F;
            float[] zoomLevels = this.documentViewController.getZoomLevels();
            if (zoomLevels != null) {
                for(int i = 0; i < zoomLevels.length; ++i) {
                    float curr = zoomLevels[i];
                    if (curr >= belowZoom && curr <= aboveZoom) {
                        index = i;
                        break;
                    }
                }
            }

            try {
                this.reflectingZoomInZoomComboBox = true;
                if (this.zoomComboBox != null) {
                    if (index > -1) {
                        this.zoomComboBox.setSelectedIndex(index);
                    } else {
                        this.zoomComboBox.setSelectedItem(NumberFormat.getPercentInstance().format((double)zoom));
                    }
                }
            } finally {
                this.reflectingZoomInZoomComboBox = false;
            }
        }

    }

    public int getDocumentViewToolMode() {
        return this.documentViewController.getToolMode();
    }

    public void setDisplayTool(int argToolName) {
        try {
            boolean actualToolMayHaveChanged = false;
            if (argToolName == 1) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(1);
                this.documentViewController.setViewCursor(1);
                this.setCursorOnComponents(8);
            } else if (argToolName == 5) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(5);
                this.documentViewController.setViewCursor(7);
                this.setCursorOnComponents(8);
            } else if (argToolName == 6) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(6);
                this.documentViewController.setViewCursor(7);
                this.setCursorOnComponents(8);
            } else if (argToolName == 7) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(7);
                this.documentViewController.setViewCursor(11);
                this.setCursorOnComponents(8);
            } else if (argToolName == 8) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(8);
                this.documentViewController.setViewCursor(7);
                this.setCursorOnComponents(8);
            } else if (argToolName == 11) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(11);
                this.documentViewController.setViewCursor(7);
                this.setCursorOnComponents(8);
            } else if (argToolName == 9) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(9);
                this.documentViewController.setViewCursor(7);
                this.setCursorOnComponents(8);
            } else if (argToolName == 12) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(12);
                this.documentViewController.setViewCursor(11);
                this.setCursorOnComponents(8);
            } else if (argToolName == 13) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(13);
                this.documentViewController.setViewCursor(11);
                this.setCursorOnComponents(8);
            } else if (argToolName == 14) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(14);
                this.documentViewController.setViewCursor(11);
                this.setCursorOnComponents(8);
            } else if (argToolName == 15) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(15);
                this.documentViewController.setViewCursor(11);
                this.setCursorOnComponents(8);
            } else if (argToolName == 16) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(16);
                this.documentViewController.setViewCursor(11);
                this.setCursorOnComponents(8);
            } else if (argToolName == 17) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(17);
                this.documentViewController.setViewCursor(11);
                this.setCursorOnComponents(8);
            } else if (argToolName == 18) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(18);
                this.documentViewController.setViewCursor(11);
                this.setCursorOnComponents(8);
            } else if (argToolName == 2) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(2);
                this.documentViewController.setViewCursor(3);
                this.setCursorOnComponents(8);
            } else if (argToolName == 4) {
                actualToolMayHaveChanged = this.documentViewController.setToolMode(4);
                this.documentViewController.setViewCursor(12);
                this.setCursorOnComponents(8);
            } else if (argToolName == 51) {
                this.setCursorOnComponents(6);
            } else if (argToolName == 50) {
                this.setCursorOnComponents(8);
            }

            if (actualToolMayHaveChanged) {
                this.reflectToolInToolButtons();
            }

            if (this.annotationPanel != null) {
                this.annotationPanel.setEnabled(false);
            }

            this.documentViewController.getViewContainer().repaint();
        } catch (HeadlessException var3) {
            var3.printStackTrace();
            logger.log(Level.FINE, "Headless exception during tool selection", var3);
        }

    }

    private void setCursorOnComponents(int cursorType) {
        Cursor cursor = this.documentViewController.getViewCursor(cursorType);
        if (this.utilityTabbedPane != null) {
            this.utilityTabbedPane.setCursor(cursor);
        }

        if (this.viewer != null) {
            this.viewer.setCursor(cursor);
        }

    }

    private void reflectToolInToolButtons() {
        this.reflectSelectionInButton(this.panToolButton, this.documentViewController.isToolModeSelected(1));
        this.reflectSelectionInButton(this.textSelectToolButton, this.documentViewController.isToolModeSelected(5));
        this.reflectSelectionInButton(this.selectToolButton, this.documentViewController.isToolModeSelected(6));
        this.reflectSelectionInButton(this.linkAnnotationToolButton, this.documentViewController.isToolModeSelected(7));
        this.reflectSelectionInButton(this.highlightAnnotationToolButton, this.documentViewController.isToolModeSelected(8));
        this.reflectSelectionInButton(this.highlightAnnotationUtilityToolButton, this.documentViewController.isToolModeSelected(8));
        this.reflectSelectionInButton(this.strikeOutAnnotationToolButton, this.documentViewController.isToolModeSelected(11));
        this.reflectSelectionInButton(this.underlineAnnotationToolButton, this.documentViewController.isToolModeSelected(9));
        this.reflectSelectionInButton(this.lineAnnotationToolButton, this.documentViewController.isToolModeSelected(12));
        this.reflectSelectionInButton(this.lineArrowAnnotationToolButton, this.documentViewController.isToolModeSelected(13));
        this.reflectSelectionInButton(this.squareAnnotationToolButton, this.documentViewController.isToolModeSelected(14));
        this.reflectSelectionInButton(this.circleAnnotationToolButton, this.documentViewController.isToolModeSelected(15));
        this.reflectSelectionInButton(this.inkAnnotationToolButton, this.documentViewController.isToolModeSelected(16));
        this.reflectSelectionInButton(this.freeTextAnnotationToolButton, this.documentViewController.isToolModeSelected(17));
        this.reflectSelectionInButton(this.textAnnotationToolButton, this.documentViewController.isToolModeSelected(18));
        this.reflectSelectionInButton(this.textAnnotationUtilityToolButton, this.documentViewController.isToolModeSelected(18));
        this.reflectSelectionInButton(this.zoomInToolButton, this.documentViewController.isToolModeSelected(2));
        this.reflectSelectionInButton(this.zoomDynamicToolButton, this.documentViewController.isToolModeSelected(4));
        this.reflectSelectionInButton(this.showHideUtilityPaneButton, this.isUtilityPaneVisible());
        this.reflectSelectionInButton(this.formHighlightButton, this.viewModel.isWidgetAnnotationHighlight());
    }

    private void reflectFitInFitButtons() {
        if (this.document != null) {
            this.reflectSelectionInButton(this.fitWidthButton, this.isDocumentFitMode(4));
            this.reflectSelectionInButton(this.fitHeightButton, this.isDocumentFitMode(3));
            this.reflectSelectionInButton(this.fitActualSizeButton, this.isDocumentFitMode(2));
        }

    }

    private void reflectFormHighlightButtons() {
        if (this.document != null) {
            this.reflectSelectionInButton(this.formHighlightButton, this.viewModel.isWidgetAnnotationHighlight());
        }

    }

    private void reflectDocumentViewModeInButtons() {
        if (this.document != null && !this.isDocumentViewMode(7)) {
            this.reflectSelectionInButton(this.singlePageViewContinuousButton, this.isDocumentViewMode(2));
            this.reflectSelectionInButton(this.facingPageViewNonContinuousButton, this.isDocumentViewMode(5));
            this.reflectSelectionInButton(this.facingPageViewContinuousButton, this.isDocumentViewMode(6));
            this.reflectSelectionInButton(this.singlePageViewNonContinuousButton, this.isDocumentViewMode(1));
        }

    }

    private void reflectSelectionInButton(AbstractButton btn, boolean selected) {
        if (btn != null) {
            if (btn.isSelected() != selected) {
                btn.setSelected(selected);
            }

            btn.setBorder(selected ? BorderFactory.createLoweredBevelBorder() : BorderFactory.createEmptyBorder());
        }

    }

    public void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(0);
        fileChooser.addChoosableFileFilter(FileExtensionUtils.getPDFFileFilter());
        if (ViewModel.getDefaultFile() != null) {
            fileChooser.setCurrentDirectory(ViewModel.getDefaultFile());
            fileChooser.setSelectedFile(ViewModel.getDefaultFile());
            fileChooser.ensureFileIsVisible(ViewModel.getDefaultFile());
        }

        fileChooser.setDialogTitle(messageBundle.getString("viewer.dialog.openFile.title"));
        int returnVal = fileChooser.showOpenDialog(this.viewer);
        if (returnVal == 0) {
            File file = fileChooser.getSelectedFile();
            fileChooser.setVisible(false);
            String extension = FileExtensionUtils.getExtension(file);
            if (extension != null) {
                if (extension.equals("pdf")) {
                    if (this.viewer != null) {
                        this.viewer.toFront();
                        this.viewer.requestFocus();
                    }

                    this.openFileInSomeViewer(file);
                } else {
                    Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openFile.error.title", "viewer.dialog.openFile.error.msg", file.getPath());
                }

                ViewModel.setDefaultFile(file);
            }
        }

        fileChooser.setVisible(false);
    }

    private void openFileInSomeViewer(File file) {
        if (this.document == null) {
            this.openDocument(file.getPath());
        } else if (this.windowManagementCallback != null) {
            int oldTool = this.getDocumentViewToolMode();
            this.setDisplayTool(51);

            try {
                this.windowManagementCallback.newWindow(file.getPath());
            } finally {
                this.setDisplayTool(oldTool);
            }
        }

    }

    public void openFileInSomeViewer(String filename) {
        try {
            File pdfFile = new File(filename);
            this.openFileInSomeViewer(pdfFile);
        } catch (Exception var3) {
        }

    }

    protected void setupSecurityHandler(Document document, SecurityCallback securityCallback) throws PDFException, PDFSecurityException {
        if (securityCallback == null) {
            document.setSecurityCallback(new MyGUISecurityCallback(this.viewer, messageBundle));
        } else {
            document.setSecurityCallback(this.documentViewController.getSecurityCallback());
        }

    }

    public void openDocument(String pathname) {
        if (pathname != null && pathname.length() > 0) {
            try {
                if (this.document != null) {
                    this.closeDocument();
                }

                this.setDisplayTool(51);
                this.document = new Document();
                this.setupSecurityHandler(this.document, this.documentViewController.getSecurityCallback());
                this.document.setFile(pathname);
                this.commonNewDocumentHandling(pathname);
            } catch (PDFException var8) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.pdfException.title", "viewer.dialog.openDocument.pdfException.msg", pathname);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var8);
            } catch (PDFSecurityException var9) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.pdfSecurityException.title", "viewer.dialog.openDocument.pdfSecurityException.msg", pathname);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var9);
            } catch (Exception var10) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.exception.title", "viewer.dialog.openDocument.exception.msg", pathname);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var10);
            } finally {
                this.setDisplayTool(1);
            }
        }

    }

    public void openURL() {
        String urlLocation = ViewModel.getDefaultURL() != null ? ViewModel.getDefaultURL() : "";
        Object o = JOptionPane.showInputDialog(this.viewer, "URL:", "Open URL", 3, (Icon)null, (Object[])null, urlLocation);
        if (o != null) {
            URLAccess urlAccess = URLAccess.doURLAccess(o.toString());
            urlAccess.closeConnection();
            if (urlAccess.errorMessage != null) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openURL.exception.title", "viewer.dialog.openURL.exception.msg", urlAccess.errorMessage, urlAccess.urlLocation);
            } else {
                if (this.viewer != null) {
                    this.viewer.toFront();
                    this.viewer.requestFocus();
                }

                this.openURLInSomeViewer(urlAccess.url);
            }

            ViewModel.setDefaultURL(urlAccess.urlLocation);
            urlAccess.dispose();
        }

    }

    private void openURLInSomeViewer(URL url) {
        if (this.document == null) {
            this.openDocument(url);
        } else if (this.windowManagementCallback != null) {
            int oldTool = this.getDocumentViewToolMode();
            this.setDisplayTool(51);

            try {
                this.windowManagementCallback.newWindow(url);
            } finally {
                this.setDisplayTool(oldTool);
            }
        }

    }

    public void openDocument(final URL location) {
        if (location != null) {
            if (this.document != null) {
                this.closeDocument();
            }

            this.setDisplayTool(51);
            this.document = new Document();

            try {
                final URLConnection urlConnection = location.openConnection();
                final int size = urlConnection.getContentLength();
                SwingWorker worker = new SwingWorker() {
                    public Object construct() {
                        BufferedInputStream in = null;

                        try {
                            Object[] messageArguments = new Object[]{location.toString()};
                            MessageFormat formatter = new MessageFormat(SwingController.messageBundle.getString("viewer.dialog.openURL.downloading.msg"));
                            ProgressMonitorInputStream progressMonitorInputStream = new ProgressMonitorInputStream(SwingController.this.viewer, formatter.format(messageArguments), new SizeInputStream(urlConnection.getInputStream(), size));
                            in = new BufferedInputStream(progressMonitorInputStream);
                            String pathOrURL = location.toString();
                            SwingController.this.document.setInputStream(in, pathOrURL);
                            SwingController.this.setupSecurityHandler(SwingController.this.document, SwingController.this.documentViewController.getSecurityCallback());
                            SwingController.this.commonNewDocumentHandling(location.getPath());
                            SwingController.this.setDisplayTool(1);
                        } catch (IOException var7) {
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException var6) {
                                    SwingController.logger.log(Level.FINE, "Error opening document.", var6);
                                }
                            }

                            SwingController.this.closeDocument();
                            SwingController.this.document = null;
                        } catch (PDFException var8) {
                            Resources.showMessageDialog(SwingController.this.viewer, 1, SwingController.messageBundle, "viewer.dialog.openDocument.pdfException.title", "viewer.dialog.openDocument.pdfException.msg", location);
                            SwingController.this.document = null;
                            SwingController.logger.log(Level.FINE, "Error opening document.", var8);
                        } catch (PDFSecurityException var9) {
                            Resources.showMessageDialog(SwingController.this.viewer, 1, SwingController.messageBundle, "viewer.dialog.openDocument.pdfSecurityException.title", "viewer.dialog.openDocument.pdfSecurityException.msg", location);
                            SwingController.this.document = null;
                            SwingController.logger.log(Level.FINE, "Error opening document.", var9);
                        } catch (Exception var10) {
                            Resources.showMessageDialog(SwingController.this.viewer, 1, SwingController.messageBundle, "viewer.dialog.openDocument.exception.title", "viewer.dialog.openDocument.exception.msg", location);
                            SwingController.this.document = null;
                            SwingController.logger.log(Level.FINE, "Error opening document.", var10);
                        }

                        return null;
                    }
                };
                worker.start();
            } catch (Exception var5) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.exception.title", "viewer.dialog.openDocument.exception.msg", location);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var5);
            }
        }

    }

    public void openDocument(InputStream inputStream, String description, String pathOrURL) {
        if (inputStream != null) {
            try {
                if (this.document != null) {
                    this.closeDocument();
                }

                this.setDisplayTool(51);
                this.document = new Document();
                this.setupSecurityHandler(this.document, this.documentViewController.getSecurityCallback());
                this.document.setInputStream(inputStream, pathOrURL);
                this.commonNewDocumentHandling(description);
            } catch (PDFException var10) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.pdfException.title", "viewer.dialog.openDocument.pdfException.msg", description);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var10);
            } catch (PDFSecurityException var11) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.pdfSecurityException.title", "viewer.dialog.openDocument.pdfSecurityException.msg", description);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var11);
            } catch (Exception var12) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.exception.title", "viewer.dialog.openDocument.exception.msg", description);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var12);
            } finally {
                this.setDisplayTool(1);
            }
        }

    }

    public void openDocument(Document embeddedDocument, String fileName) {
        if (embeddedDocument != null) {
            try {
                if (this.document != null) {
                    this.closeDocument();
                }

                this.setDisplayTool(51);
                this.document = embeddedDocument;
                this.setupSecurityHandler(this.document, this.documentViewController.getSecurityCallback());
                this.commonNewDocumentHandling(fileName);
            } catch (Exception var7) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.exception.title", "viewer.dialog.openDocument.exception.msg", fileName);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var7);
            } finally {
                this.setDisplayTool(1);
            }
        }

    }

    public void openDocument(byte[] data, int offset, int length, String description, String pathOrURL) {
        if (data != null) {
            try {
                if (this.document != null) {
                    this.closeDocument();
                }

                this.setDisplayTool(51);
                this.document = new Document();
                this.setupSecurityHandler(this.document, this.documentViewController.getSecurityCallback());
                this.document.setByteArray(data, offset, length, pathOrURL);
                this.commonNewDocumentHandling(description);
            } catch (PDFException var12) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.pdfException.title", "viewer.dialog.openDocument.pdfException.msg", description);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var12);
            } catch (PDFSecurityException var13) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.pdfSecurityException.title", "viewer.dialog.openDocument.pdfSecurityException.msg", description);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var13);
            } catch (Exception var14) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.openDocument.exception.title", "viewer.dialog.openDocument.exception.msg", description);
                this.document = null;
                logger.log(Level.FINE, "Error opening document.", var14);
            } finally {
                this.setDisplayTool(1);
            }
        }

    }

    public void commonNewDocumentHandling(String fileDescription) {
        if (this.searchPanel != null) {
            this.searchPanel.setDocument(this.document);
        }

        if (this.thumbnailsPanel != null) {
            this.thumbnailsPanel.setDocument(this.document);
        }

        boolean showUtilityPane = false;
        Catalog catalog = this.document.getCatalog();
        Object tmp = catalog.getObject(Catalog.PAGELAYOUT_KEY);
        if (tmp != null && tmp instanceof Name) {
            String pageLayout = ((Name)tmp).getName();
            int viewType = 1;
            if (pageLayout.equalsIgnoreCase("OneColumn")) {
                viewType = 2;
            } else if (pageLayout.equalsIgnoreCase("TwoColumnLeft")) {
                viewType = 4;
            } else if (pageLayout.equalsIgnoreCase("TwoColumnRight")) {
                viewType = 6;
            } else if (pageLayout.equalsIgnoreCase("TwoPageLeft")) {
                viewType = 3;
            } else if (pageLayout.equalsIgnoreCase("TwoPageRight")) {
                viewType = 5;
            }

            this.documentViewController.setViewType(viewType);
        }

        if (this.documentViewController.getViewMode() == 7) {
            this.documentViewController.revertViewType();
        }

        if (this.isPdfCollection()) {
            this.documentViewController.setViewType(7);
        }

        Name pageMode;
        if (this.utilityTabbedPane != null) {
            pageMode = catalog.getPageMode();
            showUtilityPane = pageMode.equals(Catalog.PAGE_MODE_USE_OUTLINES_VALUE) || pageMode.equals(Catalog.PAGE_MODE_OPTIONAL_CONTENT_VALUE) || pageMode.equals(Catalog.PAGE_MODE_USE_ATTACHMENTS_VALUE) || pageMode.equals(Catalog.PAGE_MODE_USE_THUMBS_VALUE);
        }

        if (showUtilityPane) {
            pageMode = catalog.getPageMode();
            if (pageMode.equals(Catalog.PAGE_MODE_USE_OUTLINES_VALUE) && this.utilityTabbedPane.indexOfComponent(this.outlinesScrollPane) > 0) {
                this.utilityTabbedPane.setSelectedComponent(this.outlinesScrollPane);
            } else if (pageMode.equals(Catalog.PAGE_MODE_OPTIONAL_CONTENT_VALUE) && this.utilityTabbedPane.indexOfComponent(this.layersPanel) > 0) {
                this.utilityTabbedPane.setSelectedComponent(this.layersPanel);
            } else if (pageMode.equals(Catalog.PAGE_MODE_USE_ATTACHMENTS_VALUE) && this.utilityTabbedPane.indexOfComponent(this.attachmentPanel) > 0) {
                this.utilityTabbedPane.setSelectedComponent(this.attachmentPanel);
            } else if (pageMode.equals(Catalog.PAGE_MODE_USE_THUMBS_VALUE) && this.utilityTabbedPane.indexOfComponent(this.thumbnailsPanel) > 0) {
                this.utilityTabbedPane.setSelectedComponent(this.thumbnailsPanel);
            } else {
                showUtilityPane = false;
            }
        }

        this.documentViewController.setDocument(this.document);
        if (this.layersPanel != null) {
            this.layersPanel.setDocument(this.document);
        }

        if (this.signaturesPanel != null) {
            this.signaturesPanel.setDocument(this.document);
        }

        if (this.attachmentPanel != null) {
            this.attachmentPanel.setDocument(this.document);
        }

        if (this.propertiesManager == null && this.windowManagementCallback != null) {
            this.propertiesManager = this.windowManagementCallback.getProperties();
        }

        float defaultZoom = (float)PropertiesManager.checkAndStoreDoubleProperty(this.propertiesManager, "application.zoom.factor.default");
        this.documentViewController.setZoom(defaultZoom);
        this.setPageFitMode(PropertiesManager.checkAndStoreIntegerProperty(this.propertiesManager, "document.pagefitMode", 1), false);
        this.applyViewerPreferences(catalog, this.propertiesManager);
        OutlineItem item = null;
        Outlines outlines = this.document.getCatalog().getOutlines();
        if (outlines != null && this.outlinesTree != null) {
            item = outlines.getRootOutlineItem();
        }

        if (item != null) {
            this.outlinesTree.setModel(new DefaultTreeModel(new OutlineItemTreeNode(item)));
            this.outlinesTree.setRootVisible(!item.isEmpty());
            this.outlinesTree.setShowsRootHandles(true);
            if (this.utilityTabbedPane != null && this.outlinesScrollPane != null && this.utilityTabbedPane.indexOfComponent(this.outlinesScrollPane) > -1) {
                this.utilityTabbedPane.setEnabledAt(this.utilityTabbedPane.indexOfComponent(this.outlinesScrollPane), true);
            }
        } else if (this.utilityTabbedPane != null && this.outlinesScrollPane != null && this.utilityTabbedPane.indexOfComponent(this.outlinesScrollPane) > -1) {
            this.utilityTabbedPane.setEnabledAt(this.utilityTabbedPane.indexOfComponent(this.outlinesScrollPane), false);
        }

        boolean hideUtilityPane = PropertiesManager.checkAndStoreBooleanProperty(this.propertiesManager, "application.utilitypane.hide", false);
        if (hideUtilityPane) {
            this.setUtilityPaneVisible(false);
        } else {
            this.setUtilityPaneVisible(showUtilityPane);
        }

        boolean showFormHighlight = PropertiesManager.checkAndStoreBooleanProperty(this.propertiesManager, "application.viewerpreferences.form.highlight", true);
        this.setFormHighlightVisible(showFormHighlight);
        OptionalContent optionalContent = this.document.getCatalog().getOptionalContent();
        if (this.layersPanel != null && this.utilityTabbedPane != null) {
            if (optionalContent != null && optionalContent.getOrder() != null) {
                if (this.utilityTabbedPane.indexOfComponent(this.layersPanel) > -1) {
                    this.utilityTabbedPane.setEnabledAt(this.utilityTabbedPane.indexOfComponent(this.layersPanel), true);
                }
            } else if (this.utilityTabbedPane.indexOfComponent(this.layersPanel) > -1) {
                this.utilityTabbedPane.setEnabledAt(this.utilityTabbedPane.indexOfComponent(this.layersPanel), false);
            }
        }

        if (this.attachmentPanel != null && this.utilityTabbedPane != null) {
            if (catalog.getEmbeddedFilesNameTree() != null && catalog.getEmbeddedFilesNameTree().getRoot() != null) {
                if (this.utilityTabbedPane.indexOfComponent(this.attachmentPanel) > -1) {
                    this.utilityTabbedPane.setEnabledAt(this.utilityTabbedPane.indexOfComponent(this.attachmentPanel), true);
                }
            } else if (this.utilityTabbedPane.indexOfComponent(this.attachmentPanel) > -1) {
                this.utilityTabbedPane.setEnabledAt(this.utilityTabbedPane.indexOfComponent(this.attachmentPanel), false);
            }
        }

        boolean signaturesExist = this.document.getCatalog().getInteractiveForm() != null && this.document.getCatalog().getInteractiveForm().isSignatureFields();
        if (this.signaturesPanel != null && this.utilityTabbedPane != null) {
            if (signaturesExist) {
                if (this.utilityTabbedPane.indexOfComponent(this.signaturesPanel) > -1) {
                    this.utilityTabbedPane.setEnabledAt(this.utilityTabbedPane.indexOfComponent(this.signaturesPanel), true);
                }
            } else if (this.utilityTabbedPane.indexOfComponent(this.signaturesPanel) > -1) {
                this.utilityTabbedPane.setEnabledAt(this.utilityTabbedPane.indexOfComponent(this.signaturesPanel), false);
            }
        }

        if (this.viewer != null) {
            Object[] messageArguments = new Object[]{fileDescription};
            MessageFormat formatter = new MessageFormat(messageBundle.getString("viewer.window.title.open.default"));
            this.viewer.setTitle(formatter.format(messageArguments));
        }

        if (this.annotationPanel != null) {
            this.annotationPanel.setEnabled(false);
        }

        this.reflectStateInComponents();
        this.updateDocumentView();
    }

    public void closeDocument() {
        if (this.searchPanel != null) {
            this.searchPanel.setDocument((Document)null);
        }

        if (this.thumbnailsPanel != null) {
            this.thumbnailsPanel.setDocument((Document)null);
        }

        if (this.layersPanel != null) {
            this.layersPanel.setDocument((Document)null);
        }

        if (this.attachmentPanel != null) {
            this.attachmentPanel.setDocument((Document)null);
        }

        if (this.signaturesPanel != null) {
            this.signaturesPanel.setDocument((Document)null);
        }

        this.documentViewController.closeDocument();
        this.documentSearchController.dispose();
        if (this.document != null) {
            this.document.dispose();
            this.document = null;
        }

        if (this.currentPageNumberTextField != null) {
            this.currentPageNumberTextField.setText("");
        }

        if (this.numberOfPagesLabel != null) {
            this.numberOfPagesLabel.setText("");
        }

        if (this.currentPageNumberTextField != null) {
            this.currentPageNumberTextField.setEnabled(false);
        }

        if (this.statusLabel != null) {
            this.statusLabel.setText(" ");
        }

        if (this.zoomComboBox != null) {
            this.zoomComboBox.setSelectedItem(NumberFormat.getPercentInstance().format(1.0D));
        }

        this.updateDocumentView();
        TreeModel treeModel = this.outlinesTree != null ? this.outlinesTree.getModel() : null;
        if (treeModel != null) {
            OutlineItemTreeNode root = (OutlineItemTreeNode)treeModel.getRoot();
            if (root != null) {
                root.recursivelyClearOutlineItems();
            }

            this.outlinesTree.getSelectionModel().clearSelection();
            this.outlinesTree.getSelectionModel().setSelectionPath((TreePath)null);
            this.outlinesTree.setSelectionPath((TreePath)null);
            this.outlinesTree.setModel((TreeModel)null);
        }

        this.setUtilityPaneVisible(false);
        if (this.viewer != null) {
            this.viewer.setTitle(messageBundle.getString("viewer.window.title.default"));
            this.viewer.invalidate();
            this.viewer.validate();
            this.viewer.getContentPane().repaint();
        }

        this.reflectStateInComponents();
    }

    public void dispose() {
        if (!this.disposed) {
            this.disposed = true;
            this.closeDocument();
            this.openFileMenuItem = null;
            this.openURLMenuItem = null;
            this.closeMenuItem = null;
            this.saveAsFileMenuItem = null;
            this.exportTextMenuItem = null;
            this.exportSVGMenuItem = null;
            this.permissionsMenuItem = null;
            this.informationMenuItem = null;
            this.printSetupMenuItem = null;
            this.printMenuItem = null;
            this.exitMenuItem = null;
            this.fitActualSizeMenuItem = null;
            this.fitPageMenuItem = null;
            this.fitWidthMenuItem = null;
            this.zoomInMenuItem = null;
            this.zoomOutMenuItem = null;
            this.rotateLeftMenuItem = null;
            this.rotateRightMenuItem = null;
            this.showHideToolBarMenuItem = null;
            this.showHideUtilityPaneMenuItem = null;
            this.firstPageMenuItem = null;
            this.previousPageMenuItem = null;
            this.nextPageMenuItem = null;
            this.lastPageMenuItem = null;
            this.searchMenuItem = null;
            this.goToPageMenuItem = null;
            this.minimiseAllMenuItem = null;
            this.bringAllToFrontMenuItem = null;
            this.windowListMenuItems = null;
            this.aboutMenuItem = null;
            this.openFileButton = null;
            this.saveAsFileButton = null;
            this.printButton = null;
            this.searchButton = null;
            this.showHideUtilityPaneButton = null;
            this.firstPageButton = null;
            this.previousPageButton = null;
            this.nextPageButton = null;
            this.lastPageButton = null;
            if (this.currentPageNumberTextField != null) {
                this.currentPageNumberTextField.removeActionListener(this);
                this.currentPageNumberTextField.removeFocusListener(this);
                this.currentPageNumberTextField.removeKeyListener(this);
                this.currentPageNumberTextField = null;
            }

            this.numberOfPagesLabel = null;
            this.zoomInButton = null;
            this.zoomOutButton = null;
            if (this.zoomComboBox != null) {
                this.zoomComboBox.removeItemListener(this);
                this.zoomComboBox = null;
            }

            this.fitActualSizeButton = null;
            this.fitHeightButton = null;
            this.fitWidthButton = null;
            this.rotateLeftButton = null;
            this.rotateRightButton = null;
            this.panToolButton = null;
            this.zoomInToolButton = null;
            this.zoomDynamicToolButton = null;
            this.textSelectToolButton = null;
            this.selectToolButton = null;
            this.linkAnnotationToolButton = null;
            this.highlightAnnotationToolButton = null;
            this.highlightAnnotationUtilityToolButton = null;
            this.underlineAnnotationToolButton = null;
            this.strikeOutAnnotationToolButton = null;
            this.lineAnnotationToolButton = null;
            this.lineArrowAnnotationToolButton = null;
            this.squareAnnotationToolButton = null;
            this.circleAnnotationToolButton = null;
            this.inkAnnotationToolButton = null;
            this.freeTextAnnotationToolButton = null;
            this.textAnnotationToolButton = null;
            this.textAnnotationUtilityToolButton = null;
            this.formHighlightButton = null;
            this.fontEngineButton = null;
            this.completeToolBar = null;
            this.outlinesTree = null;
            if (this.outlinesScrollPane != null) {
                this.outlinesScrollPane.removeAll();
                this.outlinesScrollPane = null;
            }

            if (this.searchPanel != null) {
                this.searchPanel.dispose();
                this.searchPanel = null;
            }

            if (this.thumbnailsPanel != null) {
                this.thumbnailsPanel.dispose();
                this.thumbnailsPanel = null;
            }

            if (this.layersPanel != null) {
                this.layersPanel.dispose();
            }

            if (this.attachmentPanel != null) {
                this.attachmentPanel.dispose();
            }

            if (this.signaturesPanel != null) {
                this.signaturesPanel.dispose();
            }

            if (this.utilityTabbedPane != null) {
                this.utilityTabbedPane.removeAll();
                this.utilityTabbedPane = null;
            }

            if (this.documentViewController != null) {
                this.documentViewController.removePropertyChangeListener(this);
                this.documentViewController.dispose();
            }

            if (this.documentSearchController != null) {
                this.documentSearchController.dispose();
            }

            if (this.utilityAndDocumentSplitPane != null) {
                this.utilityAndDocumentSplitPane.removeAll();
                this.utilityAndDocumentSplitPane.removePropertyChangeListener(this);
            }

            this.statusLabel = null;
            if (this.viewer != null) {
                this.viewer.removeWindowListener(this);
                this.viewer.removeAll();
            }

            this.viewModel = null;
            this.windowManagementCallback = null;
        }

    }

    public void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(messageBundle.getString("viewer.dialog.saveAs.title"));
        fileChooser.setFileSelectionMode(0);
        fileChooser.addChoosableFileFilter(FileExtensionUtils.getPDFFileFilter());
        if (ViewModel.getDefaultFile() != null) {
            fileChooser.setCurrentDirectory(ViewModel.getDefaultFile());
        }

        String origin = this.document.getDocumentOrigin();
        String originalFileName = null;
        int lastSeparator;
        if (origin != null) {
            lastSeparator = Math.max(Math.max(origin.lastIndexOf("/"), origin.lastIndexOf("\\")), origin.lastIndexOf(File.separator));
            if (lastSeparator >= 0) {
                originalFileName = origin.substring(lastSeparator + 1);
                if (originalFileName.length() > 0) {
                    fileChooser.setSelectedFile(new File(this.generateNewSaveName(originalFileName)));
                } else {
                    originalFileName = null;
                }
            }
        }

        lastSeparator = fileChooser.showSaveDialog(this.viewer);
        if (lastSeparator == 0) {
            File file = fileChooser.getSelectedFile();
            String extension = FileExtensionUtils.getExtension(file);
            if (extension == null) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.saveAs.noExtensionError.title", "viewer.dialog.saveAs.noExtensionError.msg");
                this.saveFile();
            } else if (!extension.equals("pdf")) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.saveAs.extensionError.title", "viewer.dialog.saveAs.extensionError.msg", file.getName());
                this.saveFile();
            } else if (originalFileName != null && originalFileName.equalsIgnoreCase(file.getName())) {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.saveAs.noneUniqueName.title", "viewer.dialog.saveAs.noneUniqueName.msg", file.getName());
                this.saveFile();
            } else {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    BufferedOutputStream buf = new BufferedOutputStream(fileOutputStream, 8192);
                    if (this.document.getStateManager().isChanged() && !Document.foundIncrementalUpdater) {
                        Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.saveAs.noUpdates.title", "viewer.dialog.saveAs.noUpdates.msg");
                    } else if (!this.document.getStateManager().isChanged()) {
                        this.document.writeToOutputStream(buf);
                    } else {
                        this.document.saveToOutputStream(buf);
                    }

                    buf.flush();
                    fileOutputStream.flush();
                    buf.close();
                    fileOutputStream.close();
                } catch (MalformedURLException var9) {
                    logger.log(Level.FINE, "Malformed URL Exception ", var9);
                } catch (IOException var10) {
                    logger.log(Level.FINE, "IO Exception ", var10);
                }

                ViewModel.setDefaultFile(file);
            }
        }

    }

    protected String generateNewSaveName(String fileName) {
        if (fileName != null) {
            int endIndex = fileName.toLowerCase().indexOf("pdf") - 1;
            String result;
            if (endIndex < 0) {
                result = fileName + "-new.pdf";
            } else {
                String var10000 = fileName.substring(0, endIndex);
                result = var10000 + "-new.pdf";
            }

            return result;
        } else {
            return null;
        }
    }

    public void exportText() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(messageBundle.getString("viewer.dialog.exportText.title"));
        fileChooser.setFileSelectionMode(0);
        fileChooser.addChoosableFileFilter(FileExtensionUtils.getTextFileFilter());
        if (ViewModel.getDefaultFile() != null) {
            fileChooser.setCurrentDirectory(ViewModel.getDefaultFile());
        }

        int returnVal = fileChooser.showSaveDialog(this.viewer);
        if (returnVal == 0) {
            File file = fileChooser.getSelectedFile();
            String extension = FileExtensionUtils.getExtension(file);
            if (extension != null) {
                ViewModel.setDefaultFile(file);
                TextExtractionTask textExtractionTask = new TextExtractionTask(this.document, file, messageBundle);
                ProgressMonitor progressMonitor = new ProgressMonitor(this.viewer, messageBundle.getString("viewer.dialog.exportText.progress.msg"), "", 0, textExtractionTask.getLengthOfTask());
                progressMonitor.setProgress(0);
                progressMonitor.setMillisToDecideToPopup(0);
                TextExtractionGlue glue = new TextExtractionGlue(textExtractionTask, progressMonitor);
                Timer timer = new Timer(1000, glue);
                glue.setTimer(timer);
                textExtractionTask.go();
                timer.start();
            } else {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.exportText.noExtensionError.title", "viewer.dialog.exportText.noExtensionError.msg");
                this.exportText();
            }
        }

    }

    public void exportSVG() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(messageBundle.getString("viewer.dialog.exportSVG.title"));
        fileChooser.setFileSelectionMode(0);
        fileChooser.addChoosableFileFilter(FileExtensionUtils.getSVGFileFilter());
        if (ViewModel.getDefaultFile() != null) {
            fileChooser.setCurrentDirectory(ViewModel.getDefaultFile());
        }

        int returnVal = fileChooser.showSaveDialog(this.viewer);
        if (returnVal == 0) {
            final File file = fileChooser.getSelectedFile();
            String extension = FileExtensionUtils.getExtension(file);
            if (extension != null) {
                if (extension.equals("svg")) {
                    final Document doc = this.document;
                    final int pageIndex = this.documentViewController.getCurrentPageIndex();
                    if (this.statusLabel != null) {
                        Object[] messageArguments = new Object[]{String.valueOf(pageIndex + 1), file.getName()};
                        MessageFormat formatter = new MessageFormat(messageBundle.getString("viewer.dialog.exportSVG.status.exporting.msg"));
                        this.statusLabel.setText(formatter.format(messageArguments));
                    }

                    SwingWorker worker = new SwingWorker() {
                        public Object construct() {
                            String error = null;

                            try {
                                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                                SVG.createSVG(doc, pageIndex, out);
                                out.close();
                            } catch (Throwable var6) {
                                error = var6.getMessage();
                                SwingController.logger.log(Level.FINE, "Error exporting to SVG");
                            }

                            MessageFormat formatter;
                            final String tmpMsg;
                            Object[] messageArguments;
                            if (error == null) {
                                messageArguments = new Object[]{String.valueOf(pageIndex + 1), file.getName()};
                                formatter = new MessageFormat(SwingController.messageBundle.getString("viewer.dialog.exportSVG.status.exporting.msg"));
                                tmpMsg = formatter.format(messageArguments);
                            } else {
                                messageArguments = new Object[]{String.valueOf(pageIndex + 1), file.getName(), error};
                                formatter = new MessageFormat(SwingController.messageBundle.getString("viewer.dialog.exportSVG.status.error.msg"));
                                tmpMsg = formatter.format(messageArguments);
                            }

                            Runnable doSwingWork = new Runnable() {
                                public void run() {
                                    if (SwingController.this.statusLabel != null) {
                                        SwingController.this.statusLabel.setText(tmpMsg);
                                    }

                                }
                            };
                            SwingUtilities.invokeLater(doSwingWork);
                            return null;
                        }
                    };
                    worker.setThreadPriority(1);
                    worker.start();
                } else {
                    Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.exportSVG.exportError.title", "viewer.dialog.exportSVG.exportError.msg", file.getName());
                }

                ViewModel.setDefaultFile(file);
            } else {
                Resources.showMessageDialog(this.viewer, 1, messageBundle, "viewer.dialog.exportSVG.noExtensionError.title", "viewer.dialog.exportSVG.noExtensionError.msg");
                this.exportSVG();
            }
        }

    }

    public boolean saveChangesDialog() {
        if (this.document != null) {
            boolean documentChanges = this.document.getStateManager().isChanged();
            if (documentChanges && Document.foundIncrementalUpdater) {
                Object[] colorArgument = new Object[]{this.document.getDocumentOrigin()};
                MessageFormat formatter = new MessageFormat(messageBundle.getString("viewer.dialog.saveOnClose.noUpdates.msg"));
                String dialogMessage = formatter.format(colorArgument);
                int res = JOptionPane.showConfirmDialog(this.viewer, dialogMessage, messageBundle.getString("viewer.dialog.saveOnClose.noUpdates.title"), 1);
                if (res == 0) {
                    this.saveFile();
                } else if (res != 1 && res == 2) {
                    return true;
                }
            }
        }

        return false;
    }

    public void toggleToolBarVisibility() {
        if (this.completeToolBar != null) {
            this.setToolBarVisible(!this.completeToolBar.isVisible());
        }

    }

    public void setToolBarVisible(boolean show) {
        if (this.completeToolBar != null) {
            this.completeToolBar.setVisible(show);
        }

        this.reflectStateInComponents();
    }

    public void showAboutDialog() {
        Runnable doSwingWork = new Runnable() {
            public void run() {
                AboutDialog ad = new AboutDialog(SwingController.this.viewer, SwingController.messageBundle, true, 2, 0);
                ad.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(doSwingWork);
    }

    public void showDocumentPermissionsDialog() {
        PermissionsDialog pd = new PermissionsDialog(this.viewer, this.document, messageBundle);
        pd.setVisible(true);
    }

    public void showDocumentInformationDialog() {
        DocumentInformationDialog did = new DocumentInformationDialog(this.viewer, this.document, messageBundle);
        did.setVisible(true);
    }

    public void showPrintSetupDialog() {
        PrintHelper printHelper = this.viewModel.getPrintHelper();
        if (printHelper == null) {
            MediaSizeName mediaSizeName = this.loadDefaultPrinterProperties();
            printHelper = new PrintHelper(this.documentViewController.getViewContainer(), this.getPageTree(), this.documentViewController.getRotation(), mediaSizeName, PrintQuality.NORMAL);
        } else {
            printHelper = new PrintHelper(this.documentViewController.getViewContainer(), this.getPageTree(), this.documentViewController.getRotation(), printHelper.getDocAttributeSet(), printHelper.getPrintRequestAttributeSet());
        }

        this.viewModel.setPrintHelper(printHelper);
        this.viewModel.getPrintHelper().showPrintSetupDialog();
        this.savePrinterProperties(printHelper);
    }

    public void setPrintDefaultMediaSizeName(MediaSizeName mediaSize) {
        PrintHelper printHelper = new PrintHelper(this.documentViewController.getViewContainer(), this.getPageTree(), this.documentViewController.getRotation(), mediaSize, PrintQuality.NORMAL);
        this.viewModel.setPrintHelper(printHelper);
        this.savePrinterProperties(printHelper);
    }

    public void print(final boolean withDialog) {
        if (this.printMenuItem != null) {
            this.printMenuItem.setEnabled(false);
        }

        if (this.printButton != null) {
            this.printButton.setEnabled(false);
        }

        Runnable runner = new Runnable() {
            public void run() {
                SwingController.this.initialisePrinting(withDialog);
            }
        };
        Thread t = new Thread(runner);
        t.setPriority(1);
        t.start();
    }

    private void initialisePrinting(boolean withDialog) {
        boolean canPrint = this.havePermissionToPrint();
        if (!canPrint) {
            this.reenablePrintUI();
        } else {
            PrintHelper printHelper = this.viewModel.getPrintHelper();
            if (printHelper == null) {
                MediaSizeName mediaSizeName = this.loadDefaultPrinterProperties();
                printHelper = new PrintHelper(this.documentViewController.getViewContainer(), this.getPageTree(), this.documentViewController.getRotation(), mediaSizeName, PrintQuality.HIGH);
            } else {
                printHelper = new PrintHelper(this.documentViewController.getViewContainer(), this.getPageTree(), this.documentViewController.getRotation(), printHelper.getDocAttributeSet(), printHelper.getPrintRequestAttributeSet());
            }

            printHelper.setSwingController(this);
            HashPrintRequestAttributeSet printRequestAttributeSet = printHelper.getPrintRequestAttributeSet();
            printRequestAttributeSet.add(new JobName((String)this.data.get("File"), (Locale)null));
            this.viewModel.setPrintHelper(printHelper);
            canPrint = printHelper.setupPrintService(0, this.document.getNumberOfPages() - 1, this.viewModel.getPrintCopies(), this.viewModel.isShrinkToPrintableArea(), withDialog);
            this.savePrinterProperties(printHelper);
            if (!canPrint) {
                this.reenablePrintUI();
            } else {
                this.startBackgroundPrinting(printHelper);
            }
        }

    }

    private MediaSizeName loadDefaultPrinterProperties() {
        int printMediaUnit = PropertiesManager.checkAndStoreIntegerProperty(this.propertiesManager, "document.print.mediaSize.unit", 1000);
        double printMediaWidth = PropertiesManager.checkAndStoreDoubleProperty(this.propertiesManager, "document.print.mediaSize.width", 215.9D);
        double printMediaHeight = PropertiesManager.checkAndStoreDoubleProperty(this.propertiesManager, "document.print.mediaSize.height", 279.4D);
        return MediaSize.findMedia((float)printMediaWidth, (float)printMediaHeight, printMediaUnit);
    }

    private void savePrinterProperties(PrintHelper printHelper) {
        PrintRequestAttributeSet printRequestAttributeSet = printHelper.getPrintRequestAttributeSet();
        Object printAttributeSet = printRequestAttributeSet.get(Media.class);
        if (this.propertiesManager != null && printAttributeSet instanceof MediaSizeName) {
            MediaSizeName paper = (MediaSizeName)printAttributeSet;
            MediaSize mediaSize = MediaSize.getMediaSizeForName(paper);
            int printMediaUnit = 1000;
            this.propertiesManager.set("document.print.mediaSize.unit", String.valueOf(printMediaUnit));
            double printMediaWidth = (double)mediaSize.getX(printMediaUnit);
            this.propertiesManager.set("document.print.mediaSize.width", String.valueOf(printMediaWidth));
            double printMediaHeight = (double)mediaSize.getY(printMediaUnit);
            this.propertiesManager.set("document.print.mediaSize.height", String.valueOf(printMediaHeight));
        }

    }

    private void reenablePrintUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (SwingController.this.printMenuItem != null) {
                    SwingController.this.printMenuItem.setEnabled(true);
                }

                if (SwingController.this.printButton != null) {
                    SwingController.this.printButton.setEnabled(true);
                }

            }
        });
    }

    private void startBackgroundPrinting(final PrintHelper printHelper) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SwingController.this.printProgressMonitor = new ProgressMonitor(SwingController.this.viewer, SwingController.messageBundle.getString("viewer.dialog.printing.status.start.msg"), "", 0, printHelper.getNumberOfPages());
            }
        });
        final Thread printingThread = Thread.currentThread();
        final PrinterTask printerTask = new PrinterTask(printHelper);
        this.printActivityMonitor = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int limit = printHelper.getNumberOfPages();
                int current = printHelper.getCurrentPage();
                SwingController.this.printProgressMonitor.setProgress(current);
                Object[] messageArguments = new Object[]{String.valueOf(current), String.valueOf(limit)};
                MessageFormat formatter = new MessageFormat(SwingController.messageBundle.getString("viewer.dialog.printing.status.progress.msg"));
                SwingController.this.printProgressMonitor.setNote(formatter.format(messageArguments));
                if (!printingThread.isAlive() || SwingController.this.printProgressMonitor.isCanceled()) {
                    SwingController.this.printProgressMonitor.close();
                    SwingController.this.printActivityMonitor.stop();
                    printerTask.cancel();
                    if (SwingController.this.printMenuItem != null) {
                        SwingController.this.printMenuItem.setEnabled(true);
                    }

                    if (SwingController.this.printButton != null) {
                        SwingController.this.printButton.setEnabled(true);
                    }
                }

            }
        });
        this.printActivityMonitor.start();
        printerTask.run();
    }

    public void showPageFromTextField() {
        String ob = this.currentPageNumberTextField.getText();
        if (ob != null) {
            try {
                int pageIndex = Integer.parseInt(ob) - 1;
                this.showPage(pageIndex);
            } catch (NumberFormatException var3) {
                logger.log(Level.FINE, "Error converting page number.");
            }
        }

    }

    public void followOutlineItem(OutlineItem o) {
        if (o != null) {
            int oldTool = this.getDocumentViewToolMode();

            try {
                this.outlinesTree.setCursor(Cursor.getPredefinedCursor(3));
                this.setDisplayTool(51);
                Destination dest = o.getDest();
                if (o.getAction() != null) {
                    Action action = o.getAction();
                    if (action instanceof GoToAction) {
                        dest = ((GoToAction)action).getDestination();
                    } else if (action instanceof URIAction) {
                        BareBonesBrowserLaunch.openURL(((URIAction)action).getURI());
                    } else {
                        Library library = action.getLibrary();
                        HashMap entries = action.getEntries();
                        dest = new Destination(library, library.getObject(entries, Destination.D_KEY));
                    }
                } else if (dest.getNamedDestination() != null) {
                    NamedDestinations namedDestinations = this.document.getCatalog().getDestinations();
                    if (namedDestinations != null) {
                        dest = namedDestinations.getDestination(dest.getNamedDestination());
                    }
                }

                if (dest != null) {
                    this.documentViewController.setDestinationTarget(dest);
                    return;
                }
            } finally {
                this.setDisplayTool(oldTool);
                this.outlinesTree.setCursor(Cursor.getPredefinedCursor(0));
            }

        }
    }

    private boolean isDragAcceptable(DropTargetDragEvent event) {
        return (event.getDropAction() & 3) != 0;
    }

    private boolean isDropAcceptable(DropTargetDropEvent event) {
        return (event.getDropAction() & 3) != 0;
    }

    public void zoomIn() {
        this.documentViewController.setZoomIn();
    }

    public void zoomOut() {
        this.documentViewController.setZoomOut();
    }

    public void setZoom(float zoom) {
        this.documentViewController.setZoom(zoom);
    }

    public void doCommonZoomUIUpdates(boolean becauseOfValidFitMode) {
        this.reflectZoomInZoomComboBox();
        if (!becauseOfValidFitMode) {
            this.setPageFitMode(1, false);
        }

    }

    public boolean isCurrentPage() {
        PageTree pageTree = this.getPageTree();
        if (pageTree == null) {
            return false;
        } else {
            Page page = pageTree.getPage(this.documentViewController.getCurrentPageIndex());
            return page != null;
        }
    }

    public PageTree getPageTree() {
        return this.document == null ? null : this.document.getPageTree();
    }

    public void showPage(int nPage) {
        if (nPage >= 0 && nPage < this.getPageTree().getNumberOfPages()) {
            this.documentViewController.setCurrentPageIndex(nPage);
            this.updateDocumentView();
        }

    }

    public void goToDeltaPage(int delta) {
        int currPage = this.documentViewController.getCurrentPageIndex();
        int nPage = currPage + delta;
        int totalPages = this.getPageTree().getNumberOfPages();
        if (totalPages != 0) {
            if (nPage >= totalPages) {
                nPage = totalPages - 1;
            }

            if (nPage < 0) {
                nPage = 0;
            }

            if (nPage != currPage) {
                this.documentViewController.setCurrentPageIndex(nPage);
                this.updateDocumentView();
            }
        }

    }

    public void updateDocumentView() {
        if (!this.disposed) {
            int oldTool = this.getDocumentViewToolMode();

            try {
                this.setDisplayTool(51);
                this.reflectPageChangeInComponents();
                PageTree pageTree = this.getPageTree();
                if (this.currentPageNumberTextField != null) {
                    this.currentPageNumberTextField.setText(Integer.toString(this.documentViewController.getCurrentPageDisplayValue()));
                }

                Object[] messageArguments;
                MessageFormat formatter;
                if (this.numberOfPagesLabel != null && pageTree != null) {
                    messageArguments = new Object[]{String.valueOf(pageTree.getNumberOfPages())};
                    formatter = new MessageFormat(messageBundle.getString("viewer.toolbar.pageIndicator"));
                    String numberOfPages = formatter.format(messageArguments);
                    this.numberOfPagesLabel.setText(numberOfPages);
                }

                if (this.statusLabel != null && pageTree != null) {
                    messageArguments = new Object[]{String.valueOf(this.documentViewController.getCurrentPageDisplayValue()), String.valueOf(pageTree.getNumberOfPages())};
                    formatter = new MessageFormat(messageBundle.getString("viewer.statusbar.currentPage"));
                    this.statusLabel.setText(formatter.format(messageArguments));
                }
            } catch (Exception var9) {
                logger.log(Level.FINE, "Error updating page view.", var9);
            } finally {
                this.setDisplayTool(oldTool);
            }
        }

    }

    public void rotateLeft() {
        this.documentViewController.setRotateLeft();
        this.setPageFitMode(this.documentViewController.getFitMode(), true);
    }

    public void rotateRight() {
        this.documentViewController.setRotateRight();
        this.setPageFitMode(this.documentViewController.getFitMode(), true);
    }

    public boolean isDocumentFitMode(int fitMode) {
        return this.documentViewController.getFitMode() == fitMode;
    }

    public boolean isDocumentViewMode(int viewMode) {
        return this.documentViewController.getViewMode() == viewMode;
    }

    public void setPageViewSinglePageConButton(JToggleButton btn) {
        this.singlePageViewContinuousButton = btn;
        btn.addItemListener(this);
    }

    public void setPageViewFacingPageConButton(JToggleButton btn) {
        this.facingPageViewContinuousButton = btn;
        btn.addItemListener(this);
    }

    public void setPageViewSinglePageNonConButton(JToggleButton btn) {
        this.singlePageViewNonContinuousButton = btn;
        btn.addItemListener(this);
    }

    public void setPageViewFacingPageNonConButton(JToggleButton btn) {
        this.facingPageViewNonContinuousButton = btn;
        btn.addItemListener(this);
    }

    public void setPageFitMode(int fitMode, boolean refresh) {
        if (refresh || this.documentViewController.getFitMode() != fitMode) {
            this.documentViewController.setFitMode(fitMode);
            this.reflectZoomInZoomComboBox();
            this.reflectFitInFitButtons();
        }

    }

    public void setPageViewMode(int viewMode, boolean refresh) {
        if (refresh || this.documentViewController.getViewMode() != viewMode) {
            this.documentViewController.setViewType(viewMode);
            this.reflectDocumentViewModeInButtons();
            this.reflectFitInFitButtons();
        }

    }

    public void setDocumentToolMode(int toolType) {
        if (!this.documentViewController.isToolModeSelected(toolType)) {
            this.documentViewController.setToolMode(toolType);
            this.reflectToolInToolButtons();
        }

    }

    public boolean isUtilityPaneVisible() {
        return this.utilityTabbedPane != null && this.utilityTabbedPane.isVisible();
    }

    public void setUtilityPaneVisible(boolean visible) {
        if (this.utilityTabbedPane != null) {
            this.utilityTabbedPane.setVisible(visible);
        }

        if (this.utilityAndDocumentSplitPane != null) {
            if (visible) {
                this.utilityAndDocumentSplitPane.setDividerLocation(this.utilityAndDocumentSplitPaneLastDividerLocation);
                this.utilityAndDocumentSplitPane.setDividerSize(8);
            } else {
                int divLoc = this.utilityAndDocumentSplitPane.getDividerLocation();
                if (divLoc > 5) {
                    this.utilityAndDocumentSplitPaneLastDividerLocation = divLoc;
                }

                this.utilityAndDocumentSplitPane.setDividerSize(0);
            }
        }

        this.reflectStateInComponents();
    }

    private void setFormHighlightVisible(boolean visible) {
        this.viewModel.setIsWidgetAnnotationHighlight(visible);
        this.document.setFormHighlight(this.viewModel.isWidgetAnnotationHighlight());
        ((AbstractDocumentView)this.documentViewController.getDocumentView()).repaint();
    }

    public void toggleUtilityPaneVisibility() {
        this.setUtilityPaneVisible(!this.isUtilityPaneVisible());
    }

    public void toggleFormHighlight() {
        this.viewModel.setIsWidgetAnnotationHighlight(!this.viewModel.isWidgetAnnotationHighlight());
        this.propertiesManager.setBoolean("application.viewerpreferences.form.highlight", this.viewModel.isWidgetAnnotationHighlight());
        this.reflectFormHighlightButtons();
        this.setFormHighlightVisible(this.viewModel.isWidgetAnnotationHighlight());
    }

    protected boolean safelySelectUtilityPanel(Component comp) {
        if (this.utilityTabbedPane != null && comp != null && this.utilityTabbedPane.indexOfComponent(comp) > -1) {
            this.utilityTabbedPane.setSelectedComponent(comp);
            return true;
        } else {
            return false;
        }
    }

    public void showSearchPanel() {
        if (this.utilityTabbedPane != null && this.searchPanel != null) {
            if (!this.utilityTabbedPane.isVisible()) {
                this.setUtilityPaneVisible(true);
            }

            if (this.isUtilityPaneVisible()) {
                if (this.utilityTabbedPane.getSelectedComponent() != this.searchPanel) {
                    this.safelySelectUtilityPanel(this.searchPanel);
                }

                this.searchPanel.requestFocus();
            }
        }

    }

    public void showAnnotationPanel(AnnotationComponent selectedAnnotation) {
        if (this.utilityTabbedPane != null && this.annotationPanel != null) {
            if (selectedAnnotation != null) {
                this.annotationPanel.setEnabled(true);
                this.annotationPanel.setAnnotationComponent(selectedAnnotation);
            }

            this.setUtilityPaneVisible(true);
            if (this.utilityTabbedPane.getSelectedComponent() != this.annotationPanel) {
                this.safelySelectUtilityPanel(this.annotationPanel);
            }
        }

    }

    public void showPageSelectionDialog() {
        int numPages = this.getPageTree().getNumberOfPages();
        Object[] s = new Object[numPages];

        for(int i = 0; i < numPages; ++i) {
            s[i] = Integer.toString(i + 1);
        }

        Object initialSelection = s[this.documentViewController.getCurrentPageIndex()];
        Object ob = JOptionPane.showInputDialog(this.viewer, messageBundle.getString("viewer.dialog.goToPage.description.label"), messageBundle.getString("viewer.dialog.goToPage.title"), 3, (Icon)null, s, initialSelection);
        if (ob != null) {
            try {
                int pageIndex = Integer.parseInt(ob.toString()) - 1;
                this.showPage(pageIndex);
            } catch (NumberFormatException var6) {
                logger.log(Level.FINE, "Error selecting page number.");
            }
        }

    }

    protected void applyViewerPreferences(Catalog catalog, PropertiesManager propertiesManager) {
        if (catalog != null) {
            ViewerPreferences viewerPref = catalog.getViewerPreferences();
            if (viewerPref != null && viewerPref.hasHideToolbar()) {
                if (viewerPref.getHideToolbar() && this.completeToolBar != null) {
                    this.completeToolBar.setVisible(false);
                }
            } else if (this.completeToolBar != null) {
                this.completeToolBar.setVisible(!PropertiesManager.checkAndStoreBooleanProperty(propertiesManager, "application.viewerpreferences.hidetoolbar", false));
            }

            if (viewerPref != null && viewerPref.hasHideMenubar()) {
                if (viewerPref.getHideMenubar() && this.viewer != null && this.viewer.getJMenuBar() != null) {
                    this.viewer.getJMenuBar().setVisible(false);
                }
            } else if (this.viewer != null && this.viewer.getJMenuBar() != null) {
                this.viewer.getJMenuBar().setVisible(!PropertiesManager.checkAndStoreBooleanProperty(propertiesManager, "application.viewerpreferences.hidemenubar", false));
            }

            if (viewerPref != null && viewerPref.hasFitWindow()) {
                if (viewerPref.getFitWindow() && this.viewer != null) {
                    this.viewer.setSize(this.documentViewController.getDocumentView().getDocumentSize());
                }
            } else if (PropertiesManager.checkAndStoreBooleanProperty(propertiesManager, "application.viewerpreferences.fitwindow", false) && this.viewer != null) {
                this.viewer.setSize(this.documentViewController.getDocumentView().getDocumentSize());
            }
        }

    }

    public ViewModel getViewModel() {
        return this.viewModel;
    }

    public Document getDocument() {
        return this.document;
    }

    public int getCurrentPageNumber() {
        return this.documentViewController.getCurrentPageIndex();
    }

    public float getUserRotation() {
        return this.documentViewController.getRotation();
    }

    public float getUserZoom() {
        return this.documentViewController.getZoom();
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source != null) {
            boolean cancelSetFocus = false;

            try {
                Runnable doSwingWork;
                if (source != this.openFileMenuItem && source != this.openFileButton) {
                    if (source == this.openURLMenuItem) {
                        doSwingWork = new Runnable() {
                            public void run() {
                                SwingController.this.openURL();
                            }
                        };
                        SwingUtilities.invokeLater(doSwingWork);
                    } else {
                        boolean isCanceled;
                        if (source == this.closeMenuItem) {
                            isCanceled = this.saveChangesDialog();
                            if (!isCanceled) {
                                this.closeDocument();
                            }
                        } else if (source != this.saveAsFileMenuItem && source != this.saveAsFileButton) {
                            if (source == this.exportTextMenuItem) {
                                doSwingWork = new Runnable() {
                                    public void run() {
                                        SwingController.this.exportText();
                                    }
                                };
                                SwingUtilities.invokeLater(doSwingWork);
                            } else if (source == this.exportSVGMenuItem) {
                                doSwingWork = new Runnable() {
                                    public void run() {
                                        SwingController.this.exportSVG();
                                    }
                                };
                                SwingUtilities.invokeLater(doSwingWork);
                            } else if (source == this.exitMenuItem) {
                                isCanceled = this.saveChangesDialog();
                                if (!isCanceled && this.windowManagementCallback != null) {
                                    this.windowManagementCallback.disposeWindow(this, this.viewer, (Properties)null);
                                }
                            } else if (source == this.showHideToolBarMenuItem) {
                                this.toggleToolBarVisibility();
                            } else if (source == this.minimiseAllMenuItem) {
                                doSwingWork = new Runnable() {
                                    public void run() {
                                        SwingController sc = SwingController.this;
                                        if (sc.getWindowManagementCallback() != null) {
                                            sc.getWindowManagementCallback().minimiseAllWindows();
                                        }

                                    }
                                };
                                SwingUtilities.invokeLater(doSwingWork);
                            } else if (source == this.bringAllToFrontMenuItem) {
                                doSwingWork = new Runnable() {
                                    public void run() {
                                        SwingController sc = SwingController.this;
                                        if (sc.getWindowManagementCallback() != null) {
                                            sc.getWindowManagementCallback().bringAllWindowsToFront(sc);
                                        }

                                    }
                                };
                                SwingUtilities.invokeLater(doSwingWork);
                            } else {
                                final int documentIcon;
                                if (this.windowListMenuItems != null && this.windowListMenuItems.contains(source)) {
                                    documentIcon = this.windowListMenuItems.indexOf(source);
                                    doSwingWork = new Runnable() {
                                        public void run() {
                                            SwingController sc = SwingController.this;
                                            if (sc.getWindowManagementCallback() != null) {
                                                sc.getWindowManagementCallback().bringWindowToFront(documentIcon);
                                            }

                                        }
                                    };
                                    SwingUtilities.invokeLater(doSwingWork);
                                } else if (source == this.aboutMenuItem) {
                                    this.showAboutDialog();
                                } else if (source == this.fontInformationMenuItem) {
                                    (new FontDialog(this.viewer, this, true)).setVisible(true);
                                } else if (this.document != null) {
                                    documentIcon = this.getDocumentViewToolMode();

                                    try {
                                        this.setDisplayTool(51);
                                        if (source == this.permissionsMenuItem) {
                                            doSwingWork = new Runnable() {
                                                public void run() {
                                                    SwingController.this.showDocumentPermissionsDialog();
                                                }
                                            };
                                            SwingUtilities.invokeLater(doSwingWork);
                                        } else if (source == this.informationMenuItem) {
                                            doSwingWork = new Runnable() {
                                                public void run() {
                                                    SwingController.this.showDocumentInformationDialog();
                                                }
                                            };
                                            SwingUtilities.invokeLater(doSwingWork);
                                        } else if (source == this.printSetupMenuItem) {
                                            doSwingWork = new Runnable() {
                                                public void run() {
                                                    SwingController.this.showPrintSetupDialog();
                                                }
                                            };
                                            SwingUtilities.invokeLater(doSwingWork);
                                        } else if (source == this.printMenuItem) {
                                            this.print(true);
                                        } else if (source == this.printButton) {
                                            this.print(true);
                                        } else if (source == this.undoMenuItem) {
                                            this.documentViewController.undo();
                                            this.reflectUndoCommands();
                                        } else if (source == this.redoMenuItem) {
                                            this.documentViewController.redo();
                                            this.reflectUndoCommands();
                                        } else if (source == this.deleteMenuItem) {
                                            this.documentViewController.deleteCurrentAnnotation();
                                            this.reflectUndoCommands();
                                        } else if (source != this.copyMenuItem) {
                                            if (source == this.selectAllMenuItem) {
                                                this.documentViewController.selectAllText();
                                            } else if (source == this.deselectAllMenuItem) {
                                                this.documentViewController.clearSelectedText();
                                            } else if (source == this.fitActualSizeMenuItem) {
                                                this.setPageFitMode(2, false);
                                            } else if (source == this.fitPageMenuItem) {
                                                this.setPageFitMode(3, false);
                                            } else if (source == this.fitWidthMenuItem) {
                                                this.setPageFitMode(4, false);
                                            } else if (source != this.zoomInMenuItem && source != this.zoomInButton) {
                                                if (source != this.zoomOutMenuItem && source != this.zoomOutButton) {
                                                    if (source != this.rotateLeftMenuItem && source != this.rotateLeftButton) {
                                                        if (source != this.rotateRightMenuItem && source != this.rotateRightButton) {
                                                            if (source != this.showHideUtilityPaneMenuItem && source != this.showHideUtilityPaneButton) {
                                                                if (source == this.formHighlightButton) {
                                                                    this.toggleFormHighlight();
                                                                } else if (source != this.firstPageMenuItem && source != this.firstPageButton) {
                                                                    DocumentView documentView;
                                                                    if (source != this.previousPageMenuItem && source != this.previousPageButton) {
                                                                        if (source != this.nextPageMenuItem && source != this.nextPageButton) {
                                                                            if (source != this.lastPageMenuItem && source != this.lastPageButton) {
                                                                                if (source != this.searchMenuItem && source != this.searchButton) {
                                                                                    if (source == this.goToPageMenuItem) {
                                                                                        this.showPageSelectionDialog();
                                                                                    } else if (source == this.currentPageNumberTextField) {
                                                                                        this.showPageFromTextField();
                                                                                    } else {
                                                                                        logger.log(Level.FINE, "Unknown action event: " + source.toString());
                                                                                    }
                                                                                } else {
                                                                                    cancelSetFocus = true;
                                                                                    this.showSearchPanel();
                                                                                }
                                                                            } else {
                                                                                this.showPage(this.getPageTree().getNumberOfPages() - 1);
                                                                            }
                                                                        } else {
                                                                            documentView = this.documentViewController.getDocumentView();
                                                                            this.goToDeltaPage(documentView.getNextPageIncrement());
                                                                        }
                                                                    } else {
                                                                        documentView = this.documentViewController.getDocumentView();
                                                                        this.goToDeltaPage(-documentView.getPreviousPageIncrement());
                                                                    }
                                                                } else {
                                                                    this.showPage(0);
                                                                }
                                                            } else {
                                                                this.toggleUtilityPaneVisibility();
                                                            }
                                                        } else {
                                                            this.rotateRight();
                                                        }
                                                    } else {
                                                        this.rotateLeft();
                                                    }
                                                } else {
                                                    this.zoomOut();
                                                }
                                            } else {
                                                this.zoomIn();
                                            }
                                        } else if (this.document != null && this.havePermissionToExtractContent() && (!this.documentViewController.getDocumentViewModel().isSelectAll() || this.document.getNumberOfPages() <= 250)) {
                                            StringSelection stringSelection = new StringSelection(this.documentViewController.getSelectedText());
                                            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
                                        } else {
                                            doSwingWork = new Runnable() {
                                                public void run() {
                                                    Resources.showMessageDialog(SwingController.this.viewer, 1, SwingController.messageBundle, "viewer.dialog.information.copyAll.title", "viewer.dialog.information.copyAll.msg", 250);
                                                }
                                            };
                                            SwingUtilities.invokeLater(doSwingWork);
                                        }
                                    } finally {
                                        this.setDisplayTool(documentIcon);
                                    }
                                }
                            }
                        } else {
                            doSwingWork = new Runnable() {
                                public void run() {
                                    SwingController.this.saveFile();
                                }
                            };
                            SwingUtilities.invokeLater(doSwingWork);
                        }
                    }
                } else {
                    doSwingWork = new Runnable() {
                        public void run() {
                            SwingController.this.openFile();
                        }
                    };
                    SwingUtilities.invokeLater(doSwingWork);
                }
            } catch (final Exception var12) {
                Runnable doSwingWork = new Runnable() {
                    public void run() {
                        Resources.showMessageDialog(SwingController.this.viewer, 1, SwingController.messageBundle, "viewer.dialog.error.exception.title", "viewer.dialog.error.exception.msg", var12.getMessage());
                    }
                };
                SwingUtilities.invokeLater(doSwingWork);
                logger.log(Level.FINE, "Error processing action event.", var12);
            }

            if (!cancelSetFocus) {
                this.documentViewController.requestViewFocusInWindow();
            }
        }

    }

    public void focusGained(FocusEvent e) {
    }

    public void focusLost(FocusEvent e) {
        Object src = e.getSource();
        if (src != null && src == this.currentPageNumberTextField) {
            String fieldValue = this.currentPageNumberTextField.getText();
            String modelValue = Integer.toString(this.documentViewController.getCurrentPageDisplayValue());
            if (!fieldValue.equals(modelValue)) {
                this.currentPageNumberTextField.setText(modelValue);
            }
        }

    }

    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        if (source != null) {
            boolean doSetFocus = false;
            int tool = this.getDocumentViewToolMode();
            this.setDisplayTool(51);

            try {
                if (source == this.zoomComboBox) {
                    if (e.getStateChange() == 1) {
                        this.setZoomFromZoomComboBox();
                    }
                } else if (source == this.fitActualSizeButton) {
                    if (e.getStateChange() == 1) {
                        this.setPageFitMode(2, false);
                        doSetFocus = true;
                    }
                } else if (source == this.fitHeightButton) {
                    if (e.getStateChange() == 1) {
                        this.setPageFitMode(3, false);
                        doSetFocus = true;
                    }
                } else if (source == this.fitWidthButton) {
                    if (e.getStateChange() == 1) {
                        this.setPageFitMode(4, false);
                        doSetFocus = true;
                    }
                } else if (source == this.fontEngineButton) {
                    if (e.getStateChange() == 1 || e.getStateChange() == 2) {
                        FontFactory.getInstance().toggleAwtFontSubstitution();
                        ((AbstractDocumentView)this.documentViewController.getDocumentView()).firePropertyChange("documentViewDemoChange", false, true);
                        doSetFocus = true;
                    }
                } else if (source == this.panToolButton) {
                    if (e.getStateChange() == 1) {
                        tool = 1;
                        this.setDocumentToolMode(1);
                        doSetFocus = true;
                    }
                } else if (source == this.zoomInToolButton) {
                    if (e.getStateChange() == 1) {
                        tool = 2;
                        this.setDocumentToolMode(2);
                        doSetFocus = true;
                    }
                } else if (source == this.zoomDynamicToolButton) {
                    if (e.getStateChange() == 1) {
                        tool = 4;
                        this.setDocumentToolMode(4);
                        doSetFocus = true;
                    }
                } else if (source == this.textSelectToolButton) {
                    if (e.getStateChange() == 1) {
                        tool = 5;
                        this.setDocumentToolMode(5);
                        doSetFocus = true;
                    }
                } else if (source == this.selectToolButton) {
                    if (e.getStateChange() == 1) {
                        tool = 6;
                        this.setDocumentToolMode(6);
                        this.showAnnotationPanel((AnnotationComponent)null);
                    }
                } else if (source == this.linkAnnotationToolButton) {
                    if (e.getStateChange() == 1) {
                        tool = 7;
                        this.setDocumentToolMode(7);
                    }
                } else if (source != this.highlightAnnotationToolButton && source != this.highlightAnnotationUtilityToolButton) {
                    if (source == this.strikeOutAnnotationToolButton) {
                        if (e.getStateChange() == 1) {
                            tool = 11;
                            this.setDocumentToolMode(11);
                        }
                    } else if (source == this.underlineAnnotationToolButton) {
                        if (e.getStateChange() == 1) {
                            tool = 9;
                            this.setDocumentToolMode(9);
                        }
                    } else if (source == this.lineAnnotationToolButton) {
                        if (e.getStateChange() == 1) {
                            tool = 12;
                            this.setDocumentToolMode(12);
                        }
                    } else if (source == this.lineArrowAnnotationToolButton) {
                        if (e.getStateChange() == 1) {
                            tool = 13;
                            this.setDocumentToolMode(13);
                        }
                    } else if (source == this.squareAnnotationToolButton) {
                        if (e.getStateChange() == 1) {
                            tool = 14;
                            this.setDocumentToolMode(14);
                        }
                    } else if (source == this.circleAnnotationToolButton) {
                        if (e.getStateChange() == 1) {
                            tool = 15;
                            this.setDocumentToolMode(15);
                        }
                    } else if (source == this.inkAnnotationToolButton) {
                        if (e.getStateChange() == 1) {
                            tool = 16;
                            this.setDocumentToolMode(16);
                        }
                    } else if (source == this.freeTextAnnotationToolButton) {
                        if (e.getStateChange() == 1) {
                            tool = 17;
                            this.setDocumentToolMode(17);
                        }
                    } else if (source != this.textAnnotationToolButton && source != this.textAnnotationUtilityToolButton) {
                        if (source == this.facingPageViewNonContinuousButton) {
                            if (e.getStateChange() == 1) {
                                this.setPageViewMode(5, false);
                                doSetFocus = true;
                            }
                        } else if (source == this.facingPageViewContinuousButton) {
                            if (e.getStateChange() == 1) {
                                this.setPageViewMode(6, false);
                                doSetFocus = true;
                            }
                        } else if (source == this.singlePageViewNonContinuousButton) {
                            if (e.getStateChange() == 1) {
                                this.setPageViewMode(1, false);
                                doSetFocus = true;
                            }
                        } else if (source == this.singlePageViewContinuousButton && e.getStateChange() == 1) {
                            this.setPageViewMode(2, false);
                            doSetFocus = true;
                        }
                    } else if (e.getStateChange() == 1) {
                        tool = 18;
                        this.setDocumentToolMode(18);
                    }
                } else if (e.getStateChange() == 1) {
                    tool = 8;
                    this.setDocumentToolMode(8);
                }

                if (doSetFocus) {
                    this.documentViewController.requestViewFocusInWindow();
                }
            } finally {
                this.setDisplayTool(tool);
            }
        }

    }

    public void valueChanged(TreeSelectionEvent e) {
        if (this.outlinesTree != null) {
            TreePath treePath = this.outlinesTree.getSelectionPath();
            if (treePath != null) {
                OutlineItemTreeNode node = (OutlineItemTreeNode)treePath.getLastPathComponent();
                OutlineItem o = node.getOutlineItem();
                this.followOutlineItem(o);
                this.outlinesTree.requestFocus();
            }
        }

    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        WindowManagementCallback wc = this.windowManagementCallback;
        JFrame v = this.viewer;
        DocumentViewController viewControl = this.getDocumentViewController();
        Properties viewProperties = new Properties();
        viewProperties.setProperty("document.pagefitMode", String.valueOf(viewControl.getFitMode()));
        viewProperties.setProperty("document.viewtype", String.valueOf(viewControl.getViewMode()));
        boolean cancelled = this.saveChangesDialog();
        if (!cancelled) {
            this.dispose();
            if (wc != null) {
                wc.disposeWindow(this, v, viewProperties);
            }
        }

    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }

    public void dragEnter(DropTargetDragEvent event) {
        if (!this.isDragAcceptable(event)) {
            event.rejectDrag();
        }

    }

    public void dragOver(DropTargetDragEvent event) {
    }

    public void dropActionChanged(DropTargetDragEvent event) {
        if (!this.isDragAcceptable(event)) {
            event.rejectDrag();
        }

    }

    public void drop(DropTargetDropEvent event) {
        try {
            if (!this.isDropAcceptable(event)) {
                event.rejectDrop();
                return;
            }

            event.acceptDrop(1);
            Transferable transferable = event.getTransferable();
            DataFlavor[] flavors = transferable.getTransferDataFlavors();
            DataFlavor[] arr$ = flavors;
            int len$ = flavors.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                DataFlavor dataFlavor = arr$[i$];
                if (dataFlavor.equals(DataFlavor.javaFileListFlavor)) {
                    List fileList = (List)transferable.getTransferData(dataFlavor);
                    Iterator itr = fileList.iterator();

                    while(itr.hasNext()) {
                        Object aFileList = itr.next();
                        File file = (File)aFileList;
                        if (file.getName().toLowerCase().endsWith(".pdf")) {
                            this.openFileInSomeViewer(file);
                            ViewModel.setDefaultFile(file);
                        }
                    }
                } else if (dataFlavor.equals(DataFlavor.stringFlavor)) {
                    String s = (String)transferable.getTransferData(dataFlavor);
                    int startIndex = s.toLowerCase().indexOf("http://");
                    int endIndex = s.toLowerCase().indexOf(".pdf");
                    if (startIndex >= 0 && endIndex >= 0) {
                        s = s.substring(startIndex, endIndex + 4);

                        try {
                            URL url = new URL(s);
                            this.openURLInSomeViewer(url);
                            ViewModel.setDefaultURL(s);
                        } catch (MalformedURLException var12) {
                        }
                    }
                }
            }

            event.dropComplete(true);
        } catch (IOException var13) {
            logger.log(Level.FINE, "IO exception during file drop", var13);
        } catch (UnsupportedFlavorException var14) {
            logger.log(Level.FINE, "Drag and drop not supported", var14);
        }

    }

    public void dragExit(DropTargetEvent event) {
    }

    public void keyPressed(KeyEvent e) {
        if (this.document != null) {
            int c = e.getKeyCode();
            int m = e.getModifiers();
            if (c == 83 && m == KeyEventConstants.MODIFIER_SAVE_AS || c == 80 && m == KeyEventConstants.MODIFIER_PRINT_SETUP || c == 80 && m == KeyEventConstants.MODIFIER_PRINT || c == 49 && m == KeyEventConstants.MODIFIER_FIT_ACTUAL || c == 50 && m == KeyEventConstants.MODIFIER_FIT_PAGE || c == 51 && m == KeyEventConstants.MODIFIER_FIT_WIDTH || c == 73 && m == KeyEventConstants.MODIFIER_ZOOM_IN || c == 79 && m == KeyEventConstants.MODIFIER_ZOOM_OUT || c == 76 && m == KeyEventConstants.MODIFIER_ROTATE_LEFT || c == 82 && m == KeyEventConstants.MODIFIER_ROTATE_RIGHT || c == 38 && m == KeyEventConstants.MODIFIER_FIRST_PAGE || c == 37 && m == KeyEventConstants.MODIFIER_PREVIOUS_PAGE || c == 39 && m == KeyEventConstants.MODIFIER_NEXT_PAGE || c == 40 && m == KeyEventConstants.MODIFIER_LAST_PAGE || c == 83 && m == KeyEventConstants.MODIFIER_SEARCH || c == 78 && m == KeyEventConstants.MODIFIER_GOTO) {
                int documentIcon = this.getDocumentViewToolMode();

                try {
                    this.setDisplayTool(51);
                    if (c == 83 && m == KeyEventConstants.MODIFIER_SAVE_AS) {
                        this.saveFile();
                    } else if (c == 80 && m == KeyEventConstants.MODIFIER_PRINT_SETUP) {
                        this.showPrintSetupDialog();
                    } else if (c == 80 && m == KeyEventConstants.MODIFIER_PRINT) {
                        this.print(true);
                    } else if (c == 49 && m == KeyEventConstants.MODIFIER_FIT_ACTUAL) {
                        this.setPageFitMode(2, false);
                    } else if (c == 50 && m == KeyEventConstants.MODIFIER_FIT_PAGE) {
                        this.setPageFitMode(3, false);
                    } else if (c == 51 && m == KeyEventConstants.MODIFIER_FIT_WIDTH) {
                        this.setPageFitMode(4, false);
                    } else if (c == 73 && m == KeyEventConstants.MODIFIER_ZOOM_IN) {
                        this.zoomIn();
                    } else if (c == 79 && m == KeyEventConstants.MODIFIER_ZOOM_OUT) {
                        this.zoomOut();
                    } else if (c == 76 && m == KeyEventConstants.MODIFIER_ROTATE_LEFT) {
                        this.rotateLeft();
                    } else if (c == 82 && m == KeyEventConstants.MODIFIER_ROTATE_RIGHT) {
                        this.rotateRight();
                    } else if (c == 38 && m == KeyEventConstants.MODIFIER_FIRST_PAGE) {
                        this.showPage(0);
                    } else {
                        DocumentView documentView;
                        if (c == 37 && m == KeyEventConstants.MODIFIER_PREVIOUS_PAGE) {
                            documentView = this.documentViewController.getDocumentView();
                            this.goToDeltaPage(-documentView.getPreviousPageIncrement());
                        } else if (c == 39 && m == KeyEventConstants.MODIFIER_NEXT_PAGE) {
                            documentView = this.documentViewController.getDocumentView();
                            this.goToDeltaPage(documentView.getNextPageIncrement());
                        } else if (c == 40 && m == KeyEventConstants.MODIFIER_LAST_PAGE) {
                            this.showPage(this.getPageTree().getNumberOfPages() - 1);
                        } else if (c == 83 && m == KeyEventConstants.MODIFIER_SEARCH) {
                            this.showSearchPanel();
                        } else if (c == 78 && m == KeyEventConstants.MODIFIER_GOTO) {
                            this.showPageSelectionDialog();
                        }
                    }
                } finally {
                    this.setDisplayTool(documentIcon);
                }
            }
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
        if (this.currentPageNumberTextField != null && e.getSource() == this.currentPageNumberTextField) {
            char c = e.getKeyChar();
            if (c == 27) {
                String fieldValue = this.currentPageNumberTextField.getText();
                String modelValue = Integer.toString(this.documentViewController.getCurrentPageDisplayValue());
                if (!fieldValue.equals(modelValue)) {
                    this.currentPageNumberTextField.setText(modelValue);
                }
            }
        }

    }

    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();
        Object oldValue = evt.getOldValue();
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("documentCurrentPage")) {
            if (this.currentPageNumberTextField != null && newValue instanceof Integer) {
                this.updateDocumentView();
            }
        } else {
            boolean canExtract;
            if (propertyName.equals("textSelected")) {
                canExtract = this.havePermissionToExtractContent();
                this.setEnabled(this.copyMenuItem, canExtract);
                this.setEnabled(this.deselectAllMenuItem, canExtract);
            } else if (propertyName.equals("textDeselected")) {
                canExtract = this.havePermissionToExtractContent();
                this.setEnabled(this.copyMenuItem, false);
                this.setEnabled(this.deselectAllMenuItem, false);
                this.setEnabled(this.selectAllMenuItem, canExtract);
            } else if (propertyName.equals("textSelectAll")) {
                canExtract = this.havePermissionToExtractContent();
                this.setEnabled(this.selectAllMenuItem, false);
                this.setEnabled(this.deselectAllMenuItem, canExtract);
                this.setEnabled(this.copyMenuItem, canExtract);
            } else if (!propertyName.equals("annotationSelected") && !propertyName.equals("annotationFocusGained")) {
                if (propertyName.equals("annotationDeselected")) {
                    if (this.documentViewController.getToolMode() == 6) {
                        if (logger.isLoggable(Level.FINE)) {
                            logger.fine("Deselected current annotation");
                        }

                        this.setEnabled(this.deleteMenuItem, false);
                        if (this.annotationPanel != null) {
                            this.annotationPanel.setEnabled(false);
                        }
                    }
                } else if (propertyName.equals("annotationBounds")) {
                    if (this.documentViewController.getToolMode() == 6) {
                        AnnotationState oldAnnotationState = (AnnotationState)oldValue;
                        AnnotationState newAnnotationState = (AnnotationState)newValue;
                        newAnnotationState.apply(newAnnotationState);
                        newAnnotationState.restore();
                        this.documentViewController.getDocumentViewModel().addMemento(oldAnnotationState, newAnnotationState);
                    }

                    this.reflectUndoCommands();
                } else if (propertyName.equals("lastDividerLocation")) {
                    JSplitPane sourceSplitPane = (JSplitPane)evt.getSource();
                    int dividerLocation = (Integer)evt.getNewValue();
                    if (sourceSplitPane.getDividerLocation() != dividerLocation && this.propertiesManager != null && dividerLocation > 5) {
                        this.utilityAndDocumentSplitPaneLastDividerLocation = dividerLocation;
                        this.propertiesManager.setInt("application.divider.location", this.utilityAndDocumentSplitPaneLastDividerLocation);
                    }
                }
            } else {
                this.setEnabled(this.deleteMenuItem, true);
                if (this.documentViewController.getToolMode() == 6) {
                    AnnotationComponent annotationComponent = (AnnotationComponent)newValue;
                    if (annotationComponent != null && annotationComponent.getAnnotation() != null) {
                        if (logger.isLoggable(Level.FINE)) {
                            logger.fine("selected annotation " + annotationComponent);
                        }

                        this.showAnnotationPanel(annotationComponent);
                    }
                }
            }
        }

    }
}
