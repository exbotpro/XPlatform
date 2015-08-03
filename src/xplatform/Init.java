package xplatform;

import xplatform.platform.detector.USBDetector;

public class Init {
	public static void main(String[] args){
		new USBDetector();
		while(true){}
//		Operator driving = new Driving("driving", "DRIVING APP", 1100);
//		String type = DeviceLookupTable.getLookupTable().getDeviceDescriptor("driving").getType();
//		OperatorPool.getLookupTable().putOperator("driving", driving);
//		new Thread(AppContext.getContext(driving.getID(), type, driving)).start();
	}
	
}