package hell.supersoul.magic.core;

import hell.supersoul.magic.managers.ComboManager.HitLevel;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class ComboM extends Magic {

    public ComboM(int level) {
        super(level);
    }

    protected ArrayList<Integer> comboHitTicks = new ArrayList<>();
    protected int comboTotal;

    public abstract void unleashCombo(Player caster, Entity hitTarget, double completePct);

    public abstract void normalHit(Player caster, Entity hitTarget, HitLevel level);

    public int getComboTotal() {
        return comboTotal;
    }

    public void setComboTotal(int comboTotal) {
        this.comboTotal = comboTotal;
    }

    public ArrayList<Integer> getComboHitTicks() {
        return comboHitTicks;
    }

}
