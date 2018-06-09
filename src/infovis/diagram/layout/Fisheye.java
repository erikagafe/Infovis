package infovis.diagram.layout;

import infovis.debug.Debug;
import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Element;
import infovis.diagram.elements.Vertex;

import java.util.Iterator;
import java.util.List;

/*
 * 
 */

public class Fisheye implements Layout{

    int d = 4;
    double xPos = 0;
    double yPos = 0;


	public void setMouseCoords(int x, int y, View view) {
		// TODO Auto-generated method stub
        this.xPos = x;
        this.yPos = y;
	}

	public Model transform(Model model, View view) {
		// TODO Auto-generated method stub

        Model newModel = new Model();

/*        double pFocusX = view.getWidth()/2;
        double pFocusY = view.getHeight()/2;*/

        double pFocusX = xPos;
        double pFocusY = yPos;

        List<Vertex> vertices = model.getVertices();
        for (Vertex vertex:
             vertices) {

            double x = (vertex.getCenterX() - pFocusX) / getDmax(view.getWidth(), pFocusX, vertex.getCenterX());
            double y = (vertex.getCenterY() - pFocusY) / getDmax(view.getHeight(), pFocusY, vertex.getCenterY());
            double pFishXcenterPoint = pFocusX + (((d + 1) * x) / ((d * x) + 1)) * getDmax(view.getWidth(), pFocusX, vertex.getCenterX());
            double pFishYcenterPoint = pFocusY + (((d + 1) * y) / ((d * y) + 1)) * getDmax(view.getHeight(), pFocusY, vertex.getCenterY());

            double xVertexBoundary = vertex.getX() + vertex.getWidth();
            double xVertex = (xVertexBoundary - pFocusX) / getDmax(view.getWidth(), pFocusX, xVertexBoundary);

            double yVertexBoundary = vertex.getY() + vertex.getHeight();
            double yVertex = (yVertexBoundary - pFocusY) / getDmax(view.getHeight(), pFocusY, yVertexBoundary);

            double pFishX = pFocusX + (((d + 1) * xVertex) / ((d * xVertex) + 1)) * getDmax(view.getWidth(), pFocusX, xVertexBoundary);
            double pFishY = pFocusY + (((d + 1) * yVertex) / ((d * yVertex) + 1)) * getDmax(view.getHeight(), pFocusY, yVertexBoundary);

            double ratio = vertex.getWidth() / vertex.getHeight();

            System.out.println("pFishX " + pFishX);
            System.out.println("pFishXcenterPoint " + pFishXcenterPoint);

            double newWidth = 2 * (pFishX - pFishXcenterPoint);
            double newHeight = newWidth / ratio;
            //double newHeight = 2 * (pFishY - pFishYcenterPoint);

            double topX = pFishXcenterPoint - (newWidth / 2);
            double topY = pFishYcenterPoint - (newHeight / 2);

            System.out.println("Top x " + topX);
            System.out.println("Top y " + topY);
            System.out.println("Height " + newHeight);
            System.out.println("Width " + newWidth);

            Vertex newVertex = new Vertex(topX, topY, newWidth, newHeight);
            newModel.addVertex(newVertex);
        }

		return newModel;
	}

	double getDmax(double pBoundary, double pFocus, double pNorm){
	    if(pNorm > pFocus)
	        return pBoundary - pFocus;
	    else
	        return  0 - pFocus;
    }

}
