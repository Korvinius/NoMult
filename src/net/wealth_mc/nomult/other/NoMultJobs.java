package net.wealth_mc.nomult.other;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.wealth_mc.nomult.NoMult;
import net.wealth_mc.nomult.check.CheckMultNotMult;
import net.wealth_mc.nomult.check.CheckMultPlayer;

public class NoMultJobs {


	/**
	 * Игрок имеет право nomult.priority
	 * @param player
	 */
	public static void noMultPriority(Player player) {
		String ppn = player.getName();
		String pip = player.getAddress().getAddress().toString();
		if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " Имеет право: - " + "nomult.priority");
		if (NoMult.notmult.contains(ppn)) {
			MultPlayerIP.addMultPlayerIP(ppn, pip);
			if (NoMult.instance.debug) {
			NoMult.instance.getLogger().info("Игрок: " + ppn + " НЕ МУЛЬТ");
			NoMult.instance.getLogger().info("Игрок: " + ppn + " обновлен в базе (Nick-IP) с адресом: " + pip);
			}
			joinMessage(player);
			return;
		}else {
			NotMultPlayer.addNotMultPlayer(ppn);
			MultPlayerIP.addMultPlayerIP(ppn, pip);
			if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " Добавлен в базу (Not-Mult)");
			joinMessage(player);
			return;
		}
	}

	/**
	 * Обработка игрока, который определен как не мульт
	 * @param player
	 */
	public static void isPlayerNomult(Player player) {
		String ppn = player.getName();
		String pip = player.getAddress().getAddress().toString();
		if (!CheckMultPlayer.checkMultPlayer(ppn)) {
			NotMultPlayer.addNotMultPlayer(ppn);
			MultPlayerIP.addMultPlayerIP(ppn, pip);
			if (NoMult.instance.debug) {
			NoMult.instance.getLogger().info("Игрок: " + ppn + " Впервые на сервере");
			NoMult.instance.getLogger().info("Игрок: " + ppn + " добавлен в базу (Nick-IP) с адресом: " + pip);
			NoMult.instance.getLogger().info("Игрок: " + ppn + " Добавлен в базу (Not-Mult)");
			}
			registerMessage(player);
			return;
		}
		MultPlayerIP.addMultPlayerIP(ppn, pip);
		if (NoMult.instance.debug) {
		NoMult.instance.getLogger().info("Игрок: " + ppn + " уже был на сервере");
		NoMult.instance.getLogger().info("Игрок: " + ppn + " обновлен в базе (Nick-IP) с адресом: " + pip);	
		}
		joinMessage(player);
	}

	/**
	 * Добавляем игрока в список мультов, изменяем группу и сохраняем текущую группу.
	 * @param player
	 */
	public static void isMult(Player player) {
		String ppn = player.getName();
		String pip = player.getAddress().getAddress().toString();
		String group = NoMultVault.getPrimaryGroup(player);;
		MultPlayerIP.addMultPlayerIP(ppn, pip);
		if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " обновлен в базе (Nick-IP) с адресом: " + pip);
		NoMult.groups.put(ppn, group);
		NoMultVault.playerRemoveGroup(player, group);
		NoMultVault.playerAddGroup(player, NoMult.ngroup);
		if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игроку " + ppn + " изменена группа, на: " + NoMult.ngroup);
		multMessage(player);
	}
	
	/**
	 * Обработка игрока присутствующего в списке (Не Мульт)
	 * @param player
	 * @return
	 */
	public static boolean isNoMult(Player player) {
		String ppn = player.getName();
		String pip = player.getAddress().getAddress().toString();
		String group = null;
		if (NoMult.notmult.contains(ppn)) {//игрок есть в списке  (Не Мульт)
			
			if (CheckMultNotMult.checkMultNotMult(ppn, pip)) {// игрок не один в списке  (Не Мульт)		
				if (NoMult.instance.debug) NoMult.instance.getLogger().info("адрес: " + pip + " приствуют мульты, игрок: " + ppn + " есть в базе (Не Мульт), но есть совпадения по IP с другими игроками категории (Не Мульт)");
				MultPlayerIP.addMultPlayerIP(ppn, pip);
				if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " обновлен в базе (Nick-IP) с адресом: " + pip);
				group = NoMultVault.getPrimaryGroup(player);								
				NoMultVault.playerRemoveGroup(player, group);
				NoMultVault.playerAddGroup(player, NoMult.ngroup);
				if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игроку " + ppn + " изменена группа, на: " + NoMult.ngroup);
				try {																			
					NoMult.groups.put(ppn, group);					
			     } catch (Exception e){
			     }
				multMessage(player);
				return true;							
			}
			MultPlayerIP.addMultPlayerIP(ppn, pip);
			if (NoMult.instance.debug) NoMult.instance.getLogger().info("адрес: " + pip + " приствуют мульты, но игрок: " + ppn + " есть в базе (Не Мульт)");
			joinMessage(player);
			return true;
		}
		return false;
	}
	
	
	/**
	 * 	В начале проверки, если игрок в группе мультов, убираем его оттуда
	 * @param player
	 */
	public static void rmMultCheck(Player player) {
		String ppn = player.getName();
		if (NoMultVault.playerInGroup(player, NoMult.ngroup)) {
			String ogroup = NoMultGroups.inOldGroupPlayerRM(ppn);
			NoMultVault.playerRemoveGroup(player, NoMult.ngroup);
			if (!ogroup.equals(NoMult.defgroup)) {
				NoMultVault.playerAddGroup(player, ogroup);
			}
			if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок " + ppn + " был в списке мультов, в начале проверки возвращена группа: " + ogroup);							
		}
	}
	
	public static void joinMessage(Player p) {
		if (!p.hasPermission(NoMult.permSPY) && !p.hasPermission(NoMult.permADMIN)) {
			NoMult.instance.getServer().broadcastMessage(ChatColor.YELLOW
					+ NoMult.plogin.replace("%player%", p.getName()));
		}
	}

	public static void registerMessage(Player p) {
		if (!p.hasPermission(NoMult.permSPY) && !p.hasPermission(NoMult.permADMIN)) {
			NoMult.instance.getServer().broadcastMessage(ChatColor.YELLOW
					+ NoMult.plreg.replace("%player%", p.getName()));
		}
	}

	public static void multMessage(Player p) {
		if (!p.hasPermission(NoMult.permSPY)) {
			NoMult.instance.getServer().broadcastMessage(ChatColor.GRAY
					+ NoMult.plmult.replace("%player%", p.getName()));
		}
	}
}
