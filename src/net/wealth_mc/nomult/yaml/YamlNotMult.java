package net.wealth_mc.nomult.yaml;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import net.wealth_mc.nomult.NoMult;

public class YamlNotMult {
	
	NoMult plg;			
	public YamlNotMult(NoMult noMult) {
		this.plg = noMult;
	}

	public YamlNotMult() {
//		 Пустой конструктор
	}
	
	public void loadNotMult(){
        YamlConfiguration cfg = new YamlConfiguration();
        File f = new File (NoMult.instance.getDataFolder()+File.separator+"notmult.yml");
        if (f.exists()) {
	        try {
	        	cfg.load(f);
	        } catch (Exception e){
	            e.printStackTrace();
	        }
            if (cfg != null) {                
                	NoMult.instance.notmult = cfg.getStringList("notmult");                 
 /*               if (NoMult.instance.debug) {
                    NoMult.instance.getLogger().info("notmult.yml add: " + NoMult.instance.notmult.toString());
                }*/
            }
        }
    }
	
	public synchronized void saveNotMult(){
        YamlConfiguration cfg = new YamlConfiguration();
        	cfg.set("notmult", NoMult.instance.notmult);
        File f = new File (NoMult.instance.getDataFolder()+File.separator+"notmult.yml");
        if (f.exists()) f.delete();
        try {
        	cfg.save(f);	        	
        } catch (Exception e){
        	e.printStackTrace();
        }			
	}
	
	public void saveDefaultNotMult() {
		File f = new File (NoMult.instance.getDataFolder(), "notmult.yml");
	    if (!f.exists()) {            
	    	NoMult.instance.saveResource("notmult.yml", false);
	     }
	}
}
