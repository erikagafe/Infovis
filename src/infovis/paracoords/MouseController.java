package infovis.paracoords;

import infovis.scatterplot.Model;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseListener, MouseMotionListener {
    private View view = null;
    private Model model = null;
    Shape currentShape = null;
    double lastX = 0.0;
    double lastY = 0.0;

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {


    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        lastX = x;
        lastY = y;

        view.getMarker().setRect(x, y, 0, 0);
        view.repaint();

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

        double lx = (e.getX() - lastX > 0) ? lastX : e.getX();
        double uy = (e.getY() - lastY > 0) ? lastY : e.getY();

        view.getMarker().setRect(lx, uy, Math.abs(e.getX() - lastX), Math.abs(e.getY() - lastY));
        view.repaint();


    }

    public void mouseMoved(MouseEvent e) {

    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
