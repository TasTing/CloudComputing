
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;



public class MasterServerRead extends Thread {
   Socket s1;

  public MasterServerRead(Socket s1) {
	super();
	this.s1 = s1;
  }
  public void run()
  {
	  new MasterToClient(s1).start();
	  
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
		    if(st.contentEquals("CreatPass")){
		    	int pass = s1.getPort();
		    	Random r1 = new Random();
		    	pass = pass + r1.nextInt(90000);
		    	//System.out.println("Creating Password ");		    	
		    	MasterServer.passListAdd(pass);
		    	dos.writeUTF("PAIS" + Integer.toString(pass)); 	
		    } 
		    else if(st.substring(0, 4).equals("PACM")){
		    	int pass = Integer.parseInt(st.substring(4));
		    	if(MasterServer.passExistConfirm(pass)){
		    		dos.writeUTF("PSCX"+Integer.toString(pass)); 	
		    	}
		    	else{
		    		dos.writeUTF("PSWX");
		    	}
		    }
		    else if(st.substring(0, 4).equals("NEWT")){
		    	MasterServer.addjob(st.substring(4));		    	
		    }
		    else if(st.substring(0, 4).equals("SEER")){		    	
		    	
		    	for(Userdata user:MasterServer.currentJobList)
				{
		    		if(user.getPassword().equals(MasterServer.clientid))
		    		{
					dos.writeUTF("REPO" + user.getFullData());
		    		}

				}	
		    	
		    }
		    else if(st.substring(0, 4).equals("ENDP")){		    	
		    	Userdata temp = new Userdata();
		    	temp.setjobid(st.substring(4));
		    	MasterServer.addDeleteJobList(temp);		    	
		    }
		    else if(st.substring(0, 4).equals("VIEW")){		    	
		    	Userdata temp = new Userdata();
		    	temp.setjobid(st.substring(4));
		    	MasterServer.addViewJobList(temp);		    	
		    }
		    
		    
		    
		   
	  }
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("You has disconnected");
		}
  }
}
