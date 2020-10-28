package controller.state;

import java.util.ArrayList;

import controller.CommandMemory;
import controller.Controller;
import model.Plan;
import model.Point;
import model.Shape;
import view.ViewWindow;

/**
 * Current state when the user is dragging the mouse on the whiteboard, without having clicked on a shape at the beginning of the drag.
 * @author Cayrol, Furelaud
 *
 */
public class SelectionState implements State{
	
	ArrayList<Shape> selectionnedShapes;
	ArrayList<Shape> finalSelectionnedShapes = new ArrayList<Shape>();
	ViewWindow window;
	Point start,end;
	Plan plan;
	
	public void enter(Plan plan,Point p, ViewWindow window2) {
		finalSelectionnedShapes = new ArrayList<Shape>();
		selectionnedShapes = new ArrayList<Shape>();
		start = p;
		end = new Point(0,0);
		this.plan = plan;
		this.window = window2;
	}
	
	public void leftClick(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p) {
		window.getWhiteBoardView().deleteSelectGroup();
		ctrl.setCurrentState((State) ctrl.initState);
		ctrl.initState.enter();
	}
	
	@Override
	public void rightClick(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p) {
		//Checking the sense of the selection (upleft, upright, downleft or downright)
		if(p.getX() > start.getX() && p.getX() < end.getX() && p.getY() > start.getY() && p.getY() < end.getY()) {
			window.getWhiteBoardView().drawDialogBoxGroup(window.getWindow().getX()+p.getX(),window.getWindow().getY()+p.getY());
			ctrl.setCurrentState((State) ctrl.groupDialogBoxState);
			ctrl.groupDialogBoxState.enter(finalSelectionnedShapes,whiteboard);
		}else if(p.getX() > start.getX() && p.getX() < end.getX() && p.getY() < start.getY() && p.getY() > end.getY()){
			window.getWhiteBoardView().drawDialogBoxGroup(window.getWindow().getX()+p.getX(),window.getWindow().getY()+p.getY());
			ctrl.setCurrentState((State) ctrl.groupDialogBoxState);
			ctrl.groupDialogBoxState.enter(finalSelectionnedShapes,whiteboard);
		}else if(p.getX() < start.getX() && p.getX() > end.getX() && p.getY() > start.getY() && p.getY() < end.getY()){
			window.getWhiteBoardView().drawDialogBoxGroup(window.getWindow().getX()+p.getX(),window.getWindow().getY()+p.getY());
			ctrl.setCurrentState((State) ctrl.groupDialogBoxState);
			ctrl.groupDialogBoxState.enter(finalSelectionnedShapes,whiteboard);
		}else if(p.getX() < start.getX() && p.getX() > end.getX() && p.getY() < start.getY() && p.getY() > end.getY()){
			window.getWhiteBoardView().drawDialogBoxGroup(window.getWindow().getX()+p.getX(),window.getWindow().getY()+p.getY());
			ctrl.setCurrentState((State) ctrl.groupDialogBoxState);
			ctrl.groupDialogBoxState.enter(finalSelectionnedShapes,whiteboard);
		}else {
			ctrl.setCurrentState((State) ctrl.initState);
			ctrl.initState.enter();
		}
	}
	
	@Override
	public void dragged(Point p) {
		end.setX(p.getX());
		end.setY(p.getY());
		window.getWhiteBoardView().drawSelectGroup(start,end);
		//Checking the sense of the selection (upleft, upright, downleft or downright)
		if(end.getX()>=start.getX() && end.getY()>=start.getY()) {
			for(Shape s : plan.getShapes()) {
				if(s.getPos().getX() > start.getX()-window.getTranslationWB().getX() && s.getPos().getX() < end.getX()-window.getTranslationWB().getX() && s.getPos().getY() > start.getY()-window.getTranslationWB().getY() && s.getPos().getY() < end.getY()-window.getTranslationWB().getY()) {
					if(!selectionnedShapes.contains(s))
						selectionnedShapes.add(s);
				}
			}
			for(Shape s : plan.getShapes()) {
				if(s.getPos().getX() < start.getX()-window.getTranslationWB().getX() || s.getPos().getX() > end.getX()-window.getTranslationWB().getX() || s.getPos().getY() < start.getY()-window.getTranslationWB().getY() || s.getPos().getY() > end.getY()-window.getTranslationWB().getY()) {
					if(selectionnedShapes.contains(s))
						selectionnedShapes.remove(s);
				}
			}
		}else if(end.getX()>start.getX() && end.getY()<start.getY()) {
			for(Shape s : plan.getShapes()) {
				if(s.getPos().getX() > start.getX()-window.getTranslationWB().getX() && s.getPos().getX() < end.getX()-window.getTranslationWB().getX() && s.getPos().getY() < start.getY()-window.getTranslationWB().getY() && s.getPos().getY() > end.getY()-window.getTranslationWB().getY()) {
					if(!selectionnedShapes.contains(s))
						selectionnedShapes.add(s);
				}
			}
			for(Shape s : plan.getShapes()) {
				if(s.getPos().getX() < start.getX()-window.getTranslationWB().getX() || s.getPos().getX() > end.getX()-window.getTranslationWB().getX() || s.getPos().getY() > start.getY()-window.getTranslationWB().getY() || s.getPos().getY() < end.getY()-window.getTranslationWB().getY()) {
					if(selectionnedShapes.contains(s))
						selectionnedShapes.remove(s);
				}
			}
		}else if(end.getX()<start.getX() && end.getY()>start.getY()) {
			for(Shape s : plan.getShapes()) {
				if(s.getPos().getX() < start.getX()-window.getTranslationWB().getX() && s.getPos().getX() > end.getX()-window.getTranslationWB().getX() && s.getPos().getY() > start.getY()-window.getTranslationWB().getY() && s.getPos().getY() < end.getY()-window.getTranslationWB().getY()) {
					if(!selectionnedShapes.contains(s))
						selectionnedShapes.add(s);
				}
			}
			for(Shape s : plan.getShapes()) {
				if(s.getPos().getX() > start.getX()-window.getTranslationWB().getX() || s.getPos().getX() < end.getX()-window.getTranslationWB().getX() || s.getPos().getY() < start.getY()-window.getTranslationWB().getY() || s.getPos().getY() > end.getY()-window.getTranslationWB().getY()) {
					if(selectionnedShapes.contains(s))
						selectionnedShapes.remove(s);
				}
			}
		}else {
			for(Shape s : plan.getShapes()) {
				if(s.getPos().getX() < start.getX()-window.getTranslationWB().getX() && s.getPos().getX() > end.getX()-window.getTranslationWB().getX() && s.getPos().getY() < start.getY()-window.getTranslationWB().getY() && s.getPos().getY() > end.getY()-window.getTranslationWB().getY()) {
					if(!selectionnedShapes.contains(s))
						selectionnedShapes.add(s);
				}
			}
			for(Shape s : plan.getShapes()) {
				if(s.getPos().getX() > start.getX()-window.getTranslationWB().getX() || s.getPos().getX() < end.getX()-window.getTranslationWB().getX() || s.getPos().getY() > start.getY()-window.getTranslationWB().getY() || s.getPos().getY() < end.getY()-window.getTranslationWB().getY()) {
					if(selectionnedShapes.contains(s))
						selectionnedShapes.remove(s);
				}
			}
		}
		
	}

	
	@Override
	public void released(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p) {
		if(selectionnedShapes.size()==0) {
			ctrl.setCurrentState((State) ctrl.initState);
			ctrl.initState.enter();
		}
		finalSelectionnedShapes.addAll(selectionnedShapes);
	}
	
}
