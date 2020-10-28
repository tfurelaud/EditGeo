package controller;

import model.Point;
import model.Shape;

/**
 * Used when a shape in move on a plan
 * @author Cayrol, Furelaud
 *
 */
public class MoveShapeCommand implements Command{

	private Shape fig;
	private Point old;
	private Point curr;
	
	public MoveShapeCommand(Shape f, Point old) {
		this.fig = f;
		this.old = old;
		this.curr = f.getPos().clone();
	}
	@Override
	public void doCommand() {
		fig.setPos(curr);
	}

	@Override
	public void undoCommand() {
		fig.setPos(old);
	}

}
