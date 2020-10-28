package view;

import java.io.InputStream;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import model.ColorRGB;
import model.Point;
import model.Polygon;
import model.Shape;
import model.ShapeComposite;
import model.Square;

/**
 * The implementation of the interface "ViewPlanAPI" with JavaFX
 * @author Cayrol, Furelaud
 *
 */
public class JFXViewPlan implements ViewPlanAPI{
	private Group gc;
	private Rectangle select_group;
	private ImageView trash;
	private MouseListener mouseListener;
	private Point translation;
	
	
	public JFXViewPlan(Group group, MouseListener mouseListener,Point translation) {
		this.gc = group;
		this.mouseListener = mouseListener;
		this.select_group = new Rectangle();
		select_group.setStroke(new Color(0,0,0,1.0));
		select_group.setFill(new Color(0,0,0,0));
		this.translation = translation;
	}
	
	/**
	 * Initialize the plan
	 * @param width : the width of the plan that we want to draw
	 * @param height : the height of the plan that we want to draw
	 */
	@Override
	public void initialize(double width, double height) {
		Rectangle wb_r = new Rectangle(0,0,width,height);
        wb_r.setStroke(Color.CORNFLOWERBLUE);
        wb_r.setFill(Color.WHITE);
        gc.getChildren().add(wb_r);
	}
	
	/**
	 * Removing all the things that are draw on the plan
	 */
	@Override
	public void cleanView() {
		while(gc.getChildren().size() != 1)
			gc.getChildren().remove(1);
	}
	
	/**
	 * Drawing a polygon
	 * @param poly : the polygon to be drawn
	 */
	@Override
	public void drawPolygon(Polygon poly) {
		int r = poly.getColorRGB().getColorR();
		int g = poly.getColorRGB().getColorG();
		int b = poly.getColorRGB().getColorB();
		Color to_fill = Color.rgb(r,g,b,1.0);
		javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(); 
		for(int i=0 ; i<poly.getNbSide() ;i++) {
			polygon.getPoints().addAll(poly.getPosx()[i],poly.getPosy()[i]);
		}
		polygon.getTransforms().add(new Rotate(poly.getRotation(),poly.getPos().getX(),poly.getPos().getY()));
		
		polygon.setFill(to_fill);
		gc.getChildren().add(polygon);
	}
	
	/**
	 * Drawing a square
	 * @param poly : the square to be drawn
	 */
	@Override
	public void drawSquare(Square square) {
		int r = square.getColorRGB().getColorR();
		int g = square.getColorRGB().getColorG();
		int b = square.getColorRGB().getColorB();
		Color to_fill = Color.rgb(r,g,b,1.0);
		Rectangle rect = new Rectangle(square.getPos().getX(),square.getPos().getY(),square.getWidth(),square.getHeight());
        rect.setArcWidth(square.getRoundedCornerWidth()); 
        rect.setArcHeight(square.getRoundedCornerHeight());  
        rect.getTransforms().add(new Rotate(square.getRotation(),square.getPos().getX(),square.getPos().getY()));
		rect.setFill(to_fill);
		gc.getChildren().add(rect);
	}
	
	/**
	 * Drawing a composite
	 * @param poly : the composite to be drawn
	 */
	@Override
	public void drawShapeComposite(ShapeComposite shape) {
		for(Shape s : shape.getShapes()) {
			if(s instanceof Square) {
				this.drawSquare((Square)s);
			}
			if(s instanceof Polygon) {
				this.drawPolygon((Polygon)s);
			}
			if(s instanceof ShapeComposite) {
				this.drawShapeComposite((ShapeComposite)s);
			}
		}
	}
	
	/**
	 * Drawing an EditDialogBox
	 * @param s : the shape that we have to open a dialog box to edit. 
	 */
	@Override
	public void drawDialogBox(Shape s,double x, double y) {
		factory.setBox(this,s,x,y);
	}
	
	/**
	 * Drawing a GroupDialogBox 
	 */
	@Override
	public void drawDialogBoxGroup(double x, double y) {
		new DialogGroup(this, x, y);
	}
	
	@Override
	public void initSelectGroup(double x, double y) {
		gc.getChildren().remove(select_group);
		select_group.setX(x);
		select_group.setY(y);
		gc.getChildren().add(select_group);
	}
	
	/**
	 * Drawing the rectangle that is draw when the user is dragging is mouse and hasn't clicked on a shape at the beginning
	 */
	@Override
	public void drawSelectGroup(Point start, Point end) {
		double startx = select_group.getX();
		double starty = select_group.getY();
		//To draw from top left to bottom right
		if(end.getX()>=start.getX() && end.getY()>=start.getY()) {
			select_group.setY(starty);
			select_group.setX(startx);
			select_group.setWidth(end.getX() - start.getX());
			select_group.setHeight(end.getY() - start.getY());
		}
		//To draw from bottom left to top right
		else if(end.getX()>start.getX() && end.getY()<start.getY()) {
			select_group.setY(end.getY()-this.translation.getY());
			select_group.setX(startx);
			select_group.setWidth(end.getX() - start.getX());
			select_group.setHeight(start.getY() - end.getY());
		}
		//To draw from top right to bottom left
		else if(end.getX()<start.getX() && end.getY()>start.getY()) {
			select_group.setY(starty);
			select_group.setX(end.getX()-this.translation.getX());
			select_group.setWidth(start.getX() - end.getX());
			select_group.setHeight(end.getY() - start.getY());
		}
		//To draw from bottom right left to top left
		else {
			select_group.setY(end.getY()-this.translation.getY());
			select_group.setX(end.getX()-this.translation.getX());
			select_group.setWidth(start.getX() - end.getX());
			select_group.setHeight(start.getY() - end.getY());
		}
	}
	
