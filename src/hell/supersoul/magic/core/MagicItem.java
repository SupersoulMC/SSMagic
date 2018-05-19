package hell.supersoul.magic.core;

import java.util.ArrayList;
import java.util.HashMap;

public class MagicItem {

    public enum MagicItemType {
        BOOK, TOOL, ARMOR;
    }

    public enum MagicType {
        REGULAR, COMBO, LOCKED;
    }

    public enum ShortcutType {
        LEFT_CLICK, RIGHT_CLICK, SHIFT_LEFT_CLICK, SHIFT_RIGHT_CLICK;
    }

    ArrayList<Magic> magics = new ArrayList<>();
    HashMap<Magic, Integer> magicEXP = new HashMap<>();
    int slots = 0;
    MagicItemType itemType = null;
    HashMap<ShortcutType, Integer> shortcuts = new HashMap<>();
    int strength = 0;
    int defense = 0;

    public MagicItem(MagicItemType itemType, int slots) {
        this.itemType = itemType;
        this.slots = slots;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public MagicItemType getItemType() {
        return itemType;
    }

    public void setItemType(MagicItemType itemType) {
        this.itemType = itemType;
    }

    public HashMap<Magic, Integer> getMagicEXP() {
        return magicEXP;
    }

    public ArrayList<Magic> getMagics() {
        return magics;
    }

    public HashMap<ShortcutType, Integer> getShortcuts() {
        return shortcuts;
    }

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

}
