package hell.supersoul.magic.rpg;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
}
