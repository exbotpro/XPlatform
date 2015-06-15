package exbot.platform.common.app.state;

import java.util.ArrayList;

import exbot.platform.common.app.AppContext;
import exbot.platform.common.app.state.exception.InitException;
import exbot.platform.common.app.state.exception.RunningException;
import exbot.platform.devices.OperatorPool;
import exbot.platform.devices.ThreadPoolForAppContext;

public class UnpluggedState extends AppState{

	public UnpluggedState(String id, String type) {
		super(id, type);
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

	@Override
	public void unpluggedApp() {
		System.out.println("Unplugged State: " + type);
		OperatorPool.getLookupTable().getOperator(id).stop();
		ThreadPoolForAppContext.getLookupTable().getThread(id).appStop();
		ThreadPoolForAppContext.getLookupTable().removeThread(id);
		OperatorPool.getLookupTable().removeOperator(id);
		
		this.readyAll();
	}

	private synchronized void readyAll(){
		ArrayList<AppContext> contexts = ThreadPoolForAppContext.getLookupTable().getAll();
		
		for(AppContext context: contexts){
			OperatorPool.getLookupTable().getOperator(context.getAppId()).stop();
			context.changeToReadyState();
			ThreadPoolForAppContext.getLookupTable().getThread(context.getAppId()).start();
		}
		
	}
	
	@Override
	public void suspendApp() {
		
	}
}
