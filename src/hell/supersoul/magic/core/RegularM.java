package hell.supersoul.magic.core;

import org.bukkit.entity.Player;

public abstract class RegularM extends Magic {
	
	boolean casting = false;

    public RegularM(int level) {
        super(level);
    }

    public abstract boolean cast(Player caster);
}
