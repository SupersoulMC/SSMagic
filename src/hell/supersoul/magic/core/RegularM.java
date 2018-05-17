package hell.supersoul.magic.core;

import org.bukkit.entity.Player;

import hell.supersoul.magic.rpg.ManaManager;

public abstract class RegularM extends Magic {
	
	boolean casting = false;
	protected int requiredMP = -1;

    public RegularM(int level) {
        super(level);
    }

    public boolean cast(Player caster) {
    	if (!ManaManager.deductMP(caster, requiredMP)) {
    		//TODO cast failure effects.
    		return false;
    	}
    	castEffects(caster);
    	return true;
    }
    
    protected abstract void castEffects(Player caster);
}
