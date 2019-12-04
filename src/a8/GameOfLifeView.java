package gameOfLife;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameOfLifeView extends JPanel implements ActionListener {
	
	// default number of neighbors for birth and survival of cells
	private final int DEFAULT_LOW_BIRTH_THRESHOLD = 3;
	private final int DEFAULT_HIGH_BIRTH_THRESHOLD = 3;
	private final int DEFAULT_LOW_SURVIVE_THRESHOLD = 2;
	private final int DEFAULT_HIGH_SURVIVE_THRESHOLD = 3;
	
	private JSpotBoard board;
	private int dimensions;
	private List<GameOfLifeViewListener> listeners;
	
	public GameOfLifeView(int dimensions) {
		
		this.dimensions = dimensions;
		this.board = new JSpotBoard(this.dimensions, this.dimensions);
		
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
		
		/* settings panel: contains clear, randomize, and advance buttons */
		JPanel settingsPanel = new JPanel();
		
		JButton dimensionButton = new JButton("Change Dimension");
		JButton clearButton = new JButton("Clear");	
		JButton randomButton = new JButton("Randomize");
		JButton advanceButton = new JButton("Advance Game");

		settingsPanel.add(dimensionButton);
		settingsPanel.add(clearButton);
		settingsPanel.add(randomButton);
		settingsPanel.add(advanceButton);
		
		add(settingsPanel, BorderLayout.SOUTH);
		
		for(Component c: settingsPanel.getComponents()) {
			JButton b = (JButton) c;
			b.addActionListener(this);
		}
		
		/* threshold panel: contains fields for new birth and survival thresholds to be entered */
		JPanel thresholdPanel = new JPanel();
		
		JLabel lowBirth = new JLabel("Low birth threshold:");
		thresholdPanel.add(lowBirth);
		JTextField lowBirthText = new JTextField("" + DEFAULT_LOW_BIRTH_THRESHOLD);
		lowBirthText.setName("lb");
		thresholdPanel.add(lowBirthText);
		
		JLabel highBirth = new JLabel("High birth threshold:");
		thresholdPanel.add(highBirth);
		JTextField highBirthText = new JTextField("" + DEFAULT_HIGH_BIRTH_THRESHOLD);
		highBirthText.setName("hb");
		thresholdPanel.add(highBirthText);
		
		JLabel lowSurvive = new JLabel("Low survive threshold:");
		thresholdPanel.add(lowSurvive);
		JTextField lowSurviveText = new JTextField("" + DEFAULT_LOW_SURVIVE_THRESHOLD);
		lowSurviveText.setName("ls");
		thresholdPanel.add(lowSurviveText);
		
		JLabel highSurvive = new JLabel("High survive threshold:");
		thresholdPanel.add(highSurvive);
		JTextField highSurviveText = new JTextField("" + DEFAULT_HIGH_SURVIVE_THRESHOLD);
		highSurviveText.setName("hs");
		thresholdPanel.add(highSurviveText);
		
		add(thresholdPanel, BorderLayout.NORTH);
		
		for(Component c: thresholdPanel.getComponents()) {
			if (c.getClass() != lowBirth.getClass()) {
				JTextField f = (JTextField) c;
				f.addActionListener(this);
			}
		}
		
		listeners = new ArrayList<GameOfLifeViewListener>();
	}
	
	public JSpotBoard getBoard() {
		return this.board;
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.getClass().equals((new JButton()).getClass())) {
			JButton button = (JButton) source;
			String text = button.getText();
							
			if (text.contentEquals("Change Dimension")){
				int newDimensions;
				while (true) {
					try {
						newDimensions = 
								Integer.parseInt(JOptionPane.showInputDialog("Enter new dimension:"));

						if (newDimensions < 10 || newDimensions > 100) {
							throw new NumberFormatException();
						}

						break;
					} catch (NumberFormatException exception) {
						continue;
					}
				}
				
				fireEvent(new DimensionEvent(newDimensions));
			} else if (text.contentEquals("Clear")){
				fireEvent(new ClearEvent());
				
			} else if (text.contentEquals("Randomize")) {
				fireEvent(new RandomizeEvent());
				
			} else { // text = Advance Game
				fireEvent(new AdvanceGameEvent());
			}	
		} else { // must be a threshold JTextField
			JTextField textField = (JTextField) source;
			int threshold;
			
			try {
				threshold = Integer.parseInt(textField.getText());
				
				if (threshold < 0 || threshold > 8) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Threshold must be a number between 0 and 8.", "Invalid input", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// determine which threshold was edited
			if (textField.getName().contentEquals("lb")) {
				fireEvent(new ThresholdEvent(threshold, -1, -1, -1));
			} else if (textField.getName().contentEquals("hb")) {
				fireEvent(new ThresholdEvent(-1, threshold, -1, -1));
			} else if (textField.getName().contentEquals("ls")) {
				fireEvent(new ThresholdEvent(-1, -1, threshold, -1));
			} else { // "hs"
				fireEvent(new ThresholdEvent(-1, -1, -1, threshold));
			}
		}
	}
	
	public void addGameOfLifeViewListener(GameOfLifeViewListener l) {
		listeners.add(l);
	}
	
	public void removeGameOfLifeViewListener(GameOfLifeViewListener l) {
		listeners.remove(l);
	}
	
	public void fireEvent(GameOfLifeViewEvent e) {
		for (GameOfLifeViewListener l : listeners) {
			l.handleViewEvent(e);
		}
	}
	
	public void updateView(boolean[][] points) {
		if (points.length != dimensions) {
			// first, change dimensions variable to reflect new points array dimension
			dimensions = points.length;
			
			// then create a new board and revalidate the component so it shows us
			remove(board);
			board = new JSpotBoard(points.length, points[0].length);
			add(board, BorderLayout.CENTER);
			
			revalidate();
		}
		
		for (int i=0; i<points.length; i++) {
			for (int j=0; j<points[0].length; j++) {
				if (points[i][j]) {
					board.getSpotAt(i, j).setSpot();
				} else {
					board.getSpotAt(i, j).clearSpot();
				}
			}
		}
		
		repaint();
	}
}
