package xplatform.platform.common.app.operator;

import java.util.ArrayList;

import xplatform.platform.common.data.DataContainer;

public abstract class AbstractTCPOperator extends AbstractOperator
{
	private String host; 
	private int port;
	
	public AbstractTCPOperator(String id, String type, String host, int port) {
		super(id, type);
		this.host = host;
		this.port = port;
//		this.init();
	}

	@Override
	protected DataContainer operate(ArrayList<DataContainer> recievedData) {
		super.init(host, port);
		
		if(!recievedData.isEmpty()){
			super.sendSomeMessages(this.convertRecievedData(recievedData));
		}
		
		return null;
	}
	
	protected abstract String convertRecievedData(ArrayList<DataContainer> recievedData);
	//protected abstract DataContainer convertGeneratedData(String generatedData);

}
