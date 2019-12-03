package gameOfLife;

import java.util.Random;

public class GameOfLifeModel {
	
	// default number of neighbors for birth and survival of cells
	private final int DEFAULT_LOW_BIRTH_THRESHOLD = 3;
	private final int DEFAULT_HIGH_BIRTH_THRESHOLD = 3;
	private final int DEFAULT_LOW_SURVIVE_THRESHOLD = 2;
	private final int DEFAULT_HIGH_SURVIVE_THRESHOLD = 3;
	
	// the percent of spots that will be filled (out of the entire grid)
	private final double DEFAULT_PERCENT_RANDOM_FILLED = 0.75;
	
	private int dimensions;
	private boolean[][] pointsArray;
	
	private int[] thresholds;
	
	public GameOfLifeModel(int dimensions) {
		this.dimensions = dimensions;
		this.pointsArray = new boolean[this.dimensions][this.dimensions];
		
		int[] ts = { DEFAULT_LOW_BIRTH_THRESHOLD, DEFAULT_HIGH_BIRTH_THRESHOLD, 
				DEFAULT_LOW_SURVIVE_THRESHOLD, DEFAULT_HIGH_SURVIVE_THRESHOLD };
		this.thresholds = ts;
	}
	
	public boolean[][] getState() {
		return pointsArray;
	}
	
	/* methods to update the state of the model:
	 * -- clear the board
	 * -- randomize the board
	 * -- advance the game by one "generation"
	 */
	public void clear() {
		pointsArray = new boolean[this.dimensions][this.dimensions];
	}
	
	public void randomize() {
		this.clear();
		
		Random r = new Random();
		for (int i=0; i<dimensions*dimensions*DEFAULT_PERCENT_RANDOM_FILLED; i++) {
			pointsArray[r.nextInt(dimensions)][r.nextInt(dimensions)] = true;
		}
	}
	
	public void advanceGame() {
		boolean[][] tempCoords = new boolean[dimensions][dimensions];
		
		for (int i=0; i<dimensions; i++) {
			for (int j=0; j<dimensions; j++) {
				int numLiveNeighbors = getLiveNeighbors(i, j);
				
				if (pointsArray[i][j]) {
					if ((numLiveNeighbors >= thresholds[2]
							&& numLiveNeighbors <= thresholds[3])) {
						tempCoords[i][j] = true;
					}
				} else {
					if ((numLiveNeighbors >= thresholds[0]
							&& numLiveNeighbors <= thresholds[1])) {
						tempCoords[i][j] = true;
					}
				}
			}
		}
		
		pointsArray = tempCoords;
	}
	
	public void updateThresholds(int[] newThresholds) {
		for (int i=0; i<newThresholds.length; i++) {
			if (newThresholds[i] != 0) {
				thresholds[i] = newThresholds[i];
			}
		}
		
		/* swap upper and lower bounds if lower is greater than upper */
		if (thresholds[0] > thresholds[1]) {
			int temp = thresholds[0];
			thresholds[0] = thresholds[1];
			thresholds[1] = temp;
		}
		if (thresholds[2] > thresholds[3]) {
			int temp = thresholds[2];
			thresholds[2] = thresholds[3];
			thresholds[3] = temp;
		}
	}
	
	public void togglePoint(int x, int y) {
		if (pointsArray[x][y]) {
			pointsArray[x][y] = false;
		} else {
			pointsArray[x][y] = true;
		}
	}
	
	// helper method: given coordinates of a point, returns its number of live neighbors
	private int getLiveNeighbors(int x, int y) {
		int numLiveNeighbors = 0;
		
		// check in each direction
		if (x-1 >= 0) {
			if (y-1 >= 0 && pointsArray[x-1][y-1]) {
				numLiveNeighbors++;
			}
			if (pointsArray[x-1][y]) {
				numLiveNeighbors++;
			} 
			if (y+1 < dimensions && pointsArray[x-1][y+1]) {
				numLiveNeighbors++;
			}
		}
		
		if (x+1 < dimensions) {
			if (y-1 >= 0 && pointsArray[x+1][y-1]) {
				numLiveNeighbors++;
			}
			if (pointsArray[x+1][y]) {
				numLiveNeighbors++;
			}
			if (y+1 < dimensions && pointsArray[x+1][y+1]) {
				numLiveNeighbors++;
			}
		}
		
		if (y+1 < dimensions && pointsArray[x][y+1]) {
			numLiveNeighbors++;
		}
		if (y-1 >= 0 && pointsArray[x][y-1]) {
			numLiveNeighbors++;
		}
		
		
		return numLiveNeighbors;
	}
}
