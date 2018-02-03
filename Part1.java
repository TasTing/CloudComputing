
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.Map.Entry;


public class Part1 {
	private static <K, V extends Comparable<? super V>> List<Entry<K, V>> findGreatest(Map<K, V> map, int n) {
		Comparator<? super Entry<K, V>> comparator = new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e0, Entry<K, V> e1) {
				V v0 = e0.getValue();
				V v1 = e1.getValue();
				return v0.compareTo(v1);
			}
		};
		PriorityQueue<Entry<K, V>> highest = new PriorityQueue<Entry<K, V>>(n, comparator);
		for (Entry<K, V> entry : map.entrySet()) {
			highest.offer(entry);
			while (highest.size() > n) {
				highest.poll();
			}
		}

		List<Entry<K, V>> result = new ArrayList<Map.Entry<K, V>>();
		while (highest.size() > 0) {
			result.add(highest.poll());
		}
		return result;
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		String socket = args[0];
		String url = args[1];
		String number = args[2];
		int num = Integer.parseInt(number);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		Socket s1 = new Socket(url, Integer.parseInt(socket));
		while (true) {
			DataInputStream input = new DataInputStream(s1.getInputStream());
			String inputstream = new String(input.readUTF());
			String[] words = inputstream.split("\\s+");
			for (int i = 0; i < words.length; i++) {
				words[i] = words[i].replaceAll("[^\\w]", "").toLowerCase();
				// System.out.println("Word=" + words[i]);
				// Create a map to store the words
				if (!map.containsKey(words[i]) && !words[i].isEmpty()) {
					map.put(words[i], 0);
				}
				Iterator<Entry<String, Integer>> ite = map.entrySet().iterator();
				while (ite.hasNext()) {
					Entry<String, Integer> entry = ite.next();
					if (entry.getKey().equals(words[i])) {
						entry.setValue((Integer) entry.getValue() + 1);
					}
					if (!entry.getKey().equals(words[i])) {
						if (map.size() > 10) {
							// System.out.println(map.size());
							entry.setValue((Integer) entry.getValue() - 1);
						}
					}
					if ((Integer) entry.getValue() == 0) {
						ite.remove();
					}
				}
				List<Entry<String, Integer>> greatest = findGreatest(map,num);
				String great = "";
		        for (Entry<String, Integer> entry : greatest)
		        {
		        	great+=" "+entry.getKey()+" ";
		        }
		        System.out.println(great);
			}
		}
	}
}