package gameOfLife;

abstract public class GameOfLifeViewEvent {
	
	public boolean isDimensionEvent() {
		return false;
	}

	public boolean isClearEvent() {
		return false;
	}
	
	public boolean isRandomizeEvent() {
		return false;
	}
	
	public boolean isAdvanceGameEvent() {
		return false;
	}
	
	public abstract char getEventType();
}

class ClearEvent extends GameOfLifeViewEvent {
	
	public ClearEvent() { }
		
	@Override
	public boolean isClearEvent() {
		return true;
	}
	
	public char getEventType() {
		return 'c';
	}
}

class RandomizeEvent extends GameOfLifeViewEvent {
	
	public RandomizeEvent() { }
	
	@Override
	public boolean isRandomizeEvent() {
		return true;
	}
	
	public char getEventType() {
		return 'r';
	}
}

class AdvanceGameEvent extends GameOfLifeViewEvent {
	
	public AdvanceGameEvent() { }
	
	@Override
	public boolean isAdvanceGameEvent() {
		return true;
	}
	
	public char getEventType() {
		return 'a';
	}
}
