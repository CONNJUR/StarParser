package data;

import java.util.Iterator;

import com.google.common.base.Optional;

public class CharStream implements LList<Character> {

	private final String string;
	private final int index;
	
	public CharStream(String string) {
		this.string = string;
		this.index = 0;
	}
	
	private CharStream(String string, int index) {
		this.string = string;
		this.index = index;
	}

	@Override
	public Iterator<Character> iterator() {
		return null; // TODO string.iterator();
	}

	@Override
	public Optional<Character> getHead() {
		if(this.index >= this.string.length()) {
			return Optional.absent();
		} else {
			return Optional.of(this.string.charAt(this.index));
		}
	}

	@Override
	public Optional<LList<Character>> getTail() {
		if(this.index >= this.string.length()) {
			return Optional.absent();
		} else {
			return Optional.of((LList<Character>) new CharStream(this.string, this.index + 1));
		}
	}

	@Override
	public int size() {
		int sSize = this.string.length() - this.index;
		if(sSize < 0) {
			return 0;
		}
		return sSize;
	}
	
	public CharStream drop(int i) {
		return new CharStream(this.string, this.index + i);
	}
	
	@Override
	public String toString() {
		return this.string.substring(this.index);
	}

}
