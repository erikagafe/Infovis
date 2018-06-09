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

    int d = 2; // distortion factor. I can varies from 0 to 5

    double xPos = 0; // mouse x coordinate
    double yPos = 0; // mouse y coordinate


	public void setMouseCoords(int x, int y, View view) {
		// TODO Auto-generated method stub
        this.xPos = x;
        this.yPos = y;
	}

	public Model transform(Model model, View view) {
		// TODO Auto-generated method stub

        Model newModel = new Model();

        // pFocusX and pFocusY have the coordinates of the mouse pointer.
        // As we move the pointer in the fish eye mode, these coordinates are updated and a new model is generated
        double pFocusX = xPos;
        double pFocusY = yPos;

        //a new coordinate is calculate for each vertex of the model.
        List<Vertex> vertices = model.getVertices();
        for (Vertex vertex:
             vertices) {

            double x = (vertex.getCenterX() - pFocusX) / getDmax(view.getWidth(), pFocusX, vertex.getCenterX());
            double y = (vertex.getCenterY() - pFocusY) / getDmax(view.getHeight(), pFocusY, vertex.getCenterY());

            double pFishXcenterPoint = pFocusX + (((d + 1) * x) / ((d * x) + 1)) * getDmax(view.getWidth(), pFocusX, vertex.getCenterX());
            double pFishYcenterPoint = pFocusY + (((d + 1) * y) / ((d * y) + 1)) * getDmax(view.getHeight(), pFocusY, vertex.getCenterY());

            double xVertexBoundary = vertex.getX() + vertex.getWidth();
            double xVertex = (xVertexBoundary - pFocusX) / getDmax(view.getWidth(), pFocusX, xVertexBoundary);
            double pFishX = pFocusX + (((d + 1) * xVertex) / ((d * xVertex) + 1)) * getDmax(view.getWidth(), pFocusX, xVertexBoundary);

//            double yVertexBoundary = vertex.getY() + vertex.getHeight();
//            double yVertex = (yVertexBoundary - pFocusY) / getDmax(view.getHeight(), pFocusY, yVertexBoundary);
//            double pFishY = pFocusY + (((d + 1) * yVertex) / ((d * yVertex) + 1)) * getDmax(view.getHeight(), pFocusY, yVertexBoundary);

            double ratio = vertex.getWidth() / vertex.getHeight();

            double width = 2 * (pFishX - pFishXcenterPoint);
            // As we are using the ratio, we don't need to calculate the height using the pFishY and pFishYcenterPoint variables
            double height = width / ratio;

            double topX = pFishXcenterPoint - (width / 2);
            double topY = pFishYcenterPoint - (height / 2);

            Vertex newVertex = new Vertex(topX, topY, width, height);
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
