package net.wealth_mc.nomult.yaml;

import java.io.File;
import java.util.Set;

import net.wealth_mc.nomult.NoMult;

import org.bukkit.configuration.file.YamlConfiguration;

public class YamlGroup {

	public YamlGroup() {
//		constructor
	}
	
	public void loadGroupPlayer(){
        try {
            YamlConfiguration cfg = new YamlConfiguration();
            File f = new File (NoMult.instance.getDataFolder()+File.separator+"group.yml");
            NoMult.groups.clear();
            if (f.exists()) {
                cfg.load(f);
                Set<String> keys = cfg.getKeys(false);
                if (keys.size()>0)
                    for (String key : keys)
                    	NoMult.groups.put(key, cfg.getString(key));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
	
	public synchronized void saveGroupPlayer(){
        try {
            YamlConfiguration cfg = new YamlConfiguration();
            for (String key : NoMult.groups.keySet()){
                cfg.set(key, NoMult.groups.get(key));
            } 
            File f = new File (NoMult.instance.getDataFolder()+File.separator+"group.yml");
            if (f.exists()) f.delete();
            cfg.save(f);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
	
	
	public void saveDefaultGroup() {
		File f = new File (NoMult.instance.getDataFolder(), "group.yml");
	    if (!f.exists()) {            
	    	NoMult.instance.saveResource("group.yml", false);
	     }
	}

}
