package hell.supersoul.magic.managers;

import hell.supersoul.magic.core.MagicItem.ShortcutType;
import hell.supersoul.magic.managers.DamageM.DamageType;
import hell.supersoul.magic.rpg.PlayerM;
import hell.supersoul.magic.rpg.PlayerM.StatSource;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class EventListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (!player.isSneaking())
				EquipmentManager.triggerShortcut(player, ShortcutType.RIGHT_CLICK);
			else
				EquipmentManager.triggerShortcut(player, ShortcutType.SHIFT_RIGHT_CLICK);
		} else if (event.getAction().equals(Action.LEFT_CLICK_AIR)
				|| event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (!player.isSneaking())
				EquipmentManager.triggerShortcut(player, ShortcutType.LEFT_CLICK);
			else
				EquipmentManager.triggerShortcut(player, ShortcutType.SHIFT_LEFT_CLICK);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		if (event.isApplicable(DamageModifier.BLOCKING))
			event.setDamage(DamageModifier.BLOCKING, 0);
		if (event.isApplicable(DamageModifier.ARMOR))
			event.setDamage(DamageModifier.ARMOR, 0);
		if (!(event instanceof EntityDamageByEntityEvent)) {
			DamageM damageM = new DamageM(event.getDamage());
			damageM.setVictim(event.getEntity());
			damageM.setDamageType(DamageType.PHYSICAL);
			event.setDamage(damageM.getFinalDamage(true));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		Entity ent = event.getDamager();
		if (ent.getType().equals(EntityType.PLAYER)) {
			Player player = (Player) ent;
			ComboManager.executeHit(player, event.getEntity());
			event.setDamage(1);
		}

		// RPG Part is put here to ensure the hit is executed.
		DamageM damageM = new DamageM(event.getDamage());
		damageM.setAttacker(event.getDamager());
		damageM.setVictim(event.getEntity());
		damageM.setDamageType(DamageType.PHYSICAL);
		event.setDamage(damageM.getFinalDamage(true));
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
		if (event.getRawSlot() == event.getSlot())
			return;
		Player player = (Player) event.getWhoClicked();
		if (event.getSlot() == player.getInventory().getHeldItemSlot()
				|| (event.getSlot() >= 36 && event.getSlot() <= 39)) {
			EquipmentManager.checkAndUpdate(player, event.getSlot(), event.getCursor());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryDrag(InventoryDragEvent event) {
		if (event.isCancelled())
			return;
		if (!(event.getWhoClicked() instanceof Player))
			return;
		/*
		 * if (event.getNewItems().size() > 1) return;
		 */
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