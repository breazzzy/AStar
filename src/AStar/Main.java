package AStar;

import UTIL.GameFrame;
import UTIL.GamePanel;

public class Main {
	
	public static final String TITLE = "A* Search Algorithm";
	
	public static void main(String args[]) {
		GameFrame game = new GameFrame();
		game.setLayout(null);
		GamePanel pane = new AStarPanel(800, 600);
		game.setTitle(TITLE);
		game.setContentPane(pane);
		game.getContentPane().requestFocusInWindow();
		game.pack();
	}
}
