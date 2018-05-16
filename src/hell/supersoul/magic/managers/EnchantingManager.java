package hell.supersoul.magic.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import hell.supersoul.magic.core.Magic;
import hell.supersoul.magic.core.MagicItem;
import hell.supersoul.magic.core.MagicItem.MagicItemType;
import hell.supersoul.magic.util.Util;

public class EnchantingManager {
	
	public static ItemStack enchantMagic(ItemStack item, Magic magic) {
		
		//Safety checks.
		if (item == null)
			return null;
		if (magic == null)
			return null;
		MagicItem mi = LoreManager.getMagicItem(item);
		if (mi == null)
			return null;
		
		//The Magic Item cannot enchant the same magic again.
		if (mi.getMagics().contains(magic))
			return null;
		
		//Enchants the magic
		//TODO Assigns the player's exp instead.
		mi.getMagics().add(magic);
		mi.getMagicEXP().put(magic, 1);
		
		//Updates the lore of the itemstack.
		return LoreManager.updateItemStack(mi, item);
		
	}
	
	public static ItemStack upgradeMagic(ItemStack item, Magic magic) {
		
		//Safety checks.
		if (item == null)
			return null;
		if (magic == null)
			return null;
		MagicItem mi = LoreManager.getMagicItem(item);
		if (mi == null)
			return null;
		
		//The MagicItem must contain the magic for upgrade.
		if (!mi.getMagics().contains(magic))
			return null;
		
		//Gets the real instance in the magic list.
		int index = mi.getMagics().indexOf(magic);
		Magic instance = mi.getMagics().get(index);
		
		//Both the real instance and the parameter magic should be the same.
		if (magic.getLevel() != instance.getLevel())
			return null;
		
		//Check if the magic has reached the max level.
		if (instance.getLevel() == instance.getMaxLevel())
			return null;
		
		//Upgrades the magic.
		//TODO Assigns the player's exp instead.
		instance.setLevel(instance.getLevel() + 1);
		mi.getMagicEXP().replace(instance, 1);
		
		return LoreManager.updateItemStack(mi, item);
	}
	
	public static ItemStack activateItem(ItemStack item, int slots) {
		
		//Safety checks.
		if (item == null)
			return null;
		if (slots <= 0)
			return null;
		if (LoreManager.getMagicItem(item) != null)
			return null;
		
		//Adds the prefix if the itemStack doesn't have it.
		String line = "SSDATA#";
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore()) {
			meta.setLore(new ArrayList<>());
		}
		List<String> lore = meta.getLore();
		if (!Util.convertToVisibleString(lore.get(lore.size()-1)).contains("SSDATA")) {
			lore.add(Util.convertToInvisibleString("SSDATA#"));
			meta.setLore(lore);
		}
		
		
		//Gets the itemType from the itemStack.
		MagicItemType itemType = EnchantingManager.getItemType(item);
		if (itemType == null)
			return null;
		
		//Creates the MagicItem, then updates the itemStack.
		MagicItem mi = new MagicItem(itemType, slots);
		return LoreManager.updateItemStack(mi, item);
	}
	
	public static ArrayList<ItemStack> removeMagic(ItemStack item) {
		//TODO The magic book is not completed yet, this method returns null temporarily.
		return null;
	}
	
	public static MagicItemType getItemType(ItemStack item) {
		if (item == null)
			return null;
		Material type = item.getType();
		if (type.equals(Material.BOOK))
			return MagicItemType.BOOK;
		else if (Arrays.asList(Material.CHAINMAIL_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET, Material.IRON_HELMET, Material.LEATHER_HELMET,
				Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.LEATHER_CHESTPLATE,
				Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.LEATHER_LEGGINGS,
				Material.CHAINMAIL_BOOTS, Material.DIAMOND_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.LEATHER_BOOTS).contains(type))
			return MagicItemType.ARMOR;
		else if (Arrays.asList(Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD,
				Material.DIAMOND_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.STONE_SPADE, Material.WOOD_SPADE,
				Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOOD_AXE,
				Material.DIAMOND_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.WOOD_PICKAXE,
				Material.DIAMOND_HOE, Material.GOLD_HOE, Material.IRON_HOE, Material.STONE_HOE, Material.WOOD_HOE).contains(type))
			return MagicItemType.TOOL;
		return null;
	}
	
}
