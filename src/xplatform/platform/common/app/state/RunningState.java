package xplatform.platform.common.app.state;

import xplatform.platform.common.app.operator.AbstractOperator;
import xplatform.platform.common.app.state.exception.InitException;
import xplatform.platform.common.app.state.exception.RunningException;

public class RunningState extends AppState{

	private AbstractOperator operator;
	public RunningState(String id, String type) {
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
	public void runApp(AbstractOperator operator) throws RunningException {
//		System.out.println("Running State: " + type);
		this.operator = operator;
		if(this.operator == null)
		{
			throw new RunningException("");
		}
		operator.run();
		//new Thread(operator).start();
		
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
