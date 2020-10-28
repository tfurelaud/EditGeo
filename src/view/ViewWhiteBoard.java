package view;

import model.Plan;
import model.Point;
import model.Shape;

/**
 * The whiteboard view. Manage all the graphic action on it.
 * @author Cayrol, Furelaud
 *
 */
public class ViewWhiteBoard extends ViewPlan{
	
	public ViewWhiteBoard(Plan plan, ViewPlanAPI view_api) {
		super(plan, view_api);
		super.repaint();
	}
	
	public void update() {
		super.repaint();
	}
	public void drawDialogBox(Shape s,double x, double y) {
		super.getViewAPI().drawDialogBox(s, x, y);
	}
	
	public void drawDialogBoxGroup(double x, double y) {
		super.getViewAPI().drawDialogBoxGroup(x, y);
	}
	
	public void initSelectGroup(double x, double y) {
		super.getViewAPI().initSelectGroup(x, y);
	}
	public void drawSelectGroup(Point start, Point end) {
		super.getViewAPI().drawSelectGroup(start, end);
	}
	
	public void deleteSelectGroup() {
		super.getViewAPI().deleteSelectGroup();
	}
}
