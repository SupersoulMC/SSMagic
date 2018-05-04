package hell.supersoul.magic.managers;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.ComboM;
import hell.supersoul.magic.core.MagicItem;
import hell.supersoul.magic.managers.EquipmentManager.EquipmentSlot;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class ComboManager {
	public enum HitLevel {
		ZERO, ONE, TWO;
	}

	static HashMap<Player, HitLevel> currentHit = new HashMap<>();
	static HashMap<Player, Integer> currentHitTask = new HashMap<>();
	static HashMap<Player, ArrayList<HitLevel>> comboCount = new HashMap<>();

	// Make the combo bar, hit ticks measured in ticks
	public static void executeHit(Player player, Entity target) {

		// Safety checks.
		ArrayList<Integer> hitTicks = new ArrayList<>();
		if (player == null)
			return;
		if (!player.isOnline())
			return;

		// Cancels hit when in combo
		if (comboCount.containsKey(player) && comboCount.get(player).size() > 0 && !currentHit.containsKey(player))
			return;

		// Stops the previous hit.
		if (!comboCount.containsKey(player))
			comboCount.put(player, new ArrayList<>());
		if (currentHitTask.containsKey(player)) {
			Bukkit.getScheduler().cancelTask(currentHitTask.get(player));
			if (currentHit.containsKey(player) && currentHit.get(player).equals(HitLevel.ZERO))
				comboCount.get(player).clear();
			else
				comboCount.get(player).add(currentHit.get(player));
		}

		// Sets prefix and suffix's colors.
		String l = "";
		if (!currentHit.containsKey(player))
			l = "7";
		else
			l = ComboManager.hitLevelToColorCode(currentHit.get(player)) + "";

		// Creates the combo bar
		String bar = "";

		// Calculates the number of combos.
		String comboBar = "";
		boolean combo = false;
		MagicItem magicItem = EquipmentManager.getMagicItem(player, EquipmentSlot.HAND);
		if (magicItem != null) {
			ComboM magic = EquipmentManager.getTheOnlyComboMagic(magicItem);
			if (magic != null) {
				hitTicks.addAll(magic.getComboHitTicks());
				int totalCombo = magic.getComboTotal();
				int currentCount = 0;
				if (comboCount.containsKey(player)) {
					currentCount = comboCount.get(player).size();
				}
				if (totalCombo == currentCount) {
					player.sendMessage("Combo");
					combo = true;
					currentHit.remove(player);
					l = "b";
					magic.unleashCombo(player, target, 0);
					bar = bar + StringUtils.repeat(ChatColor.BLUE + "|", hitTicks.get(0));
					bar = bar + StringUtils.repeat(ChatColor.BLUE + "|", hitTicks.get(1));
					bar = bar + StringUtils.repeat(ChatColor.BLUE + "|", hitTicks.get(2));
					bar = bar + StringUtils.repeat(ChatColor.BLUE + "|", hitTicks.get(3));
				} else {
					player.sendMessage("Hit");
					bar = bar + StringUtils.repeat(ChatColor.DARK_GRAY + "|", hitTicks.get(0));
					bar = bar + StringUtils.repeat(ChatColor.YELLOW + "|", hitTicks.get(1));
					bar = bar + StringUtils.repeat(ChatColor.GOLD + "|", hitTicks.get(2));
					bar = bar + StringUtils.repeat(ChatColor.DARK_GRAY + "|", hitTicks.get(3));
					combo = false;
					if (currentHit.containsKey(player))
					magic.normalHit(player, target, currentHit.get(player));
					else
						magic.normalHit(player, target, HitLevel.ONE);
				}
				comboBar = ChatColor.COLOR_CHAR + l + ChatColor.BOLD + "[ ";
				if (currentCount > 0)
					for (HitLevel level : comboCount.get(player)) {
						comboBar = comboBar + ChatColor.COLOR_CHAR + ComboManager.hitLevelToColorCode(level) + "¡½";
					}

				for (int i = 0; i < (totalCombo - currentCount); i++) {
					comboBar = comboBar + ChatColor.DARK_GRAY + "¡½";
				}
				comboBar = comboBar + " " + ChatColor.COLOR_CHAR + l + ChatColor.BOLD + "]     ";
			}
		} else {
			hitTicks.add(10);
			hitTicks.add(6);
			hitTicks.add(4);
		}

		// Composes the combo bar.
		String pre = comboBar + ChatColor.COLOR_CHAR + l + ChatColor.BOLD + "> ";
		String suf = " " + ChatColor.COLOR_CHAR + l + ChatColor.BOLD + "<";
		String mid = bar;

		boolean c = combo;

		int id = new BukkitRunnable() {
			int n = 0;
			int count = 0;

			@Override
			public void run() {

				if (!c) {
					// Replaces the current tick in the combo bar with the red vertical.
					String comp = pre;
					if (n > 0)
						comp = comp + mid.substring(0, n);
					comp = comp + ChatColor.RED + "|";
					if (n + 3 <= mid.length())
						comp = comp + mid.substring(n + 3);
					else
						comp = pre + mid;
					comp = comp + suf;
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(comp));

					// Sets the current hit level.
					if (count <= hitTicks.get(0))
						currentHit.put(player, HitLevel.ZERO);
					else if (count - hitTicks.get(0) <= hitTicks.get(1))
						currentHit.put(player, HitLevel.ONE);
					else if (count - hitTicks.get(0) - hitTicks.get(1) <= hitTicks.get(2))
						currentHit.put(player, HitLevel.TWO);
					else
						currentHit.put(player, HitLevel.ZERO);

				} else {
					// Composes the bar for the combo
					String comp = pre;
					comp = comp + StringUtils.repeat(ChatColor.AQUA + "|", (mid.length() - n) / 3) + StringUtils.repeat(ChatColor.DARK_GRAY + "|", count);
					comp = comp + suf;
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(comp));
				}

				count++;
				n = n + 3;

				// Cancels when the player doesn't hit again.
				if ((c && n > mid.length()) || (!c && n > mid.length())) {
					currentHitTask.remove(player);
					currentHit.remove(player);
					comboCount.get(player).clear();
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
					this.cancel();
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 1).getTaskId();

		// Put the task ID in for the next hit to cancel it.
		currentHitTask.put(player, id);
	}

	public static char hitLevelToColorCode(HitLevel hitLevel) {
		if (hitLevel == null)
			return '7';
		if (hitLevel.equals(HitLevel.ZERO))
			return '7';
		else if (hitLevel.equals(HitLevel.ONE))
			return 'e';
		else if (hitLevel.equals(HitLevel.TWO))
			return '6';
		return '7';
	}

	public static HashMap<Player, HitLevel> getCurrentHit() {
		return currentHit;
	}

	public static HashMap<Player, Integer> getCurrentHitTask() {
		return currentHitTask;
	}

	public static HashMap<Player, ArrayList<HitLevel>> getComboCount() {
		return comboCount;
	}
}
