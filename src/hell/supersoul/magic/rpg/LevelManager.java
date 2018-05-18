package hell.supersoul.magic.rpg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class LevelManager {
	
	//The total amount of EXP required to be at the indexed level. (Level 0 does not exist, Level 1 does not require EXP)
	static List<Integer> levelEXP = Arrays.asList(-1,0,10,50,100,150);
	
	//The total amount of MP for each indexed level. (Level 0 does not exist.)
	static List<Integer> levelMP = Arrays.asList(-1,10,11,12,13,14);
	
	//The total amount of HP for each indexed level. (Level 0 does not exist.)
	static List<Integer> levelHP = Arrays.asList(-1,20,21,22,23,24);
	
	//The strength for each indexed level. (Level 0 does not exist.)
	static List<Integer> levelStrength = Arrays.asList(-1,0,1,2,5,7);
	
	//The defense for each indexed level. (Level 0 does not exist.)
	static List<Integer> levelDefense = Arrays.asList(-1,0,1,2,4,6);
	
	static HashMap<EntityType, Integer> defaultExp = new HashMap<>();
	static HashMap<String, Integer> mobExp = new HashMap<>();
	static {
		defaultExp.put(EntityType.ZOMBIE, 2);
	}
	
	public static List<Integer> getLevelEXP() {
		return levelEXP;
	}

	public static List<Integer> getLevelMP() {
		return levelMP;
	}

	public static List<Integer> getLevelHP() {
		return levelHP;
	}
	
	public static List<Integer> getLevelStrength() {
		return levelStrength;
	}

	public static List<Integer> getLevelDefense() {
		return levelDefense;
	}

	public static HashMap<EntityType, Integer> getDefaultExp() {
		return defaultExp;
	}

	public static HashMap<String, Integer> getMobExp() {
		return mobExp;
	}

	public static void addExp(Player player, int amount) {
		
		//Safety checks.
		if (player == null)
			return;
		PlayerM playerM = PlayerM.getPlayerM(player);
		if (playerM == null)
			return;
		
		//If player reaches max level return;
		if (playerM.getLevel() >= levelEXP.size() - 1)
			return;
		
		int olevel = playerM.getLevel();
		playerM.setEXP(playerM.getEXP() + amount);
		int nlevel = playerM.getLevel();
		if (olevel != nlevel) {
			player.sendMessage("level up");
			player.sendMessage(olevel + " to " + nlevel);
		}
	}
	
}
