package view;


import java.io.File;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Point;

/**
 * The implementation of the interface "ViewWindowAPI" with JavaFX
 * @author Cayrol, Furelaud
 *
 */
public class JFXViewWindow extends Application implements ViewWindowAPI{
	private static Stage stage;
	private static Pane root;
	private static Group UpBar;
	private static Scene scene;
	private static String nameApp;
	private static MouseListener mouseListener;
	private static ViewPlanAPI wb;
	private static ViewPlanAPI tb;
	private static double width;
	private static double height;
	private static double upBarHeight;
	
	public JFXViewWindow() {}
	
	public void InitApp(Point translationWb, Point translationTb,double globalWidth, double globalHeight, String nameApp, double dec, MouseListener mouseListener, double upBarHeight) {
		JFXViewWindow.mouseListener = mouseListener;
		JFXViewWindow.nameApp = nameApp;
		JFXViewWindow.width = globalWidth + dec;
		JFXViewWindow.height = globalHeight + dec;
		JFXViewWindow.upBarHeight = upBarHeight;
		JFXViewWindow.root = new Pane();
		
		Group tb_group = new Group();
        Group wb_group = new Group();
        Group UpBar    = new Group();
        JFXViewWindow.UpBar = UpBar;
        
        wb_group.setTranslateX(translationWb.getX());
        wb_group.setTranslateY(translationWb.getY());
        
        tb_group.setTranslateY(translationTb.getY());
        
        	
        ViewPlanAPI viewWB = new JFXViewPlan(wb_group, mouseListener,translationWb);
        JFXViewWindow.wb = viewWB;	
        
        ViewPlanAPI viewTB = new JFXViewPlan(tb_group, mouseListener,translationTb);
        JFXViewWindow.tb = viewTB;
        
        root.getChildren().addAll(tb_group, wb_group, UpBar);
    }
	
	public void launchApp(String[] args) {
		launch(args);
	}

	/**
	 * Start the app
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle(nameApp);
		JFXViewWindow.stage = stage;
		JFXViewWindow.scene = new Scene(root, width, height);
        
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> mousePressed(e) );
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> mouseDragged(e) );
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> mouseReleased(e));
        
        drawButtons(width,upBarHeight);
        
        stage.setScene(scene);
        stage.show();
	}
	
	/**
	 * Draw all the buttons
	 */
	public void drawButtons(double width, double height) {
		Rectangle topbar_r = new Rectangle();
        topbar_r.setX(0);
        topbar_r.setY(0);
        topbar_r.setWidth(width);
        topbar_r.setHeight(height);
        topbar_r.setFill(Color.LIGHTBLUE);
        topbar_r.setStroke(Color.CORNFLOWERBLUE);
        UpBar.getChildren().add(topbar_r);
        
        Node[] buttons = new Node[6];
        Button NewB = new Button("New");
		Button SaveAs = new Button("SaveAs");
		Button Load = new Button("Load");
		Separator sep = new Separator();
		Button Undo = new Button("Undo");
		Button Redo = new Button("Redo");
		buttons[0] = NewB;
		buttons[1] = SaveAs;
		buttons[2] = Load;
		buttons[3] = sep;
		buttons[4] = Undo;
		buttons[5] = Redo;
		
		ToolBar tb_buttons = new ToolBar(buttons);
		tb_buttons.setLayoutY(10);
		tb_buttons.setLayoutX(70);
		Background blue = new Background(new BackgroundFill(Color.CORNFLOWERBLUE,CornerRadii.EMPTY, null ));
		tb_buttons.setBackground(blue);
		UpBar.getChildren().add(tb_buttons);
		controlButtons(buttons);
	}
	
	/**
	 * When the mouse id pressed (depending of JFX)
	 * @param evt the mouse attributes
	 */
	public void mousePressed(MouseEvent evt) {
		switch(evt.getButton()) {
			case PRIMARY:
				sendMousePressed(new MouseEventInfo(evt.getX(), evt.getY(),MouseButton.BUTTON1));
				break;
			case SECONDARY:
				sendMousePressed(new MouseEventInfo(evt.getX(), evt.getY(),MouseButton.BUTTON2));
				break;
		default:
			break;
		}
	}

	/**
	 * When the mouse id dragged
	 * @param evt the mouse attributes
	 */
	public void mouseDragged(MouseEvent evt) {
		sendMouseDragged(new MouseEventInfo(evt.getX(), evt.getY(),MouseButton.BUTTON1));
	}
	
	/**
	 * When the mouse id released
	 * @param evt the mouse attributes
	 */
	public void mouseReleased(MouseEvent evt) {
		sendMouseReleased(new MouseEventInfo(evt.getX(), evt.getY(),MouseButton.BUTTON1));
	}
	
	/**
	 * When the mouse id pressed (not depending of JFX)
	 * @param evt the mouse attributes
	 */
	public void sendMousePressed(MouseEventInfo evt) {
		mouseListener.mousePressed(evt);
	}

	// THE MOUSE LISTENER : BUTTONS AND ACTIONS
	
	public void sendMouseDragged(MouseEventInfo evt) {
		mouseListener.mouseDragged(evt);
	}
	
	public void sendMouseReleased(MouseEventInfo evt) {
		mouseListener.mouseReleased(evt);
	}
	
	public void sendNewClicked() {
		mouseListener.newClicked();;
	}
	
	public void sendUndoClicked() {
		mouseListener.undoClicked();
	}
	
	public void sendRedoClicked() {
		mouseListener.redoClicked();
	}
	
	public void sendSaveClicked(File selectedFile) {
		mouseListener.saveClicked(selectedFile);
	}
	
	public void sendLoadClicked(File selectedFile) {
		mouseListener.loadClicked(selectedFile);
	}
	
	public void controlButtons(Node[] buttons) {
		((Button) buttons[4]).setOnAction((event) -> {
        	sendUndoClicked();
        });
		((Button) buttons[5]).setOnAction((event) -> {
        	sendRedoClicked();
        });
		((Button) buttons[1]).setOnAction((event) -> {
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().add(
			         new ExtensionFilter("txt", "*.txt"));
			File selectedFile = fileChooser.showOpenDialog(stage);
			if(selectedFile != null) {
				sendSaveClicked(selectedFile);
			}
        });
		((Button) buttons[2]).setOnAction((event) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().add(
			         new ExtensionFilter("txt", "*.txt"));
			File selectedFile = fileChooser.showOpenDialog(stage);
			if(selectedFile != null) {
				sendLoadClicked(selectedFile);
			}
			
        });
		((Button) buttons[0]).setOnAction((event) -> {
			sendNewClicked();
        });
	}
	
	//GETTERS
	public ViewPlanAPI getWhiteBoardAPI() {
		return wb;
	}
	
	public ViewPlanAPI getToolBarAPI() {
		return tb;
	}
	
	public Point getPosWindow() {
		return new Point(stage.getX(),stage.getY());
	}
}
