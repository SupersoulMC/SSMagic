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
		
		//Safety checks.
		if (player == null)
			return;
		PlayerM playerM = PlayerM.getPlayerM(player);
		if (playerM == null)
			return;
		if (chargingTasks.containsKey(player))
			return;

		//Creates the MP Charge task.
		int chargeSeconds = 5;
		int taskID = new BukkitRunnable() {
			int time = chargeSeconds * 20 + 1;
            @Override
            public void run() {
            	time --;
            	
            	//Changes the HUD, mark down the time left.
            	chargingTime.put(player, time);
            	player.setExp( (float) time / (chargeSeconds*20f));
            	player.setLevel(0);
            	
            	//Resets back to normal state.
            	if (time <= 0) {
            		chargingTasks.remove(player);
            		chargingTime.remove(player);
            		playerM.setMP(playerM.getMaxMP());
            		this.cancel();
            	}
            	
            }
        }.runTaskTimer(Main.getInstance(), 0, 1).getTaskId();
        
        //Reset player's MP, mark down the taskID.
        chargingTasks.put(player, taskID);
        playerM.setMP(0);
        
	}
	
	//Deducts the player's MP. Use -1 for amount to deduct all MP.
	public static boolean deductMP(Player player, int amount) {
		
		//Safety checks.
		if (player == null)
			return false;
		PlayerM playerM = PlayerM.getPlayerM(player);
		if (playerM == null)
			return false;
		
		//False when MP is charging.
		if (chargingTasks.containsKey(player))
			return false;
		
		//False when not enough MP.
		if (amount > playerM.getMP())
			return false;
		
		//Deducts the MP and returns true.
		if (amount == -1) {
			playerM.setMP(0);
		} else {
			playerM.setMP(playerM.getMP() - amount);
		}
		return true;
		
	}
	
}
