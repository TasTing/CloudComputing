
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class ClientRead extends Thread {
   Socket s1;
   int ErrorCount = 0;

  public ClientRead(Socket s1) {
	super();
	this.s1 = s1;
  }
  public void run()
  {
	  try {
		  InputStream s1In;
		  OutputStream s1Out;
	  while(true)
	  {     
		    s1In = s1.getInputStream();		   
		    DataInputStream dis = new DataInputStream(s1In);
		    OutputStream s1out = s1.getOutputStream();
	    	DataOutputStream dos = new DataOutputStream (s1out);
		    String st = new String (dis.readUTF());
		   // Client.updateMessagePrint("Server Respond   :" + st);
		    if(st.substring(0, 4).equals("PAIS")){
		    	Client.updateMessagePrint("your password is :" + st.substring(4) + "\n");
		    }
		    else if(st.substring(0, 3).equals("PSC")){
		    	Client.updateMessagePrint("correct: Press enter" + "\n");
		    	Client.setClient(Integer.parseInt(st.substring(4)));
		    	Client.setCondition1();
		    }
		    else if(st.substring(0, 3).equals("PSW")){
		    	Client.updateMessagePrint("Wrong password" + "\n");
		    }
		    else if(st.substring(0, 4).equals("REPO")){
		    	Userdata tmp = new Userdata();
		    	tmp.putFullData(st.substring(4));
		    	System.out.println("jobId: " + tmp.getjobid() + " password: " + tmp.getPassword() + "\n");
		    	Client.updateMessagePrint("jobId: " + tmp.getjobid() + " password: " + tmp.getPassword() + "\n");
		    }		    
		    else if(st.substring(0, 4).equals("REPT")){

		    	Client.updateMessagePrint(st.substring(4));
		    }

	  }
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		    Client.updateMessagePrint("You has disconnected" + "\n");

		}
  }
}
