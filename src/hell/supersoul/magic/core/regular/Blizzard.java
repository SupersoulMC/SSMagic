package hell.supersoul.magic.core.regular;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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

@SuppressWarnings("deprecation")
public class Blizzard extends RegularM {
	
	
	public Blizzard(Integer level) {
		super.level = level;
	}
	
	@Override
	public boolean cast(Player caster) {
		if(level == 1) {
		    Integer count = 0;
			for(Entity e : caster.getWorld().getNearbyEntities(caster.getLocation(), 3, 3, 3)) {
				if(!e.equals(caster)) {
					if(e instanceof LivingEntity) {
					    count++;
						((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 5 * 20, level));
						((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, level * 5 * 20, -5));
						LinkedHashMap<Location, Material> changed = new LinkedHashMap<Location, Material>();
						new BukkitRunnable() {
							@Override
				            public void run() {
								Block b = e.getLocation().getBlock().getRelative(BlockFace.DOWN);
								if(((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW)) {
                                    e.getWorld().spigot().playEffect(e.getLocation().add(0.0, 1.0, 0.0), Effect.FIREWORKS_SPARK, 0, 0, 0.5f, 0.5f, 0.5f, 0f, 16, 64);
									if(!(b.getState() instanceof InventoryHolder) && !(b.getType() == Material.AIR)) {
										if(!changed.containsKey(b.getLocation())) {
											changed.put(b.getLocation(), b.getType());
										}
										for(Player p : Bukkit.getOnlinePlayers()) {
											p.sendBlockChange(b.getLocation(), Material.PACKED_ICE.getId(), (byte) 0);
										}
									}
								} else {
									for(Entry<Location, Material> e2 : changed.entrySet()) {
										for(Player p : Bukkit.getOnlinePlayers()) {
											p.sendBlockChange(e2.getKey(), e2.getValue().getId(), (byte) 0);
										}
										b.getWorld().spigot().playEffect(e2.getKey(), Effect.TILE_BREAK, 174, 0, 0.5f, 0.5f, 0.5f, 0f, 16, 64);
										b.getWorld().playSound(e2.getKey(), Sound.BLOCK_GLASS_BREAK, 3.0F, 1.0F);
									}
									this.cancel();
								}
							}
						}.runTaskTimer(Main.instance, 0, 1);
					}
				}
			}
			if(count == 0) {
                caster.getWorld().spigot().playEffect(caster.getLocation().add(0.0, 1.0, 0.0), Effect.FIREWORKS_SPARK, 0, 0, 3f, 2f, 3f, 10f, 16, 64);
            }
		} else {
			for(Entity e : caster.getWorld().getNearbyEntities(caster.getLocation(), level * 2, 3, level * 2)) {
				if(!e.equals(caster)) {
					if(e instanceof LivingEntity) {
						((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 5 * 20, level));
						((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, level * 5 * 20, -5));
						LinkedHashMap<Location, Material> changed = new LinkedHashMap<Location, Material>();
						new BukkitRunnable() {
							@Override
				            public void run() {
								Block b = e.getLocation().getBlock().getRelative(BlockFace.DOWN);
								if(((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW)) {
									e.getWorld().spigot().playEffect(e.getLocation().add(0.0, 1.0, 0.0), Effect.FIREWORKS_SPARK, 0, 0, 0.5f, 0.5f, 0.5f, 0f, 16, 64);
									if(!(b.getState() instanceof InventoryHolder) && !(b.getType() == Material.AIR)) {
										if(!changed.containsKey(b.getLocation())) {
											changed.put(b.getLocation(), b.getType());
										}
										for(Player p : Bukkit.getOnlinePlayers()) {
											p.sendBlockChange(b.getLocation(), Material.PACKED_ICE.getId(), (byte) 0);
										}
									}
								} else {
									for(Entry<Location, Material> e2 : changed.entrySet()) {
										for(Player p : Bukkit.getOnlinePlayers()) {
											p.sendBlockChange(e2.getKey(), e2.getValue().getId(), (byte) 0);
										}
										b.getWorld().spigot().playEffect(e2.getKey(), Effect.TILE_BREAK, 174, 0, 0.5f, 0.5f, 0.5f, 0f, 16, 64);
										b.getWorld().playSound(e2.getKey(), Sound.BLOCK_GLASS_BREAK, 3.0F, 1.0F);
									}
									this.cancel();
								}
							}
						}.runTaskTimer(Main.instance, 0, 1);
					}
				}
			}
		}
		return false;
	}

}
