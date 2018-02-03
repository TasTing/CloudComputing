
public class workerData {
	
	String WorkerUrl;
	int WorkerPort;
	int ProcessNum;
	boolean condition = false;
	String jobid;

	public workerData(){
		super();
	}
	public boolean getCondition(){
		return condition;
	}
	
	public int getPort(){
		return WorkerPort;
	}
	
	public String getjobid(){
		return jobid;
	}
	
	public String getWorkerUrl(){
		return WorkerUrl;
		
	}
	
	public int getProcessNum(){
		return ProcessNum;
	}
	
	public void setWorkerUrl(String WorkerUrl){
		this.WorkerUrl = WorkerUrl;
	}
	
	public void setWorkerPort(int WorkerPort){
		this.WorkerPort = WorkerPort;
	}
	
	public void setProcessNum(int ProcessNum){
		this.ProcessNum = ProcessNum;
	}
	
	public void setcondition(boolean condition){
		this.condition = condition;
	}
	
	public void setjobid(String jobid){
		this.jobid = jobid;
	}
}
