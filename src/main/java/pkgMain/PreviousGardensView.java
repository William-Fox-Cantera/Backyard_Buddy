package pkgMain;
import java.util.ArrayList;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text; 
import javafx.scene.layout.TilePane; 
import javafx.geometry.*; 
import javafx.scene.layout.VBox;
import java.util.HashMap;
import java.util.Map;

/**
 * Class - PreviousGardensView, view for previously saved gardens
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
public class PreviousGardensView extends View{
	private BorderPane root;
	private GridPane grid;
	public Text configurations;
	private Scene scene;
	private FlowPane flow; 
	private TilePane tile1;
	private VBox v;
	private HBox h;
	public Controller controller;
	public FlowPane titleBar;
	public Button deleteAll;
	public Button refreshButton;
	public TilePane delete;
	
	private int flowSize = 500;
	private int vHeight = 575;
	private int vWidth = 600;
	private int sceneHeight = 1100;
	private int sceneWidth = 700;
	
	
	/**
	 * PreviousGardensView, constructor
	 * 
	 * @param stage
	 * @param cont
	 * @param saved
	 */
	PreviousGardensView(Stage stage, Controller cont, ArrayList<String> saved) {
		controller = cont;
		titleBar = getTitleBar("Previous Gardens", controller);
		delete = new TilePane(Orientation.HORIZONTAL);
		delete.setPadding(new Insets(insetVal, insetVal, insetVal, insetVal));
		
		delete.setAlignment(Pos.CENTER);
		flow = new FlowPane(Orientation.VERTICAL); // Default is horizontal
		root = new BorderPane(); // Its just a border pane, simpler to call it root though
		v = new VBox();
		v.setPadding(new Insets(insetVal, insetVal, insetVal, insetVal));
		v.setSpacing(insetVal);
		h = new HBox();
		h.setPadding(new Insets(insetVal, insetVal, insetVal, insetVal));
		h.setSpacing(insetVal);

		h.getChildren().add(addDeleteAllButton(saved));
		h.getChildren().add(addRefreshButton(saved));
		h.setAlignment(Pos.CENTER);
		
		configurations = new Text("Previous Gardens");
		configurations.setDisable(true); // Make it non-interactive
		configurations.setScaleX(scaleVal);
		configurations.setScaleY(scaleVal);
		configurations.setTranslateY(yTranslateVal);

		load(saved);
	}
	
	
	/**
	 * load, sets up stuff on the screen including the saved garden objects.
	 * 
	 * @param saved an arraylist of the names of the saved gardens
	 */
	public void load(ArrayList<String> saved) {
		flow.setStyle("-fx-background-color:#00ff00;");
		flow.setMaxHeight(flowSize);
		flow.setMaxWidth(flowSize);
		v.setMaxHeight(vHeight);
		v.setMaxWidth(vWidth);
		//v.getChildren().addAll(tilePanes);
		//flow.getChildren().addAll(tilePanes);
		flow.setAlignment(Pos.CENTER); 
        flow.setColumnHalignment(HPos.CENTER); 
        flow.setRowValignment(VPos.CENTER); 
        
        loadSavedGardens(saved);
		 // Finally, makes the scene after adding the flowpane to the center of root
		root.setCenter(flow);
		root.setTop(titleBar);
		root.setBottom(h);
		root.setAlignment(configurations, Pos.CENTER);
		scene = new Scene(root, sceneHeight, sceneWidth);
	}
	
	
	/**
	 * getScene, This is where the display garden scene gets put together. The plants 
	 *           from the lists are listed in the side bar the title bar gets added, etc.
	 * 
	 * @return scene the finished and ready scene to display DesignGardenScene
	 */
	public Scene getScene() {
		return this.scene;
	}
	
	
	/**
	 * loadSavedGardens, load the previously saved gardens from a file.
	 * 
	 * @param saved an arraylist of the names of the saved gardens
	 */
	public void loadSavedGardens(ArrayList<String> saved) {
		for(int i = 0; i < saved.size(); i++) {
			PreviousGarden pg = new PreviousGarden(flow,saved.get(i),controller,saved,Model.ratings.get(saved.get(i)));
		}
	}
	
	
	/**
	 * refresh, adds any gardens that are saved but not currently on the screen.
	 * 
	 * @param saved arraylist of the names of the saved gardens
	 * @param toBeAdded arraylist of the names of the gardens to be added
	 * @param toBeDeleted arraylist of the names of the gardens to be deleted
	 */
	public void refresh(ArrayList<String> saved, ArrayList<String> toBeAdded, ArrayList<String> toBeDeleted) {
		HashMap<String, Integer> tmp = new HashMap<>();
		for(int x = 0; x < toBeAdded.size(); x++) {
			tmp.put(toBeAdded.get(x),x);
		}
		ArrayList<String> tempList = new ArrayList<>();
		for(Map.Entry<String, Integer> p : tmp.entrySet()) {
			tempList.add(p.getKey());
		}
		double r;
		for(int i = 0; i < tempList.size(); i++) {
			System.out.println(model.getRatings());
			r = model.ratings.get(tempList.get(i));
			PreviousGarden pg = new PreviousGarden(flow,tempList.get(i),controller,saved,Model.ratings.get(tempList.get(i)));
		}
		toBeAdded.clear();
	}
	
	
	/**
	 * addDeleteAllButton, adds a button that deletes all the saved gardens.
	 * 
	 * @param saved arraylist of the names of the saved gardens
	 * @return the delete all Button
	 */
	public Button addDeleteAllButton(ArrayList<String> saved) {
		deleteAll = new Button("Delete All");
		deleteAll.setScaleX(1.5);
		deleteAll.setScaleY(1.5);
		deleteAll.setOnMouseClicked(e -> {
			controller.clearSavedGardens(e,saved);
			flow.getChildren().removeAll(flow.getChildren());
		});
		return deleteAll;
	}
	
	
	/**
	 * addRefreshButton, adds a button to refresh the view of the saved gardens.
	 * 
	 * @param saved arraylist of the names of the saved gardens
	 * @return refreshButton to refresh all saved gardens when pressed
	 */
	public Button addRefreshButton(ArrayList<String> saved) {
		refreshButton = new Button("Refresh");
		refreshButton.setScaleX(1.5);
		refreshButton.setScaleY(1.5);
		refreshButton.setOnMouseClicked(e -> refresh(saved,Model.toBeAdded,Model.toBeDeleted));
		return refreshButton;
	}
}