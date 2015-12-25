package net.wealth_mc.nomult;

import java.util.List;

import net.wealth_mc.nomult.check.CheckMultNotMult;
import net.wealth_mc.nomult.other.NotMultPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoMultCmd implements CommandExecutor {
	
	private final NoMult plg;
	 
	public NoMultCmd(NoMult plg) {
		this.plg = plg;
	}
	private NotMultPlayer notmultplayer;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		notmultplayer = new NotMultPlayer();
		
		if(cmd.getName().equalsIgnoreCase("nomult")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.DARK_RED + "[NoMult] :" + ChatColor.GOLD + " Вы не ввели аргумент: /nomult [args]");
				return true;				
			}			
			if (args[0].equalsIgnoreCase("reload")) {
// ***      Перезагрузка конфига и сохранение переменных				

				if (sender instanceof Player) {
					if (sender.hasPermission("nomult.admin")) {
//						this.plg.reloadConfig();
						NoMult.instance.reloadConf();
						NoMult.instance.saveConf();
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "файл конфигурации перезагружен, переменые сохранены в файлы *.yml");
						return true;
					} else {
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "У Вас нет прав на перезагрузку конфига!");
						plg.getLogger().info("[У игрока: " + sender.getName() + " нет прав на перезагрузку конфига");
						return true;
					}
				} else {
//					this.plg.reloadConfig();
					NoMult.instance.reloadConf();
					NoMult.instance.saveConf();
					sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD +  "файл конфигурации перезагружен, переменые сохранены в файлы *.yml");
					return true;
				}
			}else if (args[0].equalsIgnoreCase("save")) {
// ***      Сохранение переменных
				if (sender instanceof Player) {
					if (sender.hasPermission("nomult.admin")) {				
						NoMult.instance.saveConf();
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "переменые сохранены в файлы *.yml");
						return true;
					} else {
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "У Вас нет прав на запись переменных!");
						plg.getLogger().info("[У игрока: " + sender.getName() + " нет прав на перезагрузку конфига");
						return true;
					}
				} else {
					NoMult.instance.saveConf();
					sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + "переменые сохранены в файлы *.yml");
					return true;
				}
			}else if (args[0].equalsIgnoreCase("addnm")) {
				String nick = null;
				if (sender instanceof Player) {
					if (sender.hasPermission("nomult.admin")) {
						if (args.length == 1) {
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult addnm [args1], логин не может быть пустым");
							return true;
							} else {
								nick = args[1];
								if (notmultplayer.addNotMultPlayers(nick)) {
									sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GREEN + nick + ChatColor.GOLD + " добавлен в список (Не мульт)");
								}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GREEN + nick + ChatColor.GOLD + " уже есть в списке (Не мульт)");
								return true;
							}						
					}else {
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "нет прав на добавление в список (Не мульт)");
						return true;
					}
				}else if (args.length == 1) {
					sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult addnm [args1], логин не может быть пустым");
					return true;
					} else {
						nick = args[1];
						if (notmultplayer.addNotMultPlayers(nick)) {
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GREEN + nick + ChatColor.GOLD + " добавлен в список (Не мульт)");
						}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GREEN + nick + ChatColor.GOLD + " уже есть в списке (Не мульт)");
						return true;
					}
			}else if (args[0].equalsIgnoreCase("removenm")) {
				String nick = null;
				if (sender instanceof Player) {
					if (sender.hasPermission("nomult.admin")) {
						if (args.length == 1) {
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult removenm [args1], логин не может быть пустым");
							return true;
							} else {
								nick = args[1];
								if (notmultplayer.rmNotMultPlayer(nick)) {
									sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " удален из списка (Не мульт)");
								}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " отсутствует в списке (Не мульт)");
								return true;
							}						
					}else {
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "нет прав на удаление из списка (Не мульт)");
						return true;						
					}
				}else if (args.length == 1) {
					sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult removenm [args1], логин не может быть пустым");
					return true;
					}else {
						nick = args[1];
						if (notmultplayer.rmNotMultPlayer(nick)) {
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " удален из списка (Не мульт)");
						}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " отсутствует в списке (Не мульт)");
						return true;
					}
			}else if (args[0].equalsIgnoreCase("replacenm")) {
				String nick1 = null;
				String nick2 = null;
				if (sender instanceof Player) {
					if (sender.hasPermission("nomult.admin")) {
						if (args.length == 1) {							
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult removenm [args1], логин не может быть пустым");
							return true;
						}else if (args.length == 2) {
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args2]: /nomult removenm [args1] [args1], логин2  не может быть пустым");
							return true;
						}else {
							nick1 = args[1];
							nick2 = args[2];
							if (notmultplayer.replNotMultPlayer(nick1, nick2)) {
								sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GREEN + nick1  + ChatColor.GOLD + " заменен в списке (Не мульт) на игрока: " + ChatColor.GREEN + nick2);
							}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GREEN + nick1  + ChatColor.GOLD + " отсутствует в списке (Не мульт) невозможно заменить на: " + ChatColor.GREEN + nick2);
							return true;								
							}						
					} else {
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "нет прав на удаление из списка (Не мульт)");
						return true;
					}
				}else if (args.length == 1) {					
					sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult removenm [args1], логин не может быть пустым");
					return true;
					}else if (args.length == 2) {
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args2]: /nomult removenm [args1] [args1], логин2  не может быть пустым");
						return true;
					}else {
						nick1 = args[1];
						nick2 = args[2];
						if (notmultplayer.replNotMultPlayer(nick1, nick2)) {
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult] : " + ChatColor.GREEN + nick1  + ChatColor.GOLD + " заменен в списке (Не мульт) на игрока: " + ChatColor.GREEN + nick2);
						}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GREEN + nick1  + ChatColor.GOLD + " отсутствует в списке (Не мульт) невозможно заменить на: " + ChatColor.GREEN + nick2);
						return true;
					}
			}else if (args[0].equalsIgnoreCase("help")) {			
				sender.sendMessage("[NoMult]: /nomult reload - перезагрузка конфигурации");
				sender.sendMessage("[NoMult]: /nomult save - сохранить переменные в файлы");
				sender.sendMessage("[NoMult]: /nomult addnm [nickname] - добавить игрока в список (Не мульт)");
			}else if (args[0].equalsIgnoreCase("check")) {
				String nick = null;
				List<String> multlist = null;
				List<String> nomultlist = null;
				CheckMultNotMult checkmnm = new CheckMultNotMult();
				if (sender instanceof Player) {
					if (sender.hasPermission("nomult.moder") || sender.hasPermission("nomult.admin")) {
						if (args.length == 1) {
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult check [args1], логин не может быть пустым");
							return true;
							} else {
								nick = args[1];
								multlist = checkmnm.getMultMult(nick);
								if (!(multlist == null)){ 
									sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Проверка: " + nick + " в базе найдено: " + multlist.toString());
									nomultlist = checkmnm.getNotMult(multlist);
								}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Игрок: " + nick + " в базе не найден");
								if (!(nomultlist == null)){ 
									sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "В списках (Не мульт) найдено: " + nomultlist.toString());
								}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Игрок: " + nick + " в списках (Не мульт) не найден.");
								return true;
							}						
					}else {
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "нет прав на удаление из списка (Не мульт)");
						return true;						
					}
				}else if (args.length == 1) {
					sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult removenm [args1], логин не может быть пустым");
					return true;
					}else {
						nick = args[1];
						multlist = checkmnm.getMultMult(nick);
						if (!multlist.equals(null)){ 
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Проверка: " + nick + " в базе найдено: " + multlist.toString());
							nomultlist = checkmnm.getNotMult(multlist);
						}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Игрок: " + nick + " в базе не найден");
						if (!(nomultlist == null)){ 
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "В списках (Не мульт) найдено: " + nomultlist.toString());
						}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Игрок: " + nick + " в списках (Не мульт) не найден.");
						return true;
					}
			}else if (args[0].equalsIgnoreCase("my")) {
				List<String> multlist = null;
				List<String> nomultlist = null;
				CheckMultNotMult checkmnm = new CheckMultNotMult();
				if (sender instanceof Player) {
					if (sender.hasPermission("nomult.player") || sender.hasPermission("nomult.admin") || sender.hasPermission("nomult.moder")) {
						multlist = checkmnm.getMultMult(sender.getName());
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Проверка: " + sender.getName() + " в базе найдено: " + multlist.toString());
						nomultlist = checkmnm.getNotMult(multlist);
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "В списках (Не мульт) найдено: " + nomultlist.toString());
					return true;
					}else {
						sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "нет прав на удаление из списка (Не мульт)");
						return true;						
					}
				}else {
					sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Эта команда для игроков, из консоли не работает");
					return true;
				}
			}
			sender.sendMessage(ChatColor.DARK_RED + "[NoMult]:" + ChatColor.GOLD + " Вы ввели не существующий аргумент: /nomult [args]");
			return true;
		}
		return false;
	}
}
