package exbot.platform.devices.loader;

import java.net.URL;
import java.net.URLClassLoader;

import exbot.platform.devices.DeviceLookupTable;

public class ClassLoader extends URLClassLoader{


	private volatile static ClassLoader classLoader;
	private ClassLoader(URL[] arg0) {
		super(arg0);
	}
	
	public static ClassLoader getClassLoader(){
		if(classLoader==null)
		{
			synchronized (DeviceLookupTable.class){
				if(classLoader==null)
				{
					classLoader = new ClassLoader(new URL[]{});
				}
			}
		}
		
		return classLoader;
	}
	
	public void addJAR(URL url){
		super.addURL(url);
	}

}
