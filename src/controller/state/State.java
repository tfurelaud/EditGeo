package controller.state;

import java.io.File;

import controller.CommandMemory;
import controller.Controller;
import model.ColorRGB;
import model.Plan;
import model.Point;
import view.ViewWindow;

/**
 * Interface that all states are implementing
 * @author Cayrol, Furelaud
 *
 */
public interface State {

	/**
	 * To undo a command
	 * @param cm : contains all commands
	 */
	public default void undo(CommandMemory cm){};
	
	/**
	 * To redo a command
	 * @param cm : contains all commands
	 */
	public default void redo(CommandMemory cm){};
	
	/**
	 * Used when the user has been right clicking
	 * @param p : the point where the user has been clicking
	 */
	public default void rightClick(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p){};
	
	/**
	 * Used when the user has been left clicking
	 * @param p : the point where the user has been clicking
	 */
	public default void leftClick(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p){};
	
	/**
	 * Used when the user is dragging the mouse
	 * @param p : the current point where the mouse is
	 */
	public default void dragged(Point p) {};
	
	/**
	 * Used when the user released a click
	 * @param p : the point where the mouse has been released
	 */
	public default void released(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p) {};
	
	/**
	 * Used to setup the new current state
	 */
	public default void enter() {};
	
	/**
	 * Used when the user has been clicking on the "Save" button and chose the file where he wants to save.
	 * @param file : the file where the software has to be saved
	 * @param toolbar : the toolbar that needs to be saved
	 * @param whiteboard : the whiteboard that needs to be saved
	 */
	public default void clickOnSave(Controller ctrl, File file, Plan toolbar, Plan whiteboard) {};
	
	/**
	 * Used when the user has been clicking on the "Load" button and chose the file that he wants to load.
	 * @param file : the file that has to be load
	 * @param toolbar : the toolbar that needs to be load
	 * @param whiteboard : the whiteboard that needs to be load
	 */
	public default void clickOnLoad(Controller ctrl, File file, Plan toolbar, Plan whiteboard) {};
	
	/**
	 * Used when the user clicked on "Cancel"
	 */
	public default void clickOnCancel(Controller ctrl) {}
	
	/**
	 * Used when the user clicked on "Close"
	 */
	public default void clickOnClose(Controller ctrl){}
	
	/**
	 * Used when the user clicked on "Group" when he selected a zone
	 */
	public default void clickOnGroup(Controller controller,CommandMemory cmd) {};
	
	/**
	 * Used when the user clicked on "Degroup" when he selected a composite
	 */
	public default void clickOnDegroup(Controller controller,CommandMemory cmd) {};
	
	/**
	 * Used when the user clicked on "New"
	 * @param whiteboard : whiteboard to be cleans
	 */
	public default void clickOnNew(Controller ctrl, Plan whiteboard) {}
	
	/**
	 * Used when the user click on apply, when the dialog box is up and when the shape is a Polygon.
	 * @param color : the color of the polygon
	 * @param dimSide : the dimension of a side of the polygon
	 * @param nbSide : the number of side of the polygon
	 * @param rotation_center : the rotation center of the polygon
	 * @param rotation : the rotation of the polygon
	 */
	public default void clickOnApply(ColorRGB color, double dimSide, int nbSide, Point rotation_center, double rotation) {};
	
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
	public default void clickOnApply(ColorRGB color, double width, double height,double roundedX, double roundedY, Point rotation_center, double rotation) {};
	
	/**
	 * Used when the user click on apply, when the dialog box is up and when the shape is a Composite.
	 * @param color : the color of the Composite
	 * @param width : the width of the Composite
	 * @param height : the height of the Composite
	 * @param rotation_center : the rotation center of the Composite
	 * @param rotation : the rotation of the Composite
	 */
	public default void clickOnApply(ColorRGB color, double width, double height, Point rotation_center, double rotation) {};
	
	/**
	 * Used when the user click on OK, when the dialog box is up and when the shape is a Polygon.
	 * @param color : the color of the polygon
	 * @param dimSide : the dimension of a side of the polygon
	 * @param nbSide : the number of side of the polygon
	 * @param rotation_center : the rotation center of the polygon
	 * @param rotation : the rotation of the polygon
	 */
	public default void clickOnOK(Controller ctrl, CommandMemory cmd, ColorRGB color, double dimSide, int nbSide, Point rotation_center,double rotation) {};
	
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
	public default void clickOnOK(Controller ctrl, CommandMemory cmd, ColorRGB color, double width, double height,double roundedX, double roundedY, Point rotation_center,double rotation) {};
	
	/**
	 * Used when the user click on OK, when the dialog box is up and when the shape is a Composite.
	 * @param color : the color of the Composite
	 * @param width : the width of the Composite
	 * @param height : the height of the Composite
	 * @param rotation_center : the rotation center of the Composite
	 * @param rotation : the rotation of the Composite
	 */
	public default void clickOnOK(Controller ctrl, CommandMemory cmd, ColorRGB color, double width, double height, Point rotation_center,double rotation) {};
}
