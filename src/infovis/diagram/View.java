package infovis.diagram;

import infovis.diagram.elements.Element;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;


public class View extends JPanel implements ComponentListener {
    private Model model = null;
    private Color color = Color.BLUE;
    private Color cWhite = Color.WHITE;
    private double scale = 1;
    private double overviewScale = 0.2;
    private double translateX = 0;
    private double translateY = 0;

    private static final float ASPECT_RATIO = 1.5f;

    private Rectangle2D overviewRect, marker;

    private boolean rectanglesInitialized = false;

    public Model getModel() {

        return model;
    }

    public void setModel(Model model) {

        this.model = model;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public void initRectangles() {
        overviewRect = new Rectangle2D.Double(getWidth() * 0.05, getHeight() * 0.05,  getWidth() * 0.2, getWidth() * 0.2 / ASPECT_RATIO);
        marker = new Rectangle2D.Double(overviewRect.getX(), overviewRect.getY(), overviewRect.getWidth() / scale, overviewRect.getHeight() / scale);
        rectanglesInitialized = true;

    }

    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.clearRect(0, 0, getWidth(), getHeight());


        if (!rectanglesInitialized) {
            initRectangles();
        }

        g2D.setColor(new Color(228, 235, 242, 205));
        g2D.fill(overviewRect);
        g2D.setColor(new Color(142, 211, 244, 205));
        g2D.fill(marker);
        g2D.translate(overviewRect.getX(), overviewRect.getY());
        g2D.scale(overviewScale, overviewScale);
        paintDiagram(g2D);

        g2D.scale( 5,   5);
        g2D.translate(-overviewRect.getX(), -overviewRect.getY());
        g2D.scale(scale, scale);
        g2D.translate(-translateX, -translateY);
        paintDiagram(g2D);
        g2D.scale(1 / scale, 1 / scale);

    }

    private void paintDiagram(Graphics2D g2D) {
        for (Element element : model.getElements()) {
            element.paint(g2D);
        }
    }

    public void setScale(double scale) {
        if (!rectanglesInitialized) {
            initRectangles();
        }

        this.scale = scale;
        updateMarker(marker.getX(), marker.getY());
    }

    public double getScale() {

        return scale;
    }

    public double getTranslateX() {

        return translateX;
    }

    public void setTranslateX(double translateX) {

        this.translateX = translateX;
    }

    public double getTranslateY() {

        return translateY;
    }

    public void setTranslateY(double translateY) {
        this.translateY = translateY;
    }

    public void updateTranslation() {
        double x  = (marker.getX() - overviewRect.getMinX()) / overviewRect.getWidth() * getWidth();
        double y = (marker.getY() - overviewRect.getMinY()) / overviewRect.getHeight() * getHeight();
        setTranslateX(x);
        setTranslateY(y);
    }

    public void updateMarker(double x, double y) {
        if (!rectanglesInitialized) {
            initRectangles();
        }
        marker.setRect(x, y, overviewRect.getWidth() / scale, overviewRect.getHeight() / scale);

        if (!overviewRect.contains(marker.getBounds2D())) {
            double new_x = marker.getX();
            double new_y = marker.getY();

            if (marker.getMaxX() > overviewRect.getMaxX()) {
                new_x = overviewRect.getMaxX() - marker.getWidth();
            }
            else if (marker.getMinX() < overviewRect.getMinX())
            {
                new_x = overviewRect.getMinX();
            }
            if (marker.getMaxY() > overviewRect.getMaxY()) {
                new_y = overviewRect.getMaxY() - marker.getHeight();

            }
            else if (marker.getMinY() < overviewRect.getMinY()) {
                new_y = overviewRect.getMinY();
            }
                marker.setRect(new_x, new_y, overviewRect.getWidth() / scale, overviewRect.getHeight() / scale);
            }

        updateTranslation();
    }

    public Rectangle2D getMarker() {
        return marker;
    }

    public boolean markerContains(int x, int y) {
        if (!rectanglesInitialized) {
            initRectangles();
        }
        return marker.contains(x, y);
    }

    public Rectangle2D getOverviewRect() {

        return overviewRect;
    }

    public boolean overViewContains(int x, int y) {
        if (!rectanglesInitialized) {
            initRectangles();
        }
        return overviewRect.contains(x, y);
    }

    public void updateOverView(double x, double y) {
        if (!rectanglesInitialized) {
            initRectangles();
        }
        double oldx = overviewRect.getX();
        double oldy = overviewRect.getY();
        overviewRect = new Rectangle2D.Double(x, y, getWidth() * 0.2, getWidth() * 0.2 / ASPECT_RATIO);
        double markerOffsetX = oldx - marker.getX();
        double markerOffsetY = oldy - marker.getY();
        updateMarker(x - markerOffsetX, y - markerOffsetY);

    }

    @Override
    public void componentResized(ComponentEvent e) {
        rectanglesInitialized = false;

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}