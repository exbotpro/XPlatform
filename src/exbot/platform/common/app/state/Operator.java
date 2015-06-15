package exbot.platform.common.app.state;

import java.util.ArrayList;
import java.util.Hashtable;

import exbot.platform.common.data.DataBuffer;
import exbot.platform.common.data.DataContainer;
import exbot.platform.common.data.Publisher;


public abstract class Operator {
	
	protected String ID;
	protected String type;
	protected int interval;
	protected boolean running = true;
	protected ArrayList<String> subscribeFrom = new ArrayList<String>();
	private Publisher publisher;
	private Hashtable<String, DataBuffer> dataRepo = new Hashtable<String, DataBuffer>();
	
	/**
	 * This constructor is to set the ID of your application 
	 * @param id: USB-ID (e.g., productID: vendorID)
	 */
	public Operator(String id, String type, int interval) {
		this.ID = id;
		this.type = type;
		this.interval = interval;
		this.publisher = new Publisher(id);
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
	public void requestSubscription(Operator publisherOperator){
		publisherOperator.getPublisher().regist(this); //regist the current operator to the publisher of the operator producing data that the current want to recieve.
		this.dataRepo.put(publisherOperator.getID(), new DataBuffer());
		
	}
	
	/**
	 * Main method to run the operator (device)
	 * The method is infinitely run until "running" flag turns into false value.
	 * When the operator runs, 1) getting recieved data, producing new data in DataContainer,
	 * 2) announcing the produced data to the its subscribers using publisher are carried out.
	 */
	public void run() {
		while(running){
			try {
				Thread.sleep(this.interval);
				DataContainer dc = this.operate(this.getRecievedData());
				this.publisher.announce(dc);
				
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

}
