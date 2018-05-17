package hell.supersoul.magic.core.regular;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import hell.supersoul.magic.core.RegularM;
import hell.supersoul.magic.rpg.ManaManager;

public class Heal extends RegularM {
	
	public Heal(int level) {
		super(level);
		maxLevel = 5;
		requiredMP = -1;
	}

	@Override
	public void castEffects(Player caster) {
		
		double healpct = level * 0.1 + 0.5;
		double lostHealth = caster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - caster.getHealth();
		caster.setHealth(caster.getHealth() + lostHealth * healpct);
		return;
		
	}
	
}
