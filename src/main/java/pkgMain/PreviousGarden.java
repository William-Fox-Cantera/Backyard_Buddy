package pkgMain;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.Button;
import java.awt.Component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import java.util.ArrayList;

/**
 * Class - PreviousGarden, this class helps load ina previously saved garden.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
public class PreviousGarden extends Component {
	private static final long serialVersionUID = 1L;
	private FlowPane flow;
	private String name;
	private VBox v;
	
	
	/**
	 * Constructor for Previous Garden
	 * @param pane the pane for the page
	 * @param saved name of garden 
	 * @param controller its the controller that its taking
	 * @param savedArr arrayList of gardenshapes
	 * @param score the score of the garden
	 */
	public PreviousGarden(FlowPane pane, String saved, Controller controller, ArrayList<String> savedArr, double score) {
		v = new VBox();
		v.setPadding(new Insets(20, 20, 20, 20));
		v.setAlignment(Pos.TOP_LEFT);
		name = saved;
		flow = new FlowPane();
		flow.setAlignment(Pos.TOP_LEFT);
		flow.setPadding((new Insets(20, 20, 20, 20)));
		createName(v,saved);
		createScore(v,score);
		Button load = createLoad(v);
		load.setOnMouseClicked(e -> controller.handleLoadGarden(e, saved));
		Button delete = createDelete(v);
		delete.setOnMouseClicked(e -> {
			controller.handleSpecificDelete(e, saved, savedArr, pane);
			pane.getChildren().remove(((Node) e.getSource()).getParent());
		});
		pane.getChildren().add(v);
	}
	

	/**
	 * getName, getter for the name.
	 * 
	 * @return name the garden name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * createScore, gets the gardens score and creates text for it.
	 * @param pane the pane being passed in
	 * @param score the score for the garden
	 */
	public void createScore(VBox pane, double score) {
		Text scoreText = new Text("Garden Score: " + score);
		pane.getChildren().add(scoreText);
	}
	
	/**
	 * createName, gets the gardens name and creates text for it.
	 * @param pane the pane
	 * @param saved the name of the saved garden
	 * 
	 */
	public void createName(VBox pane, String saved) {
		Text name = new Text("Garden Name: " + saved);
		pane.getChildren().add(name);
	}
	
	
	/**
	 * createLoad, creates a load button.
	 * @param pane the pane
	 * @return the load button
	 */
	public Button createLoad(VBox pane) {
		Button load = new Button("Load");
		pane.getChildren().add(load);
		return load;
	}
	
	
	/**
	 * createDelete, creates a delete button.
	 * @param pane the pane
	 * @return the delete button
	 */
	public Button createDelete(VBox pane) {
		Button delete = new Button("Delete");
		pane.getChildren().add(delete);
		return delete;
	}
}
