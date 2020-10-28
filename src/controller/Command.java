package controller;

/**
 * Interface used to implement undo/redo
 * @author Cayrol,Furelaud
 *
 */
public interface Command {
	public void doCommand();
	
	public void undoCommand();
	
}
