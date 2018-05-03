package hell.supersoul.magic.managers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import hell.supersoul.magic.core.Magic;
import hell.supersoul.magic.core.MagicItem;
import hell.supersoul.magic.core.MagicItem.MagicItemType;
import hell.supersoul.magic.util.Util;

public class LoreManager {
	
	//Converts string lore to a MagicItem instance
	public static MagicItem getMagicItem(String lore) {
		
		if (lore == null)
			return null;
		
		//Initialize variables for looping
		lore = Util.convertToVisibleString(lore);
		String[] statements = lore.split("|");
		if (!statements[0].equals("SSMAGIC"))
			return null;
		int slots = 0;
		MagicItemType itemType = null;
		ArrayList<Magic> magics = new ArrayList<>();
		HashMap<Magic, Integer> magicEXP = new HashMap<>();
		
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
					Constructor<?> constructor = clazz.getConstructor(Integer.class);
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
		}
		
		//Creates the MagicItem
		MagicItem magicItem = new MagicItem(itemType, slots);
		magicItem.getMagics().addAll(magics);
		magicItem.getMagicEXP().putAll(magicEXP);
		
		return magicItem;
	}
}
