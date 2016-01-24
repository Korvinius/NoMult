package net.wealth_mc.nomult.other;

import net.wealth_mc.nomult.NoMult;

public class NotMultPlayer {
	
	public static synchronized void addNotMultPlayer(String p) {
			NoMult.notmult.add(p);
	}
	
	public static boolean addNotMultPlayers(String p) {
		if (!NoMult.notmult.contains(p)) {
			NoMult.notmult.add(p);
			return true;
		}
		return false;
	}
	
	public static boolean rmNotMultPlayer(String p) {
		if (NoMult.notmult.contains(p)) {
			 NoMult.notmult.remove(p);
			 return true;
		 }
		 return false;			 
	}
	
	public static boolean replNotMultPlayer(String p1, String p2) {
		 if (NoMult.notmult.contains(p1)) {
			 NoMult.notmult.remove(p1);
			 NoMult.notmult.add(p2);
			 return true;
		 }
		 return false;
	}
}