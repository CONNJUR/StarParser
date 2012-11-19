package edu.uchc.connjur.star;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Tuple2;

/**
 * @author mattf
 *
 * An NMR-Star save frame consists of:
 * - a bunch of key-value pairs, AKA datums
 * - a bunch of loops
 * 
 * Question:  should the keys be unique?  Answer:  I don't know
 *   what it's like in the actual Star format, but I think it'll
 *   be a good thing to enforce.
 *   
 */
public class SaveFrame {
	
	public final Map<String, String> datums;
	public final List<Loop> loops;
	
	public SaveFrame(List<Tuple2<String, String>> datums, List<Loop> loops) {
		this.datums = new HashMap<String, String>();
		for(Tuple2<String, String> pair : datums) {
			this.datums.put(pair.first, pair.second);
		}
		this.loops = loops;
	}

}
