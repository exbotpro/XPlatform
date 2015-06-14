package exbot.example.driving;

import java.util.ArrayList;
import java.util.Random;

import exbot.example.camera.DetectedObject;
import exbot.platform.common.app.state.Operator;
import exbot.platform.common.data.DataContainer;

public class Driving extends Operator{

	public Driving(String id, int interval) {
		super(id, interval);
		super.setSubscribeFrom(this.getPublishingDevice());
	}
	
	private ArrayList<String> getPublishingDevice(){
		ArrayList<String> publishingDevice = new ArrayList<String>();
		publishingDevice.add("b1ac:f000");
		return publishingDevice;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected DataContainer operate(ArrayList<DataContainer> recievedData) {
		
		String message = "Using ";
		for(DataContainer dc : recievedData){
			ArrayList<DetectedObject> objs = (ArrayList<DetectedObject>)dc.getDataList();
			for(DetectedObject o: objs) message += o.getX() + "," + o.getY();
		}
		
		
		DataContainer data = new MovingInfoContainer(super.ID);
		MovingInfo mv = getObjectLocation();
		message += ", the desired position is calculated as " + mv.getVelocity() + ", " + mv.getSteering() ;
		System.out.println(message + "-->");
		
		data.set(mv);
		return data;
	}
	
	private MovingInfo getObjectLocation(){
		return new MovingInfo(new Random().nextDouble(), new Random().nextDouble());
	}
}
