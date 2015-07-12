package xplatform.platform.common.app.state;

import java.util.ArrayList;

import xplatform.platform.adaptation.ThreadCoordinator;
import xplatform.platform.common.app.AppContext;
import xplatform.platform.common.app.operator.AbstractOperator;
import xplatform.platform.common.app.state.exception.InitException;
import xplatform.platform.common.app.state.exception.RunningException;
import xplatform.platform.devices.AppContextPool;
import xplatform.platform.devices.OperatorPool;

public class UnpluggedState extends AppState{

	public UnpluggedState(String id, String type) {
		super(id, type);
	}

	@Override
	public AbstractOperator initApp() throws InitException {
		return null;
	}

	@Override
	public boolean waitPRDApp() {
		return false;
	}

	@Override
	public void runApp(AbstractOperator operator) throws RunningException {
		
	}

	@Override
	public void terminateApp() {
		
	}

	@Override
	public void unpluggedApp() {
		System.out.println("Unplugged State: " + type);

		this.readyAll();
		
		OperatorPool.getLookupTable().getOperator(id).stop();
		AppContextPool.getLookupTable().getThread(id).appStop();
		AppContextPool.getLookupTable().removeThread(id);
		OperatorPool.getLookupTable().removeOperator(id);
		ThreadCoordinator.getThreadCoordinator().initInterval(id);
	}

	private synchronized void readyAll(){
		ArrayList<AppContext> contexts = AppContextPool.getLookupTable().getAll();
		
		for(AppContext context: contexts){
			AbstractOperator op = OperatorPool.getLookupTable().getOperator(context.getAppId());
			op.stop();
			op.removeDataBufferOf(this.id);
			AppContextPool.getLookupTable().getThread(context.getAppId()).appStop();
			context.changeToReadyState();
			context.appStart();
		}
	}
	
	@Override
	public void suspendApp() {
		
	}
}
