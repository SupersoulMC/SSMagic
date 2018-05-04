package hell.supersoul.magic.core.combo;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.ComboM;
import hell.supersoul.magic.managers.ComboManager.HitLevel;

import hell.supersoul.magic.util.ParticleUtil;
import hell.supersoul.magic.util.Util;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Arrays;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Location loc = caster.getLocation();
        Double point1Y = Util.randomDouble(0.0, 2.0) + loc.getY();
        Double point2Y = Util.randomDouble(0.0, 2.0) + loc.getY();
        List<Double> points = getRandomPointRotation(loc);
        ParticleUtil.createArcParticles(caster.getWorld(), caster.getLocation(), point1Y, point2Y, points.get(0) + loc.getYaw(), points.get(1) + loc.getYaw(), 0.05, 10);
		return;
	}

	public List<Double> getRandomPointRotation(Location loc) {
        Boolean randomPoint = new Random().nextBoolean();
        List<Double> points = new ArrayList<>();
        if(randomPoint) {
            Double point1R = Util.randomDouble(-35.0, -25.0);
            Double point2R = Util.randomDouble(25.0, 35.0);
            points.add(point1R);
            points.add(point2R);
        } else {
            Double point1R = Util.randomDouble(25.0, 35.0);
            Double point2R = Util.randomDouble(-35.0, -25.0);
            points.add(point1R);
            points.add(point2R);
        }
        return points;
    }

}
