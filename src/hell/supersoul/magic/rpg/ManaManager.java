package hell.supersoul.magic.rpg;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import hell.supersoul.magic.Main;

public class ManaManager {

	//chargingTime is in ticks.
	static HashMap<Player, Integer> chargingTasks = new HashMap<>();
	static HashMap<Player, Integer> chargingTime = new HashMap<>();
	
	
	
	public static void chargeMP(Player player) {
		
		if (player == null)
			return;
		PlayerM playerM = PlayerM.getPlayerM(player);
		if (playerM == null)
			return;
		if (chargingTasks.containsKey(player))
			return;

		int taskID = new BukkitRunnable() {
			int time = 50 * 20 + 1;
            @Override
            public void run() {
            	time --;
            	
            	chargingTime.put(player, time);
            	player.setExp( (float) time / (50f*20f));
            	player.setLevel(0);
            	
            	if (time <= 0) {
            		chargingTasks.remove(player);
            		chargingTime.remove(player);
            		playerM.setMP(playerM.getMaxMP());
            		this.cancel();
            	}
            	
            }
        }.runTaskTimer(Main.getInstance(), 0, 1).getTaskId();
        
        chargingTasks.put(player, taskID);
        playerM.setMP(0);
        
	}
	
}
