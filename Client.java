
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;

public class Client extends Frame implements ActionListener {
	
    static int condition = 0;
    static int clientNumber;
    private TextField tfInput; // Single-line TextField to receive tfInput key
	private static TextArea taDisplay; // Multi-line TextArea to taDisplay result
	static String output;
	static String input;
	static Socket s1;
	static LinkedList<message> messageSend = new LinkedList<message>();
	static LinkedList<message> messagePrint = new LinkedList<message>();
    
    public void actionPerformed(ActionEvent evt) {
    	String input = tfInput.getText();
    	message tmp = new message(input);
    	messageSend.add(tmp);
    	
		taDisplay.append("You have typed " + tfInput.getText() + "\n");
		tfInput.setText("");
		// Assign Text Field to a String you going to outputstream to the master
		// node
		
	}
    
    public synchronized static void updateMessagePrint(String data){
    	messagePrint.add(new message(new String(data)));
    }
    
    public synchronized static void updateGUI(){    
    	
    	//System.out.println(messagePrint.removeFirst().getMessage());

    	taDisplay.append(messagePrint.removeFirst().getMessage());

    }
    
    public static String getInput(){
    	while(true){
    		System.out.println(" ");
    		if(!messageSend.isEmpty()){
    			return messageSend.removeFirst().getMessage();
    		}
    	}
    }
    
    public Client() {
		setLayout(new FlowLayout()); // "super" frame sets to FlowLayout

		add(new Label("Enter Here: "));
		tfInput = new TextField(50);
		add(tfInput);

		tfInput.addActionListener(this);

		taDisplay = new TextArea(20, 70); // 5 rows, 40 columns
		add(taDisplay);

		// tfInput TextField (source) fires KeyEvent.
		// tfInput adds "this" object as a KeyEvent listener.

		setTitle("Word Frequent Count Client"); // "super" Frame sets title
		setSize(600, 400); // "super" Frame sets initial size
		setVisible(true); // "super" Frame shows
	}
    
    public static void setClient(int client){
    	clientNumber = client;
    }
    
    public static void setCondition1(){
    	condition = 1;
    }
    
    public static void setCondition0(){
    	condition = 0;
    }
    
	public static void main(String args[]) throws IOException  {
		Socket s1 = new Socket("127.0.0.1",1254);
		LinkedList<Userdata> customerList = new LinkedList<Userdata>();
		
		
		OutputStream s1out = s1.getOutputStream();
    	DataOutputStream dos = new DataOutputStream (s1out);
    	
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		//messagePrint.add(new message(new String()));
		

		new GUIupdater().start();;
		new Client();
		
		new ClientRead(s1).start();
		messagePrint.add(new message(new String("Greeting: Welcome to Client Server!" + "\n")));
		
		while(condition == 0){  
			
			messagePrint.add(new message(new String("Do you have your own passcode? (y/n)" + "\n")));
			String message = getInput();			
			if(message.equals("y")){
				//If user already has passcode
				messagePrint.add(new message(new String("Please put your password" + "\n")));
				String passFromUser = getInput();
				passFromUser = "PACM" + passFromUser;
				messagePrint.add(new message(new String("Verifying....." + "\n")));
				dos.writeUTF(passFromUser);		
			
			}
			else if(message.equals("n")){
				//If user do not have a passcode
				messagePrint.add(new message(new String("Creating password....." + "\n")));
				String pass = "CreatPass";
				dos.writeUTF(pass);				
			}
			else{  
				//Input is wrong
				messagePrint.add(new message(new String("Wrong Input" + "\n")));
			}
			
		}	

		     
		while(condition == 1){
			messagePrint.add(new message(new String("Client " + clientNumber + ": " + "\n")));
			messagePrint.add(new message(new String("Operate by enter index number: " + "\n")));
			messagePrint.add(new message(new String("1: Create new Process" + "\n")));
			messagePrint.add(new message(new String("2: View existing Process" + "\n")));

			String message = getInput();
			
			if(message.equals("1")){
				Userdata task = new Userdata();
				task.setPassword(Integer.toString(clientNumber));
				messagePrint.add(new message(new String("Please enter the target URL:" + "\n")));
				String url = getInput();
				task.seturl(url);
				messagePrint.add(new message(new String("Please enter the target port:" + "\n")));
				String port = getInput();
				task.setsocketNumber(port);
				messagePrint.add(new message(new String("Please enter the wanted result number" + "\n")));
				String number = getInput();
				task.setcountNumber(number);
				messagePrint.add(new message(new String("data:" + task.getData() + "\n")));
				dos.writeUTF("NEWT" + task.getData());		
			}
			else if(message.equals("2")){
				dos.writeUTF("SEER");	
				messagePrint.add(new message(new String("Your current process: (Enter the jobid of the process you wants to view)" + "\n")));
				String Viewnumber = getInput();
				dos.writeUTF("VIEW" + Viewnumber);	
				messagePrint.add(new message(new String("Do you want to End this process (y/n)" + "\n")));
				String Ifend = getInput();
				if(Ifend.equals("y")){
					dos.writeUTF("ENDP" + Viewnumber);	
				}

				
			}
			else{
				messagePrint.add(new message(new String("Error: Wrong Input" + "\n")));
			}
		}
	}
}
