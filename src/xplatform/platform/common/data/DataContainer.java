package xplatform.platform.common.data;

import java.util.ArrayList;


public abstract class DataContainer implements Cloneable{
	
	protected String deviceID;
	protected ArrayList<?> listOfData;

	public DataContainer(String deviceID){
		this.deviceID = deviceID;
	}
	
	public String getPortFrom() {
		return deviceID;
	}
	
	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public ArrayList<?> getListOfData() {
		return listOfData;
	}

	public void setListOfData(ArrayList<?> listOfData) {
		this.listOfData = listOfData;
	}

	public DataContainer clone(){
		DataContainer data = null;
		try {
			data = (DataContainer) super.clone();
//			data.set(this.listOfData.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
