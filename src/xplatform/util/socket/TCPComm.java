package xplatform.util.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPComm {

	protected String host;
	protected int serverPort = 0;
	protected ServerSocket serverSock = null;
    protected Socket sock = null;
    protected boolean running = true;
    private DataOutputStream sockOutput = null;
    
    protected TCPComm() {


    }
    
    protected void init(String host, int serverPort){
    	if(this.sock == null){
    		this.host = host;
        	this.serverPort = serverPort;
    		try {
    			this.sock = new Socket(host, serverPort);
    			this.sock.setOOBInline(true);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    @SuppressWarnings("deprecation")
	protected void listenMessage(){
        while(running)
        {
        	try
        	{
        		Socket server = serverSock.accept();
        		DataInputStream in = new DataInputStream(server.getInputStream());
        		String value = in.readLine();
        		System.out.println(value);
        	}catch(SocketTimeoutException s){
        		break;
        	}catch(IOException e) {
        		e.printStackTrace();
        		break;
        	}
        }
    }
    
    protected void pauseServerListening(){
    	this.running = false;
    }
    
    protected void resumeServerListening(){
    	this.running = true;
    	listenMessage();
    }
    
    public void sendSomeMessages(String data) {

    	try {
			sockOutput = new DataOutputStream(sock.getOutputStream());
			sockOutput.write(data.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
       
    }
    
    public void closeSocket(){
        try {
        	sockOutput.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
