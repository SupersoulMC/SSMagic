package hell.supersoul.magic.core;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class ComboM extends Magic {

    public ComboM(int level) {
        super(level);
    }

    protected ArrayList<Integer> comboHitTicks = new ArrayList<>();
    protected int comboTotal;

    public abstract boolean cast(Entity victim);

    public abstract boolean cast(Player caster);

}
