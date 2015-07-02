package xplatform.platform.common.app.state;

import java.util.ArrayList;

import xplatform.platform.common.app.AppContext;
import xplatform.platform.common.app.operator.AbstractOperator;
import xplatform.platform.common.app.state.exception.InitException;
import xplatform.platform.common.app.state.exception.RunningException;
import xplatform.platform.devices.OperatorPool;
import xplatform.platform.devices.ThreadPoolForAppContext;

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
		ThreadPoolForAppContext.getLookupTable().getThread(id).appStop();
		ThreadPoolForAppContext.getLookupTable().removeThread(id);
		OperatorPool.getLookupTable().removeOperator(id);
		
	}

	private synchronized void readyAll(){
		ArrayList<AppContext> contexts = ThreadPoolForAppContext.getLookupTable().getAll();
		for(AppContext context: contexts){
			AbstractOperator op = OperatorPool.getLookupTable().getOperator(context.getAppId());
			op.stop();
			op.removeDataBufferOf(this.id);			
			ThreadPoolForAppContext.getLookupTable().getThread(context.getAppId()).appStop();
			context.changeToReadyState();
			context.appStart();
		}
	}
	
	@Override
	public void suspendApp() {
		
	}
}
