package pkgMain;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Class - ConfigSave, handles saving the configurations entered by the user.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
public class ConfigSave implements Serializable{
	private static final long serialVersionUID = -3574497672964401835L;
	public HashMap<Integer, String> configs;
	
	
	/**
	 * ConfigSave, constructor.
	 * 
	 * @param hm the hashmap of configurations
	 */
	public ConfigSave(HashMap<Integer, String> hm) {
		configs = new HashMap<>();
		configs.putAll(hm);
	}

	
	/**
	 * getConfigs, getter for the configurations. 
	 * 
	 * @return a map of configs
	 */
	public HashMap<Integer, String> getConfigs() {
		return configs;
	}

	
	/**
	 * setConfigs, setter for the configs. 
	 * 
	 * @param configs a map of configs
	 */
	public void setConfigs(HashMap<Integer, String> configs) {
		this.configs = configs;
	}
}
