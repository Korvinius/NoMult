package net.wealth_mc.nomult.other;

import net.wealth_mc.nomult.NoMult;

public class NotMultPlayer {
	
	public synchronized void addNotMultPlayer(String p) {
			NoMult.instance.notmult.add(p);
	}
	
	public static boolean addNotMultPlayers(String p) {
		if (!NoMult.instance.notmult.contains(p)) {
			NoMult.instance.notmult.add(p);
			return true;
		}
		return false;
	}
	
	public static boolean rmNotMultPlayer(String p) {
		if (NoMult.instance.notmult.contains(p)) {
			 NoMult.instance.notmult.remove(p);
			 return true;
		 }
		 return false;			 
	}
	
	public static boolean replNotMultPlayer(String p1, String p2) {
		 if (NoMult.instance.notmult.contains(p1)) {
			 NoMult.instance.notmult.remove(p1);
			 NoMult.instance.notmult.add(p2);
			 return true;
		 }
		 return false;
	}
}