package view;

import controller.Controller;
import model.Plan;
import model.Point;

/**
 * The Window view. Manage all the graphic action on it.
 * @author Cayrol, Furelaud
 *
 */
public class ViewWindow {
	private ViewWindowAPI w_api;
	private String nameApp;
	private Plan toolbar_plan, whiteboard_plan;
	private ViewToolBar toolbar;
	private ViewWhiteBoard whiteboard;
	private Point translationWb;
	private Point translationTb;
	private double width, height, upbarheight, tbwidth, tbheight, wbwidth, wbheight;
	private MouseListener mouseListener;
	private int dec = 5; 
	
	public ViewWindow(Plan toolbar, Plan whiteboard, Point translationWb, Point translationTb,double globalWidth, double globalHeight, String nameApp, ViewWindowAPI w_api) {
		this.nameApp = nameApp;
		this.toolbar_plan = toolbar;
		this.whiteboard_plan = whiteboard;
		this.translationWb = translationWb;
		this.translationTb = translationTb;
		this.upbarheight = Math.min(translationWb.getY(),translationTb.getY()) - dec;
		this.width = globalWidth;
		this.height = globalHeight;
		this.tbwidth = toolbar.getWidth();
		this.tbheight = toolbar.getHeight();
		this.wbwidth = whiteboard.getWidth();
		this.wbheight = whiteboard.getHeight();
		this.w_api = w_api;
	}
	
	public void InitApp(String[] args) {
		w_api.InitApp(translationWb, translationTb, width, height, nameApp, dec, mouseListener, upbarheight);
		this.toolbar = new ViewToolBar(toolbar_plan, w_api.getToolBarAPI(), "garbage.png");
		this.whiteboard = new ViewWhiteBoard(whiteboard_plan, w_api.getWhiteBoardAPI());
		w_api.launchApp(args);
	}
	
	//SETTER
	public void setController(Controller ctrl) {
		this.mouseListener = new MouseListener(ctrl);
	}
	
	// GETTERS
	public ViewToolBar getToolbarView() {
		return toolbar;
	}
	
	public ViewWhiteBoard getWhiteBoardView() {
		return whiteboard;
	}
	
	public double getTBWidth() {
		return tbwidth;
	}
	
	public Point getTranslationWB() {
		return translationWb;
	}
	
	public Point getTranslationTB() {
		return translationTb;
	}
	
	public Point getWindow() {
		return new Point(w_api.getPosWindow().getX(),w_api.getPosWindow().getY());
	}
	
	
	public ViewPlan getView(Point p) {
		if(p.getX() > this.translationTb.getX() && p.getX() < this.translationTb.getX() + this.tbwidth && p.getY() > this.translationTb.getY() && p.getY() < this.translationTb.getY() + this.tbheight )
			return this.toolbar;
		if(p.getX() > this.translationWb.getX() && p.getX() < this.translationWb.getX() + this.wbwidth && p.getY() > this.translationWb.getY() && p.getY() < this.translationWb.getY() + this.wbheight )
			return this.whiteboard;
		return null;
	}
}
