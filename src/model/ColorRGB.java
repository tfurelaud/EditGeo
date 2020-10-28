package model;

import java.io.Serializable;

/**
 * Used to not be dependent on a library
 * @author Cayrol, Furelaud
 *
 */
public class ColorRGB implements Cloneable,Serializable{
	
	private static final long serialVersionUID = 4099725998253271145L;
	int r, g, b;
	
	public ColorRGB(int r, int g, int b) {
		if(r<=255 && r >= 0)
			this.r = r;
		else if(r > 255)
			this.r = 255;
		else
			this.r = 0;
		if(g <= 255 && g >= 0)
			this.g = g;
		else if(g > 255)
			this.g = 255;
		else 
			this.g = 0;
		if(b <= 255 && b >= 0)
			this.b = b;
		else if(b > 255)
			this.b = 255;
		else 
			this.b = 0;
	}
	
	public ColorRGB clone() {
		ColorRGB copy;
		try {
			copy = (ColorRGB)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
		return copy;
	}
	
	//SETTER
	public void setColorRGB(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	//GETTERS
	public int getColorR() {
		return this.r;
	}
	
	public int getColorG() {
		return this.g;
	}
	
	public int getColorB() {
		return this.b;
	}
	
}
