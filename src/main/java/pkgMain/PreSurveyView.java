package pkgMain;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class - PreSurveyView, this class sets up the scene for the pre-survey.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
public class PreSurveyView extends View{
	Controller controller;
	BorderPane bp1 = new BorderPane();
	FlowPane titleBar; 

	
	public PreSurveyView(Stage stage, Controller control) {
		controller = control;
		titleBar = getTitleBar("Presurvey", controller);
		//controller = new Controller(stage, this);
		
	} 
	
	
	/**
	 * displayQuestions - displays the pre-survey questions
	 */
	public Scene getScene() {
        // create a tile pane 
		VBox r = new VBox();
  
        // create a label 
        Label q1 = new Label("What is your climate?"); 
  
        // create radiobuttons 
        RadioButton q1a = new RadioButton("Humid"); 
        RadioButton q1b = new RadioButton("Wet"); 
        RadioButton q1c = new RadioButton("Dry"); 
        
        Label q2 = new Label("What level care are you willing to give?");
        
        RadioButton q2a = new RadioButton("High");
        RadioButton q2b = new RadioButton("Medium");
        RadioButton q2c = new RadioButton("Low");
        
        Label q3 = new Label("What kind of garden do you have?");
        
        RadioButton q3a = new RadioButton("Large");
        RadioButton q3b = new RadioButton("Medium");
        RadioButton q3c = new RadioButton("Small");
        
        Label q4 = new Label("Do you currently attempt to attract wildlife?");
        
        RadioButton q4a = new RadioButton("Yes");
        RadioButton q4b = new RadioButton("No");
        
        Label q5 = new Label("What size plant is your preference?");
        
        RadioButton q5a = new RadioButton("Big");
        RadioButton q5b = new RadioButton("Medium");
        RadioButton q5c = new RadioButton("Small");
        RadioButton q5d = new RadioButton("No Preference");
       
        
        // add label 
        r.getChildren().add(q1); 
        r.getChildren().add(q1a); 
        r.getChildren().add(q1b); 
        r.getChildren().add(q1c); 
        
        r.getChildren().add(q2); 
        r.getChildren().add(q2a); 
        r.getChildren().add(q2b); 
        r.getChildren().add(q2c); 
        
        r.getChildren().add(q3); 
        r.getChildren().add(q3a); 
        r.getChildren().add(q3b); 
        r.getChildren().add(q3c); 
        
        r.getChildren().add(q4); 
        r.getChildren().add(q4a); 
        r.getChildren().add(q4b); 
        
        r.getChildren().add(q5); 
        r.getChildren().add(q5a); 
        r.getChildren().add(q5b); 
        r.getChildren().add(q5c); 
        r.getChildren().add(q5d);
        
        ScrollPane sp = new ScrollPane();
        sp.setContent(r);
        sp.setPannable(true);
        //Button home = new Button("Home");
        Button submit = new Button("Submit");
        
        submit.setLayoutX(250);
        submit.setLayoutY(200);
        
        r.getChildren().add(submit);
        //r.getChildren().add(home);
        
        submit.setOnAction(e -> {
        	submit();
        });
        
        bp1.setCenter(sp);
        bp1.setTop(titleBar);
       // bp1.setLeft(tiles);
        
        // create a scene 
        Scene sc = new Scene(bp1, 300, 200); 
       
  
        // set the scene 
        //stage.setScene(sc); 
  
        //stage.show(); 
        return sc;
		
		
		
		
	}
	
	
	/**
	 * makeRadioButtons displays the radio buttons in the presurvey
	 * 
	 * @param text text for button
	 * @param pane the pane
	 * @return the radio button
	 */
	public RadioButton makeRadioButtons(String text, TilePane pane) {
		RadioButton r = new RadioButton(text);
		r.setScaleX(1.5);
		r.setScaleY(1.5);
		pane.getChildren().add(r);
		return r;
		
	}
	
	
	/**
	 * submits the questions from the presurvey
	 */
	public void submit() {
		System.out.println("Submit");
	}
	
	
	/**
	 * onClick
	 */
	public void onClick() {}
}
