package hell.supersoul.magic.core;

import hell.supersoul.magic.core.regular.RegularMEnum;
import org.bukkit.entity.Player;

public abstract class RegularM extends Magic {

    public RegularM(int level) {
        super(level);
    }

    public abstract boolean cast(Player caster);
    public abstract RegularMEnum getType();

}
