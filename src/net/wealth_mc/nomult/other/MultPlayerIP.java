package net.wealth_mc.nomult.other;

import java.util.Map.Entry;

import net.wealth_mc.nomult.NoMult;

public class MultPlayerIP{
	
	public synchronized void addMultPlayerIP(String p, String ip) {
		 try {
			 NoMult.instance.mult.put(p, new String(ip));
	     } catch (Exception e){
	     }
	}

	public static boolean rmMultPlayer(String p) {
		boolean is = false;
		for(Entry<String, String> entry : NoMult.instance.mult.entrySet()) {
			if(entry.getValue().equals(p)) {
				NoMult.instance.mult.remove(entry);
				is = true;
			}
		}
		return is;
	}

	public static boolean rmMultIP(String ip) {
		boolean is = false;
		for(Entry<String, String> entry : NoMult.instance.mult.entrySet()) {
			if(entry.getKey().equals(ip)) {
				NoMult.instance.mult.remove(entry);
				is = true;
			}
		}
		return is;
	}
}
