//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.icepdf.core.pobjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.icepdf.core.SecurityCallback;
import org.icepdf.core.application.ProductInfo;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.io.ConservativeSizingByteArrayOutputStream;
import org.icepdf.core.io.RandomAccessFileInputStream;
import org.icepdf.core.io.SeekableByteArrayInputStream;
import org.icepdf.core.io.SeekableInput;
import org.icepdf.core.io.SeekableInputConstrainedWrapper;
import org.icepdf.core.pobjects.CrossReference.UsedEntry;
import org.icepdf.core.pobjects.acroform.FieldDictionary;
import org.icepdf.core.pobjects.acroform.InteractiveForm;
import org.icepdf.core.pobjects.annotations.AbstractWidgetAnnotation;
import org.icepdf.core.pobjects.graphics.WatermarkCallback;
import org.icepdf.core.pobjects.graphics.text.PageText;
import org.icepdf.core.pobjects.security.SecurityManager;
import org.icepdf.core.util.Defs;
import org.icepdf.core.util.LazyObjectLoader;
import org.icepdf.core.util.Library;
import org.icepdf.core.util.Parser;

public class Document
{
    private static final Logger            logger              = Logger.getLogger(Document.class.toString());
    private static final String            INCREMENTAL_UPDATER = "org.icepdf.core.util.IncrementalUpdater";
    public static        boolean           foundIncrementalUpdater;
    private              WatermarkCallback watermarkCallback;
    private              Catalog           catalog;
    private              PTrailer          pTrailer;
    private              StateManager      stateManager;
    private              String            origin;
    private              String            cachedFilePath;
    private              SecurityCallback  securityCallback;
    private static       boolean           isCachingEnabled;
    private static       boolean           isFileCachingEnabled;
    private static       int               fileCacheMaxSize;
    private              Library           library             = null;
    private              SeekableInput     documentSeekableInput;

    public static String getLibraryVersion()
    {
        return ProductInfo.PRIMARY + "." + ProductInfo.SECONDARY + "." + ProductInfo.TERTIARY + " " + ProductInfo.RELEASE_TYPE;
    }

    public Document()
    {
    }

    public void setWatermarkCallback(WatermarkCallback watermarkCallback)
    {
        this.watermarkCallback = watermarkCallback;
    }

    private void setDocumentOrigin(String o)
    {
        this.origin = o;
        if (logger.isLoggable(Level.CONFIG))
        {
            logger.config("MEMFREE: " + Runtime.getRuntime().freeMemory() + " of " + Runtime.getRuntime().totalMemory());
            logger.config("LOADING: " + o);
        }

    }

    private void setDocumentCachedFilePath(String o)
    {
        this.cachedFilePath = o;
    }

    private String getDocumentCachedFilePath()
    {
        return this.cachedFilePath;
    }

    public void setFile(String filepath) throws PDFException, PDFSecurityException, IOException
    {
        this.setDocumentOrigin(filepath);
        File            file        = new File(filepath);
        FileInputStream inputStream = new FileInputStream(file);
        int             fileLength  = inputStream.available();
        if (isFileCachingEnabled && file.length() > 0L && fileLength <= fileCacheMaxSize)
        {
            byte[] data = new byte[fileLength];
            inputStream.read(data);
            this.setByteArray(data, 0, fileLength, filepath);
        }
        else
        {
            RandomAccessFileInputStream rafis = RandomAccessFileInputStream.build(new File(filepath));
            this.setInputStream(rafis);
        }

        if (inputStream != null)
        {
            inputStream.close();
        }

    }

