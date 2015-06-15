package exbot.example.wheel;

import java.util.ArrayList;

import exbot.example.driving.MovingInfo;
import exbot.platform.common.app.state.Operator;
import exbot.platform.common.data.DataContainer;

public class Wheel extends Operator {

	public Wheel(String id, String type) {
		super(id, type, 1000);
		super.setSubscribeFrom(this.getPublishingDevice());
	}
	
	private ArrayList<String> getPublishingDevice(){
		ArrayList<String> publishingDevice = new ArrayList<String>();
		publishingDevice.add("driving");
		return publishingDevice;
	}
	
	@Override
	protected DataContainer operate(ArrayList<DataContainer> recievedData) {
		for(DataContainer dc : recievedData)
			System.out.println(" Wheels have been run towards: " + 
						((MovingInfo)dc.getData()).getVelocity() + ", " + ((MovingInfo)dc.getData()).getSteering());
		
		System.out.println();
		return null;
	}
}