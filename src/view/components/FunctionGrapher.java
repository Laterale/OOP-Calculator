package view.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import view.logics.FunctionCalculatorImpl;
/**
 * 
 * 
 *
 */
public class FunctionGrapher extends JPanel {
    private static final long serialVersionUID = -6534831232343094643L;
    private static double scale = 10;
    private static final int LINES_DISTANCE = 5;
    private static double bound = 3 - 10 / FunctionGrapher.scale;
    private final List<Double> results1 = new ArrayList<>();
    private final List<Double> results2 = new ArrayList<>();
    /**
     *
     */
    public FunctionGrapher() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double width = screenSize.getWidth() * 0.35;
        final double height = screenSize.getHeight() / 2;
        this.setPreferredSize(new Dimension((int) width, (int) height));
        this.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        this.addMouseWheelListener(m -> {
            if (m.getWheelRotation() > 0 && FunctionGrapher.scale > 10) {
                FunctionGrapher.scale--;
            } else if (m.getWheelRotation() < 0) {
                FunctionGrapher.scale++;
            }
            this.repaint();
        });
    }
    /**
     * @param g
     */
    public void paintComponent(final Graphics g) {
        final int w = this.getWidth();
        final int h = this.getHeight();
        super.paintComponent(g);
        drawGrid(g, w, h);
        drawAxes(g, w, h);
        drawLines(g, w, h);
        drawFunction(g, w, h);
    }

    private void drawAxes(final Graphics g, final int w, final int h) {
        final Graphics2D axes = (Graphics2D) g;
        axes.setStroke(new BasicStroke(1));
        axes.setColor(Color.BLACK);
        axes.drawLine(0, h / 2, w, h / 2);
        axes.drawLine(w / 2, 0, w / 2, h);
        axes.drawString("o", w / 2 - 10, h / 2 + 10);
        axes.drawString("x", w - 10, h / 2 + 10);
        axes.drawString("y", w / 2 - 10, 10 + 3);
    }

    private void drawFunction(final Graphics g, final int w, final int h) {
        final Graphics2D fun = (Graphics2D) g;
        fun.setStroke(new BasicStroke(1));
        if (!results1.isEmpty()) {
            fun.setColor(Color.RED);
            final Polygon f1 = getPolygon(results1, w, h);
            fun.drawPolyline(f1.xpoints, f1.ypoints, f1.npoints);
            }
        if (!results2.isEmpty()) {
                fun.setColor(Color.BLUE);
                final Polygon f2 = getPolygon(results2, w, h);
                fun.drawPolyline(f2.xpoints, f2.ypoints, f2.npoints);
            }
        }

    private void drawLines(final Graphics g, final int w, final int h) {
        final Graphics2D lines = (Graphics2D) g;
        lines.setStroke(new BasicStroke());
        lines.setColor(Color.BLACK);
        for (int count = 0; count < FunctionCalculatorImpl.RANGE; count++) {
            lines.drawLine((int) (w / 2 + count * FunctionGrapher.scale * 2), (int) (h / 2 + bound), (int) (w / 2 + count * FunctionGrapher.scale * 2), (int) (h / 2 - 3 + 10 / FunctionGrapher.scale));
            lines.drawLine((int) (w / 2 - count * FunctionGrapher.scale * 2), (int) (h / 2 + bound), (int) (w / 2 - count * FunctionGrapher.scale * 2), (int) (h / 2 - 3 + 10 / FunctionGrapher.scale));
            if (count % FunctionGrapher.LINES_DISTANCE == 0 && count != 0) {
                lines.drawString(Integer.toString(count), (int) (w / 2 + count * FunctionGrapher.scale * 2 - 4 - 2), (int) (h / 2 - 4 - 2 + 10 / FunctionGrapher.scale));
                lines.drawString(Integer.toString(-(count)), (int) (w / 2 - count * FunctionGrapher.scale * 2 - 4 - 4), (int) (h / 2 - 4 - 2 + 10 / FunctionGrapher.scale));
            }
        }
        for (int count = 0; count < FunctionCalculatorImpl.RANGE; count++) {
            lines.drawLine((int) (w / 2 + bound), (int) (h / 2 + count * FunctionGrapher.scale * 2), (int) (w / 2 - 3 + 10 / FunctionGrapher.scale), (int) (h / 2 + count * FunctionGrapher.scale * 2));
            lines.drawLine((int) (w / 2 + bound), (int) (h / 2 - count * FunctionGrapher.scale * 2), (int) (w / 2 - 3 + 10 / FunctionGrapher.scale), (int) (h / 2 - count * FunctionGrapher.scale * 2));
            if (count % FunctionGrapher.LINES_DISTANCE == 0 && count != 0) {
                lines.drawString(Integer.toString(-(count)), (int) (w / 2 + bound + 4 + 1), (int) (h / 2 + count * FunctionGrapher.scale * 2 + 4 + 1));
                lines.drawString(Integer.toString(count), (int) (w / 2 + bound + 3), (int) (h / 2 - count * FunctionGrapher.scale * 2 + 4 + 1));
            }
        }
    }

    private void drawGrid(final Graphics g, final int w, final int h) {
        final Graphics2D grid = (Graphics2D) g;
        grid.setStroke(new BasicStroke(1));
        grid.setColor(Color.LIGHT_GRAY);
        for (int count = 0; count < FunctionCalculatorImpl.RANGE; count++) {
            grid.drawLine((int) (w / 2 + count * FunctionGrapher.scale), 0, (int) (w / 2 + count * FunctionGrapher.scale), h);
            grid.drawLine((int) (w / 2 - count * FunctionGrapher.scale), 0, (int) (w / 2 - count * FunctionGrapher.scale), h);
        }
        for (int count = 0; count < FunctionCalculatorImpl.RANGE; count++) {
            grid.drawLine(0, (int) (h / 2 + count * FunctionGrapher.scale), w, (int) (h / 2 + count * FunctionGrapher.scale));
            grid.drawLine(0, (int) (h / 2 - count * FunctionGrapher.scale), w, (int) (h / 2 - count * FunctionGrapher.scale));
        }
    }
    /**
     *@param first
     *@param results
     */
    public void paintFunction(final List<Double> results, final boolean first) {
        if (first) {
            results1.clear();
            this.results1.addAll(results);
        } else {
            results2.clear();
            this.results2.addAll(results);
        }
        this.repaint();
    }
    /**
     *@param first
     */
    public void deleteFunction(final boolean first) {
        if (first) {
            results1.clear();
        } else {
            results2.clear();
        }
        this.repaint();
    }

    private Polygon getPolygon(final List<Double> results, final int w, final int h) {
        final Polygon polygon = new Polygon();
        double x = -FunctionCalculatorImpl.RANGE;
        for (final Double y : results) {
                polygon.addPoint((int) (w / 2 + x * FunctionGrapher.scale * 2), (int) (h / 2 - y.doubleValue()  * FunctionGrapher.scale * 2));
                x += FunctionCalculatorImpl.PRECISION;
        }
        return polygon;
    }
}

