package hell.supersoul.magic.core.combo;

import hell.supersoul.magic.core.ComboM;
import hell.supersoul.magic.managers.ComboManager.HitLevel;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Berserk extends ComboM {

    public Berserk(int level) {
        super(level);
        this.comboTotal = 5;
        this.comboHitTicks.add(7);
        this.comboHitTicks.add(5);
        this.comboHitTicks.add(3);
        this.comboHitTicks.add(3);
    }

    @Override
    public void unleashCombo(Player caster, Entity victim, double completePct) {
        if (victim instanceof LivingEntity) {
            ((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5 * level, 1));
            ((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * level, 1));
        }
        return;
    }

	@Override
	public void normalHit(Player caster, Entity hitTarget, HitLevel level) {
		for(Entity e : caster.getNearbyEntities(2.0, 2.0, 2.0)) {
		    Vector vector = e.getLocation().toVector().subtract(caster.getLocation().toVector()).normalize();
		    vector.multiply(0.5);
		    vector.setY(0.5);
		    e.setVelocity(vector);
        }
		return;
	}

}
