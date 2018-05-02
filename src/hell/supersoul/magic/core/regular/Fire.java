package hell.supersoul.magic.core.regular;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.RegularM;

@SuppressWarnings("deprecation")
public class Fire extends RegularM {

	public Boolean trigger = true;
	
	public Fire(Main plugin, Integer level) {
		super.plugin = plugin;
		super.level = level;
	}
	
	@Override
	public boolean cast(Player caster) {
		Location start = caster.getLocation().add(0.0, 1.0, 0.0);
		for(Integer i = 0; i < 19; i++) {
			Vector vector = caster.getLocation().getDirection();
			vector.normalize();
			final Integer i2 = i;
			new BukkitRunnable() {
				@Override
				public void run() {
					if(!trigger) {
						return;
					}
					Vector vector2 = vector.multiply(i2 / 2.0).clone();
					Location loc = start.clone().add(vector2.toLocation(caster.getWorld()));
					caster.getWorld().spigot().playEffect(loc, Effect.FLAME, 0, 0, 0, 0, 0, 0, 16, 64);
					for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
						if(e instanceof LivingEntity && !e.equals(caster)) {
							for(Integer i2 = 0; i2 < 5; i2++) {
								new BukkitRunnable() {
									@Override
									public void run() {
										if(e.isDead()) {
											return;
										}
										((LivingEntity) e).damage(level);
										e.getWorld().spigot().playEffect(e.getLocation().add(0.0, 1.0, 0.0), Effect.FLAME, 0, 0, 0.5f, 0.5f, 0.5f, 0f, 16, 64);
									}
								}.runTaskLater(Main.instance, i2 * 20);
							}
							trigger = false;
							break;
						}
					}
				}
			}.runTaskLater(plugin, i);
		}
		return false;
	}

}
