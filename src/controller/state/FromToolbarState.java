package controller.state;

import controller.AddShapeCommand;
import controller.CommandMemory;
import controller.Controller;
import controller.DeleteShapeCommand;
import model.Plan;
import model.Point;
import model.Shape;
import view.ViewToolBar;
import view.ViewWhiteBoard;
import view.ViewWindow;

/**
 * Current state when we peek up a shape from toolbar 
 * @author Cayrol, Furelaud
 *
 */
public class FromToolbarState implements State{

	Shape shape;
	Shape stb;
	ViewWindow window;
	Plan plan;
	@Override
	public void dragged(Point p) {
		if(window.getView(p) != null) {
			if(p.getY() - window.getTranslationWB().getY() > 0)
				shape.setPos(new Point(p.getX() - window.getTranslationWB().getX(),p.getY() - window.getTranslationWB().getY()));
			else
				shape.setPos(new Point(p.getX() - window.getTranslationWB().getX(),0));
		}
	}
	@Override
	public void released(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p) {
		if(window.getView(p) instanceof ViewWhiteBoard) {
			cmd.addCommand(new AddShapeCommand(plan,shape));
			ctrl.setCurrentState((State) ctrl.initState);
		}
		else if(window.getView(p) instanceof ViewToolBar) {
			plan.removeShape(shape);
			//Trash
			if(p.getY() < window.getTranslationTB().getY() + window.getToolbarView().getPosy_trash() + window.getToolbarView().getHeight_trash() && p.getY() > window.getTranslationTB().getY() + window.getToolbarView().getPosy_trash()) {
				window.getToolbarView().getPlan().removeShape(stb);
				cmd.addCommand(new DeleteShapeCommand(window.getToolbarView().getPlan(),stb));
			}
			ctrl.setCurrentState((State) ctrl.initState);
		}
	}
	
	public void enter(ViewWindow window2,Plan plan, Shape copy, Point p, Shape stb) {
		this.window = window2;
		this.shape = copy;
		this.stb = stb;
		this.plan = plan;
		shape.setPos(new Point(p.getX() - window2.getTranslationWB().getX(),p.getY() - window2.getTranslationWB().getY()));
		plan.addShape(shape);
	}
}