    public void setUrl(URL url) throws PDFException, PDFSecurityException, IOException
    {
        InputStream in = null;

        try
        {
            URLConnection urlConnection = url.openConnection();
            in = urlConnection.getInputStream();
            String pathOrURL = url.toString();
            this.setInputStream(in, pathOrURL);
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }

        }

    }

    public void setInputStream(InputStream in, String pathOrURL) throws PDFException, PDFSecurityException, IOException
    {
        this.setDocumentOrigin(pathOrURL);
        int size;
        if (!isCachingEnabled)
        {
            ConservativeSizingByteArrayOutputStream byteArrayOutputStream = new ConservativeSizingByteArrayOutputStream(102400);
            byte[]                                  buffer                = new byte[4096];

            int length;
            while ((length = in.read(buffer, 0, buffer.length)) > 0)
            {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            size = byteArrayOutputStream.size();
            byteArrayOutputStream.trim();
            byte[]                       data                 = byteArrayOutputStream.relinquishByteArray();
            SeekableByteArrayInputStream byteArrayInputStream = new SeekableByteArrayInputStream(data, 0, size);
            this.setInputStream(byteArrayInputStream);
        }
        else
        {
            File tempFile = File.createTempFile("ICEpdfTempFile" + this.getClass().hashCode(), ".tmp");
            tempFile.deleteOnExit();
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile.getAbsolutePath(), true);
            byte[]           buffer           = new byte[4096];

            while ((size = in.read(buffer, 0, buffer.length)) > 0)
            {
                fileOutputStream.write(buffer, 0, size);
            }

            fileOutputStream.flush();
            fileOutputStream.close();
            this.setDocumentCachedFilePath(tempFile.getAbsolutePath());
            RandomAccessFileInputStream rafis = RandomAccessFileInputStream.build(tempFile);
            this.setInputStream(rafis);
        }

    }

    public void setByteArray(byte[] data, int offset, int length, String pathOrURL) throws PDFException, PDFSecurityException, IOException
    {
        this.setDocumentOrigin(pathOrURL);
        if (!isCachingEnabled)
        {
            SeekableByteArrayInputStream byteArrayInputStream = new SeekableByteArrayInputStream(data, offset, length);
            this.setInputStream(byteArrayInputStream);
        }
        else
        {
            File tempFile = File.createTempFile("ICEpdfTempFile" + this.getClass().hashCode(), ".tmp");
            tempFile.deleteOnExit();
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile.getAbsolutePath(), true);
            fileOutputStream.write(data, offset, length);
            fileOutputStream.flush();
            fileOutputStream.close();
            this.setDocumentCachedFilePath(tempFile.getAbsolutePath());
            RandomAccessFileInputStream rafis = RandomAccessFileInputStream.build(tempFile);
            this.setInputStream(rafis);
        }

    }

    public void setInputStream(SeekableInput in, String pathOrURL) throws PDFException, PDFSecurityException, IOException
    {
        this.setDocumentOrigin(pathOrURL);
        this.setInputStream(in);
    }

    private void setInputStream(SeekableInput in) throws PDFException, PDFSecurityException, IOException
    {
        try
        {
            this.documentSeekableInput = in;
            this.library = new Library();
            this.library.setDocumentInput(this.documentSeekableInput);
            boolean loaded = false;

            try
            {
                this.loadDocumentViaXRefs(in);
                if (this.catalog != null)
                {
                    this.catalog.init();
                }

                loaded = true;
            }
            catch (PDFException var4)
            {
                throw var4;
            }
            catch (PDFSecurityException var5)
            {
                throw var5;
            }
            catch (Exception var6)
            {
                if (logger.isLoggable(Level.WARNING))
                {
                    logger.warning("Cross reference deferred loading failed, will fall back to linear reading.");
                }
            }

            if (!loaded)
            {
                if (this.catalog != null)
                {
                    this.catalog = null;
                }

                if (this.library != null)
                {
                    this.library = null;
                }

                this.library = new Library();
                this.pTrailer = null;
                in.seekAbsolute(0L);
                this.loadDocumentViaLinearTraversal(in);
                if (this.catalog != null)
                {
                    this.catalog.init();
                }
            }

            this.stateManager = new StateManager(this.pTrailer);
            this.library.setStateManager(this.stateManager);
        }
        catch (PDFException var7)
        {
            logger.log(Level.FINE, "Error loading PDF file during linear parse.", var7);
            this.dispose();
            throw var7;
        }
        catch (PDFSecurityException var8)
        {
            this.dispose();
            throw var8;
        }
        catch (IOException var9)
        {
            this.dispose();
            throw var9;
        }
        catch (Exception var10)
        {
            /**
             * evdm 비정상 종료시 스트림 close start
             */
            in.close();
            /**
             * evdm 비정상 종료시 스트림 close end
             */

            this.dispose();
            logger.log(Level.SEVERE, "Error loading PDF Document.", var10);
            throw new IOException(var10.getMessage());
        }
    }

    private void loadDocumentViaXRefs(SeekableInput in) throws PDFException, PDFSecurityException, IOException
    {
        int      offset          = this.skipPastAnyPrefixJunk(in);
        long     xrefPosition    = this.getInitialCrossReferencePosition(in) + (long) offset;
        PTrailer documentTrailer = null;
        if (xrefPosition > 0L)
        {
            in.seekAbsolute(xrefPosition);
            Parser parser = new Parser(in);
            Object obj    = parser.getObject(this.library);
            if (obj instanceof PObject)
            {
                obj = ((PObject) obj).getObject();
            }

            PTrailer trailer = (PTrailer) obj;
            if (trailer == null)
            {
                throw new RuntimeException("Could not find trailer");
            }

            if (trailer.getPrimaryCrossReference() == null)
            {
                throw new RuntimeException("Could not find cross reference");
            }

            trailer.setPosition(xrefPosition);
            documentTrailer = trailer;
        }

        if (documentTrailer == null)
        {
            throw new RuntimeException("Could not find document trailer");
        }
        else
        {
            if (offset > 0)
            {
                documentTrailer.getCrossReferenceTable().setOffset(offset);
            }

            LazyObjectLoader lol = new LazyObjectLoader(this.library, in, documentTrailer.getPrimaryCrossReference());
            this.library.setLazyObjectLoader(lol);
            this.pTrailer = documentTrailer;
            this.catalog = documentTrailer.getRootCatalog();
            this.library.setCatalog(this.catalog);
            if (this.catalog == null)
            {
                throw new NullPointerException("Loading via xref failed to find catalog");
            }
            else
            {
                boolean madeSecurityManager = this.makeSecurityManager(documentTrailer);
                if (madeSecurityManager)
                {
                    this.attemptAuthorizeSecurityManager();
                }

                this.configurePermissions();
            }
        }
    }

    private long getInitialCrossReferencePosition(SeekableInput in) throws IOException
    {
        in.seekEnd();
        long   endOfFile       = in.getAbsolutePosition();
        long   currentPosition = endOfFile - 1L;
        long   afterStartxref  = -1L;
        String startxref       = "startxref";

        for (int startxrefIndexToMatch = startxref.length() - 1; currentPosition >= 0L && endOfFile - currentPosition < 2048L; --currentPosition)
        {
            in.seekAbsolute(currentPosition);
            int curr = in.read();
            if (curr < 0)
            {
                throw new EOFException("Could not find startxref at end of file");
            }

            if (curr == startxref.charAt(startxrefIndexToMatch))
            {
                if (startxrefIndexToMatch == 0)
                {
                    afterStartxref = currentPosition + (long) startxref.length();
                    break;
                }

                --startxrefIndexToMatch;
            }
            else
            {
                startxrefIndexToMatch = startxref.length() - 1;
            }
        }

        if (afterStartxref < 0L)
        {
            throw new EOFException("Could not find startxref near end of file");
        }
        else
        {
            in.seekAbsolute(afterStartxref);
            Parser parser          = new Parser(in);
            Number xrefPositionObj = (Number) parser.getToken();
            if (xrefPositionObj == null)
            {
                throw new RuntimeException("Could not find ending cross reference position");
            }
            else
            {
                return xrefPositionObj.longValue();
            }
        }
    }

    private void loadDocumentViaLinearTraversal(SeekableInput seekableInput) throws PDFException, PDFSecurityException, IOException
    {
        InputStream in            = seekableInput.getInputStream();
        int         objectsOffset = this.skipPastAnyPrefixJunk(in);
        this.library.setLinearTraversal();
        Parser    parser          = new Parser(in);
        PTrailer  documentTrailer = null;
        ArrayList documentObjects = new ArrayList();

        while (true)
        {
            Object pdfObject = parser.getObject(this.library);
            if (pdfObject == null)
            {
                CrossReference refs = documentTrailer.getPrimaryCrossReference();
                Iterator       i$   = documentObjects.iterator();

                while (true)
                {
                    PObject pobjects;
                    while (i$.hasNext())
                    {
                        pobjects = (PObject) i$.next();
                        Object entry = refs.getEntryForObject(pobjects.getReference().getObjectNumber());
                        if (entry != null && entry instanceof UsedEntry)
                        {
                            ((UsedEntry) entry).setFilePositionOfObject((long) pobjects.getLinearTraversalOffset());
                        }
                        else
                        {
                            refs.addUsedEntry(pobjects.getReference().getObjectNumber(), (long) pobjects.getLinearTraversalOffset(), pobjects.getReference().getGenerationNumber());
                        }
                    }

                    if (logger.isLoggable(Level.FINER))
                    {
                        i$ = documentObjects.iterator();

                        while (i$.hasNext())
                        {
                            pobjects = (PObject) i$.next();
                            logger.finer(pobjects.getClass().getName() + " " + pobjects.getLinearTraversalOffset() + " " + pobjects);
                        }
                    }

                    if (documentTrailer != null)
                    {
                        LazyObjectLoader lol = new LazyObjectLoader(this.library, seekableInput, documentTrailer.getPrimaryCrossReference());
                        this.library.setLazyObjectLoader(lol);
                    }

                    this.pTrailer = documentTrailer;
                    this.library.setCatalog(this.catalog);
                    if (documentTrailer != null)
                    {
                        boolean madeSecurityManager = this.makeSecurityManager(documentTrailer);
                        if (madeSecurityManager)
                        {
                            this.attemptAuthorizeSecurityManager();
                        }
                    }

                    this.configurePermissions();
                    return;
                }
            }

            if (pdfObject instanceof PObject)
            {
                PObject tmp = (PObject) pdfObject;
                tmp.setLinearTraversalOffset(objectsOffset + parser.getLinearTraversalOffset());
                documentObjects.add(tmp);
                Object obj = tmp.getObject();
                if (obj != null)
                {
                    pdfObject = obj;
                }
            }

            if (pdfObject instanceof Catalog)
            {
                this.catalog = (Catalog) pdfObject;
            }

            if (pdfObject instanceof PTrailer)
            {
                if (documentTrailer == null)
                {
                    documentTrailer = (PTrailer) pdfObject;
                }
                else
                {
                    PTrailer nextTrailer = (PTrailer) pdfObject;
                    if (nextTrailer.getPrev() > 0L)
                    {
                        documentTrailer.addNextTrailer(nextTrailer);
                        documentTrailer = nextTrailer;
                    }
                }
            }
        }
    }

    private int skipPastAnyPrefixJunk(InputStream in)
    {
        if (!in.markSupported())
        {
            return 0;
        }
        else
        {
            try
            {
                boolean scanLength        = true;
                String  scanFor           = "%PDF-";
                int     scanForLength     = "%PDF-".length();
                int     scanForIndex      = 0;
                boolean scanForWhiteSpace = false;
                in.mark(2048);

                for (int i = 0; i < 2048; ++i)
                {
                    int data = in.read();
                    if (data < 0)
                    {
                        in.reset();
                        return 0;
                    }

                    if (scanForWhiteSpace)
                    {
                        ++scanForIndex;
                        if (Parser.isWhitespace((char) data))
                        {
                            return scanForIndex;
                        }
                    }
                    else if (data == "%PDF-".charAt(scanForIndex))
                    {
                        ++scanForIndex;
                        if (scanForIndex == scanForLength)
                        {
                            scanForWhiteSpace = true;
                        }
                    }
                    else
                    {
                        scanForIndex = 0;
                    }
                }

                in.reset();
            }
            catch (IOException var10)
            {
                try
                {
                    in.reset();
                }
                catch (IOException var9)
                {
                }
            }

            return 0;
        }
    }

    private int skipPastAnyPrefixJunk(SeekableInput in)
    {
        if (!in.markSupported())
        {
            return 0;
        }
        else
        {
            try
            {
                boolean scanLength   = true;
                String  scanFor      = "%PDF-1.";
                int     scanForIndex = 0;
                in.mark(2048);

                for (int i = 0; i < 2048; ++i)
                {
                    int data = in.read();
                    if (data < 0)
                    {
                        in.reset();
                        return 0;
                    }

                    if (data == "%PDF-1.".charAt(scanForIndex))
                    {
                        return i;
                    }

                    scanForIndex = 0;
                }

                in.reset();
            }
            catch (IOException var8)
            {
                try
                {
                    in.reset();
                }
                catch (IOException var7)
                {
                }
            }

            return 0;
        }
    }

    private boolean makeSecurityManager(PTrailer documentTrailer) throws PDFSecurityException
    {
        boolean                 madeSecurityManager = false;
        HashMap<Object, Object> encryptDictionary   = documentTrailer.getEncrypt();
        List                    fileID              = documentTrailer.getID();
        if (fileID == null)
        {
            fileID = new ArrayList(2);
            ((List) fileID).add(new LiteralStringObject(""));
            ((List) fileID).add(new LiteralStringObject(""));
        }

        if (encryptDictionary != null && fileID != null)
        {
            this.library.setSecurityManager(new SecurityManager(this.library, encryptDictionary, (List) fileID));
            madeSecurityManager = true;
        }

        return madeSecurityManager;
    }

    private boolean configurePermissions()
    {
        if (this.catalog != null)
        {
            Permissions permissions = this.catalog.getPermissions();
            if (permissions != null)
            {
                this.library.setPermissions(permissions);
                if (logger.isLoggable(Level.FINER))
                {
                    logger.finer("Document perms dictionary found and configured. ");
                }

                return true;
            }
        }

        return false;
    }

    private void attemptAuthorizeSecurityManager() throws PDFSecurityException
    {
        if (!this.library.getSecurityManager().isAuthorized(""))
        {
            int count = 1;

            while (true)
            {
                if (this.securityCallback == null)
                {
                    throw new PDFSecurityException("Encryption error");
                }

                String password = this.securityCallback.requestPassword(this);
                if (password == null)
                {
                    throw new PDFSecurityException("Encryption error");
                }

                if (this.library.getSecurityManager().isAuthorized(password))
                {
                    break;
                }

                ++count;
                if (count > 3)
                {
                    throw new PDFSecurityException("Encryption error");
                }
            }
        }

        this.library.setEncrypted(true);
    }

    public PDimension getPageDimension(int pageNumber, float userRotation)
    {
        Page page = this.catalog.getPageTree().getPage(pageNumber);
        return page.getSize(userRotation);
    }

    public PDimension getPageDimension(int pageNumber, float userRotation, float userZoom)
    {
        Page page = this.catalog.getPageTree().getPage(pageNumber);
        return page != null ? page.getSize(userRotation, userZoom) : new PDimension(0, 0);
    }

    public String getDocumentOrigin()
    {
        return this.origin;
    }

    public String getDocumentLocation()
    {
        return this.cachedFilePath != null ? this.cachedFilePath : this.origin;
    }

    public StateManager getStateManager()
    {
        return this.stateManager;
    }

    public int getNumberOfPages()
    {
        try
        {
            return this.catalog.getPageTree().getNumberOfPages();
        }
        catch (Exception var2)
        {
            logger.log(Level.FINE, "Error getting number of pages.", var2);
            return 0;
        }
    }

    public void paintPage(int pageNumber, Graphics g, int renderHintType, int pageBoundary, float userRotation, float userZoom) throws InterruptedException
    {
        Page page = this.catalog.getPageTree().getPage(pageNumber);
        page.init();
        PDimension sz         = page.getSize(userRotation, userZoom);
        int        pageWidth  = (int) sz.getWidth();
        int        pageHeight = (int) sz.getHeight();
        Graphics   gg         = g.create(0, 0, pageWidth, pageHeight);
        page.paint(gg, renderHintType, pageBoundary, userRotation, userZoom);
        gg.dispose();
    }

    public void dispose()
    {
        if (this.documentSeekableInput != null)
        {
            try
            {
                this.documentSeekableInput.close();
            }
            catch (IOException var4)
            {
                logger.log(Level.FINE, "Error closing document input stream.", var4);
            }

            this.documentSeekableInput = null;
        }

        String fileToDelete = this.getDocumentCachedFilePath();
        if (fileToDelete != null)
        {
            File    file    = new File(fileToDelete);
            boolean success = file.delete();
            if (!success && logger.isLoggable(Level.WARNING))
            {
                logger.warning("Error deleting URL cached to file " + fileToDelete);
            }
        }

    }

    public long writeToOutputStream(OutputStream out) throws IOException
    {
        long                            documentLength = this.documentSeekableInput.getLength();
        SeekableInputConstrainedWrapper wrapper        = new SeekableInputConstrainedWrapper(this.documentSeekableInput, 0L, documentLength);

        try
        {
            byte[] buffer = new byte[4096];

            int length;
            while ((length = wrapper.read(buffer, 0, buffer.length)) > 0)
            {
                out.write(buffer, 0, length);
            }
        }
        catch (Throwable var14)
        {
            logger.log(Level.FINE, "Error writing PDF output stream.", var14);
            throw new IOException(var14.getMessage());
        }
        finally
        {
            try
            {
                wrapper.close();
            }
            catch (IOException var13)
            {
            }

        }

        return documentLength;
    }

    public long saveToOutputStream(OutputStream out) throws IOException
    {
        long documentLength = this.writeToOutputStream(out);
        if (foundIncrementalUpdater)
        {
            try
            {
                Class<?> incrementalUpdaterClass = Class.forName("org.icepdf.core.util.IncrementalUpdater");
                Object[] argValues               = new Object[]{this, out, documentLength};
                Method   method                  = incrementalUpdaterClass.getDeclaredMethod("appendIncrementalUpdate", Document.class, OutputStream.class, Long.TYPE);
                long     appendedLength          = (Long) method.invoke((Object) null, argValues);
                return documentLength + appendedLength;
            }
            catch (Throwable var9)
            {
                logger.log(Level.FINE, "Could not call incremental updater.", var9);
            }
        }

        return documentLength;
    }

    public Image getPageImage(int pageNumber, int renderHintType, int pageBoundary, float userRotation, float userZoom) throws InterruptedException
    {
        Page page = this.catalog.getPageTree().getPage(pageNumber);
        page.init();
        PDimension    sz         = page.getSize(pageBoundary, userRotation, userZoom);
        int           pageWidth  = (int) sz.getWidth();
        int           pageHeight = (int) sz.getHeight();
        BufferedImage image      = ImageUtility.createCompatibleImage(pageWidth, pageHeight);
        Graphics      g          = image.createGraphics();
        page.paint(g, renderHintType, pageBoundary, userRotation, userZoom);
        g.dispose();
        return image;
    }

    public PageText getPageText(int pageNumber) throws InterruptedException
    {
        PageTree pageTree = this.catalog.getPageTree();
        if (pageNumber >= 0 && pageNumber < pageTree.getNumberOfPages())
        {
            Page pg = pageTree.getPage(pageNumber);
            return pg.getText();
        }
        else
        {
            return null;
        }
    }

    public PageText getPageViewText(int pageNumber) throws InterruptedException
    {
        PageTree pageTree = this.catalog.getPageTree();
        if (pageNumber >= 0 && pageNumber < pageTree.getNumberOfPages())
        {
            Page pg = pageTree.getPage(pageNumber);
            return pg.getViewText();
        }
        else
        {
            return null;
        }
    }

    public SecurityManager getSecurityManager()
    {
        return this.library.getSecurityManager();
    }

    public void setSecurityCallback(SecurityCallback securityCallback)
    {
        this.securityCallback = securityCallback;
    }

    public PInfo getInfo()
    {
        return this.pTrailer == null ? null : this.pTrailer.getInfo();
    }

    public void setFormHighlight(boolean highlight)
    {
        if (this.catalog != null && this.catalog.getInteractiveForm() != null)
        {
            InteractiveForm   interactiveForm = this.catalog.getInteractiveForm();
            ArrayList<Object> widgets         = interactiveForm.getFields();
            if (widgets != null)
            {
                Iterator i$ = widgets.iterator();

                while (i$.hasNext())
                {
                    Object widget = i$.next();
                    this.descendFormTree(widget, highlight);
                }
            }
        }

    }

    private void descendFormTree(Object formNode, boolean highLight)
    {
        if (formNode instanceof AbstractWidgetAnnotation)
        {
            ((AbstractWidgetAnnotation) formNode).setEnableHighlightedWidget(highLight);
        }
        else if (formNode instanceof FieldDictionary)
        {
            FieldDictionary child = (FieldDictionary) formNode;
            formNode = child.getKids();
            if (formNode != null)
            {
                ArrayList kidsArray = (ArrayList) formNode;
                Iterator  i$        = kidsArray.iterator();

                while (i$.hasNext())
                {
                    Object kid = i$.next();
                    if (kid instanceof Reference)
                    {
                        kid = this.library.getObject((Reference) kid);
                    }

                    if (kid instanceof AbstractWidgetAnnotation)
                    {
                        ((AbstractWidgetAnnotation) kid).setEnableHighlightedWidget(highLight);
                    }
                    else if (kid instanceof FieldDictionary)
                    {
                        this.descendFormTree(kid, highLight);
                    }
                }
            }
        }

    }

    public List<Image> getPageImages(int pageNumber) throws InterruptedException
    {
        Page pg = this.catalog.getPageTree().getPage(pageNumber);
        pg.init();
        return pg.getImages();
    }

    public PageTree getPageTree()
    {
        if (this.catalog != null)
        {
            PageTree pageTree = this.catalog.getPageTree();
            if (pageTree != null)
            {
                pageTree.setWatermarkCallback(this.watermarkCallback);
            }

            return pageTree;
        }
        else
        {
            return null;
        }
    }

    public Catalog getCatalog()
    {
        return this.catalog;
    }

    public static void setCachingEnabled(boolean cachingEnabled)
    {
        isCachingEnabled = cachingEnabled;
    }

    static
    {
        try
        {
            Class.forName("org.icepdf.core.util.IncrementalUpdater");
            foundIncrementalUpdater = true;
        }
        catch (ClassNotFoundException var1)
        {
            logger.log(Level.WARNING, "PDF write support was not found on the class path");
        }

        isCachingEnabled = Defs.sysPropertyBoolean("org.icepdf.core.streamcache.enabled", false);
        isFileCachingEnabled = Defs.sysPropertyBoolean("org.icepdf.core.filecache.enabled", true);
        fileCacheMaxSize = Defs.intProperty("org.icepdf.core.filecache.size", 200000000);
    }
}
