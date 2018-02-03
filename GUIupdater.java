
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class GUIupdater extends Thread {


  public GUIupdater() {
	super();

  }
  public void run()
  {
	 
	  while(true)
	  {     
		  if(!Client.messagePrint.isEmpty()){
			  Client.updateGUI();
		  }
		  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

  }
}
