package hell.supersoul.magic.managers;

import java.util.HashMap;

import org.bukkit.entity.Entity;

import hell.supersoul.magic.mobs.MobM;

public class MobManager {
	
	static HashMap<Entity, MobM> aliveMobs = new HashMap<>();

	public static HashMap<Entity, MobM> getAliveMobs() {
		return aliveMobs;
	}
	
}
