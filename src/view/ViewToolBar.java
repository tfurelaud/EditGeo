package view;

import model.Plan;
import model.Point;
import model.Polygon;
import model.Shape;
import model.ShapeComposite;
import model.Square;

/**
 * The toolbar view. Manage all the graphic action on it.
 * @author Cayrol, Furelaud
 *
 */
public class ViewToolBar extends ViewPlan{
	private double height_item = 40;
	private double dec_x;
	private double dec_y;
	private double posy_trash; 
	private double height_trash = 40;
	private double width_trash = 40;
	
	public ViewToolBar(Plan plan, ViewPlanAPI view_api, String trashName) {
		super(plan,view_api);
		this.dec_x = plan.getWidth()/4;
		this.dec_y = 15;
        this.posy_trash = super.getPlan().getHeight()-43;
		super.getViewAPI().init_trash(trashName, plan.getWidth()/2-20, plan.getHeight()-43, width_trash, height_trash);
		repaint();
	}
	
	/**
	 * To update the toolbar display
	 */
	public void repaint() {
		super.repaint();
		super.getViewAPI().draw_trash();
	}
	
	/**
	 * To know of which Shape the user is clicking
	 * @param p : where the user clicked
	 * @return the shape that the user has been clicking
	 */
	public Shape peekUpShape(Point p) {
		double pos = p.getY();
		int numItem =  (int) (pos/(height_item+dec_y));
		Shape ret = null;
		if(super.getPlan().getShapes() != null && super.getPlan().getShapes().size() > numItem)
			ret = super.getPlan().getShapes().get(numItem);
		return ret;
	}
	
	/**
	 * Drawing a polygon
	 */
	public void drawPolygon(Polygon poly_old) {
		double laderH = 1;
		laderH =  (super.getPlan().getWidth()/2)/poly_old.getWidth();
		Polygon poly = (Polygon)poly_old.clone();
		int pos = super.getPlan().getShapes().indexOf(poly_old);
		poly.setPos(dec_x + poly.getDiffx()*laderH, pos*height_item + (pos+1)*dec_y + poly.getDiffy()*laderH);
		if(poly.getWidth() > super.getPlan().getWidth()/2)
			poly.setWidth(poly.getWidth() * laderH);
		if(poly.getHeight() > height_item)
			poly.set_points();
		
		super.drawPolygon(poly);
	}
	
	/**
	 * Drawing a square
	 */
	public void drawSquare(Square square) {
		double laderH =  (super.getPlan().getWidth()/2)/square.getWidth();
		double laderV =  height_item/square.getHeight();
		Square copy = (Square) square.clone();
		int pos = super.getPlan().getShapes().indexOf(square);
		copy.setPos(dec_x + copy.getRotation()%360/2, pos*height_item + (pos+1)*dec_y);
		copy.setRoundedCornerHeight(copy.getRoundedCornerHeight()*laderH);
		copy.setRoundedCornerWidth(copy.getRoundedCornerWidth()*laderH);
		if(copy.getWidth() > super.getPlan().getWidth()/2) {
			copy.setWidth(square.getWidth() * laderH);
			copy.setHeight(square.getHeight() * laderH);
		}
		if(copy.getHeight() > height_item) {
			copy.setWidth(square.getWidth() * laderV);
			copy.setHeight(square.getHeight()*laderV);
		}
		super.drawSquare(copy);
	}
	
	/**
	 * Drawing a composite
	 */
	public void drawShapeComposite(ShapeComposite shc){
		double ratioH = (super.getPlan().getWidth()/2)/shc.getWidth();
		double ratioV = (height_item)/shc.getHeight();
		ShapeComposite copy =  (ShapeComposite)shc.clone();
		double pos = super.getPlan().getShapes().indexOf(shc);
		copy.setWidth(copy.getWidth()*ratioH);
		copy.setHeight(copy.getHeight()*ratioV);
		copy.setPos(new Point(dec_x, pos*height_item + (pos+1)*dec_y));
		for(Shape s : copy.getShapes()) {
			if(s instanceof Square) {
				((Square) s).setRoundedCornerHeight(((Square) s).getRoundedCornerHeight()*ratioH);
				((Square) s).setRoundedCornerWidth(((Square) s).getRoundedCornerWidth()*ratioV);
			}else if(s instanceof Polygon){
				s.setHeight(s.getHeight()/ratioV);
			}else {
				drawShapeCompositeComponent((ShapeComposite)s,ratioH, ratioV);
			}
		}
		super.drawShapeComposite(copy);
	}
	
	/**
	 * Drawing all component of a composite
	 */
	public void drawShapeCompositeComponent(ShapeComposite shc,double ratioH, double ratioV) {
		for(Shape s : shc.getShapes()) {
			if(s instanceof Square) {
				((Square) s).setRoundedCornerHeight(((Square) s).getRoundedCornerHeight()*ratioH);
				((Square) s).setRoundedCornerWidth(((Square) s).getRoundedCornerWidth()*ratioV);
			}else if(s instanceof Polygon){
				s.setHeight(s.getHeight()/ratioV);
			}else {
				drawShapeCompositeComponent((ShapeComposite)s,ratioH,ratioV);
			}
		}
	}
	
	//GETTERS 
	
	public double getPosy_trash() {
		return posy_trash;
	}

	public double getHeight_trash() {
		return height_trash;
	}
	
	public double getHeightItem() {
		return height_item;
	}

}
