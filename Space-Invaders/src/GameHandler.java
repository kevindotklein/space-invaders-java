import java.awt.Canvas;
import java.awt.Color;
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

	public GameHandler() {
		
		ship = new Ship();
		lasers = new ArrayList<Laser>();
		aliens = new ArrayList<Alien>();
		
		for(int i=0; i<30; i++) {
			aliens.add(new Alien(30 + i%10 * 80, 30 + i/10 * 80, 1));
		}
		
		@SuppressWarnings("unused")
		Thread thread = new Thread(this);
	}
	
	public void update() {
		ship.update();
		for(int i=0; i<lasers.size(); i++) {
			lasers.get(i).update();
			if(lasers.get(i).getY() <= 0) {
				lasers.remove(i);
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
		
		ship.render(g);
		for(int i=0; i<lasers.size(); i++) {
			lasers.get(i).render(g);
		}
		for(int j=0; j<aliens.size(); j++) {
			aliens.get(j).render(g);
		}
		
		g.dispose();
		bs.show();
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
		}
	}

}
