package controller;

import model.Plan;
import model.Shape;

/**
 * Used when a shape is remove on a plan (in the trash).
 * @author Cayrol, Furelaud
 *
 */
public class RemoveShapeCommand implements Command{

	private Plan plan;
	private Shape shape;
	
	public RemoveShapeCommand(Plan plan, Shape shape){
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
