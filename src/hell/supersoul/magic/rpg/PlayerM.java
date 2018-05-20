package hell.supersoul.magic.rpg;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import hell.supersoul.magic.managers.EquipmentManager;
import hell.supersoul.magic.rpg.SAManager.StatusAilment;

public class PlayerM {

	private static HashMap<Player, PlayerM> playerMs = new HashMap<>();

	public static PlayerM getPlayerM(Player player) {
		return playerMs.get(player);
	}

	public static HashMap<Player, PlayerM> getPlayerMs() {
		return playerMs;
	}

	public enum StatSource {
		EQUIPMENT, LEVEL;
	}

	int MP = 0;
	int EXP = 0;
	HashMap<StatSource, Integer> strength = new HashMap<>();
	HashMap<StatSource, Integer> defense = new HashMap<>();
	Player player = null;
	HashMap<StatusAilment, Integer> statusAilments = new HashMap<>();
	HashMap<StatusAilment, ArrayList<Integer>> statusAilmentsTasks = new HashMap<>();

	public PlayerM(Player player, int exp) {
		this.EXP = exp;
		this.player = player;
		strength.put(StatSource.EQUIPMENT, 0);
		defense.put(StatSource.EQUIPMENT, 0);

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
		player.setExp(MP / (float) this.getMaxMP());
		player.setLevel(MP);
	}

	public HashMap<StatusAilment, Integer> getStatusAilments() {
		return statusAilments;
	}

	public HashMap<StatusAilment, ArrayList<Integer>> getStatusAilmentsTasks() {
		return statusAilmentsTasks;
	}

	public int getStrength(StatSource source) {
		if (source.equals(StatSource.LEVEL))
			return LevelManager.getLevelStrength().get(getLevel());
		else
			return strength.get(source);
	}

	public int getDefense(StatSource source) {
		if (source.equals(StatSource.LEVEL))
			return LevelManager.getLevelDefense().get(getLevel());
		else
			return defense.get(source);
	}
	
	public int getTotalStrength() {
		int total = 0;
		for (int value : strength.values())
			total += value;
		total += LevelManager.getLevelStrength().get(getLevel());
		return total;
	}
	
	public int getTotalDefense() {
		int total = 0;
		for (int value : defense.values())
			total += value;
		total += LevelManager.getLevelDefense().get(getLevel());
		return total;
	}
	
	public void updateEquipmentStats() {
		strength.put(StatSource.EQUIPMENT, EquipmentManager.getEquipmentStrength(player));
		defense.put(StatSource.EQUIPMENT, EquipmentManager.getEquipmentDefense(player));
	}

	public HashMap<StatSource, Integer> getStrength() {
		return strength;
	}

	public HashMap<StatSource, Integer> getDefense() {
		return defense;
	}
}
