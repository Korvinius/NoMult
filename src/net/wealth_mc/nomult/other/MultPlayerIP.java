package net.wealth_mc.nomult.other;

import java.util.Iterator;
import java.util.Map;
import net.wealth_mc.nomult.NoMult;

public class MultPlayerIP{
	
	public static synchronized void addMultPlayerIP(String p, String ip) {
		 try {
			 NoMult.mult.put(p, new String(ip));
	     } catch (Exception e){
	     }
	}

	public static boolean rmMultPlayer(String p) {
		for(Iterator<Map.Entry<String, String>> it = NoMult.mult.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<String, String> entry = it.next();
			if(entry.getKey().equals(p)) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	public static boolean rmMultIP(String ip) {
		boolean is = false;
		for(Iterator<Map.Entry<String, String>> it = NoMult.mult.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<String, String> entry = it.next();
			if(entry.getValue().equals(ip)) {
				it.remove();
				is = true;
			}
		}
		return is;
	}
}
