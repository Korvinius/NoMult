package net.wealth_mc.nomult.check;

import org.bukkit.entity.Player;

public class CheckPerm {
	
	public boolean ceckPerm(Player p, String perm) {
		if (p.hasPermission(perm)) {	
			return true;
		}
		return false; 
	}


}
