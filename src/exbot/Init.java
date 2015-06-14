package exbot;

import exbot.example.driving.Driving;
import exbot.platform.common.app.AppContext;
import exbot.platform.common.app.state.Operator;
import exbot.platform.detector.USBDetector;

public class Init {
	public static void main(String[] args){
		new USBDetector();
		Operator driving = new Driving("driving", 1000);
//		ThreadPoolForOperators.getLookupTable().putThread("driving", driving);
		new AppContext(driving.getID(), driving).run();
		
		while(true){}
	}
}