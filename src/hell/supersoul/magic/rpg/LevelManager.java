package hell.supersoul.magic.rpg;

import java.util.ArrayList;
import java.util.Arrays;

public class LevelManager {
	
	//The total amount of EXP required to be at the indexed level. (Level 0 does not exist, Level 1 does not require EXP)
	public static ArrayList<Integer> levelEXP = (ArrayList<Integer>) Arrays.asList(-1,0,10,50,100,150);
	
	//The total amount of MP for each indexed level. (Level 0 does not exist.)
	public static ArrayList<Integer> levelMP = (ArrayList<Integer>) Arrays.asList(-1,10,11,12,13,14);
	
	//The total amount of HP for each indexed level. (Level 0 does not exist.)
	public static ArrayList<Integer> levelHP = (ArrayList<Integer>) Arrays.asList(-1,20,21,22,23,24);
	
}
