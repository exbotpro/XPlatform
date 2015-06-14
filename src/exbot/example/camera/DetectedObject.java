package exbot.example.camera;

public class DetectedObject {
		
	private double x;
	private double y;
	
	public DetectedObject(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
