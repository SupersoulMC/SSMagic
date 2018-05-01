package hell.supersoul.magic;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import hell.supersoul.magic.core.RegularM;
import hell.supersoul.magic.core.regular.Blizzard;
import hell.supersoul.magic.events.EventProcesser;

public class Main extends JavaPlugin {
	
	public static Main instance;
	
	public void onEnable() {
		
		instance = this;
		
		Bukkit.getPluginManager().registerEvents(new EventProcesser(this), this);
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;
		if(cmd.getName().equals("magictest")) {
			RegularM magic = new Blizzard(this,1);
			magic.cast(player);
		}
		return false;	
	}

}
