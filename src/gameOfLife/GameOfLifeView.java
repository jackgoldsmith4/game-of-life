package gameOfLife;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameOfLifeView extends JPanel implements ActionListener {
	
	private JSpotBoard board;
	private int dimensions;
	private List<GameOfLifeViewListener> listeners;
	
	public GameOfLifeView(int dimensions) {
		
		this.dimensions = dimensions;
		this.board = new JSpotBoard(this.dimensions, this.dimensions);
		
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
		
		JButton clearButton = new JButton("Clear");	
		JButton randomButton = new JButton("Randomize");
		JButton advanceButton = new JButton("Advance Game");
		
		JPanel settingsPanel = new JPanel();

		settingsPanel.add(clearButton);
		settingsPanel.add(randomButton);
		settingsPanel.add(advanceButton);
		
		add(settingsPanel, BorderLayout.SOUTH);
		
		for(Component c: settingsPanel.getComponents()) {
			JButton b = (JButton) c;
			b.addActionListener(this);
		}
		
		listeners = new ArrayList<GameOfLifeViewListener>();
	}
	
	public JSpotBoard getBoard() {
		return this.board;
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String text = button.getText();
						
		if (text.contentEquals("Clear")){
			fireEvent(new ClearEvent());
			
		} else if (text.contentEquals("Randomize")) {
			fireEvent(new RandomizeEvent());
			
		} else { // text = Advance Game
			fireEvent(new AdvanceGameEvent());
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
