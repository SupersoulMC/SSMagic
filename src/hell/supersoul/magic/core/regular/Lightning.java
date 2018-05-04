package hell.supersoul.magic.core.regular;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.RegularM;
import hell.supersoul.magic.util.ParticleUtil;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class Lightning extends RegularM {

    World world;
    Integer time = 0;
    Boolean end = false;
    List<Location> targeted = new ArrayList<>();

    public Lightning(int level) {
        super(level);
    }

    @Override
    public boolean cast(Player caster) {
        world = caster.getWorld();
        List<Location> newtargeted = new ArrayList<>();
        for (Entity e : caster.getWorld().getNearbyEntities(caster.getLocation(), 5 * Math.pow(1.5, level), 3, 5 * Math.pow(1.5, level))) {
            if (!e.equals(caster) && !targeted.contains(e)) {
                if (e instanceof LivingEntity) {
                    newtargeted.add(e.getLocation());
                }
            }
        }
        targeted = newtargeted;
        Main.getInstance().getEventProcessor().freezed.put(caster.getName(), true);
        new BukkitRunnable() {
            @Override
            public void run() {
                time++;
                if (time == Math.round(5 * Math.pow(0.9, level) * 20)) {
                    Main.getInstance().getEventProcessor().freezed.put(caster.getName(), false);
                    end = true;
                    this.cancel();
                    for (Location loc : targeted) {
                        EntityLightning e = new EntityLightning(((CraftWorld) Bukkit.getWorld(caster.getWorld().getName())).getHandle(), loc.getX(), loc.getY(), loc.getZ(), true);
                        PacketPlayOutSpawnEntityWeather pposew = new PacketPlayOutSpawnEntityWeather(e);
                        PacketPlayOutNamedSoundEffect pponse = new PacketPlayOutNamedSoundEffect(SoundEffects.dK, SoundCategory.WEATHER, loc.getX(), loc.getY(), loc.getZ(), 1, 1f);
                        for (Entity e2 : loc.getWorld().getNearbyEntities(loc, 32, 32, 32)) {
                            if (e2 instanceof CraftPlayer) {
                                ((CraftPlayer) e2).getHandle().playerConnection.sendPacket(pposew);
                                ((CraftPlayer) e2).getHandle().playerConnection.sendPacket(pponse);
                            }
                        }
                        for (Entity e2 : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
                            if (e2 instanceof LivingEntity) {
                                ((LivingEntity) e2).damage(3 * level);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
        //AOE particle effect task
        new BukkitRunnable() {
            @Override
            public void run() {
                if (end) {
                    this.cancel();
                    return;
                }
                Double d = 5 * Math.pow(1.5, level);
                ParticleUtil.createAOEParticles(caster, d, 0.0, 255.0, 255.0, 0.0, 1);
            }
        }.runTaskTimer(Main.getInstance(), 0, 5);
        new BukkitRunnable() {
            @Override
            public void run() {
                    if (end) {
                        this.cancel();
                    }
                    for (Location loc : targeted) {
                    Double castTime = Math.round(5 * Math.pow(0.9, level) * 20) * 1.0;
                    Double distanceFromPlayer = (castTime - time) / castTime * 255.0;
                    for(Integer i = 0; i < 10; i++) {
                        world.spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY() + 2.0 + distanceFromPlayer + 1.1 , loc.getZ(), 0, 1, 1, 0.0);
                        world.spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY() + 2.0 + distanceFromPlayer , loc.getZ(), 0, 1, 1, 0.0);
                    }
                    for(Integer i = 0; i < 1; i++) {
                        world.spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 0, (120.0 + 135.0 * (castTime - time) / castTime) / 255.0 , 0.0, 21/255.0);
                        world.spawnParticle(Particle.REDSTONE, loc.getX() + 0.5, loc.getY(), loc.getZ() + 0.5, 0, (120.0 + 135.0 * (castTime - time) / castTime) / 255.0 , 0.0, 21/255.0);
                        world.spawnParticle(Particle.REDSTONE, loc.getX() + 0.5, loc.getY(), loc.getZ() - 0.5, 0, (120.0 + 135.0 * (castTime - time) / castTime) / 255.0 , 0.0, 21/255.0);
                        world.spawnParticle(Particle.REDSTONE, loc.getX()  - 0.5, loc.getY(), loc.getZ() - 0.5, 0, (120.0 + 135.0 * (castTime - time) / castTime) / 255.0 , 0.0, 21/255.0);
                        world.spawnParticle(Particle.REDSTONE, loc.getX() - 0.5, loc.getY(), loc.getZ() + 0.5, 0, (120.0 + 135.0 * (castTime - time) / castTime) / 255.0 , 0.0, 21/255.0);
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 5);
        return false;
    }

}
