package xplatform.platform.common.app.state;

import java.util.ArrayList;

import xplatform.platform.common.app.operator.AbstractOperator;
import xplatform.platform.common.app.state.exception.InitException;
import xplatform.platform.common.app.state.exception.RunningException;
import xplatform.platform.devices.OperatorPool;

public abstract class AppState {
	protected String id;
	protected ArrayList<String> publisherList;
	protected AbstractOperator op;
	protected String type;
	
	protected AppState(String id, String type){
		this.id = id;
		this.type = type;
	}
	
	
	public AbstractOperator getOp() {
		return op;
	}


	public void setOperator(AbstractOperator op) {
		this.op = op;
	}


	public AppState(String id, AbstractOperator op, String type) {
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
	
	public abstract AbstractOperator initApp() throws InitException;
	public abstract boolean waitPRDApp();
	public abstract void runApp(AbstractOperator operator) throws RunningException;
	public abstract void terminateApp();
	public abstract void unpluggedApp();
	public abstract void suspendApp();
	
	protected void putDevice(String id, AbstractOperator op){
		OperatorPool.getLookupTable().putOperator(id, op);
	}
	
	protected AbstractOperator getDevice(String id){
		return OperatorPool.getLookupTable().getOperator(id);
	}
	
	protected void removeDevice(String id){
		OperatorPool.getLookupTable().removeOperator(id);
	}
}
