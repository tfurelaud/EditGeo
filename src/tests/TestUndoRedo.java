package tests;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import controller.AddShapeCommand;
import controller.AddToCompositCommand;
import controller.ChangeAttributShapeCommand;
import controller.CommandMemory;
import controller.DeleteShapeCommand;
import controller.MoveShapeCommand;
import controller.RemoveFromCompositeCommand;
import controller.RemoveShapeCommand;
import model.ColorRGB;
import model.Plan;
import model.Point;
import model.Polygon;
import model.Shape;
import model.ShapeComposite;
import model.Square;

/**
 * Testing all the undo/redo commands
 * @author Cayrol, Furelaud
 *
 */
public class TestUndoRedo {
	
	
	@Test
	public final void testPosition() {
		ColorRGB green = new ColorRGB(0,255,0);
	    ColorRGB blue = new ColorRGB(0,0,255);
		Shape square = new Square(new Point(100,300),100,100,new Point(2,30),green,20,30);
		Shape poly = new Polygon(new Point(400,100),8,50,new Point(2,30),blue);
		CommandMemory cmd = new CommandMemory();
		assertTrue(square.getPos().getX() == 100);
		assertTrue(square.getPos().getY() == 300);
		assertTrue(poly.getPos().getX() == 400);
		assertTrue(poly.getPos().getY() == 100);
		square.setPos(new Point(300,200));
		poly.setPos(new Point(600,50));
		cmd.addCommand(new MoveShapeCommand(square,new Point(100,300)));
		cmd.addCommand(new MoveShapeCommand(poly,new Point(400,100)));
		assertTrue(square.getPos().getX() == 300);
		assertTrue(square.getPos().getY() == 200);
		assertTrue(poly.getPos().getX() == 600);
		assertTrue(poly.getPos().getY() == 50);
		cmd.undo();
		cmd.undo();
		assertTrue(square.getPos().getX() == 100);
		assertTrue(square.getPos().getY() == 300);
		assertTrue(poly.getPos().getX() == 400);
		assertTrue(poly.getPos().getY() == 100);
		cmd.redo();
		cmd.redo();
		assertTrue(square.getPos().getX() == 300);
		assertTrue(square.getPos().getY() == 200);
		assertTrue(poly.getPos().getX() == 600);
		assertTrue(poly.getPos().getY() == 50);
	}

	@Test
	public final void testAddShape() {
		int globalHeight = 600;
		int globalWidth = 800;
		Plan toolbar = new Plan(globalWidth/10,globalHeight - globalHeight/10,false);
		Plan whiteboard = new Plan(globalWidth - globalWidth/10,globalHeight - globalHeight/10,false);
		CommandMemory cmd = new CommandMemory();
		Shape to_add_square = new Square(new Point(200,20),100,100,new Point(2,30),new ColorRGB(200,152,263),20,30);
		Shape to_add_poly = new Square(new Point(200,20),100,100,new Point(2,30),new ColorRGB(200,152,263),20,30);
		toolbar.addShape(to_add_square);
		toolbar.addShape(to_add_poly);
		whiteboard.addShape(to_add_square);
		whiteboard.addShape(to_add_poly);
		cmd.addCommand(new AddShapeCommand(toolbar,to_add_square));
		cmd.addCommand(new AddShapeCommand(toolbar,to_add_poly));
		cmd.addCommand(new AddShapeCommand(whiteboard,to_add_square));
		cmd.addCommand(new AddShapeCommand(whiteboard,to_add_poly));
		assertTrue(toolbar.getShapes().contains(to_add_square));
		assertTrue(toolbar.getShapes().contains(to_add_poly));
		assertTrue(whiteboard.getShapes().contains(to_add_square));
		assertTrue(whiteboard.getShapes().contains(to_add_poly));
		cmd.undo();
		cmd.undo();
		cmd.undo();
		cmd.undo();
		assertFalse(toolbar.getShapes().contains(to_add_square));
		assertFalse(toolbar.getShapes().contains(to_add_poly));
		assertFalse(whiteboard.getShapes().contains(to_add_square));
		assertFalse(whiteboard.getShapes().contains(to_add_poly));
		cmd.redo();
		cmd.redo();
		cmd.redo();
		cmd.redo();
		assertTrue(toolbar.getShapes().contains(to_add_square));
		assertTrue(toolbar.getShapes().contains(to_add_poly));
		assertTrue(whiteboard.getShapes().contains(to_add_square));
		assertTrue(whiteboard.getShapes().contains(to_add_poly));
		
	}
	
