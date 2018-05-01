package hell.supersoul.magic.core.regular;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.RegularM;
import net.minecraft.server.v1_12_R1.EntityLightning;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityWeather;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@SuppressWarnings("deprecation")
public class Lightning extends RegularM {
	
	public Boolean trigger = true;
	
	public Lightning(Integer level) {
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
						this.cancel();
					}
					Vector vector2 = vector.multiply(i2 / 2.0).clone();
					Location loc = start.clone().add(vector2.toLocation(caster.getWorld()));
					caster.getWorld().spigot().playEffect(loc, Effect.COLOURED_DUST, 0, 0, 1f, 1f, 1f, 1, 0, 64);
					for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
						if(e instanceof LivingEntity && !e.equals(caster) && !(e instanceof EntityLightning)) {
							Location loc1 = e.getLocation();
							EntityLightning el = new EntityLightning(((CraftWorld) Bukkit.getWorld(caster.getWorld().getName())).getHandle(), loc1.getX(), loc1.getY(), loc1.getZ(), true);
							PacketPlayOutSpawnEntityWeather packet = new PacketPlayOutSpawnEntityWeather(el);
							for(Player p : Bukkit.getOnlinePlayers()) {
								((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
							}
							((LivingEntity) e).damage(level * 3.0);
							((LivingEntity) e).setFireTicks(level * 40);
							trigger = false;
						}
					}
				}
			}.runTaskLater(Main.instance, i);
		}
		return false;
	}

}
