package model;

/**
 * The square shape
 * @author Cayrol, Furelaud
 *
 */
public class Square extends Shape{
	
	/**
	 * The serial number
	 */
	private static final long serialVersionUID = 5454402932104259401L;
	private double roundedCornerWidth,roundedCornerHeight;
 
	public Square(Point p, double width, double height, Point rotation_center,ColorRGB color, double roundedCornerWidth, double roundedCornerHeight) {
		super(p, width, height, rotation_center,0,0, color);
		this.roundedCornerWidth = roundedCornerWidth;
		this.roundedCornerHeight = roundedCornerHeight;
	}

	//GETTERS
	public double getRoundedCornerWidth() {
		return roundedCornerWidth;
	}

	public double getRoundedCornerHeight() {
		return roundedCornerHeight;
	}

	//SETTERS
	
	public void setRoundedCornerWidth(double roundedCornerWidth) {
		this.roundedCornerWidth = roundedCornerWidth;
		super.notifyView();
	}
	
	public void setRoundedCornerHeight(double roundedCornerHeight) {
		this.roundedCornerHeight = roundedCornerHeight;
		super.notifyView();
	};
	//
	public boolean onIt(double x, double y) {
		Point p = new Point(x,y);
		return super.onIt(p);
	}

}
