package model;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import view.ViewObserver;

/**
 * The plan are the plan that are in the software (basically toolbar and whiteboard are plans)
 * @author Cayrol, Furelaud
 *
 */
public class Plan implements Serializable{
	
	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 6177124641289922281L;
	/**
	 * Observers list
	 */
	private transient ArrayList<ViewObserver> observers;
	private transient Serializer se;
	/**
	 * All the shapes that the plan contains
	 */
	private ArrayList<Shape> shapes;
	private double width;
	private double height;
	/**
	 * Is the plan need to be autosaved or not ? (toolbar : yes, whiteboard : no)
	 */
	private boolean autosave;
	
	public Plan(double width, double height, boolean autosave) {
		this.observers = new ArrayList<ViewObserver>();
		this.shapes = new ArrayList<Shape>();
		this.width = width;
		this.height = height;
		this.se = new Serializer();
		this.autosave = autosave;
		if(autosave)
			this.autoload();
	}
	
	public void addShape(Shape shape) {
		shapes.add(shape);
		notify(shape, false);
		if(autosave)
			se.autosave(this);
	}
	
	public void removeShape(Shape shape) {
		shapes.remove(shape);
		notify(shape, true);
		if(autosave)
			se.autosave(this);
	}
	
	
	public void addObservers(ViewObserver obs) {
		observers.add(obs);
	}
	
	public void removeObservers(ViewObserver obs) {
		observers.remove(obs);
	}
	
	public void notify(Shape shape, boolean remove) {
		for(ViewObserver o : observers) {
			o.update(shape, remove);
		}
	}

	//GETTERS
	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
	public ArrayList<Shape> getShapes(){
		return this.shapes;
	}
	
	public ArrayList<ViewObserver> getObservers(){
		return this.observers;
	}
	//
	
	/**
	 * Used to know if a point is on a shape in the plan or not
	 * @param p : the point that we want to know
	 * @return true if the point is on a shape in the plan and false otherwise
	 */
	public Shape onIt(Point p) {
		Shape ret = null;
		for(Shape s : shapes) {
			if(s.onIt(p))
				ret = s;
		}
		return ret;
	}
	
	public void autoload(){
		for(Shape s : shapes) {
			removeShape(s);
		}
		Plan newP = se.autoload(this);
		if(newP == null)
			return;
		for(Shape s : newP.getShapes()) {
			for(ViewObserver obs : observers)
				s.addObservers(obs);
			this.shapes.add(s);
		}
		this.width = newP.getWidth();
		this.height = newP.getHeight();
	}
	
	public void save(File file) {
		se.save(this, file);
	}
	
	public void load(ObjectInputStream ois) {
		Iterator<Shape> sit = shapes.iterator();
		while(sit.hasNext()) {
			Shape s = sit.next();
			sit.remove();
			notify(s,true);
		}
		Plan newP = se.load(ois);
		if(newP == null)
			return;
		for(Shape s : newP.getShapes()) {
			addShape(s);
		}
		
		this.width = newP.getWidth();
		this.height = newP.getHeight();
	}
	
}
