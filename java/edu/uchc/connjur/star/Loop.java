package edu.uchc.connjur.star;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mattf
 *
 */
public class Loop {
	
	public final List<Map<String, String>> rows;
	
	public Loop(List<String> identifiers, List<String> values) {
		this.rows = new ArrayList<Map<String,String>>();
		int identSize = identifiers.size();
		// TODO check that the identifiers are unique
		int valIndex = 0;
		// don't want to loop endlessly if there's no keys
		if(identSize == 0 && values.size() > 0) {
			throw new RuntimeException("Star Loop with > 0 values must have > 0 identifiers");
		}
		while(true) {
			// takes care of the case that the values are empty
			if(valIndex == values.size()) {
				break;
			} else if (values.size() - valIndex < identSize) {
				throw new RuntimeException("Star Loop must have a number of values that's an integer multiple of the number of identifiers");
			}
			Map<String, String> row = new HashMap<String, String>();
			for(int j = 0; j < identSize; j++, valIndex++) {
				row.put(identifiers.get(j), values.get(j));
			}
			this.rows.add(row);
		}
	}
}
