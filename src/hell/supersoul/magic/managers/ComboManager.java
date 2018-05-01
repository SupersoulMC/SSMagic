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
		ZERO, ONE, TWO;
	}

	static HashMap<Player, HitLevel> currentHit = new HashMap<>();
	static HashMap<Player, HitLevel> lastHit = new HashMap<>();

	// Make the combo bar, hit ticks measured in ticks
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

		int total = 0;
		for (int i : hitTicks) {
			total += i;
		}
		int t = total;

		String l = "";
		if (!lastHit.containsKey(player))
			l = "7";
		else if (lastHit.get(player).equals(HitLevel.ZERO))
			l = "7";
		else if (lastHit.get(player).equals(HitLevel.ONE))
			l = "e";
		else if (lastHit.get(player).equals(HitLevel.TWO))
			l = "6";
		
		String pre = "¡±" + l + "¡±l> ";
		String bar = "";
		bar = bar + StringUtils.repeat("¡±8|", hitTicks.get(0));
		bar = bar + StringUtils.repeat("¡±e|", hitTicks.get(1));
		bar = bar + StringUtils.repeat("¡±6|", hitTicks.get(2));
		bar = bar + StringUtils.repeat("¡±8|", 3);
		String suf = " ¡±" + l + "¡±l<";
		String mid = bar;

		new BukkitRunnable() {
			int n = 0;

			@Override
			public void run() {
				String comp = pre;
				if (n > 0)
					comp = comp + mid.substring(0, n);
				comp = comp + "¡±c|";
				if (n < mid.length())
					comp = comp + mid.substring(n + 3);
				else
					comp = pre + mid;
				comp = comp + suf;
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(comp));

				n = n + 3;
				if (n > mid.length()) {
					this.cancel();
				}
			}
		}.runTaskTimer(Main.instance, 0, 1);
	}
}
