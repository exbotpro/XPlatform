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
		
		this.readyAll(OperatorPool.getLookupTable().getOperator(id));
		UnpluggedState.removeApp(this.id);
	}

	public static void removeApp(String id) {
		
		OperatorPool.getLookupTable().getOperator(id).stop();
		AppContextPool.getLookupTable().getThread(id).appStop();
		AppContextPool.getLookupTable().removeThread(id);
		OperatorPool.getLookupTable().getOperator(id).deinit();
		OperatorPool.getLookupTable().removeOperator(id);
		ThreadCoordinator.getThreadCoordinator().initInterval(id);
	}

	private synchronized void readyAll(AbstractOperator removedOperator){
		ArrayList<AppContext> contexts = AppContextPool.getLookupTable().getAll();
		
		for(AppContext context: contexts){
			AbstractOperator op = OperatorPool.getLookupTable().getOperator(context.getAppId());
			op.stop();
			op.deinit();
			op.removeDataBufferOf(this.id);
			op.getPublisher().getSubscribers().remove(removedOperator);
			AppContextPool.getLookupTable().getThread(context.getAppId()).appStop();
			context.changeToReadyState();
			context.appStart();
		}
	}
	
	@Override
	public void suspendApp() {
		
	}
}
