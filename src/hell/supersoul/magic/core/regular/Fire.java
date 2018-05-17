package hell.supersoul.magic.core.regular;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.RegularM;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@SuppressWarnings("deprecation")
public class Fire extends RegularM {

    public Boolean trigger = true;

    public Fire(int level) {
        super(level);
    }

    @Override
    public void castEffects(Player caster) {
        Location start = caster.getLocation().add(0.0, 1.0, 0.0);
        for (Integer i = 0; i < 19; i++) {
            Vector vector = caster.getLocation().getDirection();
            vector.normalize();
            final Integer i2 = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!trigger) {
                        return;
                    }
                    Vector vector2 = vector.multiply(i2 / 2.0).clone();
                    Location loc = start.clone().add(vector2.toLocation(caster.getWorld()));
                    World w = loc.getWorld();
                    w.spawnParticle(Particle.FLAME, loc, 4, 0.0, 0.0, 0.0, 0.0);
                    w.spawnParticle(Particle.SMOKE_NORMAL, loc, 16 * level, 0.1 * level, 0.1 * level, 0.1 * level, 0.05);
                    for (Entity e : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
                        if (e instanceof LivingEntity && !e.equals(caster)) {
                            for (Integer i2 = 0; i2 < 5; i2++) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (e.isDead()) {
                                            return;
                                        }
                                        ((LivingEntity) e).damage(level);
                                        w.spawnParticle(Particle.FLAME, e.getLocation().add(0.0, 1.0, 0.0), 4, 0.5, 0.5, 0.5, 0.0);
                                    }
                                }.runTaskLater(Main.getInstance(), i2 * 20);
                            }
                            trigger = false;
                            break;
                        }
                    }
                }
            }.runTaskLater(Main.getInstance(), i);
        }
        return;
    }
}
