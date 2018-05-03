package hell.supersoul.magic.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class EventListener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		//if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))
		//	ComboManager.executeHit(event.getPlayer());
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity ent = event.getDamager();
		if (!ent.getType().equals(EntityType.PLAYER))
			return;
		Player player = (Player) ent;
		ComboManager.executeHit(player);
	}
	
	@EventHandler
	public void onPlayerHeldItemEvent(PlayerItemHeldEvent event) {
		if (ComboManager.currentHit.containsKey(event.getPlayer())) {
			event.setCancelled(true);
			return;
		}
		EquipmentManager.checkAndUpdate(event.getPlayer(), event.getNewSlot());
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player)
		EquipmentManager.checkAndUpdate((Player)event.getWhoClicked(), event.getSlot());
	}
	
}