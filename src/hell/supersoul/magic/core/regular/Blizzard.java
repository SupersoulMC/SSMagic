package hell.supersoul.magic.core.regular;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import hell.supersoul.magic.util.ParticleUtil;
import hell.supersoul.magic.util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.RegularM;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("deprecation")
public class Blizzard extends RegularM {

    Double hyoriginal = 0.0;
    Double h1original = 0.0, h2original = 180.0;
    Double d = 1.2;
    Integer time = 0;
    Boolean end = false;
    List<Entity> affected = new ArrayList<>();
    World world;
    LinkedHashMap<Location, Material> changed = new LinkedHashMap<Location, Material>();

	public Blizzard(Main plugin, Integer level) {
        super.plugin = plugin;
		super.level = level;
	}
	
	@Override
	public boolean cast(Player caster) {
        new BukkitRunnable() {
            @Override
            public void run() {
                time++;
                if(time == level * 3 * 20) {
                    end = true;
                    this.cancel();
                    for(Entry<Location, Material> e2 : changed.entrySet()) {
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            p.sendBlockChange(e2.getKey(), e2.getValue().getId(), (byte) 0);
                        }
                        e2.getKey().getBlock().getWorld().spigot().playEffect(e2.getKey(), Effect.TILE_BREAK, 174, 0, 0.5f, 0.5f, 0.5f, 0f, 1, 64);
                        e2.getKey().getBlock().getWorld().playSound(e2.getKey(), Sound.BLOCK_GLASS_BREAK, 3.0F, 1.0F);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1);
        //AOE particle effect task
        new BukkitRunnable() {
            @Override
            public void run() {
                if(end) {
                    this.cancel();
                }
                Double d = level * 2.0;
                if(end) {
                    return;
                }
                Location l = caster.getLocation();
                ParticleUtil.createAOEParticles(caster, d, 0.0, 204.0 / level, 255.0, 255.0, 1);
            }
        }.runTaskTimer(plugin, 0, 2);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (end) {
                    this.cancel();
                }
                world = caster.getWorld();
                List<Entity> newaffected = new ArrayList<>();
                for (Entity e : caster.getWorld().getNearbyEntities(caster.getLocation(), level * 2, 3, level * 2)) {
                    if (!e.equals(caster) && !affected.contains(e)) {
                        if (e instanceof LivingEntity) {
                            newaffected.add(e);
                        }
                    }
                }
                affected = newaffected;
                for(Entity e : affected) {
                    ((LivingEntity) e).removePotionEffect(PotionEffectType.SLOW);
                    ((LivingEntity) e).removePotionEffect(PotionEffectType.JUMP);
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 3 * 20, level));
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, level * 3 * 20, -5));
                    Block b = e.getLocation().getBlock().getRelative(BlockFace.DOWN);
                    e.getWorld().spigot().playEffect(e.getLocation().add(0.0, 1.0, 0.0), Effect.FIREWORKS_SPARK, 0, 0, 0.5f, 0.5f, 0.5f, 0f, 1, 64);
                    if(!(b.getType() == Material.AIR) && !(b instanceof InventoryHolder)) {
                        if(!changed.containsKey(b.getLocation())) {
                            changed.put(b.getLocation(), b.getType());
                        }
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            p.sendBlockChange(b.getLocation(), Material.PACKED_ICE.getId(), (byte) 0);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(end) {
                    this.cancel();
                }
                for(Entity e : affected) {
                    if(end) {
                        this.cancel();
                        return;
                    }
                    Location l = caster.getLocation();
                    ParticleUtil.createHelixParticles(l.getWorld(), l.getX(), hyoriginal + l.getY(), l.getZ(), d, h1original, 204.0 / level, 255.0, 255.0, 4);
                    ParticleUtil.createHelixParticles(l.getWorld(), l.getX(), hyoriginal + l.getY(), l.getZ(), d, h2original, 204.0 / level, 255.0, 255.0, 4);
                    hyoriginal += 0.025;
                    if(hyoriginal > 2.0) {
                        hyoriginal = 0.0;
                    }
                    h1original += 2.25;
                    h2original += 2.25;
                }
            }
        }.runTaskTimer(plugin, 0, 1);
		return false;
	}

}
