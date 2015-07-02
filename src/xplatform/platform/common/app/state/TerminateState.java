package xplatform.platform.common.app.state;

import xplatform.platform.common.app.operator.AbstractOperator;
import xplatform.platform.common.app.state.exception.InitException;

public class TerminateState extends AppState{

	public TerminateState(String id, String type) {
		super(id, type);
	}

	@Override
	public AbstractOperator initApp() throws InitException {
		return null;
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
		System.out.println("Terminate State: " + type);
		
	}

	@Override
	public void unpluggedApp() {
		
	}

	@Override
	public void suspendApp() {
		
	}
}
