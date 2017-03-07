package p012;
/**
 * Clase del hilo encargado de mover una pelota.
 * @author Félix Laguna Teno
 * @author Jesús Carro Tomé
 */
public class BallThread extends Thread{
	/**
	 * Bola de la que se encarga el objeto.
	 */
	private final Ball bola;
	/**
	 * Constructor, recibe una pelota de parámetro.
	 * @param bola
	 */
	public BallThread(Ball bola){
		this.bola=bola;
	}
	/**
	 * Ejecuta la acción.
	 * No hemos decidido que este hilo pinte porque entonces deberíamos
	 * tener una referencia al tablero y habría que comprobar condiciones de
	 * carrera a la hora de pintar.
	 */
	@Override
	public void run(){
		try {
			while (!this.isInterrupted()){
				bola.move();
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			System.out.println(this.getName()+" interrumpido");
		}
	}
}
