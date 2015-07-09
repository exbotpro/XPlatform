package xplatform.platform.common.app.operator;

import java.util.ArrayList;
import java.util.Hashtable;

import xplatform.platform.adaptation.ThreadCoordinator;
import xplatform.platform.common.data.DataBuffer;
import xplatform.platform.common.data.DataContainer;
import xplatform.platform.common.data.Publisher;


public abstract class AbstractOperator {
	
	protected static String path;
	protected String ID;
	protected String type;

	protected long interval;
	protected long p_time;
	
	protected boolean running = true;
	protected ArrayList<String> subscribeFrom = new ArrayList<String>();
	private Publisher publisher;
	private Hashtable<String, DataBuffer> dataRepo = new Hashtable<String, DataBuffer>();
	
	/**
	 * This constructor is to set the ID of your application 
	 * @param id: USB-ID (e.g., productID: vendorID)
	 */
	public AbstractOperator(String id, String type) {
		this.ID = id;
		this.type = type;
		this.p_time = 0;
		this.publisher = new Publisher(id);
		this.setSubscribeFrom(this.getDependedDeviceID());
	}
	
	public long getInterval() {
		return interval;
	}
	
	/**
	 * this method is used for synchronizing apps.
	 * 
	 * @param interval
	 */
	public void setInterval(long interval) {
		this.interval = interval;
	}


	/**
	 * This method is for Controller and Actuator Application
	 * if you want to subscribe from publishers, 
	 * call this method for registering your application to the publishers you delivers
	 * @param subscribeFrom: list of publishers
	 */
	public void setSubscribeFrom(ArrayList<String> subscribeFrom) {
		this.subscribeFrom = subscribeFrom;
	}
	

	/**
	 * This method is for Controller and Actuator applications
	 * @return
	 */
	public ArrayList<String> getSubscribeFrom() {
		return subscribeFrom;
	}
	
	/**
	 * This method is for Controller and Actuator applications.
	 * The method requests to regist the current operator to other applications 
	 * that the current operator wants to be subscribed from.
	 */
	public void requestSubscription(AbstractOperator publisherOperator){
		publisherOperator.getPublisher().regist(this); //regist the current operator to the publisher of the operator producing data that the current want to recieve.
		this.dataRepo.put(publisherOperator.getID(), new DataBuffer());
	}
	
	public void removeDataBufferOf(String publisherID)
	{
		this.dataRepo.remove(publisherID);
	}
	
	/**
	 * Main method to run the operator (device)
	 * The method is infinitely run until "running" flag turns into false value.
	 * When the operator runs, 1) getting recieved data, producing new data in DataContainer,
	 * 2) announcing the produced data to the its subscribers using publisher are carried out.
	 */
	public void run() {
		
		this.init();
		
		while(running){
			try {
				this.interval = ThreadCoordinator.getThreadCoordinator().getInterval(this.subscribeFrom);
				long myProcessingTime = ThreadCoordinator.getThreadCoordinator().getMyInterval(ID);
				
				long delay = this.interval-this.p_time;
				if(delay>0)Thread.sleep(delay);
				long start = System.currentTimeMillis();
				ThreadCoordinator.getThreadCoordinator().setInitTime(this.ID, start);
				
				DataContainer dc = this.operate(this.getRecievedData());
				long end = System.currentTimeMillis();
				
				ThreadCoordinator.getThreadCoordinator().setEndTime(this.ID, end);
				ThreadCoordinator.getThreadCoordinator().setInterval(this.ID);
				
				this.publisher.announce(dc);
				long x_cur = end - start;
				this.p_time = x_cur;
				long cur_delay = myProcessingTime - x_cur;
				if(cur_delay>0) Thread.sleep(cur_delay);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<DataContainer> getRecievedData() {
		ArrayList<DataContainer> recievedData = new ArrayList<DataContainer>();
		
		for(String device: subscribeFrom){
			DataBuffer b = this.dataRepo.get(device);
			if(b!=null && b.getData()!=null){
				recievedData.add(b.getData().clone());
				this.dataRepo.get(device).setData(null);
			}
		}
		
		return recievedData;
	}

	public void stop() {
		this.running = false;
	}
	
	public void start() {
		this.running = true;
	}
	
	public Publisher getPublisher() {
		return publisher;
	}
	
	public String getID() {
		return this.ID;
	}
	
	public Hashtable<String, DataBuffer> getDataRepo() {
		return dataRepo;
	}
	
	/**
	 * implement this method for carrying out the functionality of the applications you are developing.
	 */
	protected abstract DataContainer operate(ArrayList<DataContainer> recievedData);
	protected abstract ArrayList<String> getDependedDeviceID();
	protected abstract void init();

}
