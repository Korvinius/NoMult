package net.wealth_mc.nomult.other;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.wealth_mc.nomult.NoMult;

public class NoMultLogin implements Runnable {

	private Player player;
	private boolean addrm;
	private Thread thread; 

	public NoMultLogin(Player p, boolean a) {
		this.player = p;
		this.addrm = a;
		thread = new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		if (addrm) {
			if (!NoMult.playerlogin.contains(player)) playerAdd(player);
		}else if (NoMult.playerlogin.contains(player)) {
			playerRemove(player);
		}
	}

	private void playerAdd(Player p) {
		NoMult.playerlogin.add(p);
		if (!p.hasPermission(NoMult.permSPY) && !p.hasPermission(NoMult.permADMIN)) {
			NoMult.instance.getServer().broadcastMessage(ChatColor.GOLD + p.getName() 
					+ ChatColor.YELLOW + " " + NoMult.plogin);
		}
	}

	private void playerRemove(Player p) {
		NoMult.playerlogin.remove(p);
		if (!p.hasPermission(NoMult.permSPY) && !p.hasPermission(NoMult.permADMIN)) {
			NoMult.instance.getServer().broadcastMessage(ChatColor.GOLD + p.getName() 
					+ ChatColor.YELLOW + " " + NoMult.plogout);
		}
	}
}
