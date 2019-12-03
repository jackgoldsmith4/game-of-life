package gameOfLife;

public class GameOfLifeController implements GameOfLifeViewListener, SpotListener {
	
	private GameOfLifeView view;
	private GameOfLifeModel model;
	
	public GameOfLifeController(GameOfLifeView view, GameOfLifeModel model) {
		this.view = view;
		this.model = model;

		view.addGameOfLifeViewListener(this);
		view.getBoard().addSpotListener(this);
	}
	
	public void handleViewEvent(GameOfLifeViewEvent e) {
		
		switch (e.getEventType()) {
		
		case 'c': // clear event
			model.clear();
			break;
		
		case 'r': // randomize event
			model.randomize();
			break;
			
		case 'a': // advance game event
			model.advanceGame();
			break;
			
		case 't': // threshold change
			ThresholdEvent t = (ThresholdEvent) e;
			int[] thresholds = { t.getLBThreshold(), t.getHBThreshold(), t.getLSThreshold(), t.getHSThreshold() };
			model.updateThresholds(thresholds);
			break;
		}
		
		view.updateView(model.getState()); // pass the new state to the view
	}
	
	public void spotEntered(Spot spot) { }
	
	public void spotExited(Spot spot) { }
	
	public void spotClicked(Spot spot) {
		spot.toggleSpot();
		
		model.togglePoint(spot.getSpotX(), spot.getSpotY());
	}
}
