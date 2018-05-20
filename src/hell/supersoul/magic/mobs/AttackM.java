package hell.supersoul.magic.mobs;

public abstract class AttackM {
	
	MobM mobM = null;
	int attackTicks = 0;
	
	public AttackM(MobM mobM, int attackTicks) {
		this.attackTicks = attackTicks;
		this.mobM = mobM;
	}
	
	public abstract void attack();

	public MobM getMobM() {
		return mobM;
	}

	public int getAttackTicks() {
		return attackTicks;
	}

	public void setAttackTicks(int attackTicks) {
		this.attackTicks = attackTicks;
	}
	
}
