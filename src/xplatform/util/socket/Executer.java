package xplatform.util.socket;

import xplatform.platform.devices.Path;

public class Executer implements Runnable {
	protected String file;
	protected Process process;
	public Executer(String file){
		this.file = file;
	}
	
	@Override
	public void run() {
		try{
			process = new ProcessBuilder(Path.repository + file).start();
			process.waitFor();
		}catch (Exception e){
			
		}
	}
	
	public void deinit() {
		if(process!=null)
			process.destroy();
	}
}
