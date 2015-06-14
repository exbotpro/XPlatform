package exbot.platform.devices.loader;

import java.lang.reflect.Constructor;

import exbot.platform.common.app.state.Operator;
import exbot.platform.devices.DeviceDescriptor;
import exbot.platform.devices.ThreadPoolForOperators;


public class DeviceLoader {

	public static Operator load(String id, DeviceDescriptor desc){
		Operator op = null;
		if(desc!= null){
			try {
	    		//if no app for the device is in the local place, search for repository in Internet and download it!
				
	    		Class<?> c = Class.forName(desc.getClasspath());
				Constructor<?> con = c.getDeclaredConstructors()[0];
				op = (Operator) con.newInstance(new Object[] {new String(id)});
				ThreadPoolForOperators.getLookupTable().putThread(id, op);
				
			} catch (Exception e) {
				System.err.println("A class for operating the device is not found");
			}
		}
		
		return op;
	}
	
}
