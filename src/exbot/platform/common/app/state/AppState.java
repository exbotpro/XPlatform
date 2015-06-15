package exbot.platform.common.app.state;

import java.util.ArrayList;

import exbot.platform.common.app.state.exception.InitException;
import exbot.platform.common.app.state.exception.RunningException;
import exbot.platform.devices.OperatorPool;

public abstract class AppState {
	protected String id;
	protected ArrayList<String> publisherList;
	protected Operator op;
	protected String type;
	
	protected AppState(String id, String type){
		this.id = id;
		this.type = type;
	}
	
	
	public Operator getOp() {
		return op;
	}


	public void setOperator(Operator op) {
		this.op = op;
	}


	public AppState(String id, Operator op, String type) {
		this.id = id;
		this.op = op;
		this.type = type;
		publisherList = op.getSubscribeFrom();
	}


	public final String getId() {
		return id;
	}
	
	public ArrayList<String> getPublisherList() {
		return publisherList;
	}


	public void setPublisherList(ArrayList<String> publisherList) {
		this.publisherList = publisherList;
	}
	
	public abstract Operator initApp() throws InitException;
	public abstract boolean waitPRDApp();
	public abstract void runApp(Operator operator) throws RunningException;
	public abstract void terminateApp();
	public abstract void unpluggedApp();
	public abstract void suspendApp();
	
	protected void putDevice(String id, Operator op){
		OperatorPool.getLookupTable().putOperator(id, op);
	}
	
	protected Operator getDevice(String id){
		return OperatorPool.getLookupTable().getOperator(id);
	}
	
	protected void removeDevice(String id){
		OperatorPool.getLookupTable().removeOperator(id);
	}
}
