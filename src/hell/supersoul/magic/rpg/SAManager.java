package hell.supersoul.magic.rpg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

public class SAManager {

	public enum StatusAilment {
		FROZEN, PARALYZED, BURN, SLEEP, SILENCED, CONFUSED, INJURED, TAUNTING, POISON;
	}

	public static void applyStatusAilment(Player player, StatusAilment status, int durationTicks,
			boolean extendExisting) {

		// Safety checks.
		if (player == null || status == null || durationTicks <= 0)
			return;
		PlayerM playerM = PlayerM.getPlayerM(player);
		if (playerM == null)
			return;

		// If player already has the status ailment, extend or override it.
		if (playerM.getStatusAilments().containsKey(status)) {
			int current = 0;
			if (extendExisting)
				current = playerM.getStatusAilments().get(status);
			playerM.getStatusAilments().put(status, current + durationTicks);
			return;
		}
		
		//Apply the status
		//TODO complete all the status ailments.
		switch (status) {
		case FROZEN:
			break;
		case BURN:
			break;
		case CONFUSED:
			break;
		case INJURED:
			break;
		case PARALYZED:
			break;
		case POISON:
			break;
		case SILENCED:
			break;
		case SLEEP:
			break;
		case TAUNTING:
			break;
		}

	}

	public static void cureStatusAilment(Player player, StatusAilment status) {
		
	}

}
