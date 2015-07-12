package xplatform.util.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPComm {

	protected int serverPort = 0;
    protected String host;
	protected ServerSocket serverSock = null;

    protected Socket sock = null;
    private InputStream sockInput = null;
    private OutputStream sockOutput = null;
    
    protected TCPComm() {

    }
    
    protected void init(String host, int serverPort){
    	if(this.sock == null){
    		this.host = host;
        	this.serverPort = serverPort;
            try {
    			this.sock = new Socket(host, serverPort);
    		} catch (UnknownHostException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
        
//        try {
//            serverSock = new ServerSocket(serverPort);
//        }
//        catch (IOException e){
//            e.printStackTrace(System.err);
//        }
    }
    
    // All this method does is wait for some bytes from the
    // connection, read them, then write them back again, until the
    // socket is closed from the other side.
    protected void handleConnection(InputStream sockInput, OutputStream sockOutput) {
        while(true) {
            byte[] buf=new byte[1024];
            int bytes_read = 0;
            try {
                bytes_read = sockInput.read(buf, 0, buf.length);
                
                if(bytes_read < 0) {
                    return;
                }
                
                String message = new String(buf, 0, bytes_read);
                sockOutput.write(buf, 0, bytes_read);
                sockOutput.flush();
            }
            catch (Exception e){
                System.err.println("Exception reading from/writing to socket, e="+e);
                e.printStackTrace(System.err);
                return;
            }
        }

    }

    protected void waitForConnections() {
        Socket sock = null;
        InputStream sockInput = null;
        OutputStream sockOutput = null;
        while (true) {
            try {
                sock = serverSock.accept();
                sockInput = sock.getInputStream();
                sockOutput = sock.getOutputStream();
            
                handleConnection(sockInput, sockOutput);
                sock.close();
            }
            catch (Exception e){
                System.err.println("Exception while closing socket.");
                e.printStackTrace(System.err);
            }

        }
    }
    
    protected void sendSomeMessages(String data) {
//        try {
//            sock = new Socket(this.host, serverPort);
//            sockInput = sock.getInputStream();
//            sockOutput = sock.getOutputStream();
////            byte[] buf = new byte[data.length];
//            sockOutput.write(data, 0, data.length); 
////            sockInput.read(buf, 0, buf.length);
////            sock.close();
//        }catch (IOException e){
//            System.err.println("Exception closing socket.");
//            e.printStackTrace(System.err);
//        }
    	
    	try
        {
 
            //Send the message to the server
            OutputStream os = sock.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
 
            String number = "2";
 
            String sendMessage = number + "\n";
            byte[] t = data.getBytes();
            bw.write(data);
            bw.flush();
            System.out.println("Message sent to the server : "+data);
 
            //Get the return message from the server
//            InputStream is = sock.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String message = br.readLine();
//            System.out.println("Message received from the server : " +data);
        }
        catch (Exception exception)
        {
            init(host, serverPort);
        }
       
    }
    
    
}
