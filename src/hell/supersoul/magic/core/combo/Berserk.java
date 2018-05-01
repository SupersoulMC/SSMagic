package hell.supersoul.magic.core.combo;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import hell.supersoul.magic.core.ComboM;

public class Berserk extends ComboM {

	public Berserk(Integer level) {
		super.level = level;
	}

	@Override
	public boolean cast(Player caster) {
		return false;
	}

	@Override
	public boolean cast(Entity victim) {
		if(victim instanceof LivingEntity) {
			((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5 * level, 1));
			((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * level, 1));
		}
		return false;
	}

}
