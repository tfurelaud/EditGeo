package model;

/**
 * Polygon shape
 * @author Cayrol, Furelaud
 *
 */
public class Polygon extends Shape{
	
	/**
	 * Serial number
	 */
	private static final long serialVersionUID = -1576441359751154623L;
	private int nbSide;
	private double dimSide;
	/**
	 * All the x positions of all the points of the polygon
	 */
	private double posx[];
	/**
	 * All the y positions of all the points of the polygon
	 */
	private double posy[];
	/**
	 * The difference between the lower x and the upper x.
	 */
	private double diff_x;
	/**
	 * The difference between the lower y and the upper y.
	 */
	private double diff_y;
	
	public Polygon( Point p, int nbSide, double dimSide, Point rotation_center, ColorRGB color) {
		super(p,(dimSide/(2*Math.tan(Math.PI/nbSide)))*2, (dimSide/(2*Math.tan(Math.PI/nbSide)))*2,rotation_center,0,0, color);
		this.nbSide = nbSide;
		this.dimSide = dimSide;
		set_points();
		init_pos();
	}
	
	/**
	 * Just to know if a point is on the polygon or not.
	 */
	@Override
	public boolean onIt(double x, double y) {
		return super.onIt(new Point(x - diff_x, y - diff_y));
	}
	
	public boolean onIt(Point p) {
		return super.onIt(new Point(p.getX() + diff_x, p.getY() + diff_y));
	}
	
	//SETTERS
	public void setPos(Point p) {
		super.setPos(new Point(p.getX(),p.getY()));
		set_points();
		super.notifyView();
	}
	
	public void setDimSide(double dimSide) {
		this.dimSide = dimSide;
		set_points();
		init_pos();
		super.setWidth((dimSide / (2*Math.tan(Math.PI/nbSide)) * 2));
		super.setHeight(super.getWidth());
	}
	
	public void setNbSide(int nbSide) {
		this.nbSide = nbSide;
		set_points();
		init_pos();
		super.setWidth((dimSide / (2*Math.tan(Math.PI/nbSide)) * 2));
		super.setHeight(super.getWidth());
	}
	
	public void set_points() {
		double x0 = super.getPos().getX();
		double y0 = super.getPos().getY();
		double degree_general = (180*(nbSide-2));
		double degree_inside = Math.toRadians(degree_general / nbSide); 
		posx = new double[nbSide];
		posy = new double[nbSide];
		
		posx[0] = x0;
		posy[0] = y0;
		if(nbSide%2 == 1) {
			posx[1] = ((x0-dimSide)-x0) * Math.cos(degree_inside*2) + x0;
			posy[1] = -((x0-dimSide)-x0) * Math.sin(degree_inside*2) + y0;
		}else {
			posx[1] = x0 + dimSide;
			posy[1] = y0 ;
		}

		for(int i=2 ; i<nbSide ;i++) {
			double xo = posx[i-1];
			double xb = posx[i-2];
			double yo = posy[i-1];
			double yb = posy[i-2];
			posx[i] = (xb-xo) * Math.cos(degree_inside) + (yb - yo) * Math.sin(degree_inside) + xo;
			posy[i] = -(xb-xo) * Math.sin(degree_inside) + (yb - yo) * Math.cos(degree_inside) + yo;
		}
	}
	
	
	public void init_pos() {
		double xmin = super.getPos().getX();
		double ymin = super.getPos().getY();
		for(int i = 1;i<nbSide;i++) {
			if(posx[i] < xmin)
				xmin = posx[i];
			if(posy[i] < ymin)
				ymin = posy[i];
		}
		diff_x = posx[0] - xmin;
		diff_y = posy[0] - ymin;
	}
	
	public void setWidth(double width) {
		double aux = width * Math.tan(Math.PI / getNbSide());
		setDimSide(aux);
	}
	
	public void setHeight(double height) {
		this.setWidth(height);
	}
	
	//GETTERS
	public int getNbSide() {
		return nbSide;
	}
	
	public double getDimSide() {
		return dimSide;
	}
	
	public double[] getPosx() {
		return posx;
	}
	
	public double[] getPosy() {
		return posy;
	}
	
	public double getDiffx() {
		return diff_x;
	}
	
	public double getDiffy() {
		return diff_y;
	}

}

