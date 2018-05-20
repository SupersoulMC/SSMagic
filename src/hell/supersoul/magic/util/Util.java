package hell.supersoul.magic.util;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class Util {

	public static String convertCC(String s) {
		return ChatColor.translateAlternateColorCodes((char) '&', s);
	}

	public static int randomInteger(int min, int max) {

		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static double randomDouble(double min, double max) {

		Random rand = new Random();

		double randomNum = min + (max - min) * rand.nextDouble();

		return randomNum;
	}

	public static Vector findPointOnLineBetweenPoints(Vector p1, Vector p2, double t) {
		double x = (1.0 - t) * p1.getX() + t * p2.getX();
		double y = (1.0 - t) * p1.getY() + t * p2.getY();
		double z = (1.0 - t) * p1.getZ() + t * p2.getZ();
		return new Vector(x, y, z);
	}

	public static boolean getLookingAt(Player player, Entity entity) {
		Location eye = player.getEyeLocation();
		Vector toEntity = entity.getLocation().toVector().subtract(eye.toVector());
		double dot = toEntity.normalize().dot(eye.getDirection());
		return dot > 0.99D;
	}

	public static String convertToInvisibleString(String s) {
		String hidden = "";
		for (char c : s.toCharArray())
			hidden += ChatColor.COLOR_CHAR + "" + c;
		return hidden;
	}

	public static String convertToVisibleString(String s) {
		String hidden = s.replaceAll("¡±", "");
		return hidden;
	}

	public static Location getRandomLocation(Location origin, double radius) {
		Random r = new Random();
		double randomRadius = r.nextDouble() * radius;
		double theta = Math.toRadians(r.nextDouble() * 360);
		double phi = Math.toRadians(r.nextDouble() * 180 - 90);
		double x = randomRadius * Math.cos(theta) * Math.sin(phi);
		double y = randomRadius * Math.sin(theta) * Math.cos(phi);
		double z = randomRadius * Math.cos(phi);
		Location newLoc = origin.add(x, y, z);
		return newLoc;
	}
	
	public static Location randomLocationInRegion(Location loc1, Location loc2) {
		if (!loc1.getWorld().equals(loc2.getWorld())) return null;
		return new Location(loc1.getWorld(), randomInteger(loc1.getBlockX(), loc2.getBlockX()), randomInteger(loc1.getBlockY(), loc2.getBlockY()),randomInteger(loc1.getBlockZ(), loc2.getBlockZ()));
	}
	
	public static void replaceBlock(Location loc1, Location loc2, Material replacement, Material mat, byte data, boolean effect) {
		if (loc1.getWorld() == null || loc2.getWorld() == null) return;
		if (mat == null) return;
		if (!loc1.getWorld().equals(loc2.getWorld())) return;
		int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
		int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
		int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		World world = loc1.getWorld();
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					Block block = world.getBlockAt(x, y, z);
					if (!block.getType().equals(replacement)) continue;
					if (effect)
						world.playEffect(new Location(world, x, y, z), Effect.STEP_SOUND, block.getTypeId());
					block.setType(mat);
					block.setData(data);
				}
			}
		}
	}
}