	@Test
	public final void testCompositeFormation() {
		int globalHeight = 600;
		int globalWidth = 800;
		Plan whiteboard = new Plan(globalWidth - globalWidth/10,globalHeight - globalHeight/10,false);
		CommandMemory cmd = new CommandMemory();
		ShapeComposite compo = new ShapeComposite();
		Shape to_add_square = new Square(new Point(200,20),100,100,new Point(2,30),new ColorRGB(200,152,263),20,30);
		Shape to_add_poly = new Polygon(new Point(100,20),100,100,new Point(2,30),new ColorRGB(200,152,263));
		compo.addShape(to_add_square);
		compo.addShape(to_add_poly);
		ArrayList<Shape> to_add = new ArrayList<>();
		to_add.add(to_add_square);
		to_add.add(to_add_poly);
		cmd.addCommand(new AddToCompositCommand(compo,to_add,whiteboard));
		assertTrue(compo.getShapes().contains(to_add_square));
		assertTrue(compo.getShapes().contains(to_add_poly));
		cmd.undo();
		cmd.undo();
		assertFalse(compo.getShapes().contains(to_add_square));
		assertFalse(compo.getShapes().contains(to_add_poly));
		cmd.redo();
		cmd.redo();
		assertTrue(compo.getShapes().contains(to_add_square));
		assertTrue(compo.getShapes().contains(to_add_poly));
	}
	
	@Test
	public final void testChangeAttribut() {
		int globalHeight = 600;
		int globalWidth = 800;
		Plan whiteboard = new Plan(globalWidth - globalWidth/10,globalHeight - globalHeight/10,false);
		CommandMemory cmd = new CommandMemory();
		ColorRGB color = new ColorRGB(200,152,263);
		
		Shape compo = new ShapeComposite();
		
		Shape to_add_square = new Square(new Point(200,20),100,100,new Point(2,30),color,20,30);
		Shape to_add_poly = new Polygon(new Point(100,20),100,100,new Point(15,30),color);
		
		compo.addShape(to_add_square);
		compo.addShape(to_add_poly);
		
		Shape square = new Square(new Point(200,20),100,100,new Point(2,30),color,20,30);
		Shape poly = new Polygon(new Point(100,20),100,100,new Point(15,30),color);
		Shape begin_compo =  compo.clone();
		Shape begin_square =  square.clone();
		Shape begin_poly = poly.clone();
		
		
		assertTrue(compo.getRotation()==0);
		assertTrue(compo.getColorRGB().getColorR()==color.getColorR());
		assertTrue(compo.getColorRGB().getColorG()==color.getColorG());
		assertTrue(compo.getColorRGB().getColorB()==color.getColorB());
		assertTrue(compo.getRotationCenter().getX()==0);
		assertTrue(compo.getRotationCenter().getY()==0);
		assertTrue(square.getRotation()==0);
		assertTrue(square.getColorRGB().getColorR()==color.getColorR());
		assertTrue(square.getColorRGB().getColorG()==color.getColorG());
		assertTrue(square.getColorRGB().getColorB()==color.getColorB());
		assertTrue(square.getRotationCenter().getX()==2);
		assertTrue(square.getRotationCenter().getY()==30);
		assertTrue(poly.getRotation()==0);
		assertTrue(poly.getColorRGB().getColorR()==color.getColorR());
		assertTrue(poly.getColorRGB().getColorG()==color.getColorG());
		assertTrue(poly.getColorRGB().getColorB()==color.getColorB());
		assertTrue(poly.getRotationCenter().getX()==15);
		assertTrue(poly.getRotationCenter().getY()==30);
		
		square.setRotation(45);
		square.setColorRGB(new ColorRGB(125,125,125));
		square.setRotationCenter(new Point(45,45));
		
		cmd.addCommand(new ChangeAttributShapeCommand(begin_square,square,whiteboard));
		cmd.undo();
		assertTrue(whiteboard.getShapes().contains(begin_square));
		assertFalse(whiteboard.getShapes().contains(square));
		cmd.redo();
		assertTrue(whiteboard.getShapes().contains(square));
		assertFalse(whiteboard.getShapes().contains(begin_square));
		
		
		poly.setRotation(60);
		poly.setColorRGB(new ColorRGB(100,5,254));
		poly.setRotationCenter(new Point(90,90));
		

		cmd.addCommand(new ChangeAttributShapeCommand(begin_poly,poly,whiteboard));
		cmd.undo();
		assertTrue(whiteboard.getShapes().contains(begin_poly));
		assertFalse(whiteboard.getShapes().contains(poly));
		cmd.redo();
		assertTrue(whiteboard.getShapes().contains(poly));
		assertFalse(whiteboard.getShapes().contains(begin_poly));
		
		compo.setRotation(10);
		compo.setColorRGB(new ColorRGB(6,125,210));
		compo.setRotationCenter(new Point(10,45));
		
		cmd.addCommand(new ChangeAttributShapeCommand(begin_compo,compo,whiteboard));
		cmd.undo();
		assertTrue(whiteboard.getShapes().contains(begin_compo));
		assertFalse(whiteboard.getShapes().contains(compo));
		cmd.redo();
		assertTrue(whiteboard.getShapes().contains(compo));
		assertFalse(whiteboard.getShapes().contains(begin_compo));
		
	}
	
