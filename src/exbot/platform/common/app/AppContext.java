package exbot.platform.common.app;

import java.util.ArrayList;

import exbot.platform.common.app.state.InitializedState;
import exbot.platform.common.app.state.Operator;
import exbot.platform.common.app.state.ReadyState;
import exbot.platform.common.app.state.RunningState;
import exbot.platform.common.app.state.AppState;
import exbot.platform.common.app.state.TerminateState;
import exbot.platform.common.app.state.UnpluggedState;
import exbot.platform.common.app.state.exception.InitException;
import exbot.platform.common.app.state.exception.RunningException;
import exbot.platform.devices.ThreadPoolForAppContext;

public class AppContext extends Thread{
	
	private AppState initState;
	private AppState readyState;
	private AppState runningState;
	private AppState terminateState;
	private AppState unpluggedState;
	
	private AppState state;
	private String id;
	private boolean running = true;
	private Operator op;
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
	
	private AppContext(String id, String type, Operator op){
		this.id = id;
		initState = new InitializedState(id, op, type);
		readyState = new ReadyState(id, type);
		runningState = new RunningState(id, type);
		terminateState = new TerminateState(id, type);
		unpluggedState = new UnpluggedState(id, type);
		
		this.state = this.initState;
	}
	
	public static AppContext getContext(String id, String type, Operator op){
		AppContext context = ThreadPoolForAppContext.getLookupTable().getThread(id);
		
		if(context==null)
		{
			AppContext newContext = new AppContext(id, type, op);
			ThreadPoolForAppContext.getLookupTable().putThread(id, newContext); 
			return newContext;
		}
		
		return context;
	}
	
	public static AppContext getContext(String id, String type){
		AppContext context = ThreadPoolForAppContext.getLookupTable().getThread(id);
		
		if(context==null)
		{
			AppContext newContext = new AppContext(id, type);
			ThreadPoolForAppContext.getLookupTable().putThread(id, newContext); 
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
		this.state = this.readyState;
	}

	public void appStop(){
		this.running = false;
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

	public void appStart() {
		this.running = true;
	}
}
