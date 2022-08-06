import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Ship {
	private int x, y, width, height, direction=0, velocity, delay;
	private boolean laserGun;
	
	public Ship() {
		this.width = 30;
		this.height = 30;
		this.x = MainGame.WIDTH/2 - 15;
		this.y = MainGame.HEIGHT - 90;
		this.velocity = 6;
		this.laserGun = true;
		this.delay =0;
	}
	
	public void update() {
		if(direction == 1) {
			x+=velocity;
		}else if(direction == -1) {
			x-=velocity;
		}
		
		if(delay >= 50) {
			laserGun = true;
			delay = 0;
		}
		
		delay++;
		
		if(x <= 0) {
			x=0;
		}else if(x >= MainGame.WIDTH - 45) {
			x=MainGame.WIDTH - 45;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
	}
	
	public Laser shoot() {
		this.laserGun = false;
		Laser laser = new Laser((x + width/2) - 5, y);
		return laser;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public boolean isLaserGun() {
		return laserGun;
	}

	public void setLaserGun(boolean laserGun) {
		this.laserGun = laserGun;
	}
	
	
	
}
