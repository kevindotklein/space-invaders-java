import java.awt.Color;
import java.awt.Graphics;

public class Laser {
	private int x, y, velocity;
	public Laser(int x0, int y0) {
		this.x = x0;
		this.y = y0;
		this.velocity = 10;
	}
	
	public void update() {
		y -= velocity;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 10, 20);
	}
	
	public boolean collide(Alien alien) {
		if(x + 10 >= alien.getX() && x <= alien.getX() + alien.getWidth()) {
			if(y <= alien.getY() + alien.getHeight()) {
				return true;
			}
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	
	
}
