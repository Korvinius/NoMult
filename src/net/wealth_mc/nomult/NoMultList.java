package net.wealth_mc.nomult;

import java.net.InetAddress;

import net.wealth_mc.nomult.other.NoMultRun;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.xephi.authme.events.LoginEvent;
import fr.xephi.authme.events.LogoutEvent;

public class NoMultList implements Listener {

	public NoMult plg;			
	public NoMultList(NoMult noMult) {
		this.plg = noMult;
	}

	@EventHandler
	public void onJoinPlayer (PlayerJoinEvent event) {
		if (NoMult.blockjoin) event.setJoinMessage(null);
	}

	@EventHandler
	   public void onQuitPlayer(PlayerQuitEvent event){
		if (NoMult.blockleave) event.setQuitMessage(null);
	}

	@EventHandler
	public void onAuthLoginEvent(LoginEvent event) {
		if (NoMult.pjoin) checkAuthLoginEvent(event);
	}

	@EventHandler
	public void onAuthLogoutEvent(LogoutEvent event) {
		checkAuthLogoutEvent(event);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamage(EntityDamageEvent event) {
		checkEntityDamage(event);
	}

	private void checkAuthLogoutEvent(LogoutEvent event) {
		if (!event.getPlayer().hasPermission("nomult.spy")) {
			NoMult.instance.getServer().broadcastMessage(ChatColor.GOLD + event.getPlayer().getName() 
					+ ChatColor.YELLOW + NoMult.plogout);
		}
	}
	private void checkAuthLoginEvent(LoginEvent event) {
		Player player = event.getPlayer();
		if (!event.getPlayer().hasPermission("nomult.spy")) {
			NoMult.instance.getServer().broadcastMessage(ChatColor.GOLD + event.getPlayer().getName() 
					+ ChatColor.YELLOW + NoMult.plogin);
		}
		new NoMultRun(player);
	}

	private void checkEntityDamage(EntityDamageEvent event) {
		if (NoMult.instance.pvp) {

			Player damager;
			Player taker;
			
			InetAddress dam = null;
			InetAddress tak = null;
			
			if (event instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;
				EntityDamageByEntityEvent edbi = (EntityDamageByEntityEvent) event;
				
				if ((edbe.getDamager() instanceof Player)
						&& (edbi.getEntity() instanceof Player)) {
					damager = (Player) edbe.getDamager();
					taker = (Player) edbi.getEntity();
					if (edbi.getDamager() != null && taker.isOnline()) {
						dam = damager.getAddress().getAddress();
						tak = taker.getAddress().getAddress();
						if (dam.equals(tak)){
							damager.sendMessage(ChatColor.DARK_RED + "[NoMult]: "
									+ ChatColor.WHITE + damager.getName() + " IP: "
									+ dam + ", попытался нанести урон для " + taker.getName()
									+ " IP: " + tak + ", PVP между мультиаккаунтами отключено!");
							taker.sendMessage(ChatColor.DARK_RED + "[NoMult]: "
									+ ChatColor.WHITE + damager.getName() + " IP: "
									+ dam + ", попытался нанести урон для " + taker.getName()
									+ " IP: " + tak + ", PVP между мультиаккаунтами отключено!");
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
