package controller.state;

import controller.CommandMemory;
import controller.Controller;
import controller.RemoveShapeCommand;
import model.Plan;
import model.Shape;

/**
 * Current state when a shape is deleted
 * @author Cayrol, Furelaud
 *
 */
public class DeleteShapeState implements State{
	public void enter(Controller ctrl, CommandMemory cmd, Plan p, Shape s) {
		p.removeShape(s);
		cmd.addCommand(new RemoveShapeCommand(p,s));
		ctrl.setCurrentState(ctrl.initState);
		ctrl.initState.enter();
	}
}
