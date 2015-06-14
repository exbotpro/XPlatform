package exbot.platform.common.app;

import java.util.ArrayList;

import exbot.platform.common.app.state.InitializedState;
import exbot.platform.common.app.state.Operator;
import exbot.platform.common.app.state.ReadyState;
import exbot.platform.common.app.state.RunningState;
import exbot.platform.common.app.state.State;
import exbot.platform.common.app.state.TerminateState;
import exbot.platform.common.app.state.UnpluggedState;
import exbot.platform.common.app.state.exception.InitException;
import exbot.platform.common.app.state.exception.RunningException;

public class AppContext implements Runnable{
	
	private State initState;
	private State readyState;
	private State runningState;
	private State terminateState;
	private State unpluggedState;
	
	private State state;
	private String id;
	private boolean running = true;
	private Operator op;
	private ArrayList<String> publisherList;
	
	public AppContext(String id){
		this.id = id;
		initState = new InitializedState(id);
		readyState = new ReadyState(id);
		runningState = new RunningState(id);
		terminateState = new TerminateState(id);
		unpluggedState = new UnpluggedState(id);
		
		this.state = this.initState;
	}
	
	public AppContext(String id, Operator op){
		this.id = id;
		initState = new InitializedState(id, op);
		readyState = new ReadyState(id);
		runningState = new RunningState(id);
		terminateState = new TerminateState(id);
		unpluggedState = new UnpluggedState(id);
		
		this.state = this.initState;
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

//			System.out.println("Running State");
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
			
		}else if(this.state instanceof TerminateState){
			this.state.terminateApp();
		}
	}
}
