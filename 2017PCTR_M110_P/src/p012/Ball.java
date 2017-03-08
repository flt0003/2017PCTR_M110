package p012;

import java.awt.Image;
import javax.swing.ImageIcon; 
/**
 * Clase que representa una bola.
 * Esta clase representa un objeto pasivo que luego
 * ha de ser modificado por los hilos. En nuestro caso cada
 * hilo solo se encarga de un objeto, pero para evitar que el pintor acceda
 * a información desactualizada aplicaremos concurrencia.
 * @author Félix Laguna Teno
 * @author Jesús Carro Tomé
 */
public class Ball {
	/**
	 * Cadena de localización de la imagen, media tiene que ser un source folder.
	 */
	private String Ball = "media/Ball.png"; 
	/**
	 * Variables para la posición.
	 */
	private double x,y,dx,dy;
	/**
	 * Variables para la velocidad.
	 */
	private double v,fi;
	/**
	 * Referencia de la imagen.
	 */
	private Image image;
	/**
	 * Constructor.
	 */
	public Ball() {
		java.net.URL imageURL = Ball.class.getResource(Ball);
		
		//System.out.println(imageURL.toString());
		ImageIcon ii = new ImageIcon(imageURL);
		//System.out.println(ii.toString());
		image = ii.getImage();
		x = Billiards.Width/4-16;
		y = Billiards.Height/2-16;
		v = 5;
		fi =  Math.random() * Math.PI * 2;
		//Si las condiciones del invariante no se cumple se ponen acaba
		//el programa.
		invariantes();
	}
	/**
	 * Función para ejecutar el movimiento, también incluye reflect.
	 * Se comprueban los invariantes al principio al final para no encontrarse
	 * problemas. Es sincronizado para que todos los cambios se realicen seguidos.
	 */
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
	/**
	 * Función para comprobar el invariante.
	 * Se hace con asertos ya que es algo que no tiene
	 * que pasar bajo ningún concepto.
	 * @return
	 */
	private synchronized void invariantes(){
		boolean res=false;
		res=(x>=Board.LEFTBOARD && x+110<Board.RIGHTBOARD &&
				y>=Board.TOPBOARD && y+110<Board.BOTTOMBOARD);
		assert res;
	}
	/**
	 * Función para realizar la reflexión.
	 * Comprobamos el invariante por si se llama a la función
	 * sin llamar a move. Es synchronized ya que se realizan cambios y
	 * se acceden a variables.
	 */
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
	/**
	 * Obtiene la x.
	 * Es sincronizado ya que se accede desde otros métodos y queremos que
	 * no tenga información inconsistente.
	 * @return int x
	 */
	public synchronized int getX() {
		return (int)x;
	}
	/**
	 * Obtiene la y.
	 * Es sincronizado ya que se accede desde otros métodos y queremos que
	 * no tenga información inconsistente.
	 * @return int y
	 */
	public synchronized int getY() {
		return (int)y;
	}
	/**
	 * Funciones comentadas por que no las hemos usado.
	 */
//	public double getFi() {
//		return fi;
//	}
//
//	public double getdr() {
//		return Math.sqrt(dx*dx+dy*dy);
//	}
//
//	public void setX(double x) {
//		this.x = x;
//	}
//
//	public void setY(double y) {
//		this.y = y;
//	}
//
	/**
	 * Obtiene la imagen.
	 * No es sincronizado porque no se modifica.
	 * @return Image image
	 */
	public Image getImage() {
		return image;
	}

}

