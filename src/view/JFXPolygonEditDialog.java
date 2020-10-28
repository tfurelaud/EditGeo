package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ColorRGB;
import model.Point;
import model.Polygon;
import model.Shape;

/**
 * The implementation of the interface "EditDialogBox" with JavaFX for polygons
 * @author Cayrol, Furelaud
 *
 */
public class JFXPolygonEditDialog implements EditDialogBox {
	private ColorRGB color;
	private double dimSide;
	private int nbSide;
	private Point rotation_center;
	private double rotation;
	private double x,y;
	private Button OK,appliquer,annuler;
	
	private ViewPlanAPI viewPlan;
	
	private TextField newDim;
	private TextField newNbSide;
	private TextField newRotationX;
	private TextField newRotationY;
    private TextField text_rotation;
    
    private Shape s;
    private ColorPicker colorPicker;
    private Stage dialog;
	
	public JFXPolygonEditDialog(ViewPlanAPI viewPlan, Shape shape, double x, double y){
		Polygon s = (Polygon) shape;
		this.s = s;
		this.color = s.getColorRGB();
		this.dimSide = s.getDimSide();
		this.nbSide = s.getNbSide();
		this.rotation_center = s.getRotationCenter();
		this.rotation = s.getRotation();
		this.x = x;
		this.y = y;
		this.viewPlan = viewPlan;
	}

	/**
	 * To draw the dialog box
	 */
	@Override
	public void drawDialogBox() {
		final Stage dialog = new Stage();
		dialog.setX(s.getPos().getX() + 100 + x);
        dialog.setY(s.getPos().getY() + y - 30);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Parameter modifier");
        this.dialog = dialog;
        
        VBox dialogVbox = new VBox(20);
        Scene dialogScene = new Scene(dialogVbox, 400, 300);
       
        final ColorPicker colorPicker = new ColorPicker();
		Color fcolor = new Color(s.getColorRGB().getColorR()/255.0,s.getColorRGB().getColorG()/255.0,s.getColorRGB().getColorB()/255.0,1.0);
		colorPicker.setValue(fcolor);
        final Circle circle = new Circle(20);
        circle.setFill(colorPicker.getValue());
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                circle.setFill(colorPicker.getValue());
            }
        });
        this.colorPicker = colorPicker;
        
        Text t = new Text("The middle anchor should be : x = " + (int)(s.getPos().getX() + s.getWidth()/2) + " and y = "+ (int)(s.getPos().getY() + s.getHeight()/2));
        dialogVbox.getChildren().addAll(circle,colorPicker,new Text("Actually : x = " + s.getPos().getX() + " and y = "+ s.getPos().getY()),t);
        HBox buttons = new HBox(20);
        Button OK = new Button("OK");
        Button appliquer = new Button("Appliquer");
        Button annuler = new Button("Annuler");
        this.OK = OK;
        this.appliquer = appliquer;
        this.annuler = annuler;
        
        buttons.getChildren().addAll(OK,appliquer,annuler);
        HBox otherInfo1 = new HBox(20);
        HBox otherInfo2 = new HBox(20);
        final TextField newDim;
        final TextField newNbSide;
        final TextField newRotationX;
        final TextField newRotationY;
        final TextField rotation;
    	newDim = new TextField();
    	newDim.setText(null);
    	newDim.setPromptText("Dimension of a side");
    	this.newDim = newDim;
    	
    	newNbSide = new TextField();
    	newNbSide.setText(null);
    	newNbSide.setPromptText("NbDim");
    	this.newNbSide = newNbSide;
    	
    	newRotationX = new TextField();
    	newRotationX.setText(null);
    	newRotationX.setPromptText("Anchor x");
    	this.newRotationX = newRotationX;
    	
    	newRotationY = new TextField();
    	newRotationY.setText(null);
    	newRotationY.setPromptText("Anchor y");
    	this.newRotationY = newRotationY;
    	
    	rotation = new TextField();
    	rotation.setText(null);
    	rotation.setPromptText("Rotation");
    	this.text_rotation = rotation;
    	
    	otherInfo1.getChildren().addAll(newDim, newNbSide);
    	otherInfo2.getChildren().addAll(newRotationX, newRotationY, rotation);
        
        dialogVbox.getChildren().add(otherInfo1);
        dialogVbox.getChildren().add(otherInfo2);
        
       
        dialogVbox.getChildren().add(buttons);
        dialog.setScene(dialogScene);
        dialog.show();
        
	}

	/**
	 * The buttons handler
	 */
	@Override
	public void HandleBox() {
		OK.setOnAction((event) -> {
            Color c = colorPicker.getValue();
            ColorRGB to_give = new ColorRGB((int) (c.getRed()*255), (int) (c.getGreen()*255), (int) (c.getBlue()*255));
           	this.color = to_give;
        	if (newDim.getText() != null) {
         		try {
         			double dim = Double.parseDouble(newDim.getText());
         			this.dimSide = dim;
         		}
         		catch (NumberFormatException e){}
             }
        	if (newNbSide.getText() != null) {
         		try {
         			int nbSide = Integer.parseInt(newNbSide.getText());
         			this.nbSide = nbSide;
         		}
         		catch (NumberFormatException e){}
             }
        	if (newRotationX.getText() != null && newRotationY.getText() != null) {
         		try {
         			double rotax = Double.parseDouble(newRotationX.getText());
         			double rotay = Double.parseDouble(newRotationY.getText());
         			this.rotation_center = new Point(rotax, rotay);
         		}
         		catch (NumberFormatException e){}
             }
        	if (text_rotation.getText() != null) {
         		try {
         			double rota = Double.parseDouble(text_rotation.getText());
         			this.rotation = rota;
         		}
         		catch (NumberFormatException e){}
             }
        	viewPlan.sendOK(color,dimSide,nbSide,rotation_center,this.rotation);
            dialog.close();
        });
        
        appliquer.setOnAction((event) -> {
        	 Color c = colorPicker.getValue();
             ColorRGB to_give = new ColorRGB((int) (c.getRed()*255), (int) (c.getGreen()*255), (int) (c.getBlue()*255));
             this.color = to_give;
             if (newDim.getText() != null) {
          		try {
          			double dim = Double.parseDouble(newDim.getText());
          			this.dimSide = dim;
          		}
          		catch (NumberFormatException e){}
              }
         	if (newNbSide.getText() != null) {
          		try {
          			int nbSide = Integer.parseInt(newNbSide.getText());
          			this.nbSide = nbSide;
          		}
          		catch (NumberFormatException e){}
              }
         	if (newRotationX.getText() != null && newRotationY.getText() != null) {
          		try {
          			double rotax = Double.parseDouble(newRotationX.getText());
          			double rotay = Double.parseDouble(newRotationY.getText());
          			this.rotation_center = new Point(rotax, rotay);
          		}
          		catch (NumberFormatException e){}
              }
         	if (text_rotation.getText() != null) {
          		try {
          			double rota = Double.parseDouble(text_rotation.getText());
          			this.rotation = rota;
          		}
          		catch (NumberFormatException e){}
              }
         	viewPlan.sendApply(color,dimSide,nbSide,rotation_center,this.rotation);
        });
        
        annuler.setOnAction((event) -> {
        	viewPlan.sendCancel();
        	dialog.close();
        });
        
        EventHandler<WindowEvent> closeDialog = new EventHandler<WindowEvent>() {
			public void handle(WindowEvent e) {
					viewPlan.sendClose();
			}
		};
		dialog.setOnCloseRequest(closeDialog);
	}
	
	
}
