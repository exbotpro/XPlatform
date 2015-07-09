package xplatform.platform.common.app.operator;

import java.util.ArrayList;

import org.python.core.PyInstance;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import xplatform.platform.common.data.DataContainer;

public abstract class AbstractPythonOperator extends AbstractOperator{
	protected PythonInterpreter interpreter;
	protected PyInstance instance;
	public AbstractPythonOperator(String id, String type) {
		super(id, type);
	}
	
	protected void init(){
		PythonInterpreter.initialize(System.getProperties(),  
                System.getProperties(), new String[0]);  

		this.interpreter = new PythonInterpreter();
	    this.execfile(this.getScriptName());
	    instance = this.createClass(this.getClassName(), "None");
	}
	
	protected void execfile(String scriptFileName){  
		this.interpreter.execfile(scriptFileName);  
	}
	
	protected PyInstance createClass(String className, String opts){
		return (PyInstance) this.interpreter.eval(className + "(" + opts + ")");  
	}
	
	protected DataContainer operate(ArrayList<DataContainer> recievedData){
		double[] generatedData = this.py_operate(this.convertRecievedData(recievedData));
		return this.convertGeneratedData(generatedData);
	}
	
	protected double[] py_operate(double[][] convertedRecievedData){
		
		if(convertedRecievedData.length>0){
			PyString[] args = new PyString[convertedRecievedData[0].length];
			
			for(int i = 0 ; i < convertedRecievedData[0].length ; i++)
			{
				args[i] = new PyString(String.valueOf(convertedRecievedData[0][i]));
			}
			
			instance.invoke(this.getFunctionName(), args);
		}
		
		return null;
	}
	
	protected abstract String getScriptName();
	protected abstract String getClassName();
	protected abstract String getFunctionName();
	protected abstract double[][] convertRecievedData(ArrayList<DataContainer> recievedData);
	protected abstract DataContainer convertGeneratedData(double[] generatedData);
}