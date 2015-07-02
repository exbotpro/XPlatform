package xplatform.platform.common.data;


public class DataBuffer {

	private DataContainer data;
	
	public synchronized DataContainer getData() {
		return data;
	}
	
	public synchronized void setData(DataContainer data) {
		this.data = data;
	}
}