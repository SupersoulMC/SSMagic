package hell.supersoul.magic.core.regular;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.RegularM;
import hell.supersoul.magic.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("deprecation")
public class Water extends RegularM {

    public Water(int level) {
        super(level);
    }

    @Override
    public boolean cast(Player caster) {
        for (Entity e1 : caster.getWorld().getNearbyEntities(caster.getLocation(), 10.0, 10.0, 10.0)) {
            if (!e1.equals(caster) && Util.getLookingAt(caster, e1) && e1 instanceof LivingEntity) {
                Location casterposition = caster.getLocation().add(0, 1, 0);
                for (Integer i = 1; i < 11; i++) {
                    final Integer i2 = i;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                        public void run() {
                            caster.getWorld().spigot().playEffect(Util.findPointOnLineBetweenPoints(casterposition.toVector(), e1.getLocation().add(0, 1, 0).toVector(), (i2 / 10.0)).toLocation(caster.getWorld()), Effect.SPLASH, 0, 0, 0, 0, 0, 0, 10, 128);
                            if (i2.equals(9)) {
                                for (Integer i3 = 1; i3 < 5; i3++) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            if (e1.isDead()) {
                                                this.cancel();
                                            }
                                            ((LivingEntity) e1).damage(level * 0.5);
                                        }
                                    }.runTaskLater(Main.getInstance(), i3 * 10);
                                }
                            }
                        }
                    }, i);
                }
                break;
            }
        }
        return false;
    }


}
