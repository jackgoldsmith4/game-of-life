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
	
	public boolean isThresholdEvent() {
		return true;
	}
	
	public abstract char getEventType();
}

class DimensionEvent extends GameOfLifeViewEvent {
	
	private int dimensions;
	
	public DimensionEvent(int dimensions) {
		if (dimensions < 10 || dimensions > 100) {
			throw new IllegalArgumentException("Invalid board dimension");
		}

		this.dimensions = dimensions;
	}
	
	public int getDimensions() {
		return this.dimensions;
	}
	
	@Override
	public boolean isDimensionEvent() {
		return true;
	}
	
	public char getEventType() {
		return 'd';
	}
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

class ThresholdEvent extends GameOfLifeViewEvent {
	
	private int lowBirthThreshold;
	private int highBirthThreshold;
	private int lowSurviveThreshold;
	private int highSurviveThreshold;
	
	public ThresholdEvent(int lb, int hb, int ls, int hs) {
		
		this.lowBirthThreshold = lb;
		this.highBirthThreshold = hb;
		this.lowSurviveThreshold = ls;
		this.highSurviveThreshold = hs;
	}
	
	@Override
	public boolean isThresholdEvent() {
		return true;
	}
	
	public int getLBThreshold() {
		return this.lowBirthThreshold;
	}
	
	public int getHBThreshold() {
		return this.highBirthThreshold;
	}
	
	public int getLSThreshold() {
		return this.lowSurviveThreshold;
	}
	
	public int getHSThreshold() {
		return this.highSurviveThreshold;
	}
	
	public char getEventType() {
		return 't';
	}
}
