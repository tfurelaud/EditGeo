package model;

import java.io.Serializable;
import java.util.ArrayList;

import view.ViewObserver;

/**
 * Abstract class "Shape". All shapes have to extend this
 * @author Cayrol, Furelaud
 *
 */
public abstract class Shape implements ShapePrototype,Serializable {
	
	/**
	 * The serial number
	 */
	private static final long serialVersionUID = -4305100164051426199L;
	/**
	 * All observers
	 */
	transient ArrayList<ViewObserver> observers;
	private Point pos, rotation_center;
	private double width, height, rotation;
	private ColorRGB color;
	
	public Shape(Point pos, double width, double height, Point rotation_center, double rotation, int translation,  ColorRGB color) {
		this.observers = new ArrayList<ViewObserver>();
		this.pos = pos;
		this.height = height;
		this.width = width;
		this.color = color;
		this.rotation_center = rotation_center;
		this.rotation = 0;
	}
	
	
	public void addObservers(ViewObserver obs) {
		if(this.observers == null) {
			this.observers = new ArrayList<ViewObserver>();
		}
		observers.add(obs);
	}
	
	public void removeObservers(ViewObserver obs) {
		observers.remove(obs);
	}
	
	/**
	 * Notify all observers
	 */
	public void notifyView() {
		for(ViewObserver o : observers) {
			o.update();
		}
	}
	

	public void addShape(Shape shape) {}
	
	public void removeShape(Shape shape) {}
	
	//GETTERS
	public Point getPos() {
		return pos;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public Point getRotationCenter() {
		return rotation_center;
	}
	
	@Override
	public ColorRGB getColorRGB() {
		return this.color;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public ArrayList<ViewObserver> getObs(){
		return observers;
	}
	
	public ArrayList<Shape> getShapes() {
		return null;
	};
	
	//SETTERS
	public void setPos(double x, double y) {
		pos.setX(x);
		pos.setY(y);
		notifyView();
	}
	
	public void setPosX(double x) {
		pos.setX(x);
	}
	
	public void setPosY(double y) {
		pos.setY(y);
	}
	
	public void setPos(Point p) {
		double savex = pos.getX(), savey = pos.getY();
		double rota = this.rotation;
		this.rotation_center.setX(pos.getX());
		this.rotation_center.setY(pos.getY());
		setRotation(rota);
		pos.setX(p.getX());
		pos.setY(p.getY());
		this.rotation_center.setX(savex);
		this.rotation_center.setY(savey);
		setRotation(rota);
		notifyView();
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
		notifyView();
	}

	@Override
	public void setHeight(double height) {
		this.height = height;
		notifyView();
	}
	
	public void setRotationCenter(Point rotation_center) {
		double x = rotation_center.getX();
		double y = rotation_center.getY();
		double size =  Math.sqrt(Math.pow(this.getPos().getX()-x, 2) + Math.pow(this.getPos().getY()-y,2));
		double newx = x + Math.cos(Math.toRadians(this.rotation)) * size;
		double newy = y + Math.sin(Math.toRadians(this.rotation)) * size;
		setPos(new Point(newx,newy));
		rotation_center.setX(x);
		rotation_center.setY(y);
		notifyView();
	}
	
	public void setColorRGB(int r,int g,int b){
		color.setColorRGB(r, g, b);
		notifyView();
	}
	
	public void setColorRGB(ColorRGB color){
		this.color.setColorRGB(color.getColorR(), color.getColorG(), color.getColorB());
		notifyView();
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
		notifyView();
	}
		
	public void setObservers() {
		observers = new ArrayList<ViewObserver>();
	}

	//
	public Shape clone()
    {
        try {
            Shape copy = (Shape)super.clone();
            copy.pos = this.pos.clone();
            copy.color = this.color.clone();
            copy.rotation_center = this.rotation_center.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
	
	public boolean onIt(Point p) {
		Point a = new Point(this.getPos().getX(),this.getPos().getY());
		double newx = (p.getX()-a.getX())*Math.cos(Math.toRadians(-this.rotation)) - (p.getY()-a.getY())*Math.sin(Math.toRadians(-this.rotation))+a.getX();
		double newy = (p.getX()-a.getX())*Math.sin(Math.toRadians(-this.rotation)) + (p.getY()-a.getY())*Math.cos(Math.toRadians(-this.rotation))+a.getY();
		Point p_in_plan = new Point(newx,newy);
		if(p_in_plan.getX()>a.getX() && p_in_plan.getX()<a.getX()+this.width && p_in_plan.getY()>a.getY() && p_in_plan.getY()<a.getY()+this.height) {
			return true;
		}
		return false;
	}
}
