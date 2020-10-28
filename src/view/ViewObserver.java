package view;

import model.Shape;

/**
 * Used to update views of plans.
 * @author Cayrol, Furelaud
 *
 */
public interface ViewObserver {
	/**
	 * @param shape : the shape that has been modified
	 * @param remove : is the shape have to be remove of the view ?
	 */
	public void update(Shape shape, boolean remove);
	public void update();
}
