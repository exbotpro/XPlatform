package xplatform.platform.devices.loader;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ConcurrentHashMap;

import xplatform.platform.devices.DeviceLookupTable;

public class JarLoaderFactory {

private static ConcurrentHashMap<String, JarLoader> classLoaderMap = new ConcurrentHashMap<String, JarLoader>();

	private volatile static JarLoaderFactory classLoader;
	private JarLoaderFactory(){
		
	}
	
	public static JarLoaderFactory getClassLoaderFactory(){
		if(classLoader==null)
		{
			synchronized (DeviceLookupTable.class){
				if(classLoader==null)
				{
					classLoader = new JarLoaderFactory();
				}
			}
		}
		
		return classLoader;
	}
	
	public JarLoader getClassLoader(String id){
		if(!classLoaderMap.containsKey(id)) {
			classLoaderMap.put(id, new JarLoader(new URL[]{}));
		}
		return classLoaderMap.get(id);
	}
	
	public void addJAR(String id, URL url){
		classLoaderMap.get(id).addJAR(url);
	}
	
	public void renewClassLoader(String id){
		classLoaderMap.remove(id);
	}
	
	public class JarLoader extends URLClassLoader{
		
		private JarLoader(URL[] arg0) {
			super(arg0);
		}
		
		private void addJAR(URL url){
			super.addURL(url);
		}
		
	}
}

