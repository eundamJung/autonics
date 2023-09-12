//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import javax.swing.JComponent;
import javax.swing.plaf.nimbus.AbstractRegionPainter;

public class ProgressBarClientProperty extends AbstractRegionPainter {
    private Color color17 = this.decodeColor("nimbusOrange", 0.0F, 0.0F, 0.0F, -156);
    private Color color18 = this.decodeColor("nimbusOrange", -0.015796512F, 0.02094239F, -0.15294117F, 0);
    private Color color19 = this.decodeColor("nimbusOrange", -0.004321605F, 0.02094239F, -0.0745098F, 0);
    private Color color20 = this.decodeColor("nimbusOrange", -0.008021399F, 0.02094239F, -0.10196078F, 0);
    private Color color21 = this.decodeColor("nimbusOrange", -0.011706904F, -0.1790576F, -0.02352941F, 0);
    private Color color22 = this.decodeColor("nimbusOrange", -0.048691254F, 0.02094239F, -0.3019608F, 0);
    private Color color23 = this.decodeColor("nimbusOrange", 0.003940329F, -0.7375322F, 0.17647058F, 0);
    private Color color24 = this.decodeColor("nimbusOrange", 0.005506739F, -0.46764207F, 0.109803915F, 0);
    private Color color25 = this.decodeColor("nimbusOrange", 0.0042127445F, -0.18595415F, 0.04705882F, 0);
    private Color color26 = this.decodeColor("nimbusOrange", 0.0047626942F, 0.02094239F, 0.0039215684F, 0);
    private Color color27 = this.decodeColor("nimbusOrange", 0.0047626942F, -0.15147138F, 0.1607843F, 0);
    private Color color28 = this.decodeColor("nimbusOrange", 0.010665476F, -0.27317524F, 0.25098038F, 0);
    private Rectangle2D rect = new Float(0.0F, 0.0F, 0.0F, 0.0F);
    private Path2D path = new Path2D.Float();
    private PaintContext ctx;
    private int gab;

    public ProgressBarClientProperty(int gap, Dimension dimension) {
        this.gab = gap;
        this.ctx = new PaintContext(new Insets(0, gap, 0, gap), dimension, false);
    }

    public void doPaint(Graphics2D g, JComponent c, int width, int height, Object[] extendedCacheKeys) {
        this.path = this.decodePath1();
        g.setPaint(this.color17);
        g.fill(this.path);
        this.rect = this.decodeRect3();
        g.setPaint(this.decodeGradient5(this.rect));
        g.fill(this.rect);
        this.rect = this.decodeRect4();
        g.setPaint(this.decodeGradient6(this.rect));
        g.fill(this.rect);
    }

    public PaintContext getPaintContext() {
        return this.ctx;
    }

    private Path2D decodePath1() {
        this.path.reset();
        this.path.moveTo((double)this.decodeX(0.6F), (double)this.decodeY(0.12666667F));
        this.path.curveTo((double)this.decodeAnchorX(0.6F, -2.0F), (double)this.decodeAnchorY(0.12666667F, 0.0F), (double)this.decodeAnchorX(0.12666667F, 0.0F), (double)this.decodeAnchorY(0.6F, -2.0F), (double)this.decodeX(0.12666667F), (double)this.decodeY(0.6F));
        this.path.curveTo((double)this.decodeAnchorX(0.12666667F, 0.0F), (double)this.decodeAnchorY(0.6F, 2.0F), (double)this.decodeAnchorX(0.12666667F, 0.0F), (double)this.decodeAnchorY(2.4F, -2.0F), (double)this.decodeX(0.12666667F), (double)this.decodeY(2.4F));
        this.path.curveTo((double)this.decodeAnchorX(0.12666667F, 0.0F), (double)this.decodeAnchorY(2.4F, 2.0F), (double)this.decodeAnchorX(0.6F, -2.0F), (double)this.decodeAnchorY(2.8933334F, 0.0F), (double)this.decodeX(0.6F), (double)this.decodeY(2.8933334F));
        this.path.curveTo((double)this.decodeAnchorX(0.6F, 2.0F), (double)this.decodeAnchorY(2.8933334F, 0.0F), (double)this.decodeAnchorX(3.0F, 0.0F), (double)this.decodeAnchorY(2.8933334F, 0.0F), (double)this.decodeX(3.0F), (double)this.decodeY(2.8933334F));
        this.path.lineTo((double)this.decodeX(3.0F), (double)this.decodeY(2.6F));
        this.path.lineTo((double)this.decodeX(0.4F), (double)this.decodeY(2.6F));
        this.path.lineTo((double)this.decodeX(0.4F), (double)this.decodeY(0.4F));
        this.path.lineTo((double)this.decodeX(3.0F), (double)this.decodeY(0.4F));
        this.path.lineTo((double)this.decodeX(3.0F), (double)this.decodeY(0.120000005F));
        this.path.curveTo((double)this.decodeAnchorX(3.0F, 0.0F), (double)this.decodeAnchorY(0.120000005F, 0.0F), (double)this.decodeAnchorX(0.6F, 2.0F), (double)this.decodeAnchorY(0.12666667F, 0.0F), (double)this.decodeX(0.6F), (double)this.decodeY(0.12666667F));
        this.path.closePath();
        return this.path;
    }

    private Rectangle2D decodeRect3() {
        this.rect.setRect((double)this.decodeX(0.4F), (double)this.decodeY(0.4F), (double)(this.decodeX(3.0F) - this.decodeX(0.4F)), (double)(this.decodeY(2.6F) - this.decodeY(0.4F)));
        return this.rect;
    }

    private Rectangle2D decodeRect4() {
        this.rect.setRect((double)this.decodeX(0.6F), (double)this.decodeY(0.6F), (double)(this.decodeX(2.8F) - this.decodeX(0.6F)), (double)(this.decodeY(2.4F) - this.decodeY(0.6F)));
        return this.rect;
    }

    private Paint decodeGradient5(Shape s) {
        Rectangle2D bounds = s.getBounds2D();
        float x = (float)bounds.getX();
        float y = (float)bounds.getY();
        float w = (float)bounds.getWidth();
        float h = (float)bounds.getHeight();
        return this.decodeGradient(0.5F * w + x, 0.0F * h + y, 0.5F * w + x, 1.0F * h + y, new float[]{0.038709678F, 0.05483871F, 0.07096774F, 0.28064516F, 0.4903226F, 0.6967742F, 0.9032258F, 0.9241935F, 0.9451613F}, new Color[]{this.color18, this.decodeColor(this.color18, this.color19, 0.5F), this.color19, this.decodeColor(this.color19, this.color20, 0.5F), this.color20, this.decodeColor(this.color20, this.color21, 0.5F), this.color21, this.decodeColor(this.color21, this.color22, 0.5F), this.color22});
    }

    private Paint decodeGradient6(Shape s) {
        Rectangle2D bounds = s.getBounds2D();
        float x = (float)bounds.getX();
        float y = (float)bounds.getY();
        float w = (float)bounds.getWidth();
        float h = (float)bounds.getHeight();
        return this.decodeGradient(0.5F * w + x, 0.0F * h + y, 0.5F * w + x, 1.0F * h + y, new float[]{0.038709678F, 0.061290324F, 0.08387097F, 0.27258065F, 0.46129033F, 0.4903226F, 0.5193548F, 0.71774197F, 0.91612905F, 0.92419356F, 0.93225807F}, new Color[]{this.color23, this.decodeColor(this.color23, this.color24, 0.5F), this.color24, this.decodeColor(this.color24, this.color25, 0.5F), this.color25, this.decodeColor(this.color25, this.color26, 0.5F), this.color26, this.decodeColor(this.color26, this.color27, 0.5F), this.color27, this.decodeColor(this.color27, this.color28, 0.5F), this.color28});
    }
}
