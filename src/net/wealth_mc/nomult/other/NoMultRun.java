package net.wealth_mc.nomult.other;

import org.bukkit.entity.Player;

import net.wealth_mc.nomult.NoMult;
import net.wealth_mc.nomult.check.CheckMult;

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

		if (NoMult.playerischeck.contains(player)) return;
		
		NoMult.playerischeck.add(player);
		
		if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " залогинился на сервере с адресом: " + pip + " Начинаем проверку ...");
		NoMultJobs.rmMultCheck(player); // Снять тень вначале проверки
		
		if (player.hasPermission(NoMult.permIGNORE) || player.hasPermission(NoMult.permADMIN)) { // если у игрока есть права - "nomult.ignore" прервать проверку
			if (NoMult.instance.debug) NoMult.instance.getLogger().info("Игрок: " + ppn + " Имеет право: - " + NoMult.permIGNORE + " Проверка завершена!");			
			return;						
		}
		
		if (player.hasPermission(NoMult.permPRIORITY)) {
			NoMultJobs.noMultPriority(player);
			return;
		}

		if (CheckMult.checkMult(ppn, pip)) {// Обработка игрока присутствующего в списке (Не Мульт)
			if (NoMultJobs.isNoMult(player)) {
				return;
			}
			// Обработка игрока, которого нет в списке (Не мульт) но есть мульты, 
			NoMultJobs.isMult(player); // добавляем в список мультов, изменяем группу и сохраняем текущую группу.
			return;
		}
		NoMultJobs.isPlayerNomult(player);//Если игрок не мульт или впервые на сервере
    }
}
