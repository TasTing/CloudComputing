import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class Workernode {

	static Map<String, Integer> statusmap = new HashMap<String, Integer>();
	static Map<String, String> resultmap = new HashMap<String, String>();
	static Map<String, Integer> durationmap = new HashMap<String, Integer>();
	static Map<String, Boolean> errormap = new HashMap<String, Boolean>();

	// private synchronized static void printLines(String name, InputStream ins,
	// String jobid) throws Exception {
	// String line = null;
	// BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	// while ((line = in.readLine()) != null) {
	// resultmap.put(jobid, line);
	// System.out.println(name + " " + line);
	// }
	// }

	// public synchronized static void runProcess(String command, String jobid)
	// throws Exception {
	// Process pro = Runtime.getRuntime().exec(command);
	// Boolean status=map.get(jobid);
	// printLines(command + " stdout:", pro.getInputStream(), jobid);
	// printLines(command + " stderr:", pro.getErrorStream(), jobid);
	// //printmap(process1);
	// pro.waitFor();
	// if (status==false){
	// Runtime.getRuntime().exit(0);
	// }
	// System.out.println(command + " exitValue() " + pro.exitValue());
	// }

	public synchronized static void putinresultmap(Map<String, String> resultmap2, String str, String str1) {
		resultmap2.put(str, str1);
	}

	public synchronized static void putinstatusmap(Map<String, Integer> map2, String str, Integer boo) {
		map2.put(str, boo);
	}

	public synchronized static void putinrunnningtime(Map<String, Integer> map3, String str, Integer duration) {
		map3.put(str, duration);
	}

	public synchronized static void putinerrormap(Map<String, Boolean> map4, String str, Boolean boo) {
		map4.put(str, boo);
	}

	public static int getpid(Process pro) {
		if (pro.getClass().getName().equals("java.lang.UNIXProcess")) {
			/* get the PID on unix/linux systems */
			try {
				Field f = pro.getClass().getDeclaredField("pid");
				f.setAccessible(true);
				int pid = f.getInt(pro);
				return pid;
			} catch (Throwable e) {
			}
		}
		return 0;
	}

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		// String serversocket = args[0];
		ServerSocket ss = new ServerSocket(1250);
		System.out.println("Server Started");
		while (true) {
			// waiting for socket connect
			Socket s1 = ss.accept();
			System.out.println("Worker node connected to " + s1.getPort());
			DataOutputStream output = new DataOutputStream(s1.getOutputStream());
			output.writeUTF("Welcome to workernode 1");
			// waiting for server input
			DataInputStream input = new DataInputStream(s1.getInputStream());
			String line = new String(input.readUTF());
			// split server input
			// String jobid="whatfuck";
			// String countnumber = "3";
			// String socketnumber = "1200";
			// String url = "LocalHost";
			//
			// String jobid1="whatfuck1";
			// String countnumber1 = "4";
			// String socketnumber1 = "1201";
			// String url1 = "LocalHost";
			if (line.substring(0, 4).equals("NEWP")) {
				String field[] = line.split(" ");
				String password = field[0];
				String socketnumber = field[1];
				String countnumber = field[2];
				String url = field[3];
				String jobid = field[4];
				// if start process
				// change status to on
				if (!statusmap.containsKey(jobid)) {
					putinstatusmap(statusmap, jobid, 1);
					new ProcessThread(jobid, countnumber, socketnumber, url).start();
					output.writeUTF("SUCC" + ":" + jobid + ":" + "Job initializing");
					Thread.sleep(3000);
					if (!errormap.containsKey(jobid)) {
						output.writeUTF("SUCC" + ":" + jobid + ":" + "Job is initialized");
					} else {
						output.writeUTF("ERRO" + ":" + jobid + ":" + "Job cannot initialized");
						Iterator<Entry<String, Integer>> ite = statusmap.entrySet().iterator();
						while (ite.hasNext()) {
							Entry<String, Integer> entry = ite.next();
							if (entry.getKey().equals(jobid)) {
								ite.remove();
							}
						}
						Iterator<Entry<String, String>> ite1 = resultmap.entrySet().iterator();
						while (ite1.hasNext()) {
							Entry<String, String> entry = ite1.next();
							if (entry.getKey().equals(jobid)) {
								ite1.remove();
							}
						}
						Iterator<Entry<String, Boolean>> ite2 = errormap.entrySet().iterator();
						while (ite2.hasNext()) {
							Entry<String, Boolean> entry = ite2.next();
							if (entry.getKey().equals(jobid)) {
								ite2.remove();
							}
						}
					}
				} else {
					output.writeUTF("ERRO" + ":" + jobid + ":" + "Job is already running");
				}
				// putinstatusmap(map,jobid1,true);
				// new
				// ProcessThread(jobid1,countnumber1,socketnumber1,url1).start();
			}
			if (line.substring(0, 4).equals("RMVP")) {
				String field[] = line.split(" ");
				String jobid = field[1];
				if (resultmap.containsKey(jobid)) {
					String finalresult = resultmap.get(jobid);
					Integer runduration = durationmap.get(jobid);
					output.writeUTF("RETU" + ":" + jobid + ":" + finalresult + ":" + runduration);
					putinstatusmap(statusmap, jobid, 0);
					Iterator<Entry<String, String>> ite1 = resultmap.entrySet().iterator();
					while (ite1.hasNext()) {
						Entry<String, String> entry = ite1.next();
						if (entry.getKey().equals(jobid)) {
							ite1.remove();
						}
					}
					output.writeUTF("SUCC" + ":" + jobid + ":" + "Job is removed");
				} else {
					output.writeUTF("ERRO" + ":" + jobid + ":" + "No job is running");
				}

				// if stop process
				// change specific status to stop thread.
				// post specific result

			}
			if (line.substring(0, 4).equals("VIEW")) {
				// if check process
				// post status of process
				String field[] = line.split(" ");
				String jobid = field[1];
				if (resultmap.containsKey(jobid)) {
					String nowresult = resultmap.get(jobid);
					Integer runduration = durationmap.get(jobid);
					output.writeUTF("RETU" + ":" + jobid + ":" + nowresult + ":" + runduration);
				} else {
					output.writeUTF("ERRO" + ":" + jobid + ":" + "No job is running");
				}
				// post specific result

			}
			s1.close();
		}
	}
}