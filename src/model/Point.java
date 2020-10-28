package model;

import java.io.Serializable;

/**
 * Used to not be dependent on a library
 * @author Cayrol, Furelaud
 *
 */
public class Point implements Cloneable,Serializable{

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -3818223057407441969L;
	double x,y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point clone() {
		Point copy;
		try {
			copy = (Point)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
		return copy;
	}
	
	//SETTERS
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	//GETTERS
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	
}
