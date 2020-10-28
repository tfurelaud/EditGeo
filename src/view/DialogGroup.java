package view;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The dialog box drawing used for group or degroup composite. 
 * @author Cayrol, Furelaud
 *
 */
public class DialogGroup{
	
	public DialogGroup(JFXViewPlan viewPlan, double x, double y) {
		final Stage group = new Stage();
		group.setX(x);
        group.setY(y);
		group.initModality(Modality.APPLICATION_MODAL);
		group.setTitle("Group");
        VBox dialogVbox = new VBox(20);
        Scene dialogScene = new Scene(dialogVbox, 300, 100);
        Button to_group = new Button("Group");
        Button to_degroup = new Button("De-Group");
        dialogVbox.getChildren().add(to_group);
        dialogVbox.getChildren().add(to_degroup);
        to_group.setOnAction((event) -> {
        	viewPlan.sendGroupClicked();
        	group.close();
        });

        to_degroup.setOnAction((event) -> {
        	viewPlan.sendDeGroupClicked();
        	group.close();
        });
        
        EventHandler<WindowEvent> closeDialog = new EventHandler<WindowEvent>() {
			public void handle(WindowEvent e) {
					viewPlan.sendClose();
			}
		};
        
        group.setOnCloseRequest(closeDialog);
        	
        group.setScene(dialogScene);
        group.show();
	}
}
