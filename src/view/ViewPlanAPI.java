package view;

import javafx.scene.Group;
import model.ColorRGB;
import model.Point;
import model.Polygon;
import model.Shape;
import model.ShapeComposite;
import model.Square;

/**
 * The interface to implements for a plan
 * @author Cayrol, Furelaud
 *
 */
public interface ViewPlanAPI {
	
	public static final DialogBoxFactory factory = new DialogBoxFactory();
	
	public void initialize(double width, double height);
	public void cleanView();
	public void drawPolygon(Polygon poly);
	public void drawSquare(Square square);
	public void drawShapeComposite(ShapeComposite shape);
	public Group getGroup();
	
	/// WhiteBoardAPI
	public void drawDialogBox(Shape s,double x, double y);
	public void drawDialogBoxGroup(double x, double y);
	public void initSelectGroup(double x, double y);
	public void drawSelectGroup(Point start, Point end);
	public void deleteSelectGroup();
	public void sendGroupClicked();
	public void sendDeGroupClicked();
	public void sendClose();
	public void sendCancel();
	public void sendApply(ColorRGB color, double dimSide, int nbSide, Point rotation_center, double rotation);
	public void sendApply(ColorRGB color, double width, double height, double roundedX, double roundedY, Point rotation_center, double rotation);
	public void sendApply(ColorRGB color, double width, double height, Point rotation_center, double rotation);
	public void sendOK(ColorRGB color, double dimSide, int nbSide, Point rotation_center, double rotation);
	public void sendOK(ColorRGB color, double width, double height, double roundedX, double roundedY, Point rotation_center, double rotation);
	public void sendOK(ColorRGB color, double width, double height, Point rotation_center, double rotation);
	
	//ToolBarAPI
	public void init_trash(String trash, double x, double y, double width, double height);
	public void draw_trash();
}
