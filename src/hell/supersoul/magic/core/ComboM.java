package hell.supersoul.magic.core;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class ComboM extends Magic {
	
	public abstract boolean cast(Entity victim);
	public abstract boolean cast(Player caster);
	
}
