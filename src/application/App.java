package application;

import controller.Controller;
import model.ColorRGB;
import model.Plan;
import model.Point;
import model.Polygon;
import model.Shape;
import model.Square;
import view.JFXViewWindow;
import view.ViewWindow;
import view.ViewWindowAPI;

/**
 * The main of the software
 * @author Cayrol, Furelaud
 *
 */
public class App {
	private static final int globalHeight = 600;
	private static final int globalWidth = 800;
	private static final int dec = 5;

	public static void main(String[] args) {
		
		String nameApp = "EditGeo";
		Point translationWb = new Point(globalWidth/10 + dec ,globalHeight/10 + dec);
		Point translationTb = new Point(0 ,globalHeight/10+ dec);
		Plan toolbar = new Plan(globalWidth/10,globalHeight - globalHeight/10,true);
		Plan whiteboard = new Plan(globalWidth - globalWidth/10,globalHeight - globalHeight/10,false);
		ColorRGB green = new ColorRGB(0,255,0);
        ColorRGB blue = new ColorRGB(0,0,255);
		Shape square = new Square(new Point(100,300),100,100,new Point(2,30),green,20,30);
		Shape poly = new Polygon(new Point(400,100),8,50,new Point(2,30),blue);
		
		if(toolbar.getShapes().size() == 0) {
			toolbar.addShape(square);
			toolbar.addShape(poly);
		}
		
		ViewWindowAPI w_api = new JFXViewWindow();
		ViewWindow window = new ViewWindow(toolbar, whiteboard, translationWb, translationTb,globalWidth,globalHeight,nameApp, w_api);
		
		new Controller(args,toolbar, whiteboard, window);
	}

}
