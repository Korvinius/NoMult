package net.wealth_mc.nomult.other;

import java.util.Map;

import net.wealth_mc.nomult.NoMult;

public class NoMultGroups {
	
	public String inOldGroupPlayer(String p){
		String group = null;
		try {
		for(Map.Entry<String, String> entry : NoMult.instance.groups.entrySet()) {
			if(entry.getKey().equals(p)) {
				group = entry.getValue();
			} else group = null; 
		}    
		} catch (Exception e){
	     }
		return group;
    }
	
	public synchronized String inOldGroupPlayerRM(String p){
		String dgrp = NoMult.instance.defgroup;
		String group = null;
		try {
		for(Map.Entry<String, String> entry : NoMult.instance.groups.entrySet()) {
			if(entry.getKey().equals(p)) {
				group = entry.getValue();
				NoMult.instance.groups.remove(p);
			} else group = dgrp; 
		} 
		} catch (Exception e){
	     }
		return group;
    }
	
	public synchronized void delOldGroupPlayer(String p){
		for(Map.Entry<String, String> entry : NoMult.instance.groups.entrySet()) {
			if(entry.getKey().equals(p)) {
				NoMult.instance.groups.remove(p);
			}
		}
    }

	public static boolean delPlayer(String p){
		boolean is = false;
		for(Map.Entry<String, String> entry : NoMult.instance.groups.entrySet()) {
			if(entry.getKey().equals(p)) {
				NoMult.instance.groups.remove(p);
			is = true;	
			}
		}
		return is;
	}
}
