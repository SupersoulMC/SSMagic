package hell.supersoul.magic.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.managers.ComboManager.HitLevel;
import hell.supersoul.magic.rpg.PlayerM;
import hell.supersoul.magic.util.Util;
import net.md_5.bungee.api.ChatColor;

public class DamageM {

	public enum DamageType {
		MAGIC, PHYSICAL, STATUS_AILMENT;
	}

	Entity attacker;
	Entity victim;
	DamageType damageType;
	double rawDamage = 0;

	public DamageM(double rawDamage) {
		this.rawDamage = rawDamage;
	}

	public double getFinalDamage(boolean displayHolo) {

		double damage = rawDamage;
		double damageModifier = 1.0;
		double reductionModifier = 1.0;
		String debug = "RAW DAMAGE: " + rawDamage;
		debug += " ATTACKER: ";
		ChatColor damageColor = ChatColor.GOLD;

		if (attacker != null) {

			if (attacker instanceof Player) {

				Player player = (Player) attacker;
				PlayerM playerM = PlayerM.getPlayerM(player);
				if (playerM == null)
					return 0;

				// Consider player's strength.
				double strengthModifier = (playerM.getTotalStrength() + 5d) / 5d;
				damageModifier *= strengthModifier;
				debug += " The Strength Modifier is: " + strengthModifier;

				// Consider the hit level.
				if (ComboManager.getCurrentHit().containsKey(player)) {
					double hitLevelModifier = 1.0;
					HitLevel hl = ComboManager.getCurrentHit().get(player);
					if (hl.equals(HitLevel.TWO))
						hitLevelModifier = 1.25;
					else if (hl.equals(HitLevel.ONE))
						hitLevelModifier = 0.1;
					damageModifier *= hitLevelModifier;
					debug += " The Hit Level Modifier is: " + hitLevelModifier;
				}

				// TODO other things to consider:
				// Locked Magic, Potion Effect, Status Ailments

			} else {
				// TODO Mob do damage, compare to MM
			}
		}

		debug += " DEFENDER: ";

		if (victim != null) {

			if (victim instanceof Player) {

				Player player = (Player) victim;
				PlayerM playerM = PlayerM.getPlayerM(player);
				if (playerM == null)
					return 0;

				// Consider player's defense;
				double defenseModifier = 100d / (playerM.getTotalDefense() + 100d);
				reductionModifier *= defenseModifier;
				debug += " The Defense Modifier is: " + defenseModifier;

				// TODO other things to consider:
				// Locked Magic, Potion Effect, Status Ailments

			} else {
				// TODO check MM
			}

		}

		damage = damage * damageModifier * reductionModifier;
		debug += " FINAL DAMAGE: " + damage;
		if (attacker != null)
			attacker.sendMessage("A-" + debug);
		victim.sendMessage("V-" + debug);

		// Handle the hologram thing.
		if (displayHolo) {
			Location loc = Util.getRandomLocation(victim.getLocation().add(0, 1, 0), 0.7);
			if (attacker instanceof Player) {
				Hologram holo1 = HologramsAPI.createHologram(Main.getInstance(), loc);
				Player player = (Player) attacker;
				holo1.getVisibilityManager().setVisibleByDefault(false);
				holo1.getVisibilityManager().showTo(player);
				holo1.appendTextLine(damageColor + "-" + (int) damage);

				Hologram holo2 = HologramsAPI.createHologram(Main.getInstance(), loc);
				holo2.getVisibilityManager().setVisibleByDefault(true);
				holo2.getVisibilityManager().hideTo(player);
				holo2.appendTextLine(ChatColor.GRAY + "-" + (int) damage);

				new BukkitRunnable() {
					@Override
					public void run() {
						holo1.delete();
						holo2.delete();
					}
				}.runTaskLater(Main.getInstance(), 10);
			} else {
				Hologram holo2 = HologramsAPI.createHologram(Main.getInstance(), loc);
				holo2.getVisibilityManager().setVisibleByDefault(true);
				holo2.appendTextLine(ChatColor.GRAY + "-" + (int) damage);

				new BukkitRunnable() {
					@Override
					public void run() {
						holo2.delete();
					}
				}.runTaskLater(Main.getInstance(), 10);
			}
		}

		return damage;

	}

	public Entity getAttacker() {
		return attacker;
	}

	public void setAttacker(Entity attacker) {
		this.attacker = attacker;
	}

	public Entity getVictim() {
		return victim;
	}

	public void setVictim(Entity victim) {
		this.victim = victim;
	}

	public DamageType getDamageType() {
		return damageType;
	}

	public void setDamageType(DamageType damageType) {
		this.damageType = damageType;
	}

	public double getRawDamage() {
		return rawDamage;
	}

	public void setRawDamage(double rawDamage) {
		this.rawDamage = rawDamage;
	}

}
