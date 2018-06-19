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

        //new coordinates and width/height are calculate for each vertex of the model
        List<Vertex> vertices = model.getVertices();
        for (Vertex vertex:
             vertices) {

            //Calculate pFish x and y using the center of the vertex as the pNorm
            double pNormX = vertex.getCenterX();
            double pNormY = vertex.getCenterY();

            double x = (pNormX - pFocusX) / getDmax(view.getWidth(), pFocusX, pNormX);
            double y = (pNormY - pFocusY) / getDmax(view.getHeight(), pFocusY, pNormY);

            double pFishX = pFocusX + (((d + 1) * x) / ((d * x) + 1)) * getDmax(view.getWidth(), pFocusX, pNormX);
            double pFishY = pFocusY + (((d + 1) * y) / ((d * y) + 1)) * getDmax(view.getHeight(), pFocusY, pNormY);

            //Calculate qFish x using the boundary of the original vertex as pNorm
            pNormX = vertex.getX() + vertex.getWidth();
            x = (pNormX - pFocusX) / getDmax(view.getWidth(), pFocusX, pNormX);
            double qFishX = pFocusX + (((d + 1) * x) / ((d * x) + 1)) * getDmax(view.getWidth(), pFocusX, pNormX);

//            double yVertexBoundary = vertex.getY() + vertex.getHeight();
//            double yVertex = (yVertexBoundary - pFocusY) / getDmax(view.getHeight(), pFocusY, yVertexBoundary);
//            double pFishY = pFocusY + (((d + 1) * yVertex) / ((d * yVertex) + 1)) * getDmax(view.getHeight(), pFocusY, yVertexBoundary);

            //Calculate the ratio of the original vertex to preserve the width height ratio of nodes
            double ratio = vertex.getWidth() / vertex.getHeight();

            //Calculate the width and the height of the new vertex
            double width = 2 * (qFishX - pFishX);
            // As we are using the ratio, we don't need to calculate the height using the pFishY and pFishYcenterPoint variables
            double height = width / ratio;

            //Calculate the x and y coordinates of the new vertex
            double topX = pFishX - (width / 2);
            double topY = pFishY - (height / 2);

            //Create the new vertex and add it to the new model
            Vertex newVertex = new Vertex(topX, topY, width, height);
            newModel.addVertex(newVertex);
        }

		return newModel;
	}

	//dMax is the distance from the boundary of the screen to the focus
	double getDmax(double pBoundary, double pFocus, double pNorm){
	    if(pNorm > pFocus)
	        return pBoundary - pFocus;
	    else
	        return  0 - pFocus;
    }

}
