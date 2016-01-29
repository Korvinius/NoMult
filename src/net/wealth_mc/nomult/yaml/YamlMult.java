package net.wealth_mc.nomult.yaml;

import java.io.File;
import java.util.Set;

import net.wealth_mc.nomult.NoMult;

import org.bukkit.configuration.file.YamlConfiguration;

public class YamlMult {
	NoMult plg;			
	public YamlMult(NoMult noMult) {
		this.plg = noMult;
	}
	
	public YamlMult() {
//		Пустой конструктор
	}

	public void loadMult(){
        try {
            YamlConfiguration cfg = new YamlConfiguration();
            File f = new File (NoMult.instance.getDataFolder()+File.separator+"mult.yml");
            NoMult.mult.clear();
            if (f.exists()) {
                cfg.load(f);
                Set<String> keys = cfg.getKeys(false);
                if (keys.size()>0)
                    for (String key : keys)
                    	NoMult.mult.put(key, cfg.getString(key));
                
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

	public synchronized void saveMult(){
        try {
            YamlConfiguration cfg = new YamlConfiguration();
            for (String key : NoMult.mult.keySet()){
                cfg.set(key, NoMult.mult.get(key));
            } 
            File f = new File (NoMult.instance.getDataFolder()+File.separator+"mult.yml");
            if (f.exists()) f.delete();
            cfg.save(f);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
	
	public void saveDefaultMult() {
		File f = new File (NoMult.instance.getDataFolder(), "mult.yml");
	    if (!f.exists()) {            
	    	NoMult.instance.saveResource("mult.yml", false);
	     }
	}

}
