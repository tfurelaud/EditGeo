package controller.state;

import controller.CommandMemory;
import controller.Controller;
import model.Plan;
import model.Point;
import model.Shape;
import view.ViewWindow;

/**
 * Current state when the user clicks on the toolbar
 * @author Cayrol, Furelaud
 */
public class InToolbarState implements State{
	
	Plan plan;
	ViewWindow window;
	@Override
	public void leftClick(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p) {
		Shape shape = window.getToolbarView().peekUpShape(new Point(p.getX() - window.getTranslationTB().getX(),p.getY() - window.getTranslationTB().getY()));
		if(shape != null && p!= null) {
			Shape copy = shape.clone();
			ctrl.fromToolbarState.enter(window,whiteboard,copy,p,shape);
			ctrl.setCurrentState((State) ctrl.fromToolbarState);
		}else {
			ctrl.setCurrentState((State) ctrl.initState);
		}
	}
	
	
	public void enter(ViewWindow window2,Plan toolbar) {
		this.window = window2;
		this.plan = toolbar; 
	}
}
