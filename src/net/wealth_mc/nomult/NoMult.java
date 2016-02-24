package net.wealth_mc.nomult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.wealth_mc.nomult.other.NoMultVault;
import net.wealth_mc.nomult.yaml.YamlGroup;
import net.wealth_mc.nomult.yaml.YamlMult;
import net.wealth_mc.nomult.yaml.YamlNotMult;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NoMult extends JavaPlugin {	
	
	public static NoMult instance;	
	private FileConfiguration config;
	
	public static Map<String, String> mult = new HashMap<String, String>();
	public static Map<String, String> groups = new HashMap<String, String>();
	public static List<String> notmult;
	public static Set<Player> playerischeck = new HashSet<Player>();
	
	public static final String permADMIN = "nomult.admin";
	public static final String permIGNORE = "nomult.ignore";
	public static final String permPRIORITY = "nomult.priority";
	public static final String permSPY = "nomult.spy";
	
	private NoMultCmd nomultcmd;
	private YamlMult yamlmult = new YamlMult();
	private YamlNotMult yamlnotmult = new YamlNotMult();
	private YamlGroup yamlgroup = new YamlGroup();
	
	public boolean debug = false;
	public static String ngroup;
	public static String defgroup;
	public static boolean blockjoin;
	public static boolean blockleave;
	public static String plogin;
	public static String plogout;
	public static String plmult;
	public static String plreg;
	public boolean pvp = true;
	
	@Override
	public void onEnable() {	
		instance = this;		
		config = this.getConfig();
		this.getConfig().options().copyDefaults(true).copyHeader(true);
		this.saveDefaultConfig();

		debug = config.getBoolean("debug");
		ngroup = config.getString("group");
		defgroup = config.getString("default");
		plogin = toStringColor(config.getString("message"));
		plogout = toStringColor(config.getString("message2"));
		plmult = toStringColor(config.getString("message3"));
		plreg = toStringColor(config.getString("message4"));

		blockjoin  = config.getBoolean("blockjoin");
		blockleave  = config.getBoolean("blockleave");
		pvp  = config.getBoolean("pvp");
		
		yamlmult.saveDefaultMult();
		yamlmult.loadMult();		
		yamlnotmult.saveDefaultNotMult();
		yamlnotmult.loadNotMult();
		yamlgroup.saveDefaultGroup();
		yamlgroup.loadGroupPlayer();
		
		nomultcmd = new NoMultCmd(this);		
		getCommand("nomult").setExecutor(nomultcmd);
		
		getServer().getPluginManager().registerEvents(new NoMultList(this), this);
		
		NoMultVault.init();
	}
	
	@Override
	public void onDisable() {
		yamlmult.saveMult();
		yamlnotmult.saveNotMult();
		yamlgroup.saveGroupPlayer();
	}
	
	public void reloadConf() {
		this.reloadConfig();
		config = this.getConfig();
		debug = config.getBoolean("debug");
		ngroup = config.getString("group");
		defgroup = config.getString("default");
		plogin = toStringColor(config.getString("message"));
		plogout = toStringColor(config.getString("message2"));
		plmult = toStringColor(config.getString("message3"));
		plreg = toStringColor(config.getString("message4"));
		blockjoin  = config.getBoolean("blockjoin");
		blockleave  = config.getBoolean("blockleave");
		pvp  = config.getBoolean("pvp");
	}
	
	public void saveConf() {
		yamlmult.saveMult();
		yamlnotmult.saveNotMult();
		yamlgroup.saveGroupPlayer();
	}
	
	public static String toStringColor(String input) {
	    return ChatColor.translateAlternateColorCodes('&', input);
}
}