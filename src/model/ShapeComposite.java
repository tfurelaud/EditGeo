package model;

import java.util.ArrayList;
import java.util.Iterator;

import view.ViewObserver;

/**
 * Composite of shapes
 * @author Cayrol, Furelaud
 *
 */
public class ShapeComposite extends Shape{
	
	/**
	 * The serial number
	 */
	private static final long serialVersionUID = -9083269890090335248L;
	/**
	 * All the shapes that are in the composite
	 */
	ArrayList<Shape> shapeArray;
	
	public ShapeComposite() {
		super(new Point(0,0),0,0,new Point(0,0),0,0,new ColorRGB(0,0,0));
		this.shapeArray = new ArrayList<>();
	}
	
	public void addObservers(ViewObserver obs) {
		for(Shape s : shapeArray) {
			if(s.getObs() == null) {
				s.setObservers();
			}
			if(!s.getObs().contains(obs)) {
				s.addObservers(obs);
			}
		}
		super.addObservers(obs);
	}
	
	public void addShape(Shape shape) {
		shapeArray.add(shape);
		
		super.notifyView();
	}
	
	public void removeShape(Shape shape) {
		shapeArray.remove(shape);
		super.notifyView();
	}
	
	//GETTERS
	public Point getPos(){
		double xmin = Double.MAX_VALUE, ymin = Double.MAX_VALUE;
		Iterator<Shape> it = shapeArray.iterator();
		while(it.hasNext()) {
			Shape shape = it.next();
			if(xmin > shape.getPos().getX())
				xmin = shape.getPos().getX();
			if (ymin > shape.getPos().getY())
				ymin = shape.getPos().getY();
			
		}
		Point ret = new Point(xmin,ymin);
		return ret;
	}
	
	public double getHeight() {
		double max = Double.MIN_VALUE;
		double min = this.getPos().getY();
		for(Shape f : shapeArray) {
			double curr = f.getPos().getY() + f.getHeight();
			if(curr > max)
				max = curr;
		}
		return max-min;
	}
	
	public double getWidth() {
		double max = Double.MIN_VALUE;
		double min = this.getPos().getX();
		for(Shape f : shapeArray) {
			double curr = f.getPos().getX() + f.getWidth();
			if(curr > max)
				max = curr;
		}
		return max-min;
		
	}
	
	public ColorRGB getColorRGB() {
		int meanR=0,meanG=0,meanB=0;
		for(Shape f : shapeArray) {
			ColorRGB figColor = f.getColorRGB();
			meanR += figColor.getColorR();
			meanG += figColor.getColorG();
			meanB += figColor.getColorB();
		}
		meanR = meanR / shapeArray.size();
		meanG = meanG / shapeArray.size();
		meanB = meanB / shapeArray.size();
		ColorRGB ret = new ColorRGB(meanR, meanG, meanB);
		return ret;
	}
	
	public ArrayList<Shape> getShapes(){
		return this.shapeArray;
	}
	
	//SETTERS
	public void setColorRGB(ColorRGB color) {
		for(Shape f : shapeArray) {
			f.setColorRGB(color);
		}
	}
	
	public void setColorRGB(int r, int g, int b) {
		for(Shape f : shapeArray) {
			f.setColorRGB(r,g,b);
		}
	}
	
	public void setPos(Point p) {
		double xcomp = this.getPos().getX();
		double ycomp = this.getPos().getY();
		for( Shape f : shapeArray) {
			double diff_x = f.getPos().getX() - xcomp;
			double diff_y = f.getPos().getY() - ycomp;
			Point curr = new Point(diff_x + p.getX(),diff_y + p.getY());
			f.setPos(curr);
		}
	}
	
	public void setPos(double x, double y) {
		this.setPosX(x);
		this.setPosY(y);
	}
	
	
	public void setPosX(double x) {
		double xcomp = this.getPos().getX();
		for( Shape f : shapeArray) {
			double diff = f.getPos().getX() - xcomp;
			f.setPosX(diff + x);
		}
	}
	
	public void setPosY(double y) {
		double ycomp = this.getPos().getY();
		for( Shape f : shapeArray) {
			double diff = f.getPos().getY() - ycomp;
			f.setPosY(diff + y);
		}
	}
	
	
	public void setWidth(double width) {
		if(this.getWidth() != width) {
			double ratio = this.getWidth()/width;
			double compX = this.getPos().getX();
			for( Shape f : shapeArray) {
				double save = (f.getPos().getX() - compX) / ratio;
				f.setPosX(0 + f.getPos().getX() - this.getPos().getX());
				f.setWidth(f.getWidth()/ratio);
				f.setPos(new Point(compX + save,f.getPos().getY()));
			}
		}
	}
	
	public void setHeight(double height) {
		if(this.getHeight() != height) {
			double ratio = this.getHeight()/height;
			double compY = this.getPos().getY();
			for( Shape f : shapeArray) {
				double save = (f.getPos().getY() - compY) / ratio;
				f.setPosY(0 + f.getPos().getY() - compY);
				f.setHeight(f.getHeight()/ratio);
				f.setPos(new Point(f.getPos().getX(),compY + save));
			}
		}
		
	}
	
	@Override 
	public void setRotation(double rota) {
		for(Shape s : shapeArray) {
			double wi = s.getWidth();
			double he = s.getHeight();
			s.setRotation(rota);
			s.setWidth(wi);
			s.setHeight(he);
		}
	}
	
	public void setRotationCenter(Point point) {
		for(Shape s : shapeArray) {
			double wi = s.getWidth();
			double he = s.getHeight();
			s.setRotationCenter(point);
			s.setWidth(wi);
			s.setHeight(he);
		}
	}
	
	//
	
	public Shape clone() {
		Shape ret = new ShapeComposite();
		for(Shape f : shapeArray) {
			Shape to_add = f.clone();
			ret.addShape(to_add);
		}
		return ret;
	}

	public boolean onIt(Point p) {
		for(Shape f : shapeArray) {
			if(f.onIt(p))
				return true;
		}
		return false;
	}
	
	public boolean onIt(double x, double y) {
		for(Shape f : shapeArray) {
			if(f.onIt(x,y))
				return true;
		}
		return false;
	}
	
}

