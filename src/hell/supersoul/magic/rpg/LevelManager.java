package hell.supersoul.magic.rpg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelManager {
	
	//The total amount of EXP required to be at the indexed level. (Level 0 does not exist, Level 1 does not require EXP)
	static List<Integer> levelEXP = Arrays.asList(-1,0,10,50,100,150);
	
	//The total amount of MP for each indexed level. (Level 0 does not exist.)
	static List<Integer> levelMP = Arrays.asList(-1,10,11,12,13,14);
	
	//The total amount of HP for each indexed level. (Level 0 does not exist.)
	static List<Integer> levelHP = Arrays.asList(-1,20,21,22,23,24);

	public static List<Integer> getLevelEXP() {
		return levelEXP;
	}

	public static List<Integer> getLevelMP() {
		return levelMP;
	}

	public static List<Integer> getLevelHP() {
		return levelHP;
	}
	
}
