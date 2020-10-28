package model;

import java.util.ArrayList;

/**
 * The interface that implements Shape. The prototype of shape
 * @author Cayrol, Furelaud
 *
 */
public interface ShapePrototype extends Cloneable {
	
	// Shape methods
	public Shape clone();
	public Point getPos();
	public double getWidth();
	public double getHeight();
	public Point getRotationCenter();
	public ColorRGB getColorRGB();
	public double getRotation();
	public void setPos(double x, double y);
	public void setPosX(double x);
	public void setPosY(double y);
	public void setColorRGB(int r, int g, int b);
	public void setWidth(double width);
	public void setHeight(double height);
	public void setRotation(double rotation);
	public boolean onIt(double x, double y);
	
	// Composite methods
	public ArrayList<Shape> getShapes();
	public void addShape(Shape shape);
	public void removeShape(Shape shape);
}
