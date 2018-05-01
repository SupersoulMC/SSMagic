package hell.supersoul.magic;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import hell.supersoul.magic.events.EventProcesser;

public class Main extends JavaPlugin {
	
	public static Main instance;
	
	public void onEnable() {
		
		instance = this;
		
		Bukkit.getPluginManager().registerEvents(new EventProcesser(), this);
		
	}
	
	//Testing

}
