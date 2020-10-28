package controller;


import model.Shape;
import model.ShapeComposite;
import model.Plan;

/**
 * Used when a shape is remove from a composite
 * @author Cayrol, Furelaud
 *
 */
public class RemoveFromCompositeCommand implements Command{

	private ShapeComposite shc;
	private Plan wb;
	
	public RemoveFromCompositeCommand(Plan wb, ShapeComposite shc) {
		this.wb = wb;
		this.shc = shc;
	}
	@Override
	public void doCommand() {
		for(Shape s : shc.getShapes()) {
			wb.addShape(s);
		}
		wb.removeShape(shc);
	}

	@Override
	public void undoCommand() {
		for(Shape s : shc.getShapes()) {
			wb.removeShape(s);
		}
		wb.addShape(shc);
	}

	
}
