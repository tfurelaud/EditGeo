package view;

import java.io.File;

import controller.Controller;
import model.ColorRGB;
import model.Point;

/**
 * Send order to controller
 * @author Cayrol, Furelaud
 *
 */
public class MouseListener{
	private Controller controller;

	public MouseListener(Controller controller){
		this.controller = controller;
	}

	public void mousePressed(MouseEventInfo evt) {
		Point p = new Point(evt.getX(),evt.getY());
		switch (evt.getButton()){
			case BUTTON1: 
				if (p != null)
					controller.leftClick(p); 
				break;
			case BUTTON2: 
				if(p != null)
					controller.rightClick(p); 
				break;
			default:
		}
	}

	public void mouseDragged(MouseEventInfo evt) {
		Point p = new Point(evt.getX(),evt.getY());
		if (p != null)
			controller.dragged(p); 
	}
	
	public void mouseReleased(MouseEventInfo evt) {
		Point p = new Point(evt.getX(),evt.getY());
		if (p != null)
			controller.released(p); 
	}
	
	public void newClicked() {
		controller.clickOnNew();
	}
	
	public void undoClicked() {
		controller.undo();
	}
	
	public void redoClicked() {
		controller.redo();
	}
	
	public void saveClicked(File selectedFile) {
		controller.clickOnSave(selectedFile);
	}
	
	public void loadClicked(File selectedFile) {
		controller.clickOnLoad(selectedFile);
	}
	
	public void groupClicked() {
		controller.clickOnGroup();
	}
	
	public void degroupClicked() {
		controller.clickOnDegroup();
	}
	
	public void applyClicked(ColorRGB color, double dimSide, int nbSide, Point rotation_center, double rotation) {
		controller.clickOnApply(color,dimSide,nbSide,rotation_center,rotation);
	}
	
	public void applyClicked(ColorRGB color, double width, double height,double roundedX, double roundedY, Point rotation_center, double rotation) {
		controller.clickOnApply(color,width,height,roundedX, roundedY,rotation_center,rotation);
	}
	
	public void applyClicked(ColorRGB color, double width, double height, Point rotation_center, double rotation) {
		controller.clickOnApply(color,width,height,rotation_center,rotation);
	}
	
	public void okClicked(ColorRGB color, double dimSide, int nbSide, Point rotation_center,double rotation) {
		controller.clickOnOK(color,dimSide,nbSide,rotation_center,rotation);
	}
	
	public void okClicked(ColorRGB color, double width, double height,double roundedX, double roundedY, Point rotation_center,double rotation) {
		controller.clickOnOK(color,width,height,roundedX, roundedY,rotation_center,rotation);
	}
	
	public void okClicked(ColorRGB color, double width, double height, Point rotation_center,double rotation) {
		controller.clickOnOK(color,width,height,rotation_center,rotation);
	}
	
	public void closeClicked() {
		controller.clickOnClose();;
	}
	
	public void cancelClicked() {
		controller.clickOnCancel();
	}
	
	public Controller getController() {
		return controller;
	}
}
