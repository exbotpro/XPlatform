package exbot.platform.common.app.state;

import exbot.platform.common.app.state.exception.InitException;
import exbot.platform.devices.DeviceLookupTable;

public class InitializedState extends AppState {

	
	public InitializedState(String id, String type) {
		super(id, type);
		System.out.println("Init State: " + type);
	}

	public InitializedState(String id, Operator op, String type) {
		super(id, op, type);
		System.out.println("Init State: " + type);
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

	@Override
	public void unpluggedApp() {
		
	}

	@Override
	public void suspendApp() {
		
	}

}
