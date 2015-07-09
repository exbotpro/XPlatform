package xplatform.platform.common.app.operator;

import java.util.ArrayList;

import xplatform.platform.common.data.DataContainer;
import xplatform.platform.devices.Path;

public abstract class AbstractCOperator extends AbstractOperator{

	public AbstractCOperator(String id, String type) {
		super(id, type);
		this.loadDLL(this.getDll());
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
	
	private void loadDLL(String dll) {
		
		try{
			System.load(Path.repository + dll); 
		}catch(UnsatisfiedLinkError e){
			System.out.println(e.getMessage());
		}
	}
	
	protected abstract String getDll();
	protected abstract double[][] convertRecievedData(ArrayList<DataContainer> recievedData);
	protected abstract DataContainer convertGeneratedData(double[] generatedData);
}