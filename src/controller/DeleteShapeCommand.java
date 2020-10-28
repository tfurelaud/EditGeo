package controller;

import model.Plan;
import model.Shape;

/**
 * Used when a shape is deleted on a plan.
 * @author Cayrol, Furelaud
 *
 */
public class DeleteShapeCommand implements Command{
	private Plan plan;
	private Shape shape;
	
	public DeleteShapeCommand(Plan plan, Shape shape){
		this.plan = plan;
		this.shape = shape;
	}
	
	@Override
	public void doCommand() {
		this.plan.removeShape(shape);
	}

	@Override
	public void undoCommand() {
		this.plan.addShape(shape);
	}

}
