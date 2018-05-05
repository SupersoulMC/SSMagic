package hell.supersoul.magic.core.combo;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.ComboM;
import hell.supersoul.magic.managers.ComboManager.HitLevel;
import hell.supersoul.magic.util.ParticleUtil;
import hell.supersoul.magic.util.Util;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Berserk extends ComboM {

    World world;
    double point1 = 0.0;
    double y1 = 1.0;
    double point2 = 0.0;
    double y2 = 2.5;

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
        world = caster.getWorld();
        if (victim instanceof LivingEntity) {
            ((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5 * level, 1));
            ((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * level, 1));
        }
        //First stage
        int delay = 0;
        int count = 0;
        for (double radius1 = 0.1; radius1 <= 3.0; radius1 += 0.1) {
            double finalRadius = radius1;
            new BukkitRunnable() {
                @Override
                public void run() {
                    Location loc = caster.getLocation();
                    Double finalY = loc.getY() + y1;
                    Double finalR = point1 + loc.getYaw();
                    ParticleUtil.createHelixParticles(world, Particle.SPELL_WITCH, loc.getX(), finalY, loc.getZ(), finalRadius, finalR, 0.0, 0.0, 0.0, 1, 0.0);
                    ParticleUtil.createHelixParticles(world, Particle.SPELL_WITCH, loc.getX(), finalY, loc.getZ(), finalRadius, finalR + 120.0, 0.0, 0.0, 0.0, 1, 0.0);
                    ParticleUtil.createHelixParticles(world, Particle.SPELL_WITCH, loc.getX(), finalY, loc.getZ(), finalRadius, finalR + 240.0, 0.0, 0.0, 0.0, 1, 0.0);
                    point1 += 4;
                    y1 += 2 / 30;
                }
            }.runTaskLater(Main.getInstance(), delay);
            if (count == 4) {
                count = 0;
                delay++;
            } else {
                count++;
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                int delay = 0;
                int count = 0;
                //Second stage
                for (double radius2 = 0.2; radius2 <= 2.0; radius2 += 0.2) {
                    double finalRadius = radius2;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Location loc = caster.getLocation();
                            Double finalY = loc.getY() + y2;
                            Double finalR = point1 + loc.getYaw();
                            ParticleUtil.createHelixParticles(world, Particle.SPELL_WITCH, loc.getX(), finalY, loc.getZ(), finalRadius, finalR, 0.0, 0.0, 0.0, 1, 0.0);
                            ParticleUtil.createHelixParticles(world, Particle.SPELL_WITCH, loc.getX(), finalY, loc.getZ(), finalRadius, finalR + 120.0, 0.0, 0.0, 0.0, 1, 0.0);
                            ParticleUtil.createHelixParticles(world, Particle.SPELL_WITCH, loc.getX(), finalY, loc.getZ(), finalRadius, finalR + 240.0, 0.0, 0.0, 0.0, 1, 0.0);
                        }
                    }.runTaskLater(Main.getInstance(), delay);
                    point2 += 12;
                    y2 += 0.05;
                    if (count == 4) {
                        count = 0;
                        delay++;
                    } else {
                        count++;
                    }
                }
            }
        }.runTaskLater(Main.getInstance(), delay);
        point1 = 0.0;
        y1 = 1.0;
        point2 = 0.0;
        y2 = 2.5;
        return;
    }

    @Override
    public void normalHit(Player caster, Entity hitTarget, HitLevel level) {
        Location loc = caster.getLocation();
        List<Double> points = getRandomPointRotation(loc);
        Float yaw = loc.getYaw() + 360;
        ParticleUtil.createArcParticles(caster.getWorld(), caster.getLocation(), points.get(0), points.get(1), points.get(2) + yaw, points.get(3) + yaw, 0.05, 10,
                caster.getLocation().distance(hitTarget.getLocation()) - 0.2);
    }

    public List<Double> getRandomPointRotation(Location loc) {
        boolean randomPoint = new Random().nextBoolean();
        List<Double> points = new ArrayList<>();
        if (randomPoint) {
            double point1Y = Util.randomDouble(1.0, 2.0) + loc.getY();
            double point2Y = Util.randomDouble(0.0, 1.0) + loc.getY();
            double point1R = Util.randomDouble(-35.0, -25.0);
            double point2R = Util.randomDouble(25.0, 35.0);
            points.add(point1Y);
            points.add(point2Y);
            points.add(point1R);
            points.add(point2R);
        } else {
            double point1Y = Util.randomDouble(0.0, 1.0) + loc.getY();
            double point2Y = Util.randomDouble(1.0, 2.0) + loc.getY();
            double point1R = Util.randomDouble(25.0, 35.0);
            double point2R = Util.randomDouble(-35.0, -25.0);
            points.add(point1Y);
            points.add(point2Y);
            points.add(point1R);
            points.add(point2R);
        }
        return points;
    }

}
