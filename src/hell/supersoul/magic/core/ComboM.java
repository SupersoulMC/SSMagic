package hell.supersoul.magic.core;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class ComboM extends Magic {
	
	public ComboM(int level) {
		super(level);
	}
	protected ArrayList<Integer> comboHitTicks = new ArrayList<>();
	protected int comboTotal;
	public abstract boolean cast(Entity victim);
	public abstract boolean cast(Player caster);
	
}
