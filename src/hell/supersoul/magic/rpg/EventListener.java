package hell.supersoul.magic.rpg;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import hell.supersoul.magic.Main;

public class EventListener implements Listener {
	
	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent event) {
		event.setAmount(0);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Main.loadPlayerData(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Main.unloadPlayerData(event.getPlayer());
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		
		//Safety Checks
		LivingEntity ent = event.getEntity();
		Player player = ent.getKiller();
		if (player == null)
			return;
		
		//TODO check if it is an MM Mob.
		
		//Check default list.
		if (LevelManager.getDefaultExp().containsKey(ent.getType())) {
			int amount = LevelManager.getDefaultExp().get(ent.getType());
			LevelManager.addExp(player, amount);
			Hologram holo = HologramsAPI.createHologram(Main.getInstance(), ent.getEyeLocation());
			holo.getVisibilityManager().setVisibleByDefault(false);
			holo.getVisibilityManager().showTo(player);
			holo.appendTextLine(ChatColor.AQUA.toString() + amount + " EXP");
			
			new BukkitRunnable() {
	            @Override
	            public void run() {
	            	holo.delete();
	            }
	        }.runTaskLater(Main.getInstance(), 20);
		}
		
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK))
			event.setCancelled(true);
	}
	
}
