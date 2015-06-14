package exbot.platform.common.app.state;

import java.util.ArrayList;

import exbot.platform.common.app.state.exception.InitException;
import exbot.platform.common.app.state.exception.RunningException;
import exbot.platform.devices.ThreadPoolForOperators;

public abstract class State {
	protected String id;
	protected ArrayList<String> publisherList;
	protected Operator op;
	
	protected State(String id){
		this.id = id;
	}
	
	public Operator getOp() {
		return op;
	}


	public void setOperator(Operator op) {
		this.op = op;
	}


	public State(String id, Operator op) {
		this.id = id;
		this.op = op;
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
	
	protected void putDevice(String id, Operator op){
		ThreadPoolForOperators.getLookupTable().putThread(id, op);
	}
	
	protected Operator getDevice(String id){
		return ThreadPoolForOperators.getLookupTable().getThread(id);
	}
	
	protected void removeDevice(String id){
		ThreadPoolForOperators.getLookupTable().removeThread(id);
	}
}
