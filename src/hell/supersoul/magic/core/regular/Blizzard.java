package hell.supersoul.magic.core.regular;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.RegularM;
import hell.supersoul.magic.util.ParticleUtil;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

@SuppressWarnings("deprecation")
public class Blizzard extends RegularM {

    Double hyoriginal = 0.0;
    Double horiginal = 0.0;
    Double d = 1.0;
    Integer time = 0;
    Boolean end = false;
    List<LivingEntity> affected = new ArrayList<>();
    World world;
    LinkedHashMap<Location, Material> changed = new LinkedHashMap<Location, Material>();

    public Blizzard(int level) {
        super(level);
    }

    @Override
    public boolean cast(Player caster) {
    	hyoriginal = 0.0;
        horiginal = 0.0;
        d = 1.0;
        time = 0;
        end = false;
        affected = new ArrayList<>();
        changed = new LinkedHashMap<Location, Material>();
        new BukkitRunnable() {
            @Override
            public void run() {
                time++;
                if (time == level * 3 * 20) {
                    end = true;
                    this.cancel();
                    for (Entry<Location, Material> e2 : changed.entrySet()) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendBlockChange(e2.getKey(), e2.getValue().getId(), (byte) 0);
                        }
                        e2.getKey().getBlock().getWorld().spigot().playEffect(e2.getKey(), Effect.TILE_BREAK, 174, 0, 0.5f, 0.5f, 0.5f, 0f, 1, 64);
                        e2.getKey().getBlock().getWorld().playSound(e2.getKey(), Sound.BLOCK_GLASS_BREAK, 3.0F, 1.0F);
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
                Double d = level * 2.0;
                Location l = caster.getLocation();
                ParticleUtil.createAOEParticles(caster, d, 0.0, 204.0 / level, 255.0, 255.0, 1);
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (end) {
                    this.cancel();
                }
                world = caster.getWorld();
                List<LivingEntity> newaffected = new ArrayList<>();
                for (Entity e : caster.getWorld().getNearbyEntities(caster.getLocation(), level * 2, 3, level * 2)) {
                    if (!e.equals(caster) && !affected.contains(e)) {
                        if (e instanceof LivingEntity) {
                            newaffected.add((LivingEntity) e);
                        }
                    }
                }
                affected = newaffected;
                for (LivingEntity e : affected) {
                    e.removePotionEffect(PotionEffectType.SLOW);
                    e.removePotionEffect(PotionEffectType.JUMP);
                    e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 3 * 20, level));
                    e.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, level * 3 * 20, -5));
                    Block b = e.getLocation().getBlock().getRelative(BlockFace.DOWN);
                    e.getWorld().spigot().playEffect(e.getLocation().add(0.0, 1.0, 0.0), Effect.FIREWORKS_SPARK, 0, 0, 0.5f, 0.5f, 0.5f, 0f, 1, 64);
                    if (!(b.getType() == Material.AIR) && !(b instanceof InventoryHolder)) {
                        if (!changed.containsKey(b.getLocation())) {
                            changed.put(b.getLocation(), b.getType());
                        }
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendBlockChange(b.getLocation(), Material.PACKED_ICE.getId(), (byte) 0);
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (end) {
                    this.cancel();
                }
                for (Entity e : affected) {
                    if (end) {
                        this.cancel();
                        return;
                    }
                    Location l = caster.getLocation();
                    ParticleUtil.createReddustHelixParticles(l.getWorld(), l.getX(), hyoriginal + l.getY(), l.getZ(), d, horiginal, 204.0 / level, 255.0, 255.0, 4);
                    ParticleUtil.createReddustHelixParticles(l.getWorld(), l.getX(), hyoriginal + l.getY(), l.getZ(), d, horiginal + 180, 204.0 / level, 255.0, 255.0, 4);
                    hyoriginal += 0.1;
                    if (hyoriginal > 2.0) {
                        hyoriginal = 0.0;
                    }
                    horiginal += 18;
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
        return false;
    }

}
