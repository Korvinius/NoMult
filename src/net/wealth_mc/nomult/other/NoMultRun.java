package net.wealth_mc.nomult.other;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.wealth_mc.nomult.NoMult;
import net.wealth_mc.nomult.check.CheckMult;
import net.wealth_mc.nomult.check.CheckMultNotMult;
import net.wealth_mc.nomult.check.CheckMultPlayer;
import net.wealth_mc.nomult.check.CheckNotMult;
import net.wealth_mc.nomult.check.CheckPerm;

public class NoMultRun implements Runnable {
	
	private Player player;
	private Thread thread;
	private String ppn;
	private String pip; 

	public NoMultRun(Player p) {
		this.player = p;
		this.pip = p.getAddress().getAddress().toString();
		this.ppn = p.getName();
		thread = new Thread(this, "RunLogin: " + p.getName());
		thread.start();
	}
	
	@Override
	public void run() {
		String ign = "nomult.ignore";
		String pri = "nomult.priority";  
		String adm = "nomult.admin";
		String group = null;
		String mult = NoMult.instance.ngroup;
		String dgrp = NoMult.instance.defgroup;
		
		CheckNotMult checknotmult = new CheckNotMult();
		CheckPerm checkperm = new CheckPerm();    
		CheckMult checkmult = new CheckMult();
		CheckMultPlayer checkmupl = new CheckMultPlayer();
		CheckMultNotMult multnotmult = new CheckMultNotMult();
		
		NotMultPlayer notmultplayer = new NotMultPlayer();
		MultPlayerIP multplayerip = new MultPlayerIP();
		NoMultVault nmvault = new NoMultVault();
		NoMultGroups checkgroups = null;
		
		if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " залогинился на сервере с адресом: " + pip + " Начинаем проверку ...");
		if (nmvault.playerInGroup(player, mult)) {
// ************* если игрок в группе мультов, убираем его оттуда ***************************
			checkgroups = new NoMultGroups();
			String ogroup = checkgroups.inOldGroupPlayerRM(ppn);
			nmvault.playerRemoveGroup(player, mult);
			if (!ogroup.equals(dgrp)) {
				nmvault.playerAddGroup(player, ogroup);
				}
			if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок " + ppn + " был в списке мультов, в начале проверки возвращена группа: " + ogroup);							
		}
		
		if (checkperm.ceckPerm(player, ign) || checkperm.ceckPerm(player, adm)) {
// ************* если у игрока есть права - "nomult.ignore" ********************************
			if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " Имеет право: - " + ign + " Проверка завершена!");			
			return;						
		}
		
		if (!checkperm.ceckPerm(player, pri)) {
			if (checkmult.checkMult(ppn, pip)) {
				if (checknotmult.checkNotMult(ppn)) {
					if (multnotmult.checkMultNotMult(ppn, pip)) {
//***************************** ОБНАРУЖЕН МУЛЬТИАКАУНТ!!! *********************************		
						if (NoMult.instance.debug) NoMult.instance.getLogger().info("адрес: " + pip + " приствуют мульты, игрок: " + ppn + " есть в базе (Не Мульт), но есть совпадения по IP с другими игроками категории (Не Мульт)");
						multplayerip.addMultPlayerIP(ppn, pip);
						if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " обновлен в базе (Nick-IP) с адресом: " + pip);
						group = nmvault.getPrimaryGroup(player);								
						nmvault.playerRemoveGroup(player, group);
						nmvault.playerAddGroup(player, mult);
						if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игроку " + ppn + " изменена группа, на: " + mult);
						try {																			
							NoMult.instance.groups.put(ppn, group);					
					     } catch (Exception e){
					     }
						new BukkitRunnable() {
				            @Override
				            public void run() {
				            	NoMult.instance.getServer().broadcastMessage(ChatColor.GRAY + ppn 
				            			+ " " + NoMult.plmult);
				            }
				        }.runTaskLater(NoMult.instance, 2);
						return;
// ********************* ПО ОБНАРУЖЕННОМУ МУЛЬТИАКАУНТУ РАБОТА ЗАВЕРШЕНА *********************								
					}
					multplayerip.addMultPlayerIP(ppn, pip);
					if (NoMult.instance.debug) NoMult.instance.getLogger().info("адрес: " + pip + " приствуют мульты, но игрок: " + ppn + " есть в базе (Не Мульт)");
					return;
				}
//***************************** ОБНАРУЖЕН МУЛЬТИАКАУНТ!!! *********************************
				if (NoMult.instance.debug) NoMult.instance.getLogger().info("адрес: " + pip + " приствуют мульты, игрок: " + ppn + " есть в базе (Не Мульт), но есть совпадения по IP с другими игроками категории (Не Мульт)");
				multplayerip.addMultPlayerIP(ppn, pip);
				if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " обновлен в базе (Nick-IP) с адресом: " + pip);
				group = nmvault.getPrimaryGroup(player);								
				nmvault.playerRemoveGroup(player, group);
				nmvault.playerAddGroup(player, mult);
				if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игроку " + ppn + " изменена группа, на: " + mult);
				new BukkitRunnable() {
		            @Override
		            public void run() {
		            	NoMult.instance.getServer().broadcastMessage(ChatColor.GRAY + ppn 
		            + " " + NoMult.plmult);
		            }
		        }.runTaskLater(NoMult.instance, 2);
				try {																			
					NoMult.instance.groups.put(ppn, group);					
			     } catch (Exception e){
			     }										
				return;
// ********************* ПО ОБНАРУЖЕННОМУ МУЛЬТИАКАУНТУ РАБОТА ЗАВЕРШЕНА *********************	
			}
			if (!checkmupl.checkMultPlayer(ppn)) {
				notmultplayer.addNotMultPlayer(ppn);
				multplayerip.addMultPlayerIP(ppn, pip);
				if (NoMult.instance.debug) {
				NoMult.instance.getLogger().info("Игрок: " + ppn + " Впервые на сервере");
				NoMult.instance.getLogger().info("Игрок: " + ppn + " добавлен в базу (Nick-IP) с адресом: " + pip);
				NoMult.instance.getLogger().info("Игрок: " + ppn + " Добавлен в базу (Not-Mult)");
				}
				new BukkitRunnable() {
		            @Override
		            public void run() {
		            	NoMult.instance.getServer().broadcastMessage(ChatColor.GOLD + ppn + ChatColor.YELLOW
		            			+ " " + NoMult.plreg);
		            }
		        }.runTaskLater(NoMult.instance, 2);
				return;
			}
			multplayerip.addMultPlayerIP(ppn, pip);
			if (NoMult.instance.debug) {
			NoMult.instance.getLogger().info("Игрок: " + ppn + " уже был на сервере");
			NoMult.instance.getLogger().info("Игрок: " + ppn + " обновлен в базе (Nick-IP) с адресом: " + pip);	
			}
			return;
		}else {
// ************* если у игрока есть права - "nomult.priority" ******************************
			if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " Имеет право: - " + pri);
			if (checknotmult.checkNotMult(ppn)) {
				multplayerip.addMultPlayerIP(ppn, pip);
				if (NoMult.instance.debug) {
				NoMult.instance.getLogger().info("Игрок: " + ppn + " НЕ МУЛЬТ");
				NoMult.instance.getLogger().info("Игрок: " + ppn + " обновлен в базе (Nick-IP) с адресом: " + pip);
				}
				return;
			}else {
				notmultplayer.addNotMultPlayer(ppn);
				multplayerip.addMultPlayerIP(ppn, pip);
				if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " Добавлен в базу (Not-Mult)");
				return;
			}
		}
    }

}
