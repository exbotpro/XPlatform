package xplatform.platform.adaptation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import xplatform.platform.common.app.operator.AbstractOperator;
import xplatform.platform.devices.OperatorPool;


public class ThreadCoordinator {
	private int n = 10;
	private int diff = 100;
	private Hashtable<String, Long> initTime = new Hashtable<String, Long>();
	private Hashtable<String, Long> endTime = new Hashtable<String, Long>();
	
	private volatile static ThreadCoordinator coordinator;
	private Hashtable<String, Integer> numberOfData = new Hashtable<String, Integer>();
	private Hashtable<String, Vector<Double>> allDataTable = new Hashtable<String, Vector<Double>>();
	private Hashtable<String, Double> avgTimeTable = new Hashtable<String, Double>();
	private ConcurrentHashMap<String, Long> intervalTable = new ConcurrentHashMap<String, Long>();
	private ConcurrentHashMap<String, Long> maxTimeTable = new ConcurrentHashMap<String, Long>();
	
	private ThreadCoordinator(){
		
	}
	
	public static ThreadCoordinator getThreadCoordinator(){
		if(coordinator==null)
		{
			synchronized (ThreadCoordinator.class){
				if(coordinator==null)
				{
					coordinator = new ThreadCoordinator();
				}
			}
		}
		
		return coordinator;
	}
	
	public void setInitTime(String id, long time){
		this.initTime.put(id, time);
	}
	
	public void setEndTime(String id, long time){
		this.endTime.put(id, time);
		long x_cur = time - this.initTime.get(id);
		this.addTimeTo(id, x_cur);
	}
	
	public long getInterval(ArrayList<String> depedentApps){
		long interval = 0;
		
		for(String appId: depedentApps){
			if(intervalTable.containsKey(appId) && intervalTable.get(appId)>interval) {
				interval = intervalTable.get(appId);
			}
		}
		
		return interval;
	}
	
	public long getMyInterval(String id){
		long interval = 0;
		
		
		if(intervalTable.containsKey(id)) {
				interval = intervalTable.get(id);
		}
		
		
		return interval;
	}
	
	public boolean isSetIntervalOf(String id){
		return intervalTable.containsKey(id);
	}
	
	public void setInterval(String id){
		intervalTable.put(id, (long)(this.avgTimeTable.get(id).intValue()));
	}

	public void initInterval(String id){
		intervalTable.remove(id);		
	}

	public void addTimeTo(String id, long x_cur){
		int k = 0;
		double x_bar_prev = 0;
		
		if(numberOfData.containsKey(id)){
			if(x_cur > this.maxTimeTable.get(id)) this.maxTimeTable.put(id, (long)x_cur);
			if(this.allDataTable.get(id).size()>=n) this.allDataTable.get(id).removeElementAt(0);
			this.allDataTable.get(id).addElement((double)x_cur);
			
			k = numberOfData.get(id);
			x_bar_prev = avgTimeTable.get(id);
			double x_bar = DOAMeasurer.getMovingAverage(n, x_bar_prev, x_cur, allDataTable.get(id).get(0));
			
			this.numberOfData.put(id, k+1);
			this.avgTimeTable.put(id, x_bar);
			
		}else{
			this.maxTimeTable.put(id, (long)x_cur);
			Vector<Double> data = new Vector<Double>();
			data.addElement((double)x_cur);
			this.allDataTable.put(id, data);
			numberOfData.put(id, k+1);
			avgTimeTable.put(id, (double)x_cur);
		}
	}
}