package exbot.platform.devices;

import java.util.ArrayList;
import java.util.Hashtable;

import exbot.platform.common.app.AppContext;

public class ThreadPoolForAppContext {
	private volatile static ThreadPoolForAppContext uniqueTable;
	private Hashtable<String, AppContext> lookup = new Hashtable<String, AppContext>();
	
	public static ThreadPoolForAppContext getLookupTable(){
		if(uniqueTable==null)
		{
			synchronized (DeviceLookupTable.class){
				if(uniqueTable==null)
				{
					uniqueTable = new ThreadPoolForAppContext();
				}
			}
		}
		
		return uniqueTable;
	}
	
	public void putThread(String id, AppContext op){
		lookup.put(id, op);
	}
	
	public AppContext getThread(String id){
		return lookup.get(id);
	}
	
	public void removeThread(String id){
		lookup.remove(id);
	}

	public ArrayList<AppContext> getAll() {
		ArrayList<AppContext> contexts = new ArrayList<AppContext>();
		for(String id: this.lookup.keySet()) contexts.add(this.lookup.get(id));
		return contexts;
	}
}
