package hell.supersoul.magic.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
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

}
