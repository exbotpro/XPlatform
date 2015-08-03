package xplatform.platform.common.app.state;

import xplatform.platform.common.app.operator.AbstractOperator;
import xplatform.platform.common.app.state.exception.InitException;
import xplatform.platform.devices.DeviceLookupTable;

public class InitializedState extends AppState {

	
	public InitializedState(String id, String type) {
		super(id, type);
		System.out.println("Init State: " + type);
		System.out.println("Check the corresponding dapp....");
	}

	public InitializedState(String id, AbstractOperator op, String type) {
		super(id, op, type);
		System.out.println("Init State: " + type);
		System.out.println("Check the corresponding dapp....");
		
	}
	
	@Override
	public AbstractOperator initApp() throws InitException {
		
		DeviceLookupTable lookup = DeviceLookupTable.getLookupTable();
		AbstractOperator op = lookup.getDeviceBy(id);
		
		System.out.println("The dapp has been detected!");
		
		if(op==null){
			throw new InitException("Init Exception");
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
	public void runApp(AbstractOperator operator) {

		
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