	@Test
	public final void testDeleteShape() {
		int globalHeight = 600;
		int globalWidth = 800;
		Plan toolbar = new Plan(globalWidth/10,globalHeight - globalHeight/10,false);
		Plan whiteboard = new Plan(globalWidth - globalWidth/10,globalHeight - globalHeight/10,false);
		CommandMemory cmd = new CommandMemory();
		ColorRGB color = new ColorRGB(2,20,120);
		Shape compo = new ShapeComposite();
		Shape to_add_square = new Square(new Point(200,20),100,100,new Point(2,30),color,20,30);
		Shape to_add_poly = new Polygon(new Point(100,20),100,100,new Point(15,30),color);
		compo.addShape(to_add_square);
		compo.addShape(to_add_poly);
		Shape square = new Square(new Point(350,240),100,100,new Point(2,30),color,447,340);
		Shape poly = new Polygon(new Point(12,175),100,100,new Point(15,02),color);
		whiteboard.addShape(square);
		whiteboard.addShape(poly);
		whiteboard.addShape(compo);
		toolbar.addShape(square);
		toolbar.addShape(poly);
		toolbar.addShape(compo);
		
		assertTrue(whiteboard.getShapes().contains(square));
		assertTrue(toolbar.getShapes().contains(square));
		assertTrue(whiteboard.getShapes().contains(poly));
		assertTrue(toolbar.getShapes().contains(poly));
		assertTrue(whiteboard.getShapes().contains(compo));
		assertTrue(toolbar.getShapes().contains(compo));
		
		whiteboard.removeShape(square);
		whiteboard.removeShape(poly);
		whiteboard.removeShape(compo);
		toolbar.removeShape(square);
		toolbar.removeShape(poly);
		toolbar.removeShape(compo);
		
		cmd.addCommand(new DeleteShapeCommand(whiteboard,square));
		cmd.addCommand(new DeleteShapeCommand(whiteboard,poly));
		cmd.addCommand(new DeleteShapeCommand(whiteboard,compo));
		cmd.addCommand(new DeleteShapeCommand(toolbar,square));
		cmd.addCommand(new DeleteShapeCommand(toolbar,poly));
		cmd.addCommand(new DeleteShapeCommand(toolbar,compo));
		
		assertFalse(whiteboard.getShapes().contains(square));
		assertFalse(toolbar.getShapes().contains(square));
		assertFalse(whiteboard.getShapes().contains(poly));
		assertFalse(toolbar.getShapes().contains(poly));
		assertFalse(whiteboard.getShapes().contains(compo));
		assertFalse(toolbar.getShapes().contains(compo));
		
		cmd.undo();
		cmd.undo();
		cmd.undo();
		cmd.undo();
		cmd.undo();
		cmd.undo();
		
		assertTrue(whiteboard.getShapes().contains(square));
		assertTrue(toolbar.getShapes().contains(square));
		assertTrue(whiteboard.getShapes().contains(poly));
		assertTrue(toolbar.getShapes().contains(poly));
		assertTrue(whiteboard.getShapes().contains(compo));
		assertTrue(toolbar.getShapes().contains(compo));
		
		cmd.redo();
		cmd.redo();
		cmd.redo();
		cmd.redo();
		cmd.redo();
		cmd.redo();
		
		assertFalse(whiteboard.getShapes().contains(square));
		assertFalse(toolbar.getShapes().contains(square));
		assertFalse(whiteboard.getShapes().contains(poly));
		assertFalse(toolbar.getShapes().contains(poly));
		assertFalse(whiteboard.getShapes().contains(compo));
		assertFalse(toolbar.getShapes().contains(compo));
		
	}
	
