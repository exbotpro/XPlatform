package xplatform.platform.detector.controller;

import java.util.ArrayList;

import xplatform.platform.devices.DeviceDescriptor;
import xplatform.platform.devices.Path;
import xplatform.util.xml.XMLHandler;

public class ControllerMap {

	private volatile static ControllerMap table;

	private XMLHandler xmlHanlder;

	private ControllerMap(){

		xmlHanlder = new XMLHandler(Path.controllerTablePath);
	}
	
	public static ControllerMap getTable(){
		if(table==null)
		{
			synchronized (ControllerMap.class){
				if(table==null)
				{
					table = new ControllerMap();
				}
			}
		}
		
		return table;
	}
	
	public ArrayList<DeviceDescriptor> getController(ArrayList<String> devices){
		ArrayList<DeviceDescriptor> descList = xmlHanlder.getOperatorDescriptors("Controller", devices);
		return descList;
	}
}
