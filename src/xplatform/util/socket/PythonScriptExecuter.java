package xplatform.util.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class PythonScriptExecuter implements Runnable{

	private String script;
	public PythonScriptExecuter(String script){
		this.script = script;
	}
	@Override
	public void run() {
		try{
		   Runtime r = Runtime.getRuntime();
		   Process p = r.exec("python " + script);
		   
		   BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   p.waitFor();
		   
		   while (br.ready())
			   System.out.println(br.readLine());
		}catch (Exception e){
		   String cause = e.getMessage();
		   if (cause.equals("python: not found"))
		   System.out.println("No python interpreter found.");
		}
		
	}
}
