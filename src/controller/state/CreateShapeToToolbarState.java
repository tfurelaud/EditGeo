package controller.state;

import controller.AddShapeCommand;
import controller.CommandMemory;
import controller.Controller;
import model.Plan;
import model.Point;
import model.Shape;
import view.ViewWindow;

/**
 * Current state when we create a shape from whiteboard to toolbar.
 * @author Cayrol, Furelaud
 *
 */
public class CreateShapeToToolbarState implements State{

	public void enter(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar ,Shape s, Point p, Point start) {
		s.setPos(start);
		Shape s_toolbar = s.clone();
		s_toolbar.setPos(new Point(-window.getTBWidth()/2, toolbar.getShapes().size() * window.getToolbarView().getHeightItem()));
		toolbar.addShape(s_toolbar);	
		cmd.addCommand(new AddShapeCommand(toolbar,s_toolbar));
		ctrl.setCurrentState(ctrl.initState);
	}

}
