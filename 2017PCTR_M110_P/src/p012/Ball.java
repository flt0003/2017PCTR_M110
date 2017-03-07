package p012;

import java.awt.Image;
import javax.swing.ImageIcon;
//TODO Transform the code to be used safely in a concurrent context.  
public class Ball {
	private String Ball = "../../media/Ball.png"; 

	private double x,y,dx,dy;
	private double v,fi;
	private Image image;

	public Ball() {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(Ball));
		image = ii.getImage();
		x = Billiards.Width/4-16;
		y = Billiards.Height/2-16;
		v = 5;
		fi =  Math.random() * Math.PI * 2;
		if (!invariantes()){
			System.out.println("Violaciï¿½n del invariante en la construcciï¿½n");
			System.out.println("Usando valores por defecto");
			x = Billiards.Width/4-16;
			y = Billiards.Height/2-16;
			v = 5;
			fi =  Math.random() * Math.PI * 2;
		}
	}
	public synchronized void move() {
		invariantes();
		reflect();
		v = v*Math.exp(-v/1000);
		dx = v*Math.cos(fi);
		dy = v*Math.sin(fi);
		if (Math.abs(dx) < 1 && Math.abs(dy) < 1) {
			dx = 0;
			dy = 0;
		}
		x += dx;   
		y += dy;
		invariantes();
		
	}
	private synchronized boolean invariantes(){
		boolean res=false;
		res=(x>=Board.LEFTBOARD && x+110<Board.RIGHTBOARD &&
				y>=Board.TOPBOARD && y+110<Board.BOTTOMBOARD);
		assert res;
		return res;
	}
	public synchronized void reflect() {
		if (Math.abs(x  +110- Board.RIGHTBOARD) <  Math.abs(dx)) {
			fi = Math.PI - fi;
		}
		if (Math.abs(y +110 - Board.BOTTOMBOARD) <  Math.abs(dy)) {
			fi = - fi;
		}
		if (Math.abs(x - Board.LEFTBOARD) <  Math.abs(dx)) {
			fi = Math.PI - fi;
		}
		if (Math.abs(y - Board.TOPBOARD) <  Math.abs(dy)) {
			fi = - fi;
		}
	}

	public synchronized int getX() {
		return (int)x;
	}
	
	public synchronized int getY() {
		return (int)y;
	}
	
	public double getFi() {
		return fi;
	}

	public double getdr() {
		return Math.sqrt(dx*dx+dy*dy);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Image getImage() {
		return image;
	}

}



