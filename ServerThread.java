import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class ServerThread extends Thread {

	Socket s1;

	public ServerThread(Socket s1) {
		super();
		this.s1 = s1;
	}

	public void run() {
		while (true) {
			try {
				DataOutputStream output = new DataOutputStream(s1.getOutputStream());
				String words[] = { "Hello How are you", "Hi Good day", "Great to hear that", "Hi I am Frank",
						"I Am Busy", "I am Angry", "fine, How are you", "How about go to have lunch", "About what?",
						"You are genius!","For a thread to stop itself", "no one seems to have mentioned",
						"You should not kill Thread from other one. It's considered as fairly bad habit. However, there are many ways."};
				Thread.sleep(1000);
				Random random = new Random();
				int postion = random.nextInt(9 - 0 + 1) + 0;
				output.writeUTF(words[postion]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}