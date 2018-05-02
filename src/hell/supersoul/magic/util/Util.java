package hell.supersoul.magic.util;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Util {
	
	public static String convertCC(String s) {
		return ChatColor.translateAlternateColorCodes((char)'&', s);
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

	public static void reddust(World w, Double x, Double y, Double z, Double r, Double g, Double b) {
		w.spawnParticle(Particle.REDSTONE, x, y, z, 0, r / 255.0, g / 255.0, b / 255.0);
	}

}