	@Override
	public void deleteSelectGroup() {
		gc.getChildren().remove(select_group);
	}
	
	/**
	 * When the user clicked on "Group"
	 */
	public void sendGroupClicked() {
		mouseListener.groupClicked();
	}
	
	
	/**
	 * When the user clicked on "DeGroup"
	 */
	public void sendDeGroupClicked() {
		mouseListener.degroupClicked();
	}
	
	/**
	 * When the user clicked on "Close"
	 */
	public void sendClose() {
		mouseListener.closeClicked();
	}
	
	
	/**
	 * When the user clicked on "Cancel"
	 */
	public void sendCancel() {
		mouseListener.cancelClicked();
	}
	
	/**
	 * Used when the user click on apply, when the dialog box is up and when the shape is a Polygon.
	 * @param color : the color of the polygon
	 * @param dimSide : the dimension of a side of the polygon
	 * @param nbSide : the number of side of the polygon
	 * @param rotation_center : the rotation center of the polygon
	 * @param rotation : the rotation of the polygon
	 */
	public void sendApply(ColorRGB color, double dimSide, int nbSide, Point rotation_center, double rotation) {
		mouseListener.applyClicked(color,dimSide,nbSide,rotation_center,rotation);
	}
	
	/**
	 * Used when the user click on apply, when the dialog box is up and when the shape is a square.
	 * @param color : the color of the square
	 * @param width : the width of the square
	 * @param height : the height of the square
	 * @param roundedX : the width of the rounded corner of the square
	 * @param roundedY : the height of the rounded corner of the square
	 * @param rotation_center : the rotation center of the square
	 * @param rotation : the rotation of the square
	 */
	public void sendApply(ColorRGB color, double width, double height, double roundedX, double roundedY, Point rotation_center, double rotation) {
		mouseListener.applyClicked(color,width,height,roundedX, roundedY, rotation_center,rotation);
	}
	
	/**
	 * Used when the user click on apply, when the dialog box is up and when the shape is a Composite.
	 * @param color : the color of the Composite
	 * @param width : the width of the Composite
	 * @param height : the height of the Composite
	 * @param rotation_center : the rotation center of the Composite
	 * @param rotation : the rotation of the Composite
	 */
	public void sendApply(ColorRGB color, double width, double height, Point rotation_center, double rotation) {
		mouseListener.applyClicked(color,width,height, rotation_center,rotation);
	}
	
	/**
	 * Used when the user click on OK, when the dialog box is up and when the shape is a Polygon.
	 * @param color : the color of the polygon
	 * @param dimSide : the dimension of a side of the polygon
	 * @param nbSide : the number of side of the polygon
	 * @param rotation_center : the rotation center of the polygon
	 * @param rotation : the rotation of the polygon
	 */
	public void sendOK(ColorRGB color, double dimSide, int nbSide, Point rotation_center, double rotation) {
		mouseListener.okClicked(color,dimSide,nbSide,rotation_center,rotation);
	}
	
	/**
	 * Used when the user click on OK, when the dialog box is up and when the shape is a square.
	 * @param color : the color of the square
	 * @param width : the width of the square
	 * @param height : the height of the square
	 * @param roundedX : the width of the rounded corner of the square
	 * @param roundedY : the height of the rounded corner of the square
	 * @param rotation_center : the rotation center of the square
	 * @param rotation : the rotation of the square
	 */
	public void sendOK(ColorRGB color, double width, double height, double roundedX, double roundedY, Point rotation_center, double rotation) {
		mouseListener.okClicked(color,width,height,roundedX, roundedY, rotation_center,rotation);
	}
	
	/**
	 * Used when the user click on OK, when the dialog box is up and when the shape is a Composite.
	 * @param color : the color of the Composite
	 * @param width : the width of the Composite
	 * @param height : the height of the Composite
	 * @param rotation_center : the rotation center of the Composite
	 * @param rotation : the rotation of the Composite
	 */
	public void sendOK(ColorRGB color, double width, double height, Point rotation_center, double rotation) {
		mouseListener.okClicked(color,width,height, rotation_center,rotation);
	}
	
	@Override
	public void init_trash(String Nametrash, double x, double y, double width, double height) {
		Class<?> clazz = this.getClass();
        InputStream input = clazz.getResourceAsStream(Nametrash);
        Image image = new Image(input);
 
        trash = new ImageView(image);
        trash.setFitWidth(width);
        trash.setFitHeight(height);
        trash.setX(x);
        trash.setY(y);
        gc.getChildren().add(trash);
	}
	
	@Override
	public void draw_trash() {
		gc.getChildren().add(trash);
	}
	
	public Group getGroup() {
		return gc;
	}
}
