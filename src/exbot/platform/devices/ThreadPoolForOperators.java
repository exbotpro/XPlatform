package exbot.platform.devices;

/**
 * This class maintains the table of the threads of the Operators for devices. 
 */
import java.util.Hashtable;

import exbot.platform.common.app.state.Operator;

public class ThreadPoolForOperators {

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
	
	public synchronized void putThread(String id, Operator op){
		lookup.put(id, op);
	}
	
	public Operator getThread(String id){
		return lookup.get(id);
	}
	
	public synchronized void removeThread(String id){
		lookup.remove(id);
	}
}
