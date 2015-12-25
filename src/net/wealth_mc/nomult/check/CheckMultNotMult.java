package net.wealth_mc.nomult.check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.wealth_mc.nomult.NoMult;

public class CheckMultNotMult {
	
	public boolean checkMultNotMult(String p, String ip) {
		for(Map.Entry<String, String> entry : NoMult.instance.mult.entrySet()) {
			String a = entry.getKey();
			if(entry.getValue().equals(ip) && !entry.getKey().equals(p)) {					
				if (NoMult.instance.notmult.contains(a)) {
					return true;
				}			
			}
		}
		return false;						
	}
	
	public List<String> getMultMult(String p) {
		String ip = null;
		ArrayList<String> m = new ArrayList<String>();
		for(Map.Entry<String, String> entry : NoMult.instance.mult.entrySet()) {
			if (entry.getKey().equals(p)) ip = entry.getValue();
		}
		for(Map.Entry<String, String> entry : NoMult.instance.mult.entrySet()) {
			if (entry.getValue().equals(ip)) m.add(entry.getKey());
		}
//		if (NoMult.instance.debug) NoMult.instance.getLogger().info("Проверка списка мультов игрока: " + p + " найдено: " + m.toString());
		return m;						
	}
	
	public List<String> getNotMult(List<String> p) {
		ArrayList<String> m = new ArrayList<String>();
		for(int i = 0; i < p.size(); i++) {
			if (NoMult.instance.notmult.contains(p.get(i))) {
				m.add(p.get(i));
			}
		}
		return m;						
	}
}
