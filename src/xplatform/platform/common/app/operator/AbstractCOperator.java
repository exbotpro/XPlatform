package xplatform.platform.common.app.operator;

import java.util.ArrayList;

import xplatform.platform.common.data.DataContainer;

public abstract class AbstractCOperator extends AbstractOperator{

	public AbstractCOperator(String id, String type, int interval) {
		super(id, type, interval);
		this.loadDLL(this.getPath());
	}
	
	protected void init(){
		this.c_init();
	}
	
	@Override
	protected DataContainer operate(ArrayList<DataContainer> recievedData) {
		double[] generatedData = this.c_operate(this.convertRecievedData(recievedData));
		return this.convertGeneratedData(generatedData);
	}
	
	private native double[] c_operate(double[][] recievedData);
	private native void c_init();
	
	private void loadDLL(String path) {
		try{
			System.loadLibrary(path); 
		}catch(UnsatisfiedLinkError e){
			System.out.println(e.getMessage());
		}
	}
	
	protected abstract String getPath();
	protected abstract double[][] convertRecievedData(ArrayList<DataContainer> recievedData);
	protected abstract DataContainer convertGeneratedData(double[] generatedData);
}