package net.wealth_mc.nomult.check;

import java.util.Map;

import net.wealth_mc.nomult.NoMult;

public class CheckMultIP {
		
	public boolean checkMultIP(String ip) {
		
		for(Map.Entry<String, String> entry : NoMult.instance.mult.entrySet()) {		
			if(entry.getValue().equals(ip)) {
				return true;
			}
		}
		return false;
	}
	

}
