package p012;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
/**
 * Clase del billar.
 * @author Félix Laguna Teno
 * @author Jesús Carro Tomé
 */
@SuppressWarnings("serial")
public class Billiards extends JFrame {

	public static int Width = 800;
	public static int Height = 600;
	/**
	 * Bandera para indicar si hay procesos ejecutandose o no.
	 */
	private boolean exBall;
	private JButton b_start, b_stop;

	private Board board;
	/**
	 * Lista de hilos.
	 */
	private List<Thread> hilos;
	private final int N_BALL = 13;
	private Ball[] balls;
	/**
	 * Constructor
	 */
	public Billiards() {
		exBall=false;
		board = new Board();
		board.setForeground(new Color(0, 128, 0));
		board.setBackground(new Color(0, 128, 0));

		initBalls();
		/**
		 * Inicializamos el hilo pintor al principio.
		 */
		Thread t=new PainterThread(board);
		t.setName("Pintor");
		t.start();
		b_start = new JButton("Empezar");
		b_start.addActionListener(new StartListener());
		b_stop = new JButton("Parar");
		b_stop.addActionListener(new StopListener());

		JPanel p_Botton = new JPanel();
		p_Botton.setLayout(new FlowLayout());
		p_Botton.add(b_start);
		p_Botton.add(b_stop);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(p_Botton, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setTitle("Práctica programación concurrente objetos móviles independientes");
		setResizable(false);
		setVisible(true);
	}
	/**
	 * Inicializa los objetos pasivos bolas.
	 */
	private void initBalls() {
		balls=new Ball[N_BALL];
		for(int i=0;i < N_BALL;i++){
			Ball bola = new Ball();
			balls[i] = bola;
		}
		board.setBalls(balls);
	}
	
	private class StartListener implements ActionListener {
		/**
		 * Acción al pulsar el botón empezar.
		 * Inicia los hilos solo si no están funcionando.
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!exBall){
				inicializarHilos();
				for (Thread t1: hilos){
					t1.start();
				}
			}
		}
	}
	/**
	 * Crea los hilos necesarios para la ejecución.
	 * También les pone un nombre descriptivo.
	 */
	private synchronized void inicializarHilos(){
		exBall=true;
		int contador=0;
		hilos=new LinkedList<Thread>();
		for (Ball b:balls){
			Thread t=new BallThread(b);
			t.setName("Hilo Bola "+contador);
			hilos.add(t);
			contador++;
		}
	}

	private class StopListener implements ActionListener {
		/**
		 * Acción al pulsar el botón de parar.
		 * Pone la bandera en false, y va interrumpiendo los hilos.
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			exBall=false;
			for (Thread t:hilos){
				t.interrupt();
				//System.out.println(t.isInterrupted()+t.getName());
			}

		}
	}

	public static void main(String[] args) {
		new Billiards();
	}
}