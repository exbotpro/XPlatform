package exbot.platform.common.app.state;

import exbot.platform.common.app.state.exception.InitException;
import exbot.platform.common.app.state.exception.RunningException;

public class UnpluggedState extends State{

	public UnpluggedState(String id) {
		super(id);
	}

	@Override
	public Operator initApp() throws InitException {
		return null;
	}

	@Override
	public boolean waitPRDApp() {
		return false;
	}

	@Override
	public void runApp(Operator operator) throws RunningException {
		
	}

	@Override
	public void terminateApp() {
		
	}

}
