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
import model.Shape;
import model.ShapeComposite;

/**
 * The implementation of the interface "EditDialogBox" with JavaFX for composites
 * @author Cayrol, Furelaud
 *
 */
public class JFXCompositeEditDialog implements EditDialogBox{
	private boolean change_color;
	private ColorRGB color;
	private double width;
	private double height;
	private Point rotation_center;
	private double rotation;
	private double x,y;
	private Button OK,appliquer,annuler;
	private boolean changed;
	
	private ViewPlanAPI viewPlan;
	
	private TextField newHeight;
	private TextField newWidth;
	private TextField newRotationX;
	private TextField newRotationY;
    private TextField text_rotation;
    
    private ShapeComposite s;
    private ColorPicker colorPicker;
    private Stage dialog;
	
	public JFXCompositeEditDialog(ViewPlanAPI viewPlan, Shape shape, double x, double y){
		ShapeComposite s = (ShapeComposite) shape;
		this.s = s;
		this.color = s.getColorRGB();
		this.height = s.getHeight();
		this.width = s.getWidth();
		this.rotation_center = s.getRotationCenter();
		this.rotation = s.getRotation();
		this.viewPlan = viewPlan;
		this.x = x;
		this.y = y;
	}

	/**
	 * Draw the dialogBox
	 */
	public void drawDialogBox() {
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Parameter modifier");
        dialog.setX(s.getPos().getX() + 100 + x);
        dialog.setY(s.getPos().getY() + y - 20);
        this.dialog = dialog;
        VBox dialogVbox = new VBox(20);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        
        change_color = false;
        final ColorPicker colorPicker = new ColorPicker();
		Color fcolor = new Color(s.getColorRGB().getColorR()/255.0,s.getColorRGB().getColorG()/255.0,s.getColorRGB().getColorB()/255.0,1.0);
		colorPicker.setValue(fcolor);
        final Circle circle = new Circle(20);
        circle.setFill(colorPicker.getValue());
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	change_color = true;
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
        final TextField newHeight;
        final TextField newWidth;
        final TextField newRotationX;
        final TextField newRotationY;
        final TextField text_rotation;
        newHeight = new TextField();
        newHeight.setText(null);
        newHeight.setPromptText("Height");
        this.newHeight = newHeight;
        
    	newWidth = new TextField();
    	newWidth.setText(null);
    	newWidth.setPromptText("Width");
        this.newWidth = newWidth;
    	
    	newRotationX = new TextField();
    	newRotationX.setText(null);
    	newRotationX.setPromptText("Anchor X");
    	this.newRotationX = newRotationX;
    	
    	newRotationY = new TextField();
    	newRotationY.setText(null);
    	newRotationY.setPromptText("Anchor Y");
    	this.newRotationY = newRotationY;
    	
    	text_rotation = new TextField();
    	text_rotation.setText(null);
    	text_rotation.setPromptText("Rotation");
    	this.text_rotation = text_rotation;
    	
    	otherInfo1.getChildren().addAll(newHeight,newWidth);
        otherInfo2.getChildren().addAll(newRotationX, newRotationY, text_rotation);
        dialogVbox.getChildren().addAll(otherInfo1,otherInfo2);
        dialogVbox.getChildren().add(buttons);
        dialog.setScene(dialogScene);
        dialog.show();
        
	}
	
	/**
	 * The button handler
	 */
	public void HandleBox() {
		this.changed = false;
		OK.setOnAction((event) -> {
            Color c = colorPicker.getValue();
            ColorRGB to_give = new ColorRGB((int) (c.getRed()*255), (int) (c.getGreen()*255), (int) (c.getBlue()*255));
            if(change_color == true) {
            	this.color = to_give;
            	changed = true;
            }
            if(newHeight.getText() != null) {
     			try {
     				double dim = Integer.parseInt(newHeight.getText());
         			this.height = dim;
         			changed = true;
         		}
         		catch (NumberFormatException e){}
     		}
     		if(newWidth.getText() != null) {
     			try {
     				double width = Integer.parseInt(newWidth.getText());
     				this.width = width;
     				changed = true;
         		}
         		catch (NumberFormatException e){}
     		}
     		if(newRotationX.getText() != null && newRotationY.getText() != null) {
     			try {
     				double rotax = Double.parseDouble(newRotationX.getText());
     				double rotay = Double.parseDouble(newRotationY.getText());
         			this.rotation_center = new Point(rotax,rotay);
         			changed = true;
         		}
         		catch (NumberFormatException e){}
     		}
     		if(text_rotation.getText() != null) {
     			try {
     				double rota = Double.parseDouble(text_rotation.getText());
         			this.rotation = rota;
         			changed = true;
         		}
         		catch (NumberFormatException e){}
     		}
     		if(changed == true)
     			viewPlan.sendOK(color, width, height, rotation_center, this.rotation);
     		else
     			viewPlan.sendClose();
            dialog.close();
        });
		
		appliquer.setOnAction((event) -> {
       	 	Color c = colorPicker.getValue();
            ColorRGB to_give = new ColorRGB((int) (c.getRed()*255), (int) (c.getGreen()*255), (int) (c.getBlue()*255));
            if(change_color == true) {
            	this.color = to_give;
            	changed = true;
            }
            if(newHeight.getText() != null) {
     			try {
     				double dim = Double.parseDouble(newHeight.getText());
         			this.height = dim;
         			changed = true;
         		}
         		catch (NumberFormatException e){}
     		}
     		if(newWidth.getText() != null) {
     			try {
     				System.out.println("la");
     				double width = Double.parseDouble(newWidth.getText());
                   this.width = width;
                   changed = true;
         		}
         		catch (NumberFormatException e){}
     		}if(newRotationX.getText() != null && newRotationY.getText() != null) {
    			try {
    				double rotax = Double.parseDouble(newRotationX.getText());
    				double rotay = Double.parseDouble(newRotationY.getText());
        			this.rotation_center = new Point(rotax, rotay);
        			changed = true;
        		}
        		catch (NumberFormatException e){}
    		}
    		if(text_rotation.getText() != null) {
    			try {
    				double rota = Double.parseDouble(text_rotation.getText());
        			this.rotation = rota;
        			changed = true;
        		}
        		catch (NumberFormatException e){}
    		}
    		if(changed == true)
    			viewPlan.sendApply(color, width, height, rotation_center, rotation);
    		this.changed = false;
    		this.newHeight.setText(null);
    		this.newRotationX.setText(null);
    		this.newRotationY.setText(null);
    		this.newWidth.setText(null);
    		this.text_rotation.setText(null);
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
