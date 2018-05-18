package hell.supersoul.magic.rpg;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import hell.supersoul.magic.rpg.SAManager.StatusAilment;

public class PlayerM {
	
	private static HashMap<Player, PlayerM> playerMs = new HashMap<>();
	public static PlayerM getPlayerM(Player player) {
		return playerMs.get(player);
	}
	
	public static HashMap<Player, PlayerM> getPlayerMs() {
		return playerMs;
	}
	
	int MP = 0;
	int EXP = 0;
	int strength = 0;
	int defense = 0;
	Player player = null;
	HashMap<StatusAilment, Integer> statusAilments = new HashMap<>();
	HashMap<StatusAilment, ArrayList<Integer>> statusAilmentsTasks = new HashMap<>();
	
	public PlayerM(Player player, int exp) {
		this.EXP = exp;
		this.player = player;
		
		playerMs.put(player, this);
	}

	public int getMP() {
		return MP;
	}

	public void setMP(int mP) {
		MP = mP;
		this.updateHUD();
		if (MP <= 0)
			ManaManager.chargeMP(player);
	}

	public int getEXP() {
		return EXP;
	}

	public void setEXP(int exp) {
		this.EXP = exp;
	}

	public Player getPlayer() {
		return player;
	}
	
	public int getLevel() {
		for (int exp : LevelManager.getLevelEXP()) {
			if (EXP < exp) {
				return LevelManager.getLevelEXP().indexOf(exp) - 1;
			}
		}
		int maxlv = LevelManager.getLevelEXP().size() - 1;
		if (EXP >= LevelManager.getLevelEXP().get(maxlv))
			return maxlv;
		return 1;
	}
	
	public int getMaxHP() {
		return LevelManager.getLevelHP().get(getLevel());
	}
	
	public int getMaxMP() {
		return LevelManager.getLevelMP().get(getLevel());
	}
	
	public void updateHUD() {
		player.setLevel(MP);
		player.setExp(MP / (float) this.getMaxMP());
	}

	public HashMap<StatusAilment, Integer> getStatusAilments() {
		return statusAilments;
	}

	public HashMap<StatusAilment, ArrayList<Integer>> getStatusAilmentsTasks() {
		return statusAilmentsTasks;
	}
	
	public int getStrength() {
		return LevelManager.getLevelStrength().get(getLevel());
	}
	
	public int getDefense() {
		return LevelManager.getLevelDefense().get(getLevel());
	}
}
