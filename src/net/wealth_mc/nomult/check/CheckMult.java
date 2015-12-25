package net.wealth_mc.nomult.check;

import java.util.HashMap;
import java.util.Map;

import net.wealth_mc.nomult.NoMult;

public class CheckMult {
	
	public boolean checkMult(String p, String ip) {
		Map<String, String> tempmult = new HashMap<String, String>();
//		try {
			for(Map.Entry<String, String> entry : NoMult.instance.mult.entrySet()) {
				String a = entry.getKey();
				String b = entry.getValue();
				if(entry.getValue().equals(ip) && !entry.getKey().equals(p)) {					
					tempmult.put(a, b);			
				}
			}		
//		} catch (Exception e){		
//		}
		if (tempmult.isEmpty()) {
		return false;
		}
		return true;
	}

}
