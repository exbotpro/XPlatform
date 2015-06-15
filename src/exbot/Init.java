package exbot;

import exbot.example.driving.Driving;
import exbot.platform.common.app.AppContext;
import exbot.platform.common.app.state.Operator;
import exbot.platform.detector.USBDetector;
import exbot.platform.devices.DeviceLookupTable;
import exbot.platform.devices.OperatorPool;

public class Init {
	public static void main(String[] args){
		new USBDetector();
		Operator driving = new Driving("driving", "DRIVING APP", 1000);
		String type = DeviceLookupTable.getLookupTable().getDeviceDescriptor("driving").getType();
		OperatorPool.getLookupTable().putOperator("driving", driving);
		new Thread(AppContext.getContext(driving.getID(), type, driving)).start();
	}
}