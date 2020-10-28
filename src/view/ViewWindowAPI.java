package view;

import java.io.File;

import model.Point;

/**
 * The interface to implements for a window
 * @author Cayrol, Furelaud
 *
 */
public interface ViewWindowAPI {
	//APP PART
	public void InitApp(Point translationWb, Point translationTb,double globalWidth, double globalHeight, String nameApp, double dec, MouseListener mouseListener, double upBarHeight);
	public void launchApp(String[] args);
	//DRAWING PART
	public void drawButtons(double height, double width);
	//GETTERS
	public ViewPlanAPI getWhiteBoardAPI();
	public ViewPlanAPI getToolBarAPI();
	public Point getPosWindow();
	//SENDING ORDERS TO CONTROLLER
	public void sendMousePressed(MouseEventInfo evt);
	public void sendMouseDragged(MouseEventInfo evt);
	public void sendMouseReleased(MouseEventInfo evt);
	public void sendNewClicked();
	public void sendUndoClicked();
	public void sendRedoClicked();
	public void sendSaveClicked(File selectedFile);
	public void sendLoadClicked(File selectedFile);
}
