
public class Userdata {
	String password;
	String socketNumber;
	String countNumber;
	String url;
	String jobid;
	
	public Userdata(){
		super();
	}
	
	public void putData(String data){
		String[] dataS = data.split(" ");
		password = dataS[0];
		socketNumber = dataS[1];
		countNumber = dataS[2];
		url = dataS[3];

	}
	
	public String getData(){
		return password + " " + socketNumber + " " + countNumber + " " + url;
	}
	
	public void putFullData(String data){
		String[] dataS = data.split(" ");
		password = dataS[0];
		socketNumber = dataS[1];
		countNumber = dataS[2];
		url = dataS[3];
        jobid = dataS[4];
	}
	
	public String getFullData(){
		return password + " " + socketNumber + " " + countNumber + " " + url + " " + jobid;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setsocketNumber(String socketNumber){
		this.socketNumber = socketNumber;
	}
	public void setcountNumber(String countNumber){
		this.countNumber = countNumber;
	}
	public void seturl(String url){
		this.url = url;
	}
	public void setjobid(String jobid){
		this.jobid = jobid;
	}
    
	public String getPassword(){
		return password;
	}
	
	public String getsocketNumber(){
		return socketNumber;
	}
	public String getcountNumber(){
		return countNumber;
	}
	public String geturl(){
		return url;
	}
	public String getjobid(){
		return jobid;
	}

}
