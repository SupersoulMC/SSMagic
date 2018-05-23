package hell.supersoul.magic.mobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.connorlinfoot.titleapi.TitleAPI;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.mobs.entities.IceWitch;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;

public abstract class MobM {

	MythicMob mythicMob = null;
	HashMap<Integer, ArrayList<AttackM>> phasedAttacks = new HashMap<>();
	HashMap<Integer, ArrayList<Integer>> attackChance = new HashMap<>();
	String prefix = "";
	Entity entity = null;
	boolean attacking = false;
	int currentPhase = 0;
	ArrayList<Integer> tasksToEndOnDeath = new ArrayList<>();

	public MobM(MythicMob mythicMob, Entity entity) {
		this.mythicMob = mythicMob;
		this.entity = entity;
		onSpawn();
	}

	public abstract void onSpawn();

	public abstract void onDeath();

	public void changeAttackPhase(int oldPhase, int newPhase) {
		currentPhase = newPhase;
	}

	public MythicMob getMythicMob() {
		return mythicMob;
	}

	public HashMap<Integer, ArrayList<AttackM>> getPhasedAttacks() {
		return phasedAttacks;
	}

	public void say(String message) {
		this.say(message, 30);
	}

	public void say(String message, double radius) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getLocation().distance(entity.getLocation()) <= radius)
				player.sendMessage(prefix + ChatColor.DARK_GRAY + ChatColor.BOLD + " > " + message);
		}
	}

	public void startAttacking() {

		// Safety checks
		if (attacking)
			return;
		if (attackChance.size() == 0)
			return;
		if (attackChance.get(0).size() == 0)
			return;
		if (attackChance.size() != phasedAttacks.size())
			return;
		for (int i : attackChance.keySet())
			if (attackChance.get(i).size() != phasedAttacks.get(i).size())
				return;

		attacking = true;
		nextAttack(currentPhase);
	}

	public void nextAttack(int phase) {

		if (phase != currentPhase)
			return;
		if (this.getEntity().isDead())
			return;

		// Adds the total chance.
		double totalWeight = 0.0d;
		for (int i : attackChance.get(currentPhase))
			totalWeight += i;

		// Chooses the next attack.
		int nextAttack = -1;
		double random = Math.random() * totalWeight;
		for (int i = 0; i < attackChance.get(currentPhase).size(); i++) {
			random -= attackChance.get(currentPhase).get(i);
			if (random <= 0.0d) {
				nextAttack = i;
				break;
			}
		}

		AttackM attackM = phasedAttacks.get(phase).get(nextAttack);
		attackM.attack();
		Bukkit.getLogger().info("Attacked, next attack is " + attackM.getAttackTicks());

		new BukkitRunnable() {
			@Override
			public void run() {
				nextAttack(phase);
			}
		}.runTaskLater(Main.getInstance(), attackM.getAttackTicks());
	}

	public Entity getEntity() {
		return entity;
	}

	public HashMap<Integer, ArrayList<Integer>> getAttackChance() {
		return attackChance;
	}

	public ArrayList<Integer> getTasksToEndOnDeath() {
		return tasksToEndOnDeath;
	}

	public void displayBossWarning(String message, Player player) {
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "- WARNING -", "", 0, player);
		this.sendDelayTitle(0, 5, 0, "", "", 1, player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "- WARNING -", "", 2, player);
		this.sendDelayTitle(0, 5, 0, "", "", 3, player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "- WARNING -", "", 4, player);
		this.sendDelayTitle(0, 5, 0, "", "", 5, player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "- WARNING -", "", 6, player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "- WARNING -",
				ChatColor.GRAY + "" + ChatColor.BOLD + "> " + message + ChatColor.GRAY + "" + ChatColor.BOLD + " <", 8,
				player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "- WARNING -", "", 9, player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "- WARNING -",
				ChatColor.GRAY + "" + ChatColor.BOLD + "> " + message + ChatColor.GRAY + "" + ChatColor.BOLD + " <", 10,
				player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "- WARNING -", "", 11, player);
		this.sendDelayTitle(0, 45, 0, ChatColor.RED + "- WARNING -",
				ChatColor.GRAY + "" + ChatColor.BOLD + "> " + message + ChatColor.GRAY + "" + ChatColor.BOLD + " <", 12,
				player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "- WARNING -", "", 13, player);
		this.sendDelayTitle(0, 45, 0, ChatColor.RED + "- WARNING -",
				ChatColor.GRAY + "" + ChatColor.BOLD + "> " + message + ChatColor.GRAY + "" + ChatColor.BOLD + " <", 14,
				player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "-WARNING-", "", 54, player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "-ARNIN-", "", 56, player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "-RNI-", "", 58, player);
		this.sendDelayTitle(0, 5, 0, ChatColor.RED + "-N-", "", 60, player);
		this.sendDelayTitle(0, 1, 0, ChatColor.RED + "--", "", 62, player);
	}

	public void sendDelayTitle(int fadein, int stay, int fadeout, String title, String subTitle, long delay,
			Player player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				TitleAPI.sendTitle(player, fadein, stay, fadeout, title, subTitle);
			}
		}.runTaskLater(Main.getInstance(), delay);
	}

}
