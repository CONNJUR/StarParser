package data;

/**
 * @author mattf
 * 
 * A class for which all instances are identical.  Or, a class
 * with just one instance and no data.  A *real* singleton!
 */
public class Unit {

	private static Unit instance = new Unit();
	
	private Unit() {
		
	}
	
	public static Unit of() {
		return instance;
	}
}
