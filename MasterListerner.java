import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MasterListerner extends Thread {
   Socket s1;

  public MasterListerner(Socket s1) {
	super();
	this.s1 = s1;
  }
  public void run()
  {
	  try {
		  InputStream s1In;
	  while(true)
	  {     
		    s1In = s1.getInputStream();		   
		    DataInputStream dis = new DataInputStream(s1In);
		    String st = new String (dis.readUTF());
		    System.out.println("From worker: " + st);
		    if(st.substring(0, 4).equals("RETU")){		    	
		    	reportData temp = new reportData();
		    	temp.putdata(st.substring(4));
		    	System.out.println("put in" + temp.getdata());
		    	MasterServer.addReportList(temp);		    	
		    }
		    else if(st.substring(0, 4).equals("SUCC")){		    	
		    	reportData temp = new reportData();
		    	temp.putdata(st.substring(4));
		    	System.out.println("put in" + temp.getdata());
		    	MasterServer.addReportList(temp);		    	
		    }

		    
		    
	  }
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("You has disconnected");
		}
  }
}
