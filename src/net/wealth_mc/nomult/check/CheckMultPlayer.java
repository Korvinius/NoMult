package net.wealth_mc.nomult.check;

import java.util.Map;

import net.wealth_mc.nomult.NoMult;

public class CheckMultPlayer {
	
	public static boolean checkMultPlayer(String p) {
		
		for(Map.Entry<String, String> entry : NoMult.mult.entrySet()) {		
			if(entry.getKey().equals(p)) {
				return true;
			}
		}
		return false;
	}
}
