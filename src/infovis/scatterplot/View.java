package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import javax.swing.JPanel;

public class View extends JPanel {
    private Model model = null;
    private Rectangle2D markerRectangle = new Rectangle2D.Double(0, 0, 0, 0);


    public Rectangle2D getMarkerRectangle() {
        return markerRectangle;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.clearRect(0, 0, getWidth(), getHeight());

        int dimensions;
        dimensions = model.getDim();
        ArrayList<Range> ranges = model.getRanges();
        ArrayList<Data> list = model.getList();

        HashSet<Data> selectedData = new HashSet<>();

        double space = getWidth() / (dimensions + 2);
        double spaceHeight = getHeight() / (dimensions + 2);

        for (int j = 0; j < model.getLabels().size(); j++) {
            g2D.setColor(new Color(93, 53, 62, 205));
            double y;
            if (j == 0) {
                y = spaceHeight;
                g2D.setColor(new Color(2, 75, 64, 100));
                g2D.drawLine((int) (space), (int) (y), (int) (space * (model.getLabels().size() + 1)), (int) (y));
            } else {
                y = (j + 1) * spaceHeight;
            }
            g2D.setColor(new Color(93, 53, 62, 205));
            g2D.drawString(model.getLabels().get(j), (10), (int) (y + spaceHeight /2));
            g2D.setColor(new Color(2, 75, 64, 100));
            g2D.drawLine((int) (space), (int) (y + spaceHeight), (int) (space * (model.getLabels().size() + 1)), (int) (y + spaceHeight));
        }

        for (int i = 0; i < dimensions; i++) {
            g2D.setColor(new Color(93, 53, 62, 205));
            g2D.drawString(model.getLabels().get(i), (int) ((i + 1) * space + (space / 4)), (int) (spaceHeight /2));
            g2D.setColor(new Color(2, 75, 64, 100));
            g2D.drawLine((int) ((i + 1) * space), (int) (spaceHeight), (int) ((i + 1) * space), (int) (spaceHeight * (model.getLabels().size() + 1)));
            if (i == 6) {
                g2D.drawLine((int) ((i + 2) * space), (int) (spaceHeight), (int) ((i + 2) * space), (int) (spaceHeight * (model.getLabels().size() + 1)));
            }
            for (int k = 0; k < dimensions; k++) {
                for (int item_index = 0; item_index < list.size(); item_index++) {
                    Data item = list.get(item_index);
                    double y = (item.getValue(i) - ranges.get(i).getMin()) / (ranges.get(i).getMax() - ranges.get(i).getMin()) * spaceHeight;
                    double x = (item.getValue(k) - ranges.get(k).getMin()) / (ranges.get(k).getMax() - ranges.get(k).getMin()) * space;
                    if (markerRectangle.contains((i + 1) * space + x - 3 ,(k + 1) * spaceHeight + y - 3)) {
                        selectedData.add(item);
                    } else {
                        g2D.setColor(new Color(2, 75, 64, 100));
                        g2D.fill(new Rectangle2D.Double((i + 1) * space + x - 3, (k + 1) * spaceHeight + y - 3, 6, 6));

                    }
                }

            }

        }
        for (Data item : selectedData) {
            for (int i = 0; i < dimensions; i++) {
                for (int k = 0; k < dimensions; k++) {
                    double y = (item.getValue(i) - ranges.get(i).getMin()) / (ranges.get(i).getMax() - ranges.get(i).getMin()) * spaceHeight;
                    double x = (item.getValue(k) - ranges.get(k).getMin()) / (ranges.get(k).getMax() - ranges.get(k).getMin()) * space;
                    g2D.setColor(new Color(254, 88, 88, 255));
                    g2D.fill(new Rectangle2D.Double((i + 1) * space + x - 3, (k + 1) * spaceHeight + y - 3, 6, 6));
                }
            }
        }
        g2D.setColor(new Color(178, 214, 202, 40));
       // g2D.setColor(new Color(255, 91, 7, 20));
        g2D.fill(markerRectangle);
    }

    @Override
    public void update(Graphics g) {

        paint(g);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}

