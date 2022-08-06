import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class GameHandler extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	private Ship ship;
	private ArrayList<Laser> lasers;
	private ArrayList<Alien> aliens;

	private Font font = new Font("Serif", Font.BOLD, 16);
	private int score;
	private boolean gameOver = false;

	public GameHandler() {
		
		ship = new Ship();
		lasers = new ArrayList<Laser>();
		aliens = new ArrayList<Alien>();
		
		score = 0;
		
		for(int i=0; i<30; i++) {
			aliens.add(new Alien(30 + i%10 * 80, 30 + i/10 * 80, 1));
		}
		
		@SuppressWarnings("unused")
		Thread thread = new Thread(this);
	}
	
	public void update() {
		if(!gameOver) {
			ship.update();
			for(int i=0; i<lasers.size(); i++) {
				lasers.get(i).update();
				if(lasers.get(i).getY() <= 0) {
					lasers.remove(i);
				}else {
					for(int j=0; j<aliens.size(); j++) {
						if(lasers.get(i).collide(aliens.get(j))) {
							aliens.remove(j);
							lasers.remove(i);
							score++;
							break;
						}
					}
				}
			}
			
			for(int i=0; i<aliens.size(); i++) {
				aliens.get(i).update();
				if(aliens.get(i).getX() == 0 || aliens.get(i).getX() >= MainGame.WIDTH - 60) {
					for(int j=0; j<aliens.size();j++) {
						aliens.get(j).toggleDirection();
					}
					break;
				}
				if(aliens.get(i).getX() >= ship.getX() && aliens.get(i).getX() <= ship.getX()+45) {
					if(aliens.get(i).getY()+aliens.get(i).getHeight() >= ship.getY()) {
						this.setGameOver(true);
					}
				}
				
			}
		
		}
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, MainGame.WIDTH, MainGame.HEIGHT);
		
		if(!gameOver) {
			ship.render(g);
			for(int i=0; i<lasers.size(); i++) {
				lasers.get(i).render(g);
			}
			for(int j=0; j<aliens.size(); j++) {
				aliens.get(j).render(g);
			}
			
			g.setColor(Color.white);
			g.setFont(font);
			g.drawString("SCORE: "+score, 30, 20);
		}else {
			g.setColor(Color.white);
			g.setFont(font);
			g.drawString("YOU LOSE, PRESS 'R' TO RESTART", MainGame.WIDTH/2 - 130, MainGame.HEIGHT/2 - 12);
		}
		
		g.dispose();
		bs.show();
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	@Override
	public void run() {
		while(true) {
			update();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_RIGHT:
				ship.setDirection(1);
				break;
			case KeyEvent.VK_LEFT:
				ship.setDirection(-1);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				if(ship.getDirection() == 1) {
					ship.setDirection(0);
					break;
				}
				break;
			case KeyEvent.VK_LEFT:
				if(ship.getDirection() == -1) {
					ship.setDirection(0);
					break;
				}
				break;			
			case KeyEvent.VK_SPACE:
				if(ship.isLaserGun() == true) {
					lasers.add(ship.shoot());
					break;
				}
				break;
			case KeyEvent.VK_R:
				if(gameOver == true) {
					score = 0;
					aliens.removeAll(aliens);
					for(int i=0; i<30; i++) {
						aliens.add(new Alien(30 + i%10 * 80, 30 + i/10 * 80, 1));
					}
					this.setGameOver(false);
					
				}
				break;
		}
	}

}
