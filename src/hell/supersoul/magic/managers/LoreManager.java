package hell.supersoul.magic.managers;

import hell.supersoul.magic.core.ComboM;
import hell.supersoul.magic.core.LockedM;
import hell.supersoul.magic.core.Magic;
import hell.supersoul.magic.core.MagicItem;
import hell.supersoul.magic.core.MagicItem.MagicItemType;
import hell.supersoul.magic.core.MagicItem.ShortcutType;
import hell.supersoul.magic.util.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoreManager {

    public static MagicItem getMagicItem(ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR))
            return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return null;
        if (!meta.hasLore())
            return null;
        return getMagicItem(meta.getLore().get(meta.getLore().size() - 1));
    }

    //Converts string lore to a MagicItem instance
    public static MagicItem getMagicItem(String lore) {

        if (lore == null)
            return null;

        //Initialize variables for looping
        lore = Util.convertToVisibleString(lore);
        String target = null;
        for(String line : lore.split("\\#")) {
        	if (line.startsWith("SSMAGIC|")) {
        		target = line;
        		break;
        	}
        }
        if (target == null)
        	return null;
        String[] statements = target.split("\\|");
        int slots = 0;
        int strength = 0;
        int defense = 0;
        MagicItemType itemType = null;
        ArrayList<Magic> magics = new ArrayList<>();
        HashMap<Magic, Integer> magicEXP = new HashMap<>();
        HashMap<ShortcutType, Integer> shortcuts = new HashMap<>();

        //Loops through the magic statements in the lore
        for (String string : statements) {

            //Sets the slots
            if (string.startsWith("slots")) {
                String[] words = string.split(" ");
                if (words.length <= 1)
                    return null;
                int s = Integer.parseInt(words[1]);
                if (s == 0)
                    return null;
                if (slots != 0)
                    return null;
                slots = s;
            }
            
          //Sets the strength
            if (string.startsWith("strength")) {
                String[] words = string.split(" ");
                if (words.length <= 1)
                    return null;
                int s = Integer.parseInt(words[1]);
                if (s < 0)
                    return null;
                if (strength != 0)
                    return null;
                strength = s;
            }
            
          //Sets the defense
            if (string.startsWith("defense")) {
                String[] words = string.split(" ");
                if (words.length <= 1)
                    return null;
                int s = Integer.parseInt(words[1]);
                if (s < 0)
                    return null;
                if (defense != 0)
                    return null;
                defense = s;
            }

            //Sets the item type
            else if (string.startsWith("itemType")) {
                String[] words = string.split(" ");
                if (words.length <= 1)
                    return null;
                MagicItemType iT = MagicItemType.valueOf(words[1]);
                if (iT == null)
                    return null;
                if (itemType != null)
                    return null;
                itemType = iT;
            }

            //Sets the magics
            else if (string.startsWith("MAGIC")) {
                String[] words = string.split(" ");
                if (words.length < 5)
                    return null;
                String magicType = words[1].toLowerCase();
                String magicName = words[2];
                int magicLevel = Integer.parseInt(words[3]);
                int expLevel = Integer.parseInt(words[4]);
                if (magicLevel < 1)
                    return null;
                if (expLevel < 0)
                    return null;

                //Converts the string class name to a Magic instance
                Magic magic = null;
                try {
                    Class<?> clazz = Class.forName("hell.supersoul.magic.core." + magicType + "." + magicName);
                    Constructor<?> constructor = clazz.getConstructor(Integer.TYPE);
                    Object instance = constructor.newInstance(magicLevel);
                    magic = (Magic) instance;
                } catch (NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                    return null;
                }
                if (magic == null)
                    return null;

                magics.add(magic);
                magicEXP.put(magic, expLevel);
            }

            //Creates shortcut
            else if (string.startsWith("SHORTCUT")) {
                String[] words = string.split(" ");
                if (words.length < 3)
                    return null;
                ShortcutType shortcutType = ShortcutType.valueOf(words[1]);
                if (shortcutType == null)
                    return null;
                if (shortcuts.containsKey(shortcutType))
                    return null;
                int magicOrder = Integer.parseInt(words[2]);
                if (magicOrder < 0)
                    return null;
                if (magicOrder > magics.size() - 1)
                    return null;
                shortcuts.put(shortcutType, magicOrder);
            }
        }

        //Creates the MagicItem
        MagicItem magicItem = new MagicItem(itemType, slots);
        magicItem.setDefense(defense);
        magicItem.setStrength(strength);
        magicItem.getMagics().addAll(magics);
        magicItem.getMagicEXP().putAll(magicEXP);
        magicItem.getShortcuts().putAll(shortcuts);

        return magicItem;
    }
    
    //Converts a MagicItem instance for a String.
    public static String getLore(MagicItem item) {
    	
    	//Safety check.
    	if (item == null)
    		 return null;
    	
    	//Sets itemtype and slots and prefix.
    	String output = "SSMAGIC |";
    	output = output + "itemType " + item.getItemType() + "|";
    	output = output + "slots " + item.getSlots() + "|";
    	
    	//Sets the Magics.
    	for (Magic magic : item.getMagics()) {
    		
    		String type = "REGULAR";
    		if (magic instanceof ComboM)
    			type = "COMBO";
    		if (magic instanceof LockedM)
    			type = "LOCKED";
    		
    		output = output + "MAGIC " + type + " " + magic.getClass().getSimpleName() + " " + magic.getLevel() + " " + item.getMagicEXP().get(magic) + "|";
    	}
    	
    	//Sets the shortcuts.
    	for (ShortcutType type : item.getShortcuts().keySet()) {
    		output = output + "SHORTCUT " + type + " " + item.getShortcuts().get(type) + "|";
    	}
    	
    	return output;
    }
    
    public static ItemStack updateItemStack(MagicItem mi, ItemStack item) {
    	
    	//Safety checks.
    	if (mi == null)
    		return null;
    	if (item == null)
    		return null;
    	ItemMeta meta = item.getItemMeta();
    	if (meta == null)
    		return null;
    	if (!meta.hasLore())
    		return null;
    	
    	//Finds the SSMAGIC lore.
    	int index = -1;
    	String data = Util.convertToVisibleString(meta.getLore().get(meta.getLore().size()-1));
    	List<String> list = Arrays.asList(data.split("#"));
    	for (String line : list) {
    		if (line.startsWith("SSMAGIC|")) {
    			index = list.indexOf(line);
    			break;
    		}
    	}
    	if (index < 0)
    		return null;
    	
    	//Updates the line of lore.
    	String line = LoreManager.getLore(mi);
    	list.set(index, line);
    	List<String> lore =  meta.getLore();
    	lore.set(meta.getLore().size()-1, Util.convertToInvisibleString(String.join("", list)));
    	meta.setLore(lore);
    	item.setItemMeta(meta);
    	
    	return item;
    }
}
