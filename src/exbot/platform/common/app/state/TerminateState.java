package exbot.platform.common.app.state;

import exbot.platform.common.app.state.exception.InitException;

public class TerminateState extends State{

	public TerminateState(String id) {
		super(id);
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
		System.out.println("Terminate State");
	}

}
