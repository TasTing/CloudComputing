
public class reportData {
	String jobid;
	String result;
	String time;

	public reportData(){
		super();
		
	}
	
	public void putdata(String data){
		String[] tmp = data.split(":");
		this.jobid = tmp[1];
		this.result = tmp[2];
		this.time = tmp[3];
		
	
	}
	
	public String getdata(){
	    return " Jobid: " + jobid +  " Your Result is: " + result + " Time Spend: " + time;
	}
	
	private String getprice(String time){
		int tmp = Integer.parseInt(time);
		tmp = tmp*3;
		
		return Integer.toString(tmp);
	}
	
	public String getmoneydata(){
		return " Jobid: " + jobid +  " Your Result is: [" + result + "]  Time Spend: " + time + "s  Cost: " + getprice(time) + "$";
	}
}
