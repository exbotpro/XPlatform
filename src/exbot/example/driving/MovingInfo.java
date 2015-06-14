package exbot.example.driving;

public class MovingInfo {

	private double x;
	private double y;
	
	public MovingInfo(double x, double y){
		this.x = x;
		this.y = y;
	}

	public final double getVelocity() {
		return x;
	}

	public final double getSteering() {
		return y;
	}
	
	
}
