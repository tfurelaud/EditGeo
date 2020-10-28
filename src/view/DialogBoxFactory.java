package view;

import model.Polygon;
import model.Shape;
import model.ShapeComposite;
import model.Square;

/**
 * The dialogBox factory. Used to build a dialog box depending of the type of the shape
 * @author Cayrol, Furelaud
 *
 */
public class DialogBoxFactory {
	
	public DialogBoxFactory() {}
	
	/**
	 * Used to build a dialog box depending of the type of the shape
	 * @param shape : the shape that the user want to modifie
	 * @param x : the x that we want the dialogBox to popup
	 * @param y : the y that we want the dialogBox to popup
	 * @return the perfect type of dialogBox
	 */
	public EditDialogBox setBox(ViewPlanAPI plan, Shape shape, double x, double y) {
		//If the shape is a Square
		if(shape instanceof Square) {
			EditDialogBox squareDialogBox = new JFXSquareEditDialog(plan,shape,x,y);
			squareDialogBox.launch();
			return squareDialogBox;
		}
		//If the shape is a Polygon
		if(shape instanceof Polygon) {
			EditDialogBox polygonDialogBox = new JFXPolygonEditDialog(plan,shape,x,y);
			polygonDialogBox.launch();
			return polygonDialogBox;
		}
		//If the shape is a Composite
		if(shape instanceof ShapeComposite) {
			EditDialogBox compoDialogBox = new JFXCompositeEditDialog(plan,shape,x,y);
			compoDialogBox.launch();
			return compoDialogBox;
		}
		return null;
	}
}
