package net.wealth_mc.nomult.check;

import java.util.Map;

import net.wealth_mc.nomult.NoMult;

public class CheckMultPlayerIP {
	
	public boolean checkMultPlayerIP(String p, String ip) {
		
		for(Map.Entry<String, String> entry : NoMult.mult.entrySet()) {

			if(entry.getKey().equals(p) && entry.getValue().equals(ip)) {
				return true;
			}
		}
		return false;
	}

}
