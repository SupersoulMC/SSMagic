package hell.supersoul.magic.managers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // if (event.getAction().equals(Action.LEFT_CLICK_AIR) ||
        // event.getAction().equals(Action.LEFT_CLICK_BLOCK))
        // ComboManager.executeHit(event.getPlayer());
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity ent = event.getDamager();
        if (!ent.getType().equals(EntityType.PLAYER))
            return;
        Player player = (Player) ent;
        ComboManager.executeHit(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerHeldItemEvent(PlayerItemHeldEvent event) {
        if (ComboManager.currentHit.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }
        if (!event.isCancelled())
            EquipmentManager.checkAndUpdate(event.getPlayer(), event.getNewSlot());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.isCancelled())
            return;
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        if (event.getSlot() != player.getInventory().getHeldItemSlot())
            return;
        EquipmentManager.checkAndUpdate(player, event.getSlot(), event.getCursor());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.isCancelled())
            return;
        if (!(event.getWhoClicked() instanceof Player))
            return;
		/*if (event.getNewItems().size() > 1)
			return;*/
        Player player = (Player) event.getWhoClicked();
        for (int slot : event.getInventorySlots()) {
            if (slot != player.getInventory().getHeldItemSlot())
                continue;
            EquipmentManager.checkAndUpdate(player, slot, event.getOldCursor());
        }
    }
    
    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
    	event.setCancelled(true);
    }

}