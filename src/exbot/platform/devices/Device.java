package exbot.platform.devices;

import exbot.platform.common.app.state.Operator;

public class Device {
	
	private String id;
	private Operator operator;
	
	public Device(String id, Operator operator) {
		this.id = id;
		this.operator = operator;
	}

	public String getId() {
		return id;
	}
	
	public void run(){
		new Thread(operator).run();
	}
	
}
