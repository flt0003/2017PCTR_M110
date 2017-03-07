package p012;
/**
 * Clase del hilo encargado de pintar.
 * @author Félix Laguna Teno
 * @author Jesús Carro Tomé
 */
public class PainterThread extends Thread {
	/**
	 * Tablero.
	 */
	private final Board b;
	/**
	 * Constructor, recibe un tablero.
	 * @param b
	 */
	public PainterThread(Board b){
		this.b=b;
	}
	/**
	 * Ejecuta la acción de pintar.
	 * Se encarga de llamar a repaint sobre el tablero, que
	 * pone a la cola del proceso encargado de la gestión gráfica
	 * un paint del tablero.
	 */
	@Override
	public void run(){
		try {
			while(!this.isInterrupted()){
				b.repaint();
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			System.out.println(this.getName()+" interrumpido");
		}
	}
}
