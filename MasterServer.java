import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;




public class MasterServer {
	
	static String offsetUrl = "127.0.0.1";
	static int offsetPort = 1250;
	
	
	private static ServerSocket s;
	
	static LinkedList<passList> passList = new LinkedList<passList>();
	static LinkedList<Userdata> jobList = new LinkedList<Userdata>();
	static LinkedList<workerData> WorkerList = new LinkedList<workerData>();
	static LinkedList<Userdata> currentJobList = new LinkedList<Userdata>();
	static LinkedList<Userdata> deleteJobList = new LinkedList<Userdata>();
	static LinkedList<Userdata> viewJobList = new LinkedList<Userdata>();	
	static LinkedList<reportData> reportList = new LinkedList<reportData>();
	
	static String clientid;
	
	static int currentJobNumber = 0;
	
	public synchronized static reportData getReport(){
		System.out.println("getting new list");
		return reportList.removeFirst();
	}
	
	public synchronized static void addReportList(reportData data){
		System.out.println("adding new list");
		reportList.add(data);		
	}
	
	public synchronized static Userdata getViewJobList(){
		return viewJobList.removeFirst();
	}
	
	public synchronized static void addViewJobList(Userdata data){
		viewJobList.add(data);
	}
	
	public synchronized static void removeCurrentJob(String jobid){
		int i = 0;

		for(Userdata node:MasterServer.currentJobList){
			if(node.getjobid().equals(jobid)){
				
			    currentJobList.remove(i);
			    currentJobNumber--;
			    
				System.out.println(i);
			}
			
			
			i++;
		}
	}
	
	public synchronized static void removerWorkerReference(String jobid){
		for(workerData node:MasterServer.WorkerList){
			System.out.println(node.getjobid());
			if(node.getjobid().equals(jobid)){
				System.out.println("Deleting... ");
				node.setcondition(false);
				System.out.println("Deleting done ");
				//break;
			}
		}
	}

	public synchronized static workerData getWorkerReference(String jobid){		
		for(workerData node:MasterServer.WorkerList){
			if(node.getjobid().equals(jobid)){
				return node;
			}
		}
		//Should never come to this line
		System.out.println("Big Error2!!!!!");
		workerData nocoming = new workerData();
		return nocoming;
		
	}
	
	public synchronized static Userdata getDeleteJobList(){
		return deleteJobList.removeFirst();
	}
	
	public synchronized static void addDeleteJobList(Userdata data){
		deleteJobList.add(data);
	}
	
	public synchronized static boolean IsdeleteJobListEmpty(){
		if(deleteJobList.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public synchronized static void addCurrentJobList(Userdata data){
		currentJobList.add(data);
		currentJobNumber++;
	}
	
	public synchronized static workerData getWorker(String id){
		
		for(workerData worker:MasterServer.WorkerList)
		{
			if(worker.condition == false){
				worker.setjobid(id);
				worker.setcondition(true);
				return worker;
			}				
		}
		//Should never come to this line
		System.out.println("Big Error!!!!!");
		workerData nocoming = new workerData();
		return nocoming;
	}
	
	public synchronized static boolean IsAvailbleWorkerExist(){
		for(workerData worker:MasterServer.WorkerList)
		{
			if(worker.condition == false){
				return true;
			}			
		}
		return false;
	}
	
	public synchronized static void IniWorkerList(){
		
		String url = offsetUrl;
		int port = offsetPort;
		
		
		for(int i=0;i<1;i++){
			for(int j=0;j<3;j++){
				workerData tmp = new workerData();
				tmp.setWorkerUrl(url);
				tmp.setWorkerPort(port);
				tmp.setProcessNum(j);
				tmp.setjobid("00000");
				WorkerList.add(tmp);
			}
		}		
		
		
	}
	
public synchronized static void AddWorkerList(String data){
		
		String url = data;
		int port = offsetPort;
		
		
		for(int i=0;i<1;i++){
			for(int j=0;j<3;j++){
				workerData tmp = new workerData();
				tmp.setWorkerUrl(url);
				tmp.setWorkerPort(port);
				tmp.setProcessNum(j);
				tmp.setjobid("00000");
				WorkerList.add(tmp);
			}
		}		
		
		
	}
	
	public synchronized static Userdata getNewJob(){
		Userdata tmp = new Userdata();
		tmp = jobList.removeFirst();
		return tmp;
	}
	
	public synchronized static boolean IsjobListEmpty(){
		if(jobList.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public synchronized static void addjob(String data){
		Userdata tmp = new Userdata();
		Random r1 = new Random();
		int id1 = r1.nextInt(9999);
		int id2 = r1.nextInt(9999);
		tmp.putData(data);
		tmp.setjobid(Integer.toString(id1)+Integer.toString(id2));
		jobList.add(tmp);
	}
	
	public synchronized static void setClientid(String id){
		clientid = id;
	}
	
	public synchronized static void passListAdd(int pass){
		passList password = new passList(pass);
		passList.add(password);
	}
	
	public synchronized static boolean passExistConfirm(int pass){
		for(passList passlist:MasterServer.passList)
		{
			if(passlist.passExistConfirm(pass)){
				setClientid(Integer.toString(pass));
				return true;				
			}			
		}
		return false;
	}
	
	public static void main(String args[]) throws IOException {
		s = new ServerSocket(1254);
		IniWorkerList();
	    System.out.println("Server started");
		new SendingDecision().start();

	    while(true){
	    	 
	         Socket s1=s.accept(); // Wait and accept a connection
	         System.out.println("Server connected to "+s1.getPort());
	         new MasterServerRead(s1).start();
	         
	       
         }
	
     }
	
}
