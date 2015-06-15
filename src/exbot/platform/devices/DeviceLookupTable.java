package exbot.platform.devices;
/**
 * This class maintains the table of the information regarding the applications for devices (e.g., class path and id) 
 * stored in the xml file of devoceLookup.xml.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import exbot.platform.common.app.state.Operator;
import exbot.platform.devices.loader.DeviceLoader;
import exbot.util.xml.XMLHandler;

public class DeviceLookupTable {

	private volatile static DeviceLookupTable uniqueTable;
	private XMLHandler xmlHanlder;
	private final String nodeName = "Device";
	
	private DeviceLookupTable(){
		xmlHanlder = new XMLHandler(Path.lookupTablePath);
	}
	
	public static DeviceLookupTable getLookupTable(){
		if(uniqueTable==null)
		{
			synchronized (DeviceLookupTable.class){
				if(uniqueTable==null)
				{
					uniqueTable = new DeviceLookupTable();
				}
			}
		}
		
		return uniqueTable;
	}
	
	public void addDevice(Hashtable<String, String> attributes){
		xmlHanlder.addNode("DeviceList", nodeName, attributes);
	}
	
	public Operator getDeviceBy(String id){
		DeviceDescriptor desc = xmlHanlder.getOperatorDescriptorBy(nodeName, "id", id);
		return DeviceLoader.load(id, desc);
	}
	
	public DeviceDescriptor getDeviceDescriptor(String id){
		DeviceDescriptor desc = xmlHanlder.getOperatorDescriptorBy(nodeName, "id", id);
		return desc;
	}
	
	
	public void initialCheckPath(){
		try{
			ArrayList<String> jarList = xmlHanlder.getAttributeOfNodes("Device", "jar");
			for(int i =0 ; i < jarList.size() ; i++)
			{
				File jarFile = new File(jarList.get(i));
				if(!jarFile.exists() || jarFile.equals("")) 
					xmlHanlder.removeNodeByAttribute("Device", "jar", jarList.get(i));
			}
			
		}catch(Exception e){
			System.err.println("error");
		}
	}
}
