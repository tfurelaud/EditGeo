package controller;

import model.Plan;
import model.Shape;

/**
 * Command used to add a shape to a plan
 * @author Cayrol, Furelaud
 *
 */
public class AddShapeCommand implements Command{
	private Plan plan;
	private Shape shape;
	
	public AddShapeCommand(Plan plan, Shape shape){
		this.plan = plan;
		this.shape = shape;
	}
	
	@Override
	public void doCommand() {
		this.plan.addShape(shape);
	}

	@Override
	public void undoCommand() {
		this.plan.removeShape(shape);
	}

}
