package exbot.platform.devices;

import java.util.Hashtable;

import exbot.platform.common.app.state.Operator;

public class ThreadPoolForAppContext {
	private volatile static ThreadPoolForOperators uniqueTable;
	private Hashtable<String, Operator> lookup = new Hashtable<String, Operator>();
	
	public static ThreadPoolForOperators getLookupTable(){
		if(uniqueTable==null)
		{
			synchronized (DeviceLookupTable.class){
				if(uniqueTable==null)
				{
					uniqueTable = new ThreadPoolForOperators();
				}
			}
		}
		
		return uniqueTable;
	}
	
	public void putThread(String id, Operator op){
		lookup.put(id, op);
	}
	
	public Operator getThread(String id){
		return lookup.get(id);
	}
}
