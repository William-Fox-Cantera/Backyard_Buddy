package pkgMain;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class - Main, the code runs from here. This class puts the scene into a map
 * 				 for easy switching.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
public class Main extends Application {
	
	private static Map<SceneName, Scene> scenes = new HashMap<>();
	public static Map<SceneName, View> views = new HashMap<>();
	public Controller controller;
	
	@Override 
	public void start(Stage stage) throws Exception {
		
		//Creating a new controller and model that will be used by all views
		controller = new Controller();
		Model model = new Model();
		Garden garden = new Garden();
		
		//Setting the model and stage of the shared controller
		controller.setModel(model);
		controller.setStage(stage);
		controller.setGarden(garden);
		
		Model.loadSettings(model.configs);
		model.loadSavedGardens();
		System.out.println("Saved gardens: " + model.savedGardens.size());
		
		//making all of the views and putting them in the view map first
		views.put(SceneName.DESIGNGARDENVIEW, new DesignGardenView(stage, controller));
		views.put(SceneName.DRAWGARDENVIEW, new DrawGardenView(stage,controller));
		views.put(SceneName.ADDPREEXISTINGVIEW, new AddPreexistingView(stage,controller));
		views.put(SceneName.CONFIGURATIONSVIEW, new ConfigurationsView(stage,controller, model.configs));
		views.put(SceneName.HOMEPAGEVIEW, new HomePageView(stage,controller));
		views.put(SceneName.PLANTCATALOGVIEW, new PlantCatalogView(stage,controller));
		views.put(SceneName.PRESURVEYVIEW, new PreSurveyView(stage,controller));
		views.put(SceneName.PREVIOUSGARDENSVIEW, new PreviousGardensView(stage,controller, model.savedGardens));
		views.put(SceneName.SAVEGARDENVIEW, new SaveGardenView(stage,controller));
		
		//Now putting all of the scenes in the scenes map
		scenes.put(SceneName.HOMEPAGEVIEW, views.get(SceneName.HOMEPAGEVIEW).getScene(/*model.savedGardens*/));
		scenes.put(SceneName.CONFIGURATIONSVIEW, views.get(SceneName.CONFIGURATIONSVIEW).getScene());
		scenes.put(SceneName.DESIGNGARDENVIEW, views.get(SceneName.DESIGNGARDENVIEW).getScene());
		scenes.put(SceneName.PLANTCATALOGVIEW, views.get(SceneName.PLANTCATALOGVIEW).getScene());
		scenes.put(SceneName.PRESURVEYVIEW, views.get(SceneName.PRESURVEYVIEW).getScene());
		scenes.put(SceneName.DRAWGARDENVIEW, views.get(SceneName.DRAWGARDENVIEW).getScene());
		scenes.put(SceneName.PREVIOUSGARDENSVIEW, views.get(SceneName.PREVIOUSGARDENSVIEW).getScene());
		scenes.put(SceneName.SAVEGARDENVIEW, views.get(SceneName.SAVEGARDENVIEW).getScene());
		scenes.put(SceneName.ADDPREEXISTINGVIEW, views.get(SceneName.ADDPREEXISTINGVIEW).getScene());
		
		//Finally passing them to the controller
		controller.setViews();	
		controller.setScenes(scenes);

		stage.setScene(scenes.get(SceneName.HOMEPAGEVIEW));
		stage.setTitle("Backyard Buddy");

		stage.show();
	}
	
	public static Map<SceneName, Scene> getScenes() {
		return scenes;
	}
	
	public static Map<SceneName, View> getViews() {
		return views;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
