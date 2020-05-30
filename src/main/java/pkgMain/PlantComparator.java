package pkgMain;

import java.util.Comparator;


import java.util.Map.Entry;

/**
 * Class - PlantComparator. Custom comparator for plant objects.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
public class PlantComparator implements Comparator<Entry<String,Plant>>{
	@Override
    public int compare(Entry<String, Plant> a, Entry<String, Plant> b) {
        return (a.getValue().getScore() - b.getValue().getScore());
    }
}


