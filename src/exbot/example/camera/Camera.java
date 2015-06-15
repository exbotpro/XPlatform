package exbot.example.camera;

import java.util.ArrayList;
import java.util.Random;

import exbot.platform.common.app.state.Operator;
import exbot.platform.common.data.DataContainer;

public class Camera extends Operator{

	public Camera(String id, String type){
		super(id, type, 1000);
	}
	
	@Override
	protected DataContainer operate(ArrayList<DataContainer> recievedData) {
		DetectedObjectContainer data = new DetectedObjectContainer(super.ID);
		ArrayList<DetectedObject> objs = getObjectLocation();
		String message = "Detected Object :";
		for(DetectedObject o: objs) message += o.getX() + "," + o.getY();
		message += "  -->";
		System.out.println(message);
		data.set(objs);
		
		return data;
	}
	
	private ArrayList<DetectedObject> getObjectLocation(){
		ArrayList<DetectedObject> objLocation = new ArrayList<DetectedObject>();
		for(int i = 0; i < 2; i++)
			objLocation.add(new DetectedObject(new Random().nextDouble(), new Random().nextDouble()));
		
		return objLocation;
	}
}