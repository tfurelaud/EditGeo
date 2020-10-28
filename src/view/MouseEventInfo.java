package view;

/**
 * Used to not depending of JavaFX
 * @author Cayrol, Furelaud
 *
 */
public class MouseEventInfo {
	private double x;
	private double y;
	private MouseButton button;
	
	public MouseEventInfo(double x, double y, MouseButton button) {
		this.x = x;
		this.y = y;
		this.button = button;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public MouseButton getButton() {
		return button;
	}
}
