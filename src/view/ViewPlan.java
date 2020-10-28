package view;

import model.Plan;
import model.Polygon;
import model.Shape;
import model.ShapeComposite;
import model.Square;

/**
 * The view of plans, used to draw plans and shapes on it.
 * @author Cayrol, Furelaud
 *
 */
public abstract class ViewPlan implements ViewObserver{
	private Plan plan;
	private ViewPlanAPI view_api;
	
	public ViewPlan(Plan plan, ViewPlanAPI view_api) {
		this.plan = plan;
		plan.addObservers(this);
		for(Shape s: plan.getShapes()) {
			s.addObservers(this);
		}
		this.view_api = view_api;
		view_api.initialize(plan.getWidth(), plan.getHeight());
	}
	
	/**
	 * Updating the view, with removing the shape to remove
	 */
	public void update(Shape shape, boolean remove) {
		if(!remove) {
			shape.addObservers(this);
		}
		repaint();
	}
	
	public void update() {}
	
	/**
	 * repaint all the shapes in the plan, to update them
	 */
	public void repaint() {
		view_api.cleanView();
		for(Shape s : plan.getShapes()) {
			if(s instanceof Polygon)
				drawPolygon((Polygon)s);
			
			if(s instanceof Square)
				drawSquare((Square)s);
		
			if(s instanceof ShapeComposite)
				drawShapeComposite((ShapeComposite)s);
		}
	}
	
	/**
	 * drawing a polygon
	 * @param poly : the polygon to draw
	 */
	public void drawPolygon(Polygon poly) {
		view_api.drawPolygon(poly);
	}
	
	/**
	 * drawing a square
	 * @param square : the square to draw
	 */
	public void drawSquare(Square square) {
		view_api.drawSquare(square);
	}
	
	/**
	 * drawing a composite
	 * @param composite : the composite to draw
	 */
	public void drawShapeComposite(ShapeComposite shc){
		for(Shape shape : shc.getShapes()) {
			if(shape instanceof Polygon) {
				view_api.drawPolygon((Polygon)shape);
			}
			else if(shape instanceof Square) {
				view_api.drawSquare((Square)shape);
			}
			else if(shape instanceof ShapeComposite) {
				view_api.drawShapeComposite((ShapeComposite)shape);
			}
		}
	}

	//GETTERS 
	public Plan getPlan() {
		return plan;
	}

	public ViewPlanAPI getViewAPI() {
		return view_api;
	}

}
