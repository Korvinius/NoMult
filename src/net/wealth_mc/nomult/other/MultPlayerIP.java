package net.wealth_mc.nomult.other;

import net.wealth_mc.nomult.NoMult;

public class MultPlayerIP{
	
	public synchronized void addMultPlayerIP(String p, String ip) {
		 try {
			 NoMult.instance.mult.put(p, new String(ip));
	     } catch (Exception e){
	     }
	}
}
