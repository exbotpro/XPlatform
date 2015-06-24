package exbot.platform.detector;

import exbot.platform.common.app.AppContext;
import exbot.platform.devices.DeviceDescriptor;
import exbot.platform.devices.DeviceLookupTable;
import exbot.platform.devices.loader.DeviceLoader;

public class ControllAppDetector extends Detector{

	@Override
	protected void watch() {
//		ArrayList<DeviceDescriptor> controllers = DeviceLookupTable.getLookupTable().getControllerDapps();
		DeviceDescriptor desc = DeviceLookupTable.getLookupTable().getDeviceDescriptor("driving");
		DeviceLoader.monitor("dapp", "driving", desc);
		
		System.out.println("Driving " + " D-App has been detected.");
		AppContext context = AppContext.getContext("driving", desc.getType());
		(new Thread(context)).start();
	}
}