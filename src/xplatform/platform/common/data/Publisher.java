package xplatform.platform.common.data;

import java.util.ArrayList;

import xplatform.platform.common.app.operator.AbstractOperator;

public class Publisher {
	
	private ArrayList<AbstractOperator> subscribers = new ArrayList<AbstractOperator>();
	private String masterDeviceID;
	
	
	public Publisher(String masterDeviceID) {
		this.masterDeviceID = masterDeviceID;
	}

	public void regist(AbstractOperator op){
		this.subscribers.add(op);
	}
	
	public final ArrayList<AbstractOperator> getSubscribers() {
		return subscribers;
	}

	public void announce(DataContainer data){
		for(AbstractOperator sb: subscribers){			
			sb.getDataRepo().get(masterDeviceID).setData(data);
		}
	}
}