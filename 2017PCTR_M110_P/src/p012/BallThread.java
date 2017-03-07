package p012;



public class BallThread extends Thread{
	private final Ball bola;
	public BallThread(Ball bola){
		this.bola=bola;
	}
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

