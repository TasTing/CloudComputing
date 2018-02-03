import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.io.*;


public class StreamSever {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		ServerSocket ss = new ServerSocket(1200);
		System.out.println("Server started");
		while(true){
			Socket s1 = ss.accept();
			System.out.println("Server connected to " + s1.getPort());
			new ServerThread(s1).start();
		}
	}
}
