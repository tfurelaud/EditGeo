package controller;


import java.io.File;

import controller.state.*;
import model.ColorRGB;
import model.Plan;
import model.Point;
import view.ViewWindow;


/**
 * Define all the methods that we can do in the software. 
 * Delegate the task to the current state.  
 * @author Cayrol, Furelaud
 *
 */
public class Controller {

	private Plan toolbar;
	private Plan whiteboard;
	private CommandMemory commands;
	private State currentState;
	private ViewWindow window;
	
	public final InitState initState = new InitState();
	public final InToolbarState inToolbarState = new InToolbarState();
	public final FromToolbarState fromToolbarState = new FromToolbarState();
	public final InWhiteBoardState inWhiteBoardState = new InWhiteBoardState();
	public final CreateShapeToToolbarState createShapeToToolbarState = new CreateShapeToToolbarState();
	public final MoveShapeOnWhiteBoardState moveShapeOnWhiteBoardState = new MoveShapeOnWhiteBoardState();
	public final SelectionState selectionState = new SelectionState();
	public final GroupDialogBoxState groupDialogBoxState = new GroupDialogBoxState();
	public final ModifieShapeDialogBoxState modifieShapeDialogBoxState = new ModifieShapeDialogBoxState();
	public final DeleteShapeState deleteShapeState = new DeleteShapeState();
	
	public Controller(String[] args, Plan toolbar, Plan whiteboard, ViewWindow window) {
		this.toolbar = toolbar;
		this.whiteboard = whiteboard;
		commands = new CommandMemory();
		currentState = initState;
		this.window = window;
		window.setController(this);
		window.InitApp(args);
	}
	
	public void setCurrentState(State state) {
		this.currentState = state;
	}
	
	public void rightClick(Point p) {
		currentState.rightClick(this,commands,window,toolbar,whiteboard,p);
	}
	
	public void leftClick(Point p) {
		currentState.leftClick(this,commands,window,toolbar,whiteboard,p);
	}
	
	public void dragged(Point p) {
		currentState.dragged(p);
	}
	
	public void released(Point p) {
		currentState.released(this,commands,window,toolbar,whiteboard,p);
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
		currentState.clickOnApply(color,dimSide,nbSide,rotation_center,rotation);
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
	public void clickOnApply(ColorRGB color, double width, double height,double roundedX, double roundedY, Point rotation_center, double rotation) {
		currentState.clickOnApply(color,width,height,roundedX, roundedY,rotation_center,rotation);
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
		currentState.clickOnApply(color,width,height,rotation_center,rotation);
	}
	
	/**
	 * Used when the user click on OK, when the dialog box is up and when the shape is a Polygon.
	 */
	public void clickOnOK(ColorRGB color, double dimSide, int nbSide, Point rotation_center,double rotation) {
		currentState.clickOnOK(this,commands,color,dimSide,nbSide,rotation_center,rotation);
	}
	
	/**
	 * Used when the user click on OK, when the dialog box is up and when the shape is a Square.
	 */
	public void clickOnOK(ColorRGB color, double width, double height,double roundedX, double roundedY, Point rotation_center,double rotation) {
		currentState.clickOnOK(this,commands,color,width,height,roundedX, roundedY,rotation_center,rotation);
	}
	
	/**
	 * Used when the user click on OK, when the dialog box is up and when the shape is a Composite.
	 */
	public void clickOnOK(ColorRGB color, double width, double height, Point rotation_center,double rotation) {
		currentState.clickOnOK(this,commands,color,width,height,rotation_center,rotation);
	}
	
	public void clickOnClose() {
		currentState.clickOnClose(this);
	}
	
	public void clickOnCancel() {
		currentState.clickOnCancel(this);
	}
	
	public void clickOnGroup() {
		currentState.clickOnGroup(this,commands);
	}
	
	public void clickOnDegroup() {
		currentState.clickOnDegroup(this,commands);
	}
	
	public void undo() {
		currentState.undo(commands);
	}
	
	public void redo() {
		currentState.redo(commands);
	}

	public void clickOnSave(File file) {
		currentState.clickOnSave(this, file, toolbar, whiteboard);
		
	}
	
	public void clickOnLoad(File file) {
		currentState.clickOnLoad(this, file, toolbar, whiteboard);
		
	}
	
	public void clickOnNew() {
		currentState.clickOnNew(this, whiteboard);
	}
	
	
}
