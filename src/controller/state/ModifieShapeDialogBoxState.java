package controller.state;

import controller.ChangeAttributShapeCommand;
import controller.CommandMemory;
import controller.Controller;
import model.ColorRGB;
import model.Plan;
import model.Point;
import model.Polygon;
import model.Shape;
import model.ShapeComposite;
import model.Square;
import view.ViewWhiteBoard;

/**
 * Current state when the user right clicked on a shape in the whiteboard
 * @author Cayrol, Furelaud
 */
public class ModifieShapeDialogBoxState implements State{
	Shape old;
	Shape curr;
	Plan plan;
	
	public void enter(ViewWhiteBoard viewWhiteBoard, Shape curr, Shape old) {
		this.old = old;
		this.curr = curr;
		this.plan = viewWhiteBoard.getPlan();
	}
	
	/**
	 * Used when the user click on apply, when the dialog box is up and when the shape is a Polygon.
	 * @param color : the color of the polygon
	 * @param dimSide : the dimension of a side of the polygon
	 * @param nbSide : the number of side of the polygon
	 * @param rotation_center : the rotation center of the polygon
	 * @param rotation : the rotation of the polygon
	 */
	public void clickOnApply(ColorRGB color, double dimSide, int nbSide, Point rotation_center, double rotation) {
		Polygon poly = (Polygon)curr;
		if(poly.getDimSide() != dimSide)
			poly.setDimSide(dimSide);
		if(poly.getNbSide() != nbSide)
			poly.setNbSide(nbSide);
		setShape(rotation_center, color, rotation);
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
	public void clickOnApply(ColorRGB color, double width, double height,double roundedCornerWidth, double roundedCornerHeight, Point rotation_center, double rotation) {
		Square square = (Square)curr;
		if(square.getWidth() != width)
			square.setWidth(width);
		if(square.getHeight() != height)
			square.setHeight(height);
		if(square.getRoundedCornerWidth() != roundedCornerWidth)
			square.setRoundedCornerWidth(roundedCornerWidth);
		if(square.getRoundedCornerHeight() != roundedCornerHeight)
			square.setRoundedCornerHeight(roundedCornerHeight);
		setShape(rotation_center,color, rotation);
	}
	
	/**
	 * Used when the user click on apply, when the dialog box is up and when the shape is a Composite.
	 * @param color : the color of the Composite
	 * @param width : the width of the Composite
	 * @param height : the height of the Composite
	 * @param rotation_center : the rotation center of the Composite
	 * @param rotation : the rotation of the Composite
	 */
	public void clickOnApply(ColorRGB color, double width, double height, Point rotation_center, double rotation) {
		ShapeComposite shc = (ShapeComposite) curr;
		if(shc.getWidth() != width) {
			shc.setWidth(width);
		}
		if(shc.getHeight() != height) {
			shc.setHeight(height);
		}
		setShape(rotation_center, color, rotation);
	}
	
	/**
	 * Used when the user click on OK, when the dialog box is up and when the shape is a Polygon.
	 * @param color : the color of the polygon
	 * @param dimSide : the dimension of a side of the polygon
	 * @param nbSide : the number of side of the polygon
	 * @param rotation_center : the rotation center of the polygon
	 * @param rotation : the rotation of the polygon
	 */
	public void clickOnOK(Controller ctrl, CommandMemory cmd, ColorRGB color, double dimSide, int nbSide, Point rotation_center,double rotation) {
		clickOnApply(color, dimSide, nbSide, rotation_center, rotation);
		clickOnOK(ctrl,cmd);
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
	public void clickOnOK(Controller ctrl, CommandMemory cmd, ColorRGB color, double width, double height,double roundedX, double roundedY, Point rotation_center,double rotation) {
		clickOnApply(color,width,height,roundedX,roundedY,rotation_center,rotation);
		clickOnOK(ctrl,cmd);
	}
	
	/**
	 * Used when the user click on OK, when the dialog box is up and when the shape is a Composite.
	 * @param color : the color of the Composite
	 * @param width : the width of the Composite
	 * @param height : the height of the Composite
	 * @param rotation_center : the rotation center of the Composite
	 * @param rotation : the rotation of the Composite
	 */
	public void clickOnOK(Controller ctrl, CommandMemory cmd, ColorRGB color, double width, double height, Point rotation_center,double rotation) {
		clickOnApply(color, width, height, rotation_center, rotation);
		clickOnOK(ctrl,cmd);
	}
	
	public void clickOnOK(Controller ctrl, CommandMemory cmd) {
		ctrl.setCurrentState(ctrl.initState);
		cmd.addCommand(new ChangeAttributShapeCommand(old,curr, plan));
	}
	
	public void clickOnClose(Controller ctrl) {
		ctrl.setCurrentState(ctrl.initState);
	}
	
	@Override
	public void clickOnCancel(Controller ctrl) {
		plan.removeShape(curr);
		plan.addShape(old);
		ctrl.setCurrentState(ctrl.initState);
	}
	
	public void setShape(Point rotation_center,ColorRGB color, double rotation) {
		
		if(curr.getRotation() != rotation) {
			curr.setRotation(rotation);
		}
		if(curr.getRotationCenter().getX() != rotation_center.getX() || curr.getRotationCenter().getY() != rotation_center.getY())
			curr.setRotationCenter(rotation_center);
		
		if(curr.getColorRGB().getColorR() != color.getColorR() || curr.getColorRGB().getColorG() != color.getColorG() || curr.getColorRGB().getColorB() != color.getColorB())
			curr.setColorRGB(color);
	}
	
}
