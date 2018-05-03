package hell.supersoul.magic.managers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import hell.supersoul.magic.core.Magic;
import hell.supersoul.magic.core.MagicItem;

public class EquipmentManager {

	public enum EquipmentSlot {
		HELMET, CHESTPLATE, LEGGINGS, BOOTS, HAND;
	}

	static HashMap<Player, HashMap<EquipmentSlot, MagicItem>> equipmentData = new HashMap<>();
	static HashMap<Player, HashMap<EquipmentSlot, ItemStack>> currentItem = new HashMap<>();

	// Converts an inventory slot number to an EquipmentSlot enum.
	public static EquipmentSlot toEquipmentSlot(int slot) {
		if (slot == 103)
			return EquipmentSlot.HELMET;
		else if (slot == 104)
			return EquipmentSlot.CHESTPLATE;
		else if (slot == 105)
			return EquipmentSlot.LEGGINGS;
		else if (slot == 106)
			return EquipmentSlot.BOOTS;
		else if (slot >= 0 && slot <= 8)
			return EquipmentSlot.HAND;
		return null;
	}

	//Checks and updates the equimentData of a player for an inventory slot.
	public static void checkAndUpdate(Player player, int slot) {

		//Safety checks
		if (player == null)
			return;
		if (!player.isOnline())
			return;
		if (!equipmentData.containsKey(player)) {
			equipmentData.put(player, new HashMap<>());
		}
		if (!currentItem.containsKey(player)) {
			currentItem.put(player, new HashMap<>());
		}

		//Gets a EquipmentSlot instance for the slot number.
		EquipmentSlot eSlot = EquipmentManager.toEquipmentSlot(slot);
		if (eSlot == null)
			return;

		//Checks if the item is the same as the last check, if same then no need to update.
		ItemStack item = player.getInventory().getItem(slot);
		ItemStack current = currentItem.get(player).get(eSlot);
		if (item == current)
			return;
		else
			currentItem.get(player).put(eSlot, item);

		//Checks if the new item is air, then remove the MagicItem from the player's data.
		if (item == null || item.getType().equals(Material.AIR)) {
			equipmentData.get(player).remove(eSlot);
			return;
		}

		//Checks if the new item is a MagicItem, if no then remove the previous MagicItem from the player's data.
		MagicItem magicItem = LoreManager.getMagicItem(item);
		if (magicItem == null) {
			equipmentData.get(player).remove(eSlot);
			return;
		}
		
		//Updates the equipment data of the player.
		equipmentData.get(player).put(eSlot, magicItem);
		player.sendMessage("updated " + magicItem.getMagics().get(0).getClass().getName());
		
	}
	
	public static boolean hasMagic(Player player, Magic magic, EquipmentSlot slot) {
		if (player == null || magic == null)
			return false;
		if (slot == null) {
			for (MagicItem magicItem : equipmentData.get(player).values()) {
				for (Magic m : magicItem.getMagics()) {
					if (magic.getClass().equals(m.getClass()))
						return true;
				}
			}
			return false;
		} else {
			for (Magic m : equipmentData.get(player).get(slot).getMagics()) {
				if (magic.getClass().equals(m.getClass()))
					return true;
			}
			return false;
		}
	}
}
