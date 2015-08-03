package xplatform.platform.common.app.operator;

import java.util.ArrayList;

import xplatform.platform.common.data.DataContainer;
import xplatform.util.socket.Executer;

public abstract class AbstractTCPOperator extends AbstractOperator
{
	protected Executer exe;
	private String host; 
	private int port;
	
	public AbstractTCPOperator(String id, String type, String host, int port) {
		super(id, type);
		this.host = host;
		this.port = port;
	}
	
	protected void init(){
		if(exe==null){
			exe = new Executer(getExecutableFileName());
			new Thread(exe).start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected DataContainer operate(ArrayList<DataContainer> recievedData) {
		super.init(host, port);
		
		if(!recievedData.isEmpty()){
			String data = this.convertRecievedData(recievedData);
			System.out.println(data);
			super.sendSomeMessages(data);
		}
		
		return null;
	}
	
	protected abstract String convertRecievedData(ArrayList<DataContainer> recievedData);
	protected abstract String getExecutableFileName();
	
	public void deinit(){
		this.exe.deinit();
		this.exe = null;
	}
}