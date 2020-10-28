package controller.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import controller.CommandMemory;
import controller.Controller;
import model.Plan;
import model.Point;
import model.Shape;
import view.ViewToolBar;
import view.ViewWhiteBoard;
import view.ViewWindow;

/**
 * Current state when any task have been done
 * @author Cayrol, Furelaud
 */
public class InitState implements State{
	
	@Override
	public void leftClick(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p) {
		if(window.getView(p) instanceof ViewWhiteBoard) {
			ctrl.setCurrentState((State) ctrl.inWhiteBoardState);
			ctrl.leftClick(p);
		}else if(window.getView(p) instanceof ViewToolBar ) {
			ctrl.setCurrentState((State) ctrl.inToolbarState);
			ctrl.inToolbarState.enter(window,whiteboard);
			ctrl.leftClick(p);
		}
	}
	
	@Override
	public void rightClick(Controller ctrl, CommandMemory cmd, ViewWindow window, Plan toolbar, Plan whiteboard, Point p) {
		Shape shape = whiteboard.onIt(new Point(p.getX()-window.getTranslationWB().getX(),p.getY()-window.getTranslationWB().getY()));
		if(shape != null && window.getView(p) instanceof ViewWhiteBoard) {
			Shape curr = shape.clone();
			window.getWhiteBoardView().getPlan().removeShape(shape);
			window.getWhiteBoardView().getPlan().addShape(curr);
			window.getWhiteBoardView().drawDialogBox(curr,window.getWindow().getX()+window.getTranslationWB().getX(),window.getWindow().getY() + window.getTranslationWB().getY());
			ctrl.setCurrentState((State) ctrl.modifieShapeDialogBoxState);
			ctrl.modifieShapeDialogBoxState.enter(window.getWhiteBoardView(),curr,shape);
		}
	}
	
	public void undo(CommandMemory cmd) {
		cmd.undo();
	}
	
	public void redo(CommandMemory cmd) {
		cmd.redo();
	}
	
	@Override
	public void clickOnSave(Controller ctrl, File file, Plan toolbar, Plan whiteboard) {
		FileOutputStream fichier;
		try {
			fichier = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(toolbar);
			oos.writeObject(whiteboard);
			oos.flush();
			oos.close();
		} catch (IOException | NullPointerException n) {
		}
        
	}
	public void clickOnLoad(Controller ctrl, File file, Plan toolbar, Plan whiteboard) {
		
	    ObjectInputStream ois;
		try {
			final FileInputStream fichier = new FileInputStream(file);
			ois = new ObjectInputStream(fichier);
			toolbar.load(ois);
			whiteboard.load(ois);
			ois.close();
		} catch (IOException e) {
		}
		
	}
	
	public void clickOnNew(Controller ctrl, Plan wb) {
		ArrayList<Shape> to_remove = new ArrayList<Shape>();
		for(Shape s : wb.getShapes())
			to_remove.add(s);
		for(Shape s : to_remove)
			wb.removeShape(s);
	}
	
}
