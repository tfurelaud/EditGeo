package controller.state;

import java.util.ArrayList;
import java.util.Iterator;

import controller.AddToCompositCommand;
import controller.CommandMemory;
import controller.Controller;
import controller.RemoveFromCompositeCommand;
import model.Shape;
import model.ShapeComposite;
import model.Plan;

/**
 * Current state when the user click and dragged on he whiteboard but not on a shape
 * @author Cayrol, Furelaud
 */
public class GroupDialogBoxState implements State{
	
	private ArrayList<Shape> shapes;
	private Plan plan;
	public void enter(ArrayList<Shape> shapes,Plan plan) {
		this.shapes = new ArrayList<Shape>();
		this.shapes.addAll(shapes);
		this.plan =plan;
	}
	
	@Override
	public void clickOnGroup(Controller ctrl, CommandMemory cmd) {
		Shape compo = new ShapeComposite();
		for(Shape s : shapes) {
			compo.addShape(s);
			plan.removeShape(s);
		}
		plan.addShape(compo);
		cmd.addCommand(new AddToCompositCommand((ShapeComposite)compo,shapes,this.plan));
		ctrl.setCurrentState(ctrl.initState);
		ctrl.initState.enter();
	}
	
	@Override
	public void clickOnDegroup(Controller ctrl,CommandMemory cmd) {
		ShapeComposite comp = null;
		Iterator<Shape> shapeit = shapes.iterator();
		while(shapeit.hasNext()) {
			Shape s = shapeit.next();
			if(s instanceof ShapeComposite) {
				comp = (ShapeComposite)s ;
				Iterator<Shape> it = s.getShapes().iterator();
				while(it.hasNext()) {
					Shape todegroup = it.next();
					plan.addShape(todegroup);
				}
				plan.removeShape(s);
			}
		}
		cmd.addCommand(new RemoveFromCompositeCommand(plan,comp));
		
		ctrl.setCurrentState(ctrl.initState);
		ctrl.initState.enter();
	}
	
	public void clickOnClose(Controller ctrl) {
		ctrl.setCurrentState(ctrl.initState);
	}
}
