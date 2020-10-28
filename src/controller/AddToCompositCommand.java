package controller;

import java.util.ArrayList;

import model.Plan;
import model.Shape;
import model.ShapeComposite;

/**
 * Command used to form a composite or add a new shape into a composite
 * @author Cayrol, Furelaud
 *
 */
public class AddToCompositCommand implements Command{

	private ShapeComposite comp;
	private ArrayList<Shape> fig;
	private Plan plan;
	
	public AddToCompositCommand(ShapeComposite comp, ArrayList<Shape> fig, Plan plan) {
		this.fig = fig;
		this.comp = comp;
		this.plan = plan;
	}
	@Override
	public void doCommand() {
		for(Shape s : fig) {
			comp.addShape(s);
			this.plan.removeShape(s);
		}
		this.plan.addShape(comp);
	}

	@Override
	public void undoCommand() {
		for(Shape s : fig) {
			comp.removeShape(s);
			this.plan.addShape(s);
		}
		this.plan.removeShape(comp);
	}

}
