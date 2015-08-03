package xplatform;

import xplatform.platform.detector.USBDetector;

public class Init {
	public static void main(String[] args){
		new USBDetector();
		while(true){}
	}
	
}