package exbot.platform.devices;

/**
 * This class maintains the table of the threads of the Operators for devices. 
 */
import java.util.Hashtable;

import exbot.platform.common.app.state.Operator;

public class OperatorPool {

	private volatile static OperatorPool uniqueTable;
	private Hashtable<String, Operator> lookup = new Hashtable<String, Operator>();
	
	public static OperatorPool getLookupTable(){
		if(uniqueTable==null)
		{
			synchronized (DeviceLookupTable.class){
				if(uniqueTable==null)
				{
					uniqueTable = new OperatorPool();
				}
			}
		}
		
		return uniqueTable;
	}
	
	public synchronized void putOperator(String id, Operator op){
		lookup.put(id, op);
	}
	
	public Operator getOperator(String id){
		return lookup.get(id);
	}
	
	public synchronized void removeOperator(String id){
		lookup.remove(id);
	}
}
