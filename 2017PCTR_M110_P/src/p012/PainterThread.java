package p012;


public class PainterThread extends Thread {
	private final Board b;
	public PainterThread(Board b){
		this.b=b;
	}
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