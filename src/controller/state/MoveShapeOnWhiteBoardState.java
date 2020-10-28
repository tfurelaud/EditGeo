package controller.state;

import controller.CommandMemory;
import controller.Controller;
import controller.DeleteShapeCommand;
import controller.MoveShapeCommand;
import model.Plan;
import model.Point;
import model.Polygon;
import model.Shape;
import view.ViewToolBar;
import view.ViewWhiteBoard;
import view.ViewWindow;

/**
 * Current state when the user moves a shape on the whiteboard
 * @author Cayrol, Furelaud
 */
public class MoveShapeOnWhiteBoardState implements State{
	
	private double dec_height;
	private double dec_width;
	private Shape shape;
	private Plan whiteboard;
	private ViewWindow window;
	private Point save;
	
	@Override
	public void dragged(Point p) {
		if(window.getView(p) != null) {
			whiteboard.getShapes().remove(shape);
			whiteboard.getShapes().add(shape);
			if(p.getY() - window.getTranslationWB().getY() + dec_height > 0)
				shape.setPos(new Point(p.getX() - window.getTranslationWB().getX() + dec_width,p.getY() - window.getTranslationWB().getY()+dec_height));
			else
				shape.setPos(new Point(p.getX() - window.getTranslationWB().getX() + dec_width,0));
		}
	}
	
	@Override
	public void released(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p) {
		if(window.getView(p) instanceof ViewToolBar) {
			// Trash
			if(p.getY() < window.getTranslationTB().getY() + window.getToolbarView().getPosy_trash() + window.getToolbarView().getHeight_trash() && p.getY() > window.getTranslationTB().getY() + window.getToolbarView().getPosy_trash()) {
				window.getWhiteBoardView().getPlan().removeShape(shape);
				ctrl.setCurrentState(ctrl.initState);
				shape.setPos(save);
				cmd.addCommand(new DeleteShapeCommand(window.getWhiteBoardView().getPlan(),shape));
			}else {
				ctrl.setCurrentState((State) ctrl.createShapeToToolbarState);
				ctrl.createShapeToToolbarState.enter(ctrl,cmd,window,toolbar,shape,p,save);
			}
		}
		if(window.getView(p) instanceof ViewWhiteBoard) {
			if(shape instanceof Polygon) {
				double dec = ((Polygon)shape).getDiffx();
				if(shape.getPos().getX() - dec < 0)
					shape.setPos(new Point(0+dec,shape.getPos().getY()));
			}else {
				if(shape.getPos().getX() < 0) 
					shape.setPos(new Point(0,shape.getPos().getY()));
			}
			ctrl.setCurrentState(ctrl.initState);
			cmd.addCommand(new MoveShapeCommand(shape,save));
		}
	}
	
	public void enter(ViewWindow window2, Plan toolbar, Plan whiteboard, Shape shape, Point point) {
		this.whiteboard = whiteboard;
		this.shape = shape;
		this.window = window2;
		Point point_aux = new Point(point.getX() - window2.getTranslationWB().getX(),point.getY() - window2.getTranslationWB().getY());
		this.save = shape.getPos().clone();
		dec_width = shape.getPos().getX() - point_aux.getX();
		dec_height = shape.getPos().getY() - point_aux.getY();
	}
}
