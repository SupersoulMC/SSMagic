package hell.supersoul.magic;

import com.comphenix.protocol.ProtocolLibrary;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import hell.supersoul.magic.config.MyConfig;
import hell.supersoul.magic.config.MyConfigManager;
import hell.supersoul.magic.events.EventProcesser;
import hell.supersoul.magic.rpg.ManaManager;
import hell.supersoul.magic.rpg.PlayerM;
import hell.supersoul.magic.rpg.PlayerM.StatSource;
import hell.supersoul.magic.util.InventoryPacketListener;
import hell.supersoul.magic.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {

	static MyConfigManager manager;
    static Main instance;
    EventProcesser eventProcessor;

    @Override
    public void onEnable() {

        instance = this;

        eventProcessor = new EventProcesser(this);
        new InventoryPacketListener();

        Bukkit.getPluginManager().registerEvents(eventProcessor, this);
        Bukkit.getPluginManager().registerEvents(new hell.supersoul.magic.managers.EventListener(), this);
        Bukkit.getPluginManager().registerEvents(new hell.supersoul.magic.rpg.EventListener(), this);
        
		manager = new MyConfigManager(this);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			Main.loadPlayerData(player);
		}
		
		for (Hologram holo : HologramsAPI.getHolograms(Main.getInstance())) {
			holo.delete();
		}

    }

    @Override
    public void onDisable() {
    	
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
        
        for (Player player : Bukkit.getOnlinePlayers()) {
        	Main.unloadPlayerData(player);
        }
        
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player) sender;
        if (cmd.getName().equals("magictest")) {
        	
        	if (!player.hasPermission("supersoul.staff"))
        		return false;
        	
			/*
			RegularM magic = new Lightning(Integer.parseInt(args[0]));
			magic.cast(player);
			*/

            ItemStack item = new ItemStack(Material.WOOD_SWORD);
            ItemMeta meta = item.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            String line = Util.convertToInvisibleString("SSDATA#SSMAGIC|itemType TOOL|slots 12|MAGIC REGULAR Blizzard 4 1|MAGIC COMBO Berserk 2 1|MAGIC REGULAR Heal 4 1|SHORTCUT RIGHT_CLICK 0|SHORTCUT SHIFT_RIGHT_CLICK 2|#SSNPC|name Something|");
            lore.add(line);
            meta.setLore(lore);
            item.setItemMeta(meta);
            player.getInventory().addItem(item);

        } else if (cmd.getName().equals("statistics")) {
        	
        	PlayerM playerM = PlayerM.getPlayerM(player);
        	if (playerM == null)
        		return false;
        	
        	player.sendMessage("Level: " + playerM.getLevel());
        	player.sendMessage("EXP: " + playerM.getEXP());
        	player.sendMessage("Max HP: " + playerM.getMaxHP());
        	player.sendMessage("Max MP: " + playerM.getMaxMP());
        	player.sendMessage("Total Strength: " + playerM.getTotalStrength() + " [Level: " + playerM.getStrength(StatSource.LEVEL) + " Equipment: " + playerM.getStrength(StatSource.EQUIPMENT));
        	player.sendMessage("Total Defense: " + playerM.getTotalDefense() + " [Level: " + playerM.getDefense(StatSource.LEVEL) + " Equipment: " + playerM.getDefense(StatSource.EQUIPMENT));
        	
        }
        return false;
    }

    public static Main getInstance() {
        return instance;
    }

    public EventProcesser getEventProcessor() {
        return eventProcessor;
    }

	public static MyConfigManager getMyConfigManager() {
		return manager;
	}
	
	public static PlayerM loadPlayerData(Player player) {
		
		if (player == null)
			return null;
		if (PlayerM.getPlayerMs().containsKey(player)) {
			unloadPlayerData(player);
		}
		
		MyConfig data = Main.getMyConfigManager().getNewConfig("playerData/" + player.getName() + ".yml", new String[] {
				"SS Magic Player Data File",
				"Edit only when the plugin is disabled."
				});
		int exp = data.getInt("EXP");
		PlayerM playerM = new PlayerM(player, exp);
		
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(playerM.getMaxHP());
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
		
		ManaManager.chargeMP(player);
		
		return playerM;
	}
	
	public static void savePlayerData(Player player) {
		
		if (player == null)
			return;
		PlayerM playerM = PlayerM.getPlayerM(player);
		if (playerM == null)
			return;
		
		MyConfig data = Main.getMyConfigManager().getNewConfig("playerData/" + player.getName() + ".yml", new String[] {
				"SS Magic Player Data File",
				"Edit only when the plugin is disabled."
				});
		
		data.set("EXP", playerM.getEXP());
		data.saveConfig();
		
	}
	
	public static void unloadPlayerData(Player player) {
		
		if (player == null)
			return;
		PlayerM playerM = PlayerM.getPlayerM(player);
		if (playerM == null)
			return;
		
		Main.savePlayerData(player);
		PlayerM.getPlayerMs().remove(player);
		
	}

}
