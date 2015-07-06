package xplatform.platform.devices.loader;

import java.net.URL;
import java.net.URLClassLoader;

import xplatform.platform.devices.DeviceLookupTable;

public class JarClassLoader extends URLClassLoader{

	private volatile static JarClassLoader classLoader;
	
	private JarClassLoader(URL[] arg0) {
		super(arg0);
	}
	
	public static JarClassLoader getClassLoader(){
		if(classLoader==null)
		{
			synchronized (DeviceLookupTable.class){
				if(classLoader==null)
				{
					classLoader = new JarClassLoader(new URL[]{});
				}
			}
		}
		
		return classLoader;
	}
	
	public void addJAR(URL url){
		super.addURL(url);
	}
	
}