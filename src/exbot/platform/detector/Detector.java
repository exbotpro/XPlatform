package exbot.platform.detector;

public abstract class Detector implements Runnable{

	public Detector(){
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		this.watch();
	}
	
	protected abstract void watch();

}
