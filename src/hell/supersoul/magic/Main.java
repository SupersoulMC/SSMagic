package hell.supersoul.magic;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import hell.supersoul.magic.core.RegularM;
import hell.supersoul.magic.core.regular.Blizzard;
import hell.supersoul.magic.core.regular.Fire;
import hell.supersoul.magic.events.EventProcesser;
import hell.supersoul.magic.managers.ComboManager;

public class Main extends JavaPlugin {
	
	static Main instance;
	
	public void onEnable() {
		
		instance = this;

		Bukkit.getPluginManager().registerEvents(new EventProcesser(this), this);
		Bukkit.getPluginManager().registerEvents(new hell.supersoul.magic.managers.EventListener(), this);
		
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;
		if(cmd.getName().equals("magictest")) {
			/*
			RegularM magic = new Fire(this, Integer.parseInt(args[0]));
			magic.cast(player);
			*/
			ItemStack item = new ItemStack(Material.WOOD_SWORD);
			ItemMeta meta = item.getItemMeta();
			ArrayList<String> lore = new ArrayList<>();
			String line = "SSMAGIC|itemType TOOL|slots 5|MAGIC0 COMBO Berserk 2 1|MAGIC1 REGULAR Blizzard 4 1|";
			lore.add(line);
			meta.setLore(lore);
			item.setItemMeta(meta);
			player.getInventory().addItem(item);
		}
		return false;	
	}
	
	public static Main getInstance() {
		return instance;
	}

}
