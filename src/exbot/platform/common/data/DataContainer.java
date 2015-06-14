package exbot.platform.common.data;

import java.util.ArrayList;


public abstract class DataContainer implements Cloneable{
	
	protected String deviceID;
	protected ArrayList<?> listOfData;
	protected Object data;

	public DataContainer(String deviceID){
		this.deviceID = deviceID;
	}
	
	public String getPortFrom() {
		return deviceID;
	}

	public void set(ArrayList<?> list) {
		this.listOfData = list;
	}

	public void set(Object data) {
		this.data = data;	
	}
	
	public ArrayList<?> getDataList(){
		return this.listOfData;
	}
	
	public Object getData(){
		return this.data;
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
