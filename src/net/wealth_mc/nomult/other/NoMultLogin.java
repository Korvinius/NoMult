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
		playerAddRemove(player, addrm);
	}

	private synchronized void playerAddRemove(Player p, boolean login) {
		if (login) {
			NoMult.playerlogin.add(p);
			if (!p.hasPermission("nomult.spy")) {
				NoMult.instance.getServer().broadcastMessage(ChatColor.GOLD + p.getName() 
						+ ChatColor.YELLOW + " " + NoMult.plogin);
			}
		}else if (NoMult.playerlogin.contains(p)) {
			NoMult.playerlogin.remove(p);
			if (!p.hasPermission("nomult.spy")) {
				NoMult.instance.getServer().broadcastMessage(ChatColor.GOLD + p.getName() 
						+ ChatColor.YELLOW + " " + NoMult.plogout);
			}
		}
	}

}
