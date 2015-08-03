package xplatform.platform.devices;

/**
 * This class maintains the table of the threads of the Operators for devices. 
 */
import java.util.Hashtable;

import xplatform.platform.common.app.operator.AbstractOperator;

public class OperatorPool {

	private volatile static OperatorPool uniqueTable;
	private Hashtable<String, AbstractOperator> lookup = new Hashtable<String, AbstractOperator>();
	
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
	
	public synchronized void putOperator(String id, AbstractOperator op){
		lookup.put(id, op);
	}
	
	public AbstractOperator getOperator(String id){
		return lookup.get(id);
	}
	
	public synchronized void removeOperator(String id){
		lookup.remove(id);
	}
	
	public synchronized int getSize(){
		return this.lookup.size();
	}
}
