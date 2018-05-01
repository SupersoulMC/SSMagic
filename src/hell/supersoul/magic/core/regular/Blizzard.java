package hell.supersoul.magic.core.regular;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

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
    Integer targetCount = 0;
    BukkitTask effectTask;

	public Blizzard(Main plugin, Integer level) {
        super.plugin = plugin;
		super.level = level;
	}
	
	@Override
	public boolean cast(Player caster) {
        for(Entity e : caster.getWorld().getNearbyEntities(caster.getLocation(), level * 2, 3, level * 2)) {
            if(!e.equals(caster)) {
                if(e instanceof LivingEntity) {
                    targetCount += 1;
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 5 * 20, level));
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, level * 5 * 20, -5));
                    LinkedHashMap<Location, Material> changed = new LinkedHashMap<Location, Material>();
                    effectTask = new BukkitRunnable() {
                        @Override
                        public void run() {
                            for(Integer i = 0; i <= 80; i++) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        Location l = caster.getLocation();
                                        Double h1x = d * Math.sin(h1original);
                                        Double h1z = d * Math.cos(h1original);
                                        h1x += l.getX();
                                        h1z += l.getZ();
                                        for(Integer count = 0; count < 4; count++) {
                                            caster.getWorld().spawnParticle(Particle.REDSTONE, h1x, hyoriginal + l.getY(), h1z, 0, (204.0 / level) / 255.0, 1.0, 1.0);
                                        }
                                        Double h2x = d * Math.sin(h2original);
                                        Double h2z = d * Math.cos(h2original);
                                        h2x += l.getX();
                                        h2z += l.getZ();
                                        for(Integer count = 0; count < 4; count++) {
                                            caster.getWorld().spawnParticle(Particle.REDSTONE, h1x, hyoriginal + l.getY(), h1z, 0, (204.0 / level) / 255.0, 1.0, 1.0);
                                        }
                                        hyoriginal += 0.025;
                                        if(hyoriginal > 2.0) {
                                            hyoriginal = 0.0;
                                        }
                                        h1original += 2.25;
                                        h2original += 2.25;
                                    }
                                }.runTaskLater(plugin, i);
                            }
                        }
                    }.runTaskTimer(plugin, 0, 80);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Block b = e.getLocation().getBlock().getRelative(BlockFace.DOWN);
                            if(((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW)) {
                                e.getWorld().spigot().playEffect(e.getLocation().add(0.0, 1.0, 0.0), Effect.FIREWORKS_SPARK, 0, 0, 0.5f, 0.5f, 0.5f, 0f, 1, 64);
                                if(!(b.getType() == Material.AIR)) {
                                    if(!changed.containsKey(b.getLocation())) {
                                        changed.put(b.getLocation(), b.getType());
                                    }
                                    for(Player p : Bukkit.getOnlinePlayers()) {
                                        p.sendBlockChange(b.getLocation(), Material.PACKED_ICE.getId(), (byte) 0);
                                    }
                                }
                            }
                            if(time == level * 5 * 20 * targetCount) {
                                for(Entry<Location, Material> e2 : changed.entrySet()) {
                                    for(Player p : Bukkit.getOnlinePlayers()) {
                                        p.sendBlockChange(e2.getKey(), e2.getValue().getId(), (byte) 0);
                                    }
                                    b.getWorld().spigot().playEffect(e2.getKey(), Effect.TILE_BREAK, 174, 0, 0.5f, 0.5f, 0.5f, 0f, 1, 64);
                                    b.getWorld().playSound(e2.getKey(), Sound.BLOCK_GLASS_BREAK, 3.0F, 1.0F);
                                }
                                this.cancel();
                                effectTask.cancel();
                            }
                            time++;
                        }
                    }.runTaskTimer(Main.instance, 0, 1);
                }
            }
		}
		return false;
	}

}
