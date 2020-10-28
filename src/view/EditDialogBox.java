package view;

/**
 * Interface that need to implements all the edit dialog box to be drawn.
 * @author Cayrol, Furelaud
 *
 */
public interface EditDialogBox {
	public default void launch() {
		drawDialogBox();
		HandleBox();
	}
	public abstract void drawDialogBox();
	public abstract void HandleBox();
}
