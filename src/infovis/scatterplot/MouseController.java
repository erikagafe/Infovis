package infovis.scatterplot;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseListener, MouseMotionListener {

	private Model model = null;
	private View view = null;
	double lastX = 0.0;
	double lastY = 0.0;

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
		//Iterator<Data> iter = model.iterator();
		//view.getMarkerRectangle().setRect(x,y,w,h);
		//view.repaint();

		int x = e.getX();
		int y = e.getY();

		lastX = x;
		lastY = y;

		view.getMarkerRectangle().setRect(x, y, 0, 0);
		view.repaint();


	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseDragged(MouseEvent e) {
		//view.repaint();

		double lx = (e.getX() - lastX > 0) ? lastX : e.getX();
		double uy = (e.getY() - lastY > 0) ? lastY : e.getY();

		view.getMarkerRectangle().setRect(lx, uy, Math.abs(e.getX() - lastX), Math.abs(e.getY() - lastY));
		view.repaint();

	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void setModel(Model model) {
		this.model  = model;
	}

	public void setView(View view) {
		this.view  = view;
	}

}
