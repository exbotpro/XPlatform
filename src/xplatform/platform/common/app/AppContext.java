package xplatform.platform.common.app;

import java.util.ArrayList;

import xplatform.platform.common.app.operator.AbstractOperator;
import xplatform.platform.common.app.state.AppState;
import xplatform.platform.common.app.state.InitializedState;
import xplatform.platform.common.app.state.ReadyState;
import xplatform.platform.common.app.state.RunningState;
import xplatform.platform.common.app.state.TerminateState;
import xplatform.platform.common.app.state.UnpluggedState;
import xplatform.platform.common.app.state.exception.InitException;
import xplatform.platform.common.app.state.exception.RunningException;
import xplatform.platform.devices.AppContextPool;

public class AppContext extends Thread{
	
	private AppState initState;
	private AppState readyState;
	private AppState runningState;
	private AppState terminateState;
	private AppState unpluggedState;
	
	private AppState state;
	private String id;
	private boolean running = true;
	private AbstractOperator op;
	private ArrayList<String> publisherList;
	
	private AppContext(String id, String type){
		this.id = id;
		initState = new InitializedState(id, type);
		readyState = new ReadyState(id, type);
		runningState = new RunningState(id, type);
		terminateState = new TerminateState(id, type);
		unpluggedState = new UnpluggedState(id, type);
		
		this.state = this.initState;
	}
	
	private AppContext(String id, String type, AbstractOperator op){
		this.id = id;
		initState = new InitializedState(id, op, type);
		readyState = new ReadyState(id, type);
		runningState = new RunningState(id, type);
		terminateState = new TerminateState(id, type);
		unpluggedState = new UnpluggedState(id, type);
		
		this.state = this.initState;
	}
	
	public static AppContext getContext(String id, String type, AbstractOperator op){
		AppContext context = AppContextPool.getLookupTable().getThread(id);
		
		if(context==null)
		{
			AppContext newContext = new AppContext(id, type, op);
			AppContextPool.getLookupTable().putThread(id, newContext); 
			return newContext;
		}
		
		return context;
	}
	
	public static AppContext getContext(String id, String type){
		AppContext context = AppContextPool.getLookupTable().getThread(id);
		
		if(context==null)
		{
			AppContext newContext = new AppContext(id, type);
			AppContextPool.getLookupTable().putThread(id, newContext); 
			return newContext;
		}
		
		return context;
		
	}
	
	public String getAppId() {
		return id;
	}

	public void changeToUnpluggedState() {
		this.state = this.unpluggedState;
	}
	
	public void changeToReadyState() {
		((ReadyState)this.readyState).init();
		this.state = this.readyState;
	}

	public void appStop(){
		this.running = false;
	}

	public void appStart() {
		this.running = true;
	}
	
	@Override
	public void run() {
		while(running)
		{
			this.monitorState();
		}
	}
	
	private void monitorState(){
		
		if(this.state instanceof InitializedState){
			try{
				op = this.state.getOp();
				if(op == null) op = this.state.initApp();
				this.publisherList = this.state.getPublisherList();
				this.readyState.setOperator(op);
				this.state = this.readyState;
			}catch(InitException e){
				
			}
			
		}else if(this.state instanceof ReadyState){
			this.state.setPublisherList(publisherList);
			if(this.state.waitPRDApp())
			{
				this.state = this.runningState;
			}
		}else if(this.state instanceof RunningState){
			try
			{
				op.start();
				this.state.runApp(op);
			}catch(RunningException e){
				String exceptionType = e.getExceptionType();
				if(exceptionType.equals(""))
				{
					this.state = this.terminateState;
				}else if(exceptionType.equals(""))
				{
					this.state = this.readyState;
				}else if(exceptionType.equals(""))
				{
					this.state = this.unpluggedState;
				}
			}
		}else if(this.state instanceof UnpluggedState){
			this.state.unpluggedApp();
			
		}else if(this.state instanceof TerminateState){
			this.state.terminateApp();
		}
	}

}
