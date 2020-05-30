import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import javafx.scene.shape.Circle;
import pkgMain.Model;
import pkgMain.Plant;
import pkgMain.*;

public class ModelTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	
	@Test
	public void searchPlantTest1() {
		Model model = new Model();
		ArrayList<Plant> list1 = new ArrayList<Plant>();
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial shade", PlantType.FLOWER);
		list1.add(lilac);
		ArrayList<Plant> list2 = model.searchPlants(list1, "Lilac");
		assertEquals(list2.contains(lilac), list1.contains(lilac));
	}
	
	
	@Test
	public void searchPlantTest2() {
		Model model = new Model();
		ArrayList<Plant> list1 = new ArrayList<Plant>();
		ArrayList<Plant> list2 = model.searchPlants(list1, "Lilac");
		assertTrue(list2.isEmpty());
	}
	
	
	@Test 
	public void makeScaleTest() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Point a = new Point("a", 5,5,0,0, null);
		Point b = new Point("b",5,5,3,4,null);
		model.setGardenScale(a, b, "10");
		assertTrue(Math.abs(model.getScale() - 24) < THRESHOLD);
	}
	
	/*
	@Test
	public void readGardenDataTest1() {
		Model model = new Model();
		String s = "";
		try {
			final File tempFile = tempFolder.newFile("tempFile.txt");
			FileUtils.writeStringToFile(tempFile, "hello");
			s = model.readGardenData(tempFile.getAbsolutePath());
			System.out.println(s);
			assertEquals(tempFile.exists(), true);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
	*/
	
	@Test
	public void sunScoreTest() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial shade",  PlantType.FLOWER);
		PlantIcon pi = new PlantIcon(new Circle(),0,0, lilac);
		model.lightConfig = "full-sun";
		model.addToAllPlants(pi);
		assertTrue(model.sunScore() < THRESHOLD);
	}
	
	@Test
	public void sunScoreTest2() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		PlantIcon pi = new PlantIcon(new Circle(),0,0, lilac);
		model.lightConfig = "partial-shade";
		model.addToAllPlants(pi);
		assertTrue(Math.abs(model.sunScore() - 1) < THRESHOLD);
	}
	
	@Test
	public void waterScoreTest1() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		PlantIcon pi = new PlantIcon(new Circle(),0,0, lilac);
		model.waterConfig = "dry";
		model.addToAllPlants(pi);
		assertTrue(model.waterScore() < THRESHOLD);
	}
	
	@Test
	public void waterScoreTest2() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		PlantIcon pi = new PlantIcon(new Circle(),0,0, lilac);
		model.waterConfig = "wet";
		model.addToAllPlants(pi);
		assertTrue(Math.abs(model.waterScore() -1) < THRESHOLD);
	}
	
	@Test
	public void soilScoreTest1() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		PlantIcon pi = new PlantIcon(new Circle(),0,0, lilac);
		model.soilConfig = "adaptable";
		model.addToAllPlants(pi);
		assertTrue(model.soilScore() < THRESHOLD);
	}
	
	@Test
	public void soilScoreTest2() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic,", null, "partial-shade",  PlantType.FLOWER);
		PlantIcon pi = new PlantIcon(new Circle(),0,0, lilac);
		model.soilConfig = "acidic";
		model.addToAllPlants(pi);
		assertTrue(Math.abs(model.soilScore() -1) < THRESHOLD);
	}
	
	@Test
	public void yearRoundScoreTest1() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		PlantIcon pi = new PlantIcon(new Circle(),0,0, lilac);
		model.addToAllPlants(pi);
		assertTrue(Math.abs(model.yearRoundScore() - (double) (1)/(double)(3)) < THRESHOLD);
	}
	
	@Test
	public void yearRoundScoreTest2() {
		Model model = new Model();
		final double THRESHOLD = .001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		Plant lilac2 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-summer", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		PlantIcon pi = new PlantIcon(new Circle(),0,0, lilac);
		PlantIcon pi2 = new PlantIcon(new Circle(),0,0, lilac2);
		model.addToAllPlants(pi);
		model.addToAllPlants(pi2);
		assertTrue(Math.abs(model.yearRoundScore() - (double) (2)/(double)(3)) < THRESHOLD);
	}
	
	@Test
	public void yearRoundScoreTest3() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		Plant lilac2 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-summer", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		PlantIcon pi = new PlantIcon(new Circle(),0,0, lilac);
		PlantIcon pi2 = new PlantIcon(new Circle(),0,0, lilac2);
		Plant lilac3 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-fall", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		PlantIcon pi3 = new PlantIcon(new Circle(),0,0, lilac3);
		model.addToAllPlants(pi);
		model.addToAllPlants(pi2);
		model.addToAllPlants(pi3);
		assertTrue(Math.abs(model.yearRoundScore() - 1) < THRESHOLD);
	}
	
	@Test
	public void diversityScoreTest1() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		Plant lilac2 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-summer", "lilac", "acidic", null, "partial-shade",  PlantType.TREE);
		PlantIcon pi1 =new PlantIcon(new Circle(),0,0, lilac);
		PlantIcon pi2 = new PlantIcon(new Circle(),0,0, lilac2);
		model.addToAllPlants(pi1);
		model.addToAllPlants(pi2);
		assertTrue(Math.abs(model.getDiversityScore() - .4) < THRESHOLD);		
	}
	
	@Test
	public void diversityScoreTest2() {
		Model model = new Model();
		final double THRESHOLD = .0001;
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		Plant lilac2 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-summer", "lilac", "acidic", null, "partial-shade",  PlantType.TREE);
		Plant lilac3 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.SHRUB);
		Plant lilac4 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-summer", "lilac", "acidic", null, "partial-shade",  PlantType.VINE);
		Plant lilac5 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "acidic", null, "partial-shade",  PlantType.GRASS);
		PlantIcon pi1 =new PlantIcon(new Circle(),0,0, lilac);
		PlantIcon pi2 = new PlantIcon(new Circle(),0,0, lilac2);
		PlantIcon pi3 =new PlantIcon(new Circle(),0,0, lilac3);
		PlantIcon pi4 = new PlantIcon(new Circle(),0,0, lilac4);
		PlantIcon pi5 =new PlantIcon(new Circle(),0,0, lilac5);
		model.addToAllPlants(pi1);
		model.addToAllPlants(pi2);
		model.addToAllPlants(pi3);
		model.addToAllPlants(pi4);
		model.addToAllPlants(pi5);
		assertTrue(Math.abs(model.getDiversityScore() - 1) < THRESHOLD);		
	}
	
	@Test
	public void rateGardenTest1() {
		Model model = new Model();
		model.lightConfig = "full-sun";
		model.soilConfig = "adaptable";
		model.waterConfig = "dry";
		Plant lilac = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"dry","mid-fall", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		Plant lilac2 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"dry","mid-summer", "lilac", "adaptable", null, "partial-shade",  PlantType.TREE);
		Plant lilac3 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"dry","mid-spring", "lilac", "adaptable", null, "partial-shade",  PlantType.SHRUB);
		Plant lilac4 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"dry","mid-summer", "lilac", "acidic", null, "partial-shade",  PlantType.VINE);
		Plant lilac5 = new Plant("Lilac","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-spring", "lilac", "adaptable", null, "partial-shade",  PlantType.GRASS);
		PlantIcon pi1 =new PlantIcon(new Circle(),0,0, lilac);
		PlantIcon pi2 = new PlantIcon(new Circle(),0,0, lilac2);
		PlantIcon pi3 =new PlantIcon(new Circle(),0,0, lilac3);
		PlantIcon pi4 = new PlantIcon(new Circle(),0,0, lilac4);
		PlantIcon pi5 =new PlantIcon(new Circle(),0,0, lilac5);
		model.addToAllPlants(pi1);
		model.addToAllPlants(pi2);
		model.addToAllPlants(pi3);
		model.addToAllPlants(pi4);
		model.addToAllPlants(pi5);
		model.rateGarden();
		assertTrue(model.getGardenRating() == 6.13);
	}
	
	@Test
	public void rankPlantListTest1() {
		Model model = new Model();
		HashMap<String, Plant> plantMap = new HashMap<>();
		boolean match = true;
		model.lightConfig = "full-sun";
		model.soilConfig = "adaptable";
		model.waterConfig = "dry";
		Plant lilac1 = new Plant("Lilac1","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"wet","mid-fall", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		Plant lilac2 = new Plant("Lilac2","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"dry","mid-summer", "lilac", "adaptable", null, "full-sun",  PlantType.TREE);
		Plant lilac3 = new Plant("Lilac3","lilacus", "60 inches", 24,"Lilacs are hardy, easy to grow, and low maintenance",
				"dry","mid-spring", "lilac", "adaptable", null, "partial-shade", PlantType.SHRUB);
		plantMap.put(lilac1.getName(), lilac1);
		plantMap.put(lilac2.getName(), lilac2);
		plantMap.put(lilac3.getName(), lilac3);
		ArrayList<Plant> actualSortedList = model.sortPlants(plantMap);
		ArrayList<Plant> idealSortedList = new ArrayList<>();
		idealSortedList.add(lilac2);
		idealSortedList.add(lilac3);
		idealSortedList.add(lilac1);
		for (int i = 0; i< actualSortedList.size(); i++) {
			if (actualSortedList.get(i) != idealSortedList.get(i))
				match = false;
		}
		assertTrue(match);
	}
	
	@Test
	public void getPlantScoreTest() {
		Model model = new Model();
		model.lightConfig = "full-sun";
		model.soilConfig = "adaptable";
		model.waterConfig = "dry";
		model.seasonConfig = "fall";
		
		Plant score40plant = new Plant("Lilac","x", "x", 24,"x",
				"dry","mid-fall", "lilac", "adaptable", null, "full-sun", PlantType.FLOWER);
	
		int result = model.getPlantScore(score40plant);
		assertEquals(result,40);
	}
	
	@Test
	public void sortPlantsTest() {
		Model model = new Model();
		model.lightConfig = "full-sun";
		model.soilConfig = "adaptable";
		model.waterConfig = "dry";
		model.seasonConfig = "fall";
		
		Plant score40plant = new Plant("Lilac","x", "x", 24,"x",
				"dry","mid-fall", "lilac", "adaptable", null, "full-sun", PlantType.FLOWER);
		Plant score0plant = new Plant("Lilac","x", "x", 24,"x",
				"wet","spring", "lilac", "acidic", null, "partial-shade",  PlantType.FLOWER);
		HashMap<String, Plant> plantList = new HashMap<String,Plant>();
		plantList.put("Score40Plant", score40plant);
		plantList.put("Score0Plant", score0plant);
		
		ArrayList<Plant> expectedReturn = new ArrayList<Plant>();
		expectedReturn.add(score40plant);
		expectedReturn.add(score0plant);
		ArrayList<Plant> result = model.sortPlants(plantList);
		assertEquals(result,expectedReturn);
	}

	
	
}
