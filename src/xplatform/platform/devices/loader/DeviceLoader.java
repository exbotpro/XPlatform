package xplatform.platform.devices.loader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;

import xplatform.platform.common.app.operator.AbstractOperator;
import xplatform.platform.devices.DeviceDescriptor;
import xplatform.platform.devices.OperatorPool;


public class DeviceLoader {
	public static AbstractOperator monitor(String repositoryURL, String id, DeviceDescriptor desc){
		File repository = new File(repositoryURL);
		if(!repository.isDirectory()) return null;

		while(true){
			File[] jars = repository.listFiles();
			for(File jar: jars){
				String[] extension = jar.getName().split("\\.");
				if(jar.isDirectory() || extension.length==1) continue;
				if(!extension[1].equals("jar")) continue;
				AbstractOperator op = load(jar, id, desc);
				if(op!=null) return op;
				else continue;
			}
		}
	}
	
	public static AbstractOperator load(File jar, String id, DeviceDescriptor desc){
		AbstractOperator op = null;
		if(desc!= null){
			try {
				URL url = jar.toURI().toURL();
				ClassLoader classLoader = ClassLoader.getClassLoader();
				classLoader.addJAR(url);
				Class<?> c = Class.forName (desc.getClasspath(), true, classLoader);
				Constructor<?> con = c.getDeclaredConstructors()[0];
				op = (AbstractOperator) con.newInstance(new Object[] {new String(id), new String(desc.getType())});
				OperatorPool.getLookupTable().putOperator(id, op);
				
			} catch (Exception e) {
				return null;
			}
		}
		
		return op;
	}
	
//	public static Operator load(File jar, String id, DeviceDescriptor desc){
//		Operator op = null;
//		if(desc!= null){
//			
//			try {
//				
//	    		Class<?> c = Class.forName(desc.getClasspath());
//				Constructor<?> con = c.getDeclaredConstructors()[0];
//				op = (Operator) con.newInstance(new Object[] {new String(id), new String(desc.getType())});
//				OperatorPool.getLookupTable().putOperator(id, op);
//				
//			} catch (Exception e) {
//				System.err.println("A class for operating the device is not found");
//			}
//		}
//		
//		return op;
//	}
	
}
