package gameOfLife;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameOfLife {

	public static void main(String[] args) {
		
		int dimensions;
		
		// take dimensions input from the user
		while (true) {
			try {
				String dimInput = JOptionPane.showInputDialog("Board Dimensions (10 to 100):" + 
						"\n(after 100 the cells get too small to see)");
				dimensions = Integer.parseInt(dimInput);
				
				if (dimensions < 10 || dimensions > 100) {
					continue;
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				continue;
			}
		}
		
		GameOfLifeView view = new GameOfLifeView(dimensions);
		GameOfLifeModel model = new GameOfLifeModel(dimensions);
		GameOfLifeController controller = new GameOfLifeController(view, model);
		
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("Conway's Game of Life");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setContentPane(view);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
