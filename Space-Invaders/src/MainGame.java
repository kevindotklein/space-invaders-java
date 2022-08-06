import java.awt.Dimension;

import javax.swing.JFrame;

public class MainGame {

	static final int WIDTH = 960;
	static final int HEIGHT = 720;

	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Space Invaders");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setLocationRelativeTo(null);
		GameHandler gameHandler = new GameHandler();
		frame.add(gameHandler);
		frame.addKeyListener(gameHandler);
		
		frame.setVisible(true);
		gameHandler.run();
	}

}
