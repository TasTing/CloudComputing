import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ProcessThread extends Thread {

	String jobid;
	String socket;
	String url;
	String number;
	static long start;
	static long end;

	public ProcessThread(String id, String num, String socket, String url) {
		super();
		this.jobid = id;
		this.number = num;
		this.url = url;
		this.socket = socket;
	}

	public void run() {
		start = System.nanoTime();
		try {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			// runProcess("cd C:\Users\Moxi\workspace\CloudComput\src");
			System.out.println("Process starting...");
			runProcess("javac Part1.java", jobid);
			runProcess("java Part1 " + socket + "   " + url + "   " + number + "   ", jobid);
			System.out.println("Process started...");
			Workernode.putinerrormap(Workernode.errormap, jobid, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void runProcess(String command, String jobid) throws Exception {
		Process pro = Runtime.getRuntime().exec(command);
		printLines(command + " stdout:", pro.getInputStream(), jobid);
		printLines(command + " stderr:", pro.getErrorStream(), jobid);
		// printmap(process1);
		// pro.waitFor();
		System.out.println(command + " exitValue() " + pro.exitValue());
	}

	private static void printLines(String name, InputStream ins, String jobid) throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		int run = 0;
		while ((line = in.readLine()) != null && run==0) {
			Integer status = Workernode.statusmap.get(jobid);
			//System.out.println(status);
			if (status == 0) {
				System.out.println("Command line forced to stop");
				Iterator<Entry<String, Integer>> ite = Workernode.statusmap.entrySet().iterator();
				while (ite.hasNext()) {
					Entry<String, Integer> entry = ite.next();
					if (entry.getKey().equals(jobid)) {
						ite.remove();
					}
				}
				run=1;
			}

			Workernode.putinresultmap(Workernode.resultmap, jobid, line);
			// System.out.println(Workernode.resultmap.get(jobid));
			// System.out.println("checkstatus");
			end = System.nanoTime();
			int duration1 = longToInt((end - start) / 1000000);
			int duration = duration1 / 1000;// seconds
			Workernode.putinrunnningtime(Workernode.durationmap, jobid, duration);
		}
	}

	public static int longToInt(long theLongOne) {
		return Long.valueOf(theLongOne).intValue();
	}
}
