package hell.supersoul.magic.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import hell.supersoul.magic.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ComboManager {
	enum HitLevel {
		ZERO,ONE,TWO;
	}
	static HashMap<Player, HitLevel> currentHit = new HashMap<>();
	
	//Make the combo bar, hit ticks measured in ticks
	public static void executeHit(Player player) {
		ArrayList<Integer> hitTicks = new ArrayList<>();
		if (player == null)
			return;
		if (!player.isOnline())
			return;
		ItemStack item = player.getItemOnCursor();
		if (item == null || item.getType().equals(Material.AIR)) {
			hitTicks.add(10);
			hitTicks.add(6);
			hitTicks.add(4);
		}
		String pre = "¡±7¡±l> ";
		String bar = "¡±8";
		bar = bar + StringUtils.repeat('|', hitTicks.get(0));
		bar = bar + "¡±e";
		bar = bar + StringUtils.repeat('|', hitTicks.get(1));
		bar = bar + "¡±6";
		bar = bar + StringUtils.repeat('|', hitTicks.get(2));
		bar = bar + "¡±8";
		bar = bar + StringUtils.repeat('|', 3);
		String suf = " ¡±7¡±l<";
		String mid = bar;
		new BukkitRunnable() {
			int n = 0;
			@Override
            public void run() {
				String comp = pre;
				if (n > 0)
					comp = comp + mid.substring(0,n);
				comp = comp + "¡±c|";
				if (mid.indexOf("¡±e") > comp.length())
					comp = comp + "¡±8";
				else if (mid.indexOf("¡±6") > comp.length())
					comp = comp + "¡±e";
				else comp = comp + "¡±6";
				comp = comp + mid.substring(n+1);
				comp = comp + suf;
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(comp));
				
				n++;
				if (n > mid.length()) {
					this.cancel();
				}
			}
		}.runTaskTimer(Main.instance, 0, 1);
	}
}
