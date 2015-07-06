package xplatform.platform.detector;

import xplatform.platform.common.app.AppContext;
import xplatform.platform.devices.DeviceDescriptor;
import xplatform.platform.devices.DeviceLookupTable;
import xplatform.platform.devices.Path;
import xplatform.platform.devices.loader.DeviceLoader;

public class ControllAppDetector extends Detector{

	@Override
	protected void watch() {
//		ArrayList<DeviceDescriptor> controllers = DeviceLookupTable.getLookupTable().getControllerDapps();
		DeviceDescriptor desc = DeviceLookupTable.getLookupTable().getDeviceDescriptor("driving");
		DeviceLoader.monitor(Path.repository, "driving", desc);
		
		System.out.println("Driving " + " D-App has been detected.");
		AppContext context = AppContext.getContext("driving", desc.getType());
		(new Thread(context)).start();
	}
}