//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.icepdf.ri.common;

import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.CancelablePrintJob;
import javax.print.PrintException;
import javax.swing.JFrame;

public class PrinterTask implements Runnable {
    private static final Logger logger = Logger.getLogger(PrinterTask.class.toString());
    private PrintHelper printHelper;
    private CancelablePrintJob cancelablePrintJob;

    public PrinterTask(PrintHelper printHelper) {
        this.printHelper = printHelper;
    }

    public void run() {
        try {
            if (this.printHelper != null) {
                this.cancelablePrintJob = this.printHelper.cancelablePrint();
            }
        } catch (PrintException var2) {
            logger.log(Level.FINE, "Error during printing.", var2);
        }

    }

    public void cancel() {
        boolean var6 = false;

        JFrame frame;
        label53: {
            try {
                var6 = true;
                if (this.cancelablePrintJob != null) {
                    this.cancelablePrintJob.cancel();
                    var6 = false;
                } else {
                    var6 = false;
                }
                break label53;
            } catch (PrintException var7) {
                logger.log(Level.FINE, "Error during printing, " + var7.getMessage());
                var6 = false;
            } finally {
                if (var6) {
                    frame = this.printHelper.getSwingController().applicationFrame;
                    frame.dispatchEvent(new WindowEvent(frame, 201));
                }
            }

            frame = this.printHelper.getSwingController().applicationFrame;
            frame.dispatchEvent(new WindowEvent(frame, 201));
            return;
        }

        frame = this.printHelper.getSwingController().applicationFrame;
        frame.dispatchEvent(new WindowEvent(frame, 201));
    }
}
