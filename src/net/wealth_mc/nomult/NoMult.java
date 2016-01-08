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
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class NoMult extends JavaPlugin {	
	
	public static NoMult instance;	
	private FileConfiguration config;
	
	public Map<String, String> mult = new HashMap<String, String>();
	public Map<String, String> groups = new HashMap<String, String>();
	public List<String> notmult;
	public static Set<Player> playerlogin = new HashSet<Player>();
	
	private NoMultCmd nomultcmd;
	private YamlMult yamlmult = new YamlMult();
	private YamlNotMult yamlnotmult = new YamlNotMult();
	private YamlGroup yamlgroup = new YamlGroup();
	
	public boolean debug = false;
	public String ngroup;
	public String defgroup;
	public long times;
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
		times = config.getLong("times");
		plogin = config.getString("message");
		plogout = config.getString("message2");
		plmult = config.getString("message3");
		plreg = config.getString("message4");
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

		times = times * 20;
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {  
        	@Override            
            public void run() {
        		yamlmult.saveMult();
        		yamlnotmult.saveNotMult();
        		yamlgroup.saveGroupPlayer();
            }
        }, 0L, times);
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
		plogin = config.getString("message");
		plogout = config.getString("message2");
		plmult = config.getString("message3");
		plreg = config.getString("message4");
		blockjoin  = config.getBoolean("blockjoin");
		blockleave  = config.getBoolean("blockleave");
		pvp  = config.getBoolean("pvp");
	}
	
	public void saveConf() {
		yamlmult.saveMult();
		yamlnotmult.saveNotMult();
		yamlgroup.saveGroupPlayer();
	}
	
}