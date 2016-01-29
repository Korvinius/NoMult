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
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.xephi.authme.events.LoginEvent;

public class NoMultList implements Listener {

	public NoMult plg;			
	public NoMultList(NoMult noMult) {
		this.plg = noMult;
	}

	@EventHandler
	public void onPlayerAchievementAwarded (PlayerAchievementAwardedEvent event) {
		Player p = event.getPlayer();
		if (!NoMult.playerischeck.contains(p)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onJoinPlayer (PlayerJoinEvent event) {
		if (NoMult.blockjoin) event.setJoinMessage(null);
	}

	@EventHandler
	   public void onQuitPlayer(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if (NoMult.blockleave) {
			event.setQuitMessage(null);
		}
		if (NoMult.playerischeck.contains(player)) {
			if (NoMult.blockleave 
					&& !player.hasPermission(NoMult.permSPY) 
					&& !player.hasPermission(NoMult.permADMIN)) {
				NoMult.instance.getServer().broadcastMessage(ChatColor.GOLD + player.getName() 
						+ ChatColor.YELLOW + " " + NoMult.plogout);
			}
			NoMult.playerischeck.remove(player);
		}
	}

	@EventHandler
	public void onAuthLoginEvent(LoginEvent event) {
		Player player = event.getPlayer();
		new NoMultRun(player);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamage(EntityDamageEvent event) {
		checkEntityDamage(event);
	}

	private void checkEntityDamage(EntityDamageEvent event) {
		if (NoMult.instance.pvp) {

			Player damager;
			Player taker;
			
			InetAddress dam = null;
			InetAddress tak = null;
			
			if (event instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent entity = (EntityDamageByEntityEvent) event;
				
				if ((entity.getDamager() instanceof Player)
						&& (entity.getEntity() instanceof Player)) {
					damager = (Player) entity.getDamager();
					taker = (Player) entity.getEntity();
					if (entity.getDamager() != null && taker.isOnline()) {
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
