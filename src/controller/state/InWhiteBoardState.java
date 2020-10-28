package controller.state;

import controller.CommandMemory;
import controller.Controller;
import model.Plan;
import model.Point;
import model.Shape;
import view.ViewWindow;

/**
 * Current state when the user clicks on the whiteboard
 * @author Cayrol, Furelaud
 */
public class InWhiteBoardState implements State{

	@Override
	public void leftClick(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p) {
		Shape shape = whiteboard.onIt(new Point(p.getX()-window.getTranslationWB().getX(),p.getY()-window.getTranslationWB().getY()));
		if(shape != null) {
			ctrl.moveShapeOnWhiteBoardState.enter(window, toolbar,whiteboard,shape,p);
			ctrl.setCurrentState((State) ctrl.moveShapeOnWhiteBoardState);
		}else {
			ctrl.selectionState.enter(whiteboard,p,window);
			ctrl.setCurrentState((State) ctrl.selectionState);
			window.getWhiteBoardView().initSelectGroup(p.getX() - window.getTranslationWB().getX(), p.getY() - window.getTranslationWB().getY());
			ctrl.dragged(p);
		}
	}
	
}
