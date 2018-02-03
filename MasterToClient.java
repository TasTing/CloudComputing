

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MasterToClient extends Thread {
	Socket s1;
	boolean exit = true;
	    
	    public MasterToClient(Socket s1){
	    	super();
	    	this.s1 = s1;	    	
	    }
	    
	    public void run(){
		
		while(true){ 
		    try{
		    	OutputStream s1out = s1.getOutputStream();
				DataOutputStream dos = new DataOutputStream (s1out);
				
				if(!MasterServer.reportList.isEmpty()){
                    reportData tmp = new reportData();
                    tmp = MasterServer.getReport();
                    System.out.println("try to send" + tmp.getdata());
					dos.writeUTF("REPT"+tmp.getmoneydata());
				}

				

		    } catch (IOException e) {
		    	//System.out.println("You has disconnected");
				// TODO Auto-generated catch block
		    	//e.printStackTrace();
			} 
		    // When done, just close the connection and exit
		}
		
	    }
}
