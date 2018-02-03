

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MasterToWorker extends Thread {
	Socket s1;
	boolean exit = true;
	    
	    public MasterToWorker(Socket s1){
	    	super();
	    	this.s1 = s1;	    	
	    }
	    
	    public void run(){
		
		while(exit){ 
		    try{
		    	OutputStream s1out = s1.getOutputStream();
				DataOutputStream dos = new DataOutputStream (s1out);
                InputStream s1In = s1.getInputStream();
				DataInputStream dis = new DataInputStream(s1In);
				String st = new String (dis.readUTF());
				System.out.println(st);
				String re = "RETU11245535:is what are:45ms";
				dos.writeUTF(re);
		        s1.close();
		        exit = false;
		    } catch (IOException e) {
		    	//System.out.println("You has disconnected");
				// TODO Auto-generated catch block
		    	//e.printStackTrace();
			}	

		    // When done, just close the connection and exit
		}
		
	    }
}
