package net.wealth_mc.nomult.check;

import net.wealth_mc.nomult.NoMult;

public class CheckNotMult {
	
	public boolean checkNotMult(String p) {
		if (NoMult.instance.notmult.contains(p)) {
			return true;
		}		
		return false;				
	}
}
