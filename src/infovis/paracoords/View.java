package infovis.paracoords;

import infovis.scatterplot.Model;
import infovis.scatterplot.Range;
import infovis.scatterplot.Data;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

import javax.swing.JPanel;

public class View extends JPanel {
    private Model model = null;
    private Rectangle2D marker = new Rectangle2D.Double(0, 0, 0, 0);
    private int yPadding;


    public Rectangle2D getMarker() {

        return marker;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.clearRect(0, 0, getWidth(), getHeight());


        yPadding = (int) (getHeight() * 0.1);

        int dimensions;
        dimensions = model.getDim();
        ArrayList<Range> ranges = model.getRanges();
        ArrayList<Data> list = model.getList();

        HashSet<Data> selectedData = new HashSet<>();

        list.sort(Comparator.comparingInt(p -> (int) p.getValue(0)));

        double space = getWidth() / (dimensions + 2);

        for (int i = 0; i < dimensions + 1; i++) {
            if (i > 0) {
                g2D.setColor(new Color(93, 53, 62, 205));
                g2D.drawString(model.getLabels().get(i - 1), (int) (i * space), (int) (yPadding * 0.5));
                double min = ranges.get(i - 1).getMin();
                double max = ranges.get(i - 1).getMax();
                if (i == 2) {
                    g2D.drawString(String.valueOf(min), (int) (i * space), (int) (yPadding * 0.8));
                    g2D.drawString(String.valueOf(max), (int) (i * space), (int) (getHeight() * 0.75));
                }
                g2D.drawString(String.valueOf((int) min), (int) (i * space), (int) (yPadding * 0.8));
                g2D.drawString(String.valueOf((int) max), (int) (i * space), (int) (getHeight() * 0.75));
                g2D.setColor(new Color(2, 75, 64, 100));
                g2D.drawLine((int) (i * space), (int) (yPadding * 0.8), (int) (i * space), (int) (getHeight() * 0.7));

                for (int item_index = 0; item_index < list.size(); item_index++) {
                    Data item = list.get(item_index);
                    int x = (int) (i * space);
                    double y = (item.getValue(i - 1) - ranges.get(i - 1).getMin()) / (ranges.get(i - 1).getMax() - ranges.get(i - 1).getMin()) * getHeight() * 0.6;
                    if (marker.contains(x, y + (yPadding * 0.8))) {
                        selectedData.add(item);
                    } else {
                        g2D.setColor(new Color(2, 75, 64, 100));
                        g2D.fill(new Rectangle2D.Double(x - 3, y + (yPadding * 0.8) - 3, 6, 6));
                        if (i < dimensions) {
                            double endY = (item.getValue(i) - ranges.get(i).getMin()) / (ranges.get(i).getMax() - ranges.get(i).getMin()) * getHeight() * 0.6;
                            int endX = (int) ((i + 1) * space);
                            g2D.drawLine(x, (int) (y + (yPadding * 0.8)), endX, (int) ((int) endY + (yPadding * 0.8)));
                        }

                    }

                }
            }

        }
        for (int i = 0; i < dimensions + 1; i++) {
            if (i == 0) {
                for (int j = 0; j < list.size(); j++) {
                    double y = j * (getHeight() * 0.7 / model.getList().size() - 2);
                    if (selectedData.contains(list.get(j))) {
                        g2D.setColor(new Color(254, 88, 88, 255));
                    } else {
                        g2D.setColor(new Color(2, 75, 64, 100));
                    }
                    g2D.drawString(list.get(j).getLabel(), 10, (float) (y + (yPadding * 0.8) + getHeight() * 0.01));
                    g2D.fill(new Ellipse2D.Double(space * 0.8 - 5, (float) (y + (yPadding * 0.8)) - 5, 10, 10));
                    double endY = (model.getList().get(j).getValue(0) - ranges.get(0).getMin()) / (ranges.get(0).getMax() - ranges.get(0).getMin()) * getHeight() * 0.6;
                    int endX = (int) (space);
                    g2D.drawLine((int) (space * 0.8), (int) (y + (yPadding * 0.8)), endX, (int) ((int) endY + (yPadding * 0.8)));
                }
            }
            if (i > 0) {
                for (Data item : selectedData) {
                    int x = (int) (i * space);
                    double y = (item.getValue(i - 1) - ranges.get(i - 1).getMin()) / (ranges.get(i - 1).getMax() - ranges.get(i - 1).getMin()) * getHeight() * 0.6;
                    g2D.setColor(new Color(254, 88, 88, 255));
                    g2D.fill(new Rectangle2D.Double(x - 3, y + (yPadding * 0.8) - 3, 6, 6));
                  //  g2D.setColor(new Color(247, 134, 16, 255));
                    if (i < dimensions) {
                        double endY = (item.getValue(i) - ranges.get(i).getMin()) / (ranges.get(i).getMax() - ranges.get(i).getMin()) * getHeight() * 0.6;
                        int endX = (int) ((i + 1) * space);
                        g2D.drawLine(x, (int) (y + (yPadding * 0.8)), (int) endX, (int) ((int) endY + (yPadding * 0.8)));
                    }

                }

            }
        }

        g2D.setColor(new Color(178, 214, 202, 20));
        g2D.fill(marker);

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