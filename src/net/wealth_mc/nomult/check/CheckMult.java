package net.wealth_mc.nomult.check;

import java.util.HashMap;
import java.util.Map;

import net.wealth_mc.nomult.NoMult;

public class CheckMult {
	
	public static boolean checkMult(String p, String ip) {
		Map<String, String> tempmult = new HashMap<String, String>();
		for(Map.Entry<String, String> entry : NoMult.mult.entrySet()) {
			String a = entry.getKey();
			String b = entry.getValue();
			if(entry.getValue().equals(ip) && !entry.getKey().equals(p)) {
				tempmult.put(a, b);
			}
		}
		if (tempmult.isEmpty()) {
			return false;
		}
		return true;
	}
}
