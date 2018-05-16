package hell.supersoul.magic.rpg;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerM {
	
	private static HashMap<Player, PlayerM> PlayerMs = new HashMap<>();
	public static PlayerM getPlayerM(Player player) {
		return PlayerMs.get(player);
	}
	
	int MP = 0;
	int level = 0;
	
	public PlayerM(int level) {
		this.level = level;
	}

	public int getMP() {
		return MP;
	}

	public void setMP(int mP) {
		MP = mP;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
