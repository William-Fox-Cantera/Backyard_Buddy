package pkgMain;
import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text; 
import javafx.scene.layout.TilePane; 
import javafx.geometry.*; 
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import java.io.File;
import java.io.IOException;

/**
 * Class - ConfigurationsView, View for the configurations of the garden, the user can
 * 		   edit the configurations/settings for any given garden
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria van Venrooy
 * 
 * @version 0.0.1
 */
@SuppressWarnings({"static-access", "unused"})
public class ConfigurationsView extends View {		
    private BorderPane root;
	private GridPane grid;
	public Text configurations;
	private Scene scene;
	private FlowPane flow; 
	private TilePane tile1;
	private VBox v;
	public ArrayList<String> questions;
	public ArrayList<Pane> tilePanes;
	public HashMap<Integer, String> configs;
	public Controller controller;
	public FlowPane titleBar;
	public ArrayList<String> saved;
	public ButtonGroup q1;
	public ButtonGroup q2;
	public ButtonGroup q3;
	public ButtonGroup q4;
	public ButtonGroup q5;
	public ButtonGroup q6;
	public Button nextStep = new Button("Next");
	
	// Constants
	private final int vHeight = 575;
	private final int vWidth = 600;
	private final int sceneHeight = HEIGHT;
	private final int sceneWidth = WIDTH;
	private final int tileGap = 20;
	private static final double scale = 1.5;
	private static final int PREV_PANE_HGAP = 10;
	
	
	/**
	 * ConfigurationsView, constructor.
	 * 
	 * @param stage the stage for this scene
	 * @param cont the instance of the controller
	 * @param configs the config hashmap
	 */
	ConfigurationsView(Stage stage, Controller cont, HashMap<Integer, String> configs) {
		controller = cont;
		titleBar = getTitleBar("Configurations", controller);
		nextStep.setOnMouseClicked(e -> controller.handleAndRemove(SceneName.DRAWGARDENVIEW));
		//titleBar.getChildren().add(nextStep);

		flow = new FlowPane(Orientation.HORIZONTAL); // Default is horizontal
		root = new BorderPane(); // Its just a border pane, simpler to call it root though
		questions = new ArrayList<String>();
		tilePanes = new ArrayList<Pane>();
		saved = new ArrayList<>();
		v = new VBox();
		v.setPadding(new Insets(insetVal, insetVal, insetVal, insetVal));
		v.setSpacing(insetVal);

		configurations = new Text("Configurations");
		configurations.setDisable(true); // Make it non-interactive
		configurations.setScaleX(scaleVal);
		configurations.setScaleY(scaleVal);
		configurations.setTranslateY(yTranslateVal);

		v.setMaxHeight(vHeight);
		v.setMaxWidth(vWidth);
		createList();
		createPanes(configs);
		v.getChildren().addAll(tilePanes);
		
		 // Finally, makes the scene after adding the flowpane to the center of root
		ScrollPane sp = new ScrollPane();
		sp.setContent(v);
		
		root.setCenter(sp);
		root.setTop(titleBar);
		root.setAlignment(configurations, Pos.TOP_LEFT);
		
		scene = new Scene(root, sceneWidth, sceneHeight);
		stage.setTitle	("Backyard Buddy - Configurations");
	}
	
	
	/**
	 * createList, creates lists of settings that change how the garden is viewed or
	 * 		       what will be shown.
	 */
	public void createList() {
		questions.add("1. Soil Acidity");
		questions.add("2. Water Amount");
		questions.add("3. View Season");
		questions.add("4. Light Amount");
		questions.add("5. Preferred Bloom Season");
	}
	
	
	/**
	 * createPanes, creates panes for questions and buttons.
	 * 
	 * @param configs the configs
	 */
	public void createPanes(HashMap<Integer,String> configs) {
		ToggleGroup q1 = new ToggleGroup();
		ToggleGroup q2 = new ToggleGroup();
		ToggleGroup q3 = new ToggleGroup();
		ToggleGroup q4 = new ToggleGroup();
		ToggleGroup q5 = new ToggleGroup();
		ToggleGroup q6 = new ToggleGroup();
		
		//Soil Reqs
		tile1 = new TilePane();
		tile1.setVgap(tileGap);
		tile1.setHgap(tileGap);
		tile1.setOrientation(Orientation.HORIZONTAL);
		Label text1 = new Label(questions.get(0));
		text1.setScaleX(scale);
		text1.setScaleY(scale);
		v.getChildren().add(text1);
		RadioButton b11 = ConfigurationsView.makeRadioButton("Acidic", v);
		b11.setToggleGroup(q1);
		b11.setOnAction(event -> {
			configs.put(1, "Acidic");
		});
		RadioButton b12 = ConfigurationsView.makeRadioButton("Adaptable", v);
		b12.setToggleGroup(q1);
		b12.setOnAction(event -> {
			configs.put(1, "Adaptable");
		});

		RadioButton b13 = ConfigurationsView.makeRadioButton("Alkaline", v);
		b13.setToggleGroup(q1);

		b13.setOnAction(event -> {
			configs.put(1, "Alkaline");
		});
		tilePanes.add(tile1);
		
		//WATER
		TilePane tile3 = new TilePane();
		tile3.setVgap(tileGap);
		tile3.setHgap(tileGap);
		tile3.setOrientation(Orientation.HORIZONTAL);
		Label text3 = new Label(questions.get(1));
		text3.setScaleX(scale);
		text3.setScaleY(scale);
		v.getChildren().add(text3);
		RadioButton b31 = ConfigurationsView.makeRadioButton("Dry", v);
		b31.setToggleGroup(q3);
		b31.setOnAction(event -> {
			configs.put(2, "Dry");
		});
		RadioButton b32 = ConfigurationsView.makeRadioButton("Average", v);
		b32.setToggleGroup(q3);
		b32.setOnAction(event -> {
			configs.put(2, "Average");
		});
		RadioButton b33 = ConfigurationsView.makeRadioButton("Moist", v);
		b33.setToggleGroup(q3);
		b33.setOnAction(event -> {
			configs.put(2, "Moist");
		});
		RadioButton b34 = ConfigurationsView.makeRadioButton("Wet", v);
		b34.setToggleGroup(q3);
		b34.setOnAction(event -> {
			configs.put(2, "Wet");
		});
		tilePanes.add(tile3);
		
		//SEASON
		TilePane tile4 = new TilePane();
		tile4.setVgap(tileGap);
		tile4.setHgap(tileGap);
		tile4.setOrientation(Orientation.HORIZONTAL);
		Label text4 = new Label(questions.get(2));
		text4.setScaleX(scale);
		text4.setScaleY(scale);
		v.getChildren().add(text4);
		RadioButton b41 = ConfigurationsView.makeRadioButton("Spring", v);
		b41.setToggleGroup(q4);
		b41.setOnAction(event -> {
			configs.put(3, "Spring");
		});
		RadioButton b42 = ConfigurationsView.makeRadioButton("Summer", v);
		b42.setToggleGroup(q4);
		b42.setOnAction(event -> {
			configs.put(3, "Summer");
		});
		RadioButton b43 = ConfigurationsView.makeRadioButton("Fall", v);
		b43.setToggleGroup(q4);
		b43.setOnAction(event -> {
			configs.put(3, "Fall");
		});
		RadioButton b44 = ConfigurationsView.makeRadioButton("Winter", v);
		b44.setToggleGroup(q4);
		b44.setOnAction(event -> {
			configs.put(3, "Winter");
		});
		tilePanes.add(tile4);
		
		//LIGHT
		TilePane tile5 = new TilePane();
		tile5.setVgap(tileGap);
		tile5.setHgap(tileGap);
		tile5.setOrientation(Orientation.HORIZONTAL);
		Label text5 = new Label(questions.get(3));
		text5.setScaleX(scale);
		text5.setScaleY(scale);
		v.getChildren().add(text5);
		RadioButton b51 = ConfigurationsView.makeRadioButton("Shade", v);
		b51.setToggleGroup(q5);
		b51.setOnAction(event -> {
			configs.put(4, "Shade");
		});
		RadioButton b52 = ConfigurationsView.makeRadioButton("Partial Shade", v);
		b52.setToggleGroup(q5);
		b52.setOnAction(event -> {
			configs.put(4, "Partial-Shade");
		});
		RadioButton b53 = ConfigurationsView.makeRadioButton("Filtered Shade", v);
		b53.setToggleGroup(q5);
		b53.setOnAction(event -> {
			configs.put(4, "Filtered-Shade");
		});
		RadioButton b54 = ConfigurationsView.makeRadioButton("Full Sun", v);
		b54.setToggleGroup(q5);
		b54.setOnAction(event -> {
			configs.put(4, "Full-Sun");
		});
		tilePanes.add(tile5);
		
		//Preferred Bloom Season
		TilePane tile6 = new TilePane();
		tile6.setVgap(tileGap);
		tile6.setHgap(tileGap);
		tile6.setOrientation(Orientation.HORIZONTAL);
		Label text6 = new Label(questions.get(4));
		text6.setScaleX(scale);
		text6.setScaleY(scale);
		v.getChildren().add(text6);
		RadioButton b61 = ConfigurationsView.makeRadioButton("Spring", v);
		b61.setToggleGroup(q6);
		b61.setOnAction(event -> {
			configs.put(5, "Spring");
		});
		RadioButton b62 = ConfigurationsView.makeRadioButton("Summer", v);
		b62.setToggleGroup(q6);
		b62.setOnAction(event -> {
			configs.put(5, "Summer");
		});
		RadioButton b63 = ConfigurationsView.makeRadioButton("Fall", v);
		b63.setToggleGroup(q6);
		b63.setOnAction(event -> {
			configs.put(5, "Fall");
		});
		RadioButton b64 = ConfigurationsView.makeRadioButton("Winter", v);
		b64.setToggleGroup(q6);
		b64.setOnAction(event -> {
			configs.put(5, "Winter");
		});
		tilePanes.add(tile6);
		
		TilePane savePane = new TilePane();
		savePane.setVgap(tileGap);
		savePane.setHgap(tileGap);
		savePane.setOrientation(Orientation.HORIZONTAL);
		savePane.setAlignment(Pos.CENTER);
		Button saveButton = new Button("Save");
		titleBar.getChildren().add(saveButton);
		titleBar.getChildren().add(nextStep);
		tilePanes.add(savePane);
		saveButton.setOnAction(event -> {
			controller.saveSettings(configs);
		});
		
		FlowPane prevPane = new FlowPane();
		prevPane.setVgap(tileGap);
		prevPane.setHgap(tileGap + PREV_PANE_HGAP);
		prevPane.setOrientation(Orientation.VERTICAL);
		prevPane.setAlignment(Pos.BASELINE_LEFT);
		Text prevText = new Text("Saved configs:");
		prevText.setScaleX(scale);
		prevText.setScaleY(scale);
		prevPane.getChildren().add(prevText);
		Text loadedText;
		if(configs.size() == 0) {
			loadedText = new Text("None");
			loadedText.setScaleX(scale);
			loadedText.setScaleY(scale);
			prevPane.getChildren().add(loadedText);
		} else {
			saved = Model.parseConfigs(configs.toString());
			loadSettings(saved,  prevPane);
		}
				
		ScrollPane sp = new ScrollPane();
        sp.setContent(v);
		
	}
	
	
	/**
	 * getScene, getter for the scene.
	 * 
	 * @return the scene
	 */
	public Scene getScene() {
		return this.scene;
	}
	
	
	/**
	 * makeRadioButton, takes in text and a pane and creates a button and adds it to that pane.
	 * 		       
	 * @param text the text to be displayed
	 * @param v the vBox
	 * @return a radiobutton
	 */
	public static RadioButton makeRadioButton(String text, VBox v) {
		RadioButton r = new RadioButton(text);
		r.setScaleX(scale);
		r.setScaleY(scale);
		v.getChildren().add(r);
		return r;
	}
	
	
	/**
	 * changeNextButton, changes the text and action of the next button.
	 */
	public void changeNextButtonToBack() {
		nextStep.setText("Back");
		nextStep.setOnMouseClicked(e -> controller.handleMousePress(controller.getLastScene()));
	}
	
	
	/**
	 * changeBackButtonToNext, changes the button that says back back to the 
	 * 						   next button.
	 */
	public void changeBackButtonToNext() {
		nextStep.setText("Next");
		nextStep.setOnMouseClicked(e -> controller.handleAndRemove(SceneName.DRAWGARDENVIEW));
	}
	
	
	/**
	 * loadSettings, load the settings that the user has chosen previously.
	 * 
	 * @param saved the saved configurations
	 * @param pane the pane in which to dsplay the configurations
	 */
	public void loadSettings(ArrayList<String> saved, FlowPane pane) {
		try {
			Text soilReq = new Text("Soil Reqs: " + saved.get(0));
			soilReq.setScaleX(scale);
			soilReq.setScaleY(scale);
			pane.getChildren().add(soilReq);
			
			Text laborAmt = new Text("Labor Amount: " + saved.get(1));
			laborAmt.setScaleX(scale);
			laborAmt.setScaleY(scale);
			pane.getChildren().add(laborAmt);
			
			Text waterAmt = new Text("Water Amount: " + saved.get(2));
			waterAmt.setScaleX(scale);
			waterAmt.setScaleY(scale);
			pane.getChildren().add(waterAmt);
			
			Text season = new Text("Season: " + saved.get(3));
			season.setScaleX(scale);
			season.setScaleY(scale);
			pane.getChildren().add(season);
			
			Text lightAmt = new Text("Light Amount: " + saved.get(4));
			lightAmt.setScaleX(scale);
			lightAmt.setScaleY(scale);
			pane.getChildren().add(lightAmt);
			
			Text bloom = new Text("Bloom: " + saved.get(5));
			bloom.setScaleX(scale);
			bloom.setScaleY(scale);
			pane.getChildren().add(bloom);
		} catch (Exception e) {
			System.out.println("No configs");
		}
	}	
}