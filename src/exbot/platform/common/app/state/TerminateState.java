package exbot.platform.common.app.state;

import exbot.platform.common.app.state.exception.InitException;

public class TerminateState extends AppState{

	public TerminateState(String id, String type) {
		super(id, type);
	}

	@Override
	public Operator initApp() throws InitException {
		return null;
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
		System.out.println("Terminate State: " + type);
		
	}

	@Override
	public void unpluggedApp() {
		
	}

	@Override
	public void suspendApp() {
		
	}
}