	@Test
	public final void testRemoveFromComposite() {
		int globalHeight = 600;
		int globalWidth = 800;
		Plan whiteboard = new Plan(globalWidth - globalWidth/10,globalHeight - globalHeight/10,false);
		CommandMemory cmd = new CommandMemory();
		ShapeComposite compo = new ShapeComposite();
		Shape to_add_square = new Square(new Point(200,20),100,100,new Point(2,30),new ColorRGB(200,152,263),20,30);
		Shape to_add_poly = new Polygon(new Point(100,20),100,100,new Point(2,30),new ColorRGB(200,152,263));
		compo.addShape(to_add_square);
		compo.addShape(to_add_poly);
		
		assertTrue(compo.getShapes().contains(to_add_square));
		assertTrue(compo.getShapes().contains(to_add_poly));
		
		compo.removeShape(to_add_square);
		compo.removeShape(to_add_poly);
		cmd.addCommand(new RemoveFromCompositeCommand(whiteboard,compo));
		assertFalse(compo.getShapes().contains(to_add_square));
		assertFalse(compo.getShapes().contains(to_add_poly));
		cmd.undo();
		assertFalse(whiteboard.getShapes().contains(to_add_square));
		assertFalse(whiteboard.getShapes().contains(to_add_poly));
		assertTrue(whiteboard.getShapes().contains(compo));
		cmd.redo();
		assertFalse(whiteboard.getShapes().contains(compo));
		assertFalse(whiteboard.getShapes().contains(to_add_poly));
		assertFalse(whiteboard.getShapes().contains(to_add_square));
	}
	
	@Test
	public final void testRemoveShape() {
		int globalHeight = 600;
		int globalWidth = 800;
		Plan toolbar = new Plan(globalWidth/10,globalHeight - globalHeight/10,false);
		Plan whiteboard = new Plan(globalWidth - globalWidth/10,globalHeight - globalHeight/10,false);
		CommandMemory cmd = new CommandMemory();
		ShapeComposite compo = new ShapeComposite();
		Shape to_add_square = new Square(new Point(200,20),100,100,new Point(2,30),new ColorRGB(200,152,263),20,30);
		Shape to_add_poly = new Polygon(new Point(100,20),100,100,new Point(2,30),new ColorRGB(200,152,263));
		compo.addShape(to_add_square);
		compo.addShape(to_add_poly);
		
		whiteboard.addShape(to_add_square);
		whiteboard.addShape(to_add_poly);
		whiteboard.addShape(compo);
		toolbar.addShape(to_add_square);
		toolbar.addShape(to_add_poly);
		toolbar.addShape(compo);
		
		assertTrue(whiteboard.getShapes().contains(to_add_square));
		assertTrue(whiteboard.getShapes().contains(to_add_poly));
		assertTrue(whiteboard.getShapes().contains(compo));
		assertTrue(toolbar.getShapes().contains(to_add_square));
		assertTrue(toolbar.getShapes().contains(to_add_poly));
		assertTrue(toolbar.getShapes().contains(compo));
		
		whiteboard.removeShape(to_add_square);
		whiteboard.removeShape(to_add_poly);
		whiteboard.removeShape(compo);
		toolbar.removeShape(to_add_square);
		toolbar.removeShape(to_add_poly);
		toolbar.removeShape(compo);
		
		cmd.addCommand(new RemoveShapeCommand(whiteboard,to_add_square));
		cmd.addCommand(new RemoveShapeCommand(whiteboard,to_add_poly));
		cmd.addCommand(new RemoveShapeCommand(whiteboard,compo));
		cmd.addCommand(new RemoveShapeCommand(toolbar,to_add_square));
		cmd.addCommand(new RemoveShapeCommand(toolbar,to_add_poly));
		cmd.addCommand(new RemoveShapeCommand(toolbar,compo));
		
		assertFalse(whiteboard.getShapes().contains(to_add_square));
		assertFalse(whiteboard.getShapes().contains(to_add_poly));
		assertFalse(whiteboard.getShapes().contains(compo));
		assertFalse(toolbar.getShapes().contains(to_add_square));
		assertFalse(toolbar.getShapes().contains(to_add_poly));
		assertFalse(toolbar.getShapes().contains(compo));
		
		cmd.undo();
		cmd.undo();
		cmd.undo();
		cmd.undo();
		cmd.undo();
		cmd.undo();
		
		assertTrue(whiteboard.getShapes().contains(to_add_square));
		assertTrue(whiteboard.getShapes().contains(to_add_poly));
		assertTrue(whiteboard.getShapes().contains(compo));
		assertTrue(toolbar.getShapes().contains(to_add_square));
		assertTrue(toolbar.getShapes().contains(to_add_poly));
		assertTrue(toolbar.getShapes().contains(compo));
		
		cmd.redo();
		cmd.redo();
		cmd.redo();
		cmd.redo();
		cmd.redo();
		cmd.redo();
		
		assertFalse(whiteboard.getShapes().contains(to_add_square));
		assertFalse(whiteboard.getShapes().contains(to_add_poly));
		assertFalse(whiteboard.getShapes().contains(compo));
		assertFalse(toolbar.getShapes().contains(to_add_square));
		assertFalse(toolbar.getShapes().contains(to_add_poly));
		assertFalse(toolbar.getShapes().contains(compo));
		
		
	}

}