package xplatform.platform.detector.controller;

import java.util.ArrayList;

import xplatform.platform.common.app.AppContext;
import xplatform.platform.detector.Detector;
import xplatform.platform.devices.DeviceDescriptor;
import xplatform.platform.devices.DeviceLookupTable;

public class ControllAppDetector extends Detector{

	private volatile static ControllAppDetector controllerDetector;
	private ArrayList<String> devices = new ArrayList<String>();
	private String contollAppId;
	
	private ControllAppDetector(){
		this.devices = new ArrayList<String>();
	}
	
	public final String getContollAppId() {
		return contollAppId;
	}

	public static ControllAppDetector getControllerDetector(){
		if(controllerDetector==null)
		{
			synchronized (DeviceLookupTable.class){
				if(controllerDetector==null)
				{
					controllerDetector = new ControllAppDetector();
				}
			}
		}
		
		return controllerDetector;
	}
	
	public void removeDevice(String device){
		this.devices.remove(device);
	}
	
	public void addDevice(String device){
		this.devices.add(device);
		this.watch();
	}
	
	@Override
	protected void watch() {
		DeviceDescriptor desc = getAppDescriptor();
		if(desc==null) return;
		contollAppId = desc.getId();
		System.out.println(desc.getId() + " D-App has been detected.");
		AppContext context = AppContext.getContext(desc.getId(), desc.getType());
		(new Thread(context)).start();
	}

	private DeviceDescriptor getAppDescriptor(){
		ArrayList<DeviceDescriptor> desc = ControllerMap.getTable().getController(devices);
		
		if(desc.size()>0){
			return desc.get(0);
		}
		
		return null;
	}
}