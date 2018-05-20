package hell.supersoul.magic.mobs;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.lumine.xikage.mythicmobs.mobs.MythicMob;

public abstract class MobM {
	
	MythicMob mythicMob = null;
	HashMap<Integer, ArrayList<AttackM>> phasedAttacks = new HashMap<>();
	String prefix = "";
	Entity entity = null;
	
	public MobM(MythicMob mythicMob, Entity entity) {
		this.mythicMob = mythicMob;
		this.entity = entity;
		onSpawn();
	}
	
	public abstract void onSpawn();
	
	public void changeAttackPhase(int oldPhase, int newPhase) {
	}

	public MythicMob getMythicMob() {
		return mythicMob;
	}

	public HashMap<Integer, ArrayList<AttackM>> getPhasedAttacks() {
		return phasedAttacks;
	}
	
	public void say(String message) {
		this.say(message, 30);
	}
	
	public void say(String message, double radius) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getLocation().distance(entity.getLocation()) <= radius)
				player.sendMessage(prefix + ChatColor.DARK_GRAY + ChatColor.BOLD + " > " + message);
		}
	}

	public Entity getEntity() {
		return entity;
	}
	
}
