package hell.supersoul.magic.core;

import java.util.ArrayList;
import java.util.HashMap;

public class MagicItem {

    public enum MagicItemType {
        BOOK, TOOL, ARMOR;
    }

    public enum MagicType {
        REGULAR, COMBO, LOCKED
    }

    ArrayList<Magic> magics = new ArrayList<>();
    HashMap<Magic, Integer> magicEXP = new HashMap<>();
    int slots = 0;
    MagicItemType itemType = null;

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

}
