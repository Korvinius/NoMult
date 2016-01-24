package net.wealth_mc.nomult.other;

import java.util.Iterator;
import java.util.Map;

import net.wealth_mc.nomult.NoMult;

public class NoMultGroups {
	
	public String inOldGroupPlayer(String p){
		String group = null;
		try {
		for(Map.Entry<String, String> entry : NoMult.groups.entrySet()) {
			if(entry.getKey().equals(p)) {
				group = entry.getValue();
			} else group = null; 
		}    
		} catch (Exception e){
	     }
		return group;
    }
	
	public static synchronized String inOldGroupPlayerRM(String p){
		String dgrp = NoMult.defgroup;
		String group = null;
		try {
		for(Map.Entry<String, String> entry : NoMult.groups.entrySet()) {
			if(entry.getKey().equals(p)) {
				group = entry.getValue();
				NoMult.groups.remove(p);
			} else group = dgrp; 
		} 
		} catch (Exception e){
	     }
		return group;
    }

	public static boolean delPlayer(String p){
		for(Iterator<Map.Entry<String, String>> it = NoMult.groups.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<String, String> entry = it.next();
			if(entry.getKey().equals(p)) {
				it.remove();
				return true;
			}
		}
		return false;
	}
}
