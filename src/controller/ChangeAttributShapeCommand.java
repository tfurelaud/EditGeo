package controller;

import model.Plan;
import model.Shape;

/**
 * Command used to change attributes of a shape
 * @author Cayrol, Furelaud
 *
 */
public class ChangeAttributShapeCommand implements Command{
	private Plan plan;
	private Shape old;
	private Shape newShape;
	
	public ChangeAttributShapeCommand(Shape old, Shape newShape, Plan p) {
		this.old = old;
		this.newShape = newShape;
		this.plan = p;
	}
	@Override
	public void doCommand() {
		plan.removeShape(old);
		plan.addShape(newShape);
	}

	@Override
	public void undoCommand() {
		plan.removeShape(newShape);
		plan.addShape(old);
	}
}
