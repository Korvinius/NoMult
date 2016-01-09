package net.wealth_mc.nomult;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.wealth_mc.nomult.check.CheckMultNotMult;
import net.wealth_mc.nomult.other.MultPlayerIP;
import net.wealth_mc.nomult.other.NoMultGroups;
import net.wealth_mc.nomult.other.NotMultPlayer;

public class NoMultCmd implements CommandExecutor {
	
	private final NoMult plg;
	 
	public NoMultCmd(NoMult plg) {
		this.plg = plg;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args[0].equalsIgnoreCase("reload")) {
			return cmdReload(sender);
		}else if (args[0].equalsIgnoreCase("save")) {
			return cmdSave(sender);
		}else if (args[0].equalsIgnoreCase("addnm")) {
			return cmdAddNM(sender, args);
		}else if (args[0].equalsIgnoreCase("removenm")) {
			return cmdRemoveNM(sender, args);
		}else if (args[0].equalsIgnoreCase("replacenm")) {
			return cmdReplaceNM(sender, args);
		}else if (args[0].equalsIgnoreCase("help")) {
			return cmdHelp(sender);
		}else if (args[0].equalsIgnoreCase("check")) {
			return cmdCheck(sender, args);
		}else if (args[0].equalsIgnoreCase("my")) {
			return cmdMy(sender);
		}else if (args[0].equalsIgnoreCase("delall")) {
			return cmdDel(sender, args);
		}
		return false;
	}

	private boolean cmdDel(CommandSender sender, String[] args) {
		String nick = null;
		if (sender instanceof Player) {
			if (sender.hasPermission("nomult.admin")) {
				if (args.length == 1) {
					sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult removenm [args1], логин не может быть пустым");
					return true;
					} else {
						nick = args[1];
						if (NotMultPlayer.rmNotMultPlayer(nick)) {
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " удален из списка (Не мульт)");
						}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " отсутствует в списке (Не мульт)");
						if (MultPlayerIP.rmMultPlayer(nick)) {
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " удален из списка (Мульт)");
						}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " отсутствует в списке (Мульт)");
						if (NoMultGroups.delPlayer(nick)) {
							sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " удален из списка (Group)");
						}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " отсутствует в списке (Group)");

						return true;
					}						
			}else {
				sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "нет прав на удаление из списка (Не мульт)");
				return true;						
			}
		}else if (args.length == 1) {
			sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult removenm [args1], логин не может быть пустым");
			return true;
			}
		nick = args[1];
		if (NotMultPlayer.rmNotMultPlayer(nick)) {
			sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " удален из списка (Не мульт)");
		}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " отсутствует в списке (Не мульт)");
		if (MultPlayerIP.rmMultPlayer(nick)) {
			sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " удален из списка (Мульт)");
		}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " отсутствует в списке (Мульт)");
		if (NoMultGroups.delPlayer(nick)) {
			sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " удален из списка (Group)");
		}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " отсутствует в списке (Group)");		return true;
	}

	private boolean cmdCheck(CommandSender sender, String[] args) {
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
			}
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

	private boolean cmdReplaceNM(CommandSender sender, String[] args) {
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
					if (NotMultPlayer.replNotMultPlayer(nick1, nick2)) {
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
			}
		nick1 = args[1];
		nick2 = args[2];
		if (NotMultPlayer.replNotMultPlayer(nick1, nick2)) {
			sender.sendMessage(ChatColor.DARK_RED + "[NoMult] : " + ChatColor.GREEN + nick1  + ChatColor.GOLD + " заменен в списке (Не мульт) на игрока: " + ChatColor.GREEN + nick2);
		}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GREEN + nick1  + ChatColor.GOLD + " отсутствует в списке (Не мульт) невозможно заменить на: " + ChatColor.GREEN + nick2);
		return true;
	}

	private boolean cmdRemoveNM(CommandSender sender, String[] args) {
		String nick = null;
		if (sender instanceof Player) {
			if (sender.hasPermission("nomult.admin")) {
				if (args.length == 1) {
					sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult removenm [args1], логин не может быть пустым");
					return true;
					} else {
						nick = args[1];
						if (NotMultPlayer.rmNotMultPlayer(nick)) {
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
			}
		nick = args[1];
		if (NotMultPlayer.rmNotMultPlayer(nick)) {
			sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " удален из списка (Не мульт)");
			}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: "  + ChatColor.GREEN + nick  + ChatColor.GOLD + " отсутствует в списке (Не мульт)");
		return true;
	}

	private boolean cmdAddNM(CommandSender sender, String[] args) {
		String nick = null;
		if (sender instanceof Player) {
			if (sender.hasPermission("nomult.admin")) {
				if (args.length == 1) {
					sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Не верный аргумент [args1]: /nomult addnm [args1], логин не может быть пустым");
					return true;
					} else {
						nick = args[1];
						if (NotMultPlayer.addNotMultPlayers(nick)) {
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
			}
		nick = args[1];
		if (NotMultPlayer.addNotMultPlayers(nick)) {
			sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GREEN + nick + ChatColor.GOLD + " добавлен в список (Не мульт)");
			}else sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GREEN + nick + ChatColor.GOLD + " уже есть в списке (Не мульт)");
			return true;
	}

	private boolean cmdReload(CommandSender sender) {
		if (sender instanceof Player) {
			if (sender.hasPermission("nomult.admin")) {
				NoMult.instance.reloadConf();
				NoMult.instance.saveConf();
				sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "файл конфигурации перезагружен, переменые сохранены в файлы *.yml");
				return true;
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "У Вас нет прав на перезагрузку конфига!");
				plg.getLogger().info("[У игрока: " + sender.getName() + " нет прав на перезагрузку конфига");
				return true;
			}
		}
		NoMult.instance.reloadConf();
		NoMult.instance.saveConf();
		sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD +  "файл конфигурации перезагружен, переменые сохранены в файлы *.yml");
		return true;
	}

	private boolean cmdSave(CommandSender sender) {
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
		}
		NoMult.instance.saveConf();
		sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + "переменые сохранены в файлы *.yml");
		return true;
	}

	private boolean cmdMy(CommandSender sender) {
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
		}
		sender.sendMessage(ChatColor.DARK_RED + "[NoMult]: " + ChatColor.GOLD + "Эта команда для игроков, из консоли не работает");
		return true;
	}

	private boolean cmdHelp(CommandSender sender) {
		sender.sendMessage("[NoMult]: /nomult my - проверить себя по спискам плагина");
		sender.sendMessage("[NoMult]: /nomult help - подсказка");
		sender.sendMessage("[NoMult]: /nomult check [nickname] - проверить игрока по спискам плагина");
		sender.sendMessage("[NoMult]: /nomult reload - перезагрузка конфигурации");
		sender.sendMessage("[NoMult]: /nomult save - сохранить переменные в файлы");
		sender.sendMessage("[NoMult]: /nomult addnm [nickname] - добавить игрока в список (Не мульт)");
		sender.sendMessage("[NoMult]: /nomult removenm [nickname] - удалить игрока из списка (Не мульт)");
		sender.sendMessage("[NoMult]: /nomult replacenm [nickname1] nickname2] - Заменить игрока [nickname1] "
				+ "в спискк (Не мульт) на игрока [nickname2]");
		return true;
	}
}
