package exbot.platform.common.app.state;

import exbot.platform.common.app.state.exception.InitException;
import exbot.platform.devices.DeviceLookupTable;

public class InitializedState extends State {

	
	public InitializedState(String id) {
		super(id);
		System.out.println("Init State");
	}

	public InitializedState(String id, Operator op) {
		super(id, op);
		System.out.println("Init State");
	}
	
	@Override
	public Operator initApp() throws InitException {
		DeviceLookupTable lookup = DeviceLookupTable.getLookupTable();
		Operator op = lookup.getDeviceBy(id);
		
		if(op==null){
			throw new InitException("");
		}
		
		super.putDevice(id, op);
		super.publisherList = op.getSubscribeFrom();
		return op;
	}

	@Override
	public boolean waitPRDApp() {

		return true;
	}

	@Override
	public void runApp(Operator operator) {

		
	}

	@Override
	public void terminateApp() {
		
	}

}
