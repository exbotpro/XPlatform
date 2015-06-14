package exbot.platform.common.app.state;

import exbot.platform.common.app.state.exception.InitException;
import exbot.platform.common.app.state.exception.RunningException;

public class RunningState extends State{

	private Operator operator;
	public RunningState(String id) {
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
	public void runApp(Operator operator) throws RunningException {
		this.operator = operator;
		if(this.operator == null)
		{
			throw new RunningException("");
		}
		
		new Thread(operator).start();
		
	}

	@Override
	public void terminateApp() {
		
	}

}
