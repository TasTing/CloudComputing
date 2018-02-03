

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SendingDecision extends Thread{

	static boolean IsbuildingWorker = false;
	static boolean IsNewWorkerSet = false;
	
	public SendingDecision() {
		super();
	}
	
	public void run()
	{
		JCloudsNova newWorker = new JCloudsNova();
		System.out.println("Start detecting...");
		while(true){			
			
			if(!MasterServer.IsjobListEmpty()){
				
				Userdata Usertmp = new Userdata();
				Usertmp = MasterServer.getNewJob();
				
				workerData workertmp = new workerData();
				
			    if(MasterServer.IsAvailbleWorkerExist()){
			    	
			    	workertmp = MasterServer.getWorker(Usertmp.getjobid());
			    	
			    	System.out.println("url: " + workertmp.getWorkerUrl() + " ProcessNum " + workertmp.getProcessNum() + "jobid: " + workertmp.getjobid());
			    	
			    	
			    	try {
						Socket s1 = new Socket(workertmp.getWorkerUrl(),workertmp.getPort());
						OutputStream s1out = s1.getOutputStream();
						DataOutputStream dos = new DataOutputStream (s1out);
						
						new MasterListerner(s1).start();
						
						String order = "NEWP" + Usertmp.getFullData();
						dos.writeUTF(order);	

						MasterServer.addCurrentJobList(Usertmp);
						
						//s1.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			    else if(IsbuildingWorker){
			    	System.out.println("Worker is building");
			    	try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			    else{
			    	//no available Worker
			    	System.out.println("no available Worker");
			    }				
				
			}
			else if(!MasterServer.IsdeleteJobListEmpty()){
				System.out.println("Delete table is not empty");
				Userdata Usertmp = new Userdata();
				Usertmp = MasterServer.getDeleteJobList();
				
				workerData Worktmp = new workerData();
				Worktmp = MasterServer.getWorkerReference(Usertmp.getjobid());
				
				MasterServer.removeCurrentJob(Usertmp.getjobid());
				MasterServer.removerWorkerReference(Usertmp.getjobid());
				
				try {
					Socket s1 = new Socket(Worktmp.getWorkerUrl(),Worktmp.getPort());
					OutputStream s1out = s1.getOutputStream();
					DataOutputStream dos = new DataOutputStream (s1out);
					new MasterListerner(s1).start();
					String order = "RMVP " + Usertmp.getjobid();
					dos.writeUTF(order);		

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else if(!MasterServer.viewJobList.isEmpty()){
				Userdata Usertmp = new Userdata();
				Usertmp = MasterServer.getViewJobList();
				
				workerData Worktmp = new workerData();
				Worktmp = MasterServer.getWorkerReference(Usertmp.getjobid());
				
				
				
				try {
					Socket s1 = new Socket(Worktmp.getWorkerUrl(),Worktmp.getPort());
					OutputStream s1out = s1.getOutputStream();
					DataOutputStream dos = new DataOutputStream (s1out);
					new MasterListerner(s1).start();
					
					String order = "VIEW " + Usertmp.getjobid();
					dos.writeUTF(order);		

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else if(!MasterServer.IsAvailbleWorkerExist() && !IsbuildingWorker){
				System.out.println("job full : Creating new worker");
				IsbuildingWorker = true;				
				newWorker.createServer();
			}
			else if(IsbuildingWorker){
				
				if(newWorker.GetState() == "ACTIVE"){
					System.out.println("new worker created");
					MasterServer.AddWorkerList(newWorker.GetServerIp4());
					IsbuildingWorker = false;
				}
				else if(newWorker.GetState() == "ERROR"){
					IsbuildingWorker = false;
				}
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			
			
			
		}	
		//End while(True)
	}
	//End Public void run()
}
