package hell.supersoul.magic.rpg;

import java.util.HashMap;

import org.bukkit.entity.Player;

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
	Player player = null;
	
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
		player.setExp(MP / this.getMaxMP());
	}
	
}
