package hell.supersoul.magic.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ParticleUtil {

    private static void redDust(World w, Double x, Double y, Double z, Double r, Double g, Double b) {
        /* 
         * When red value == 0 then it will always be red no matter what the other colour values are,
         * that's why if red == 0 set it to Float.MIN_VALUE
         */
        w.spawnParticle(Particle.REDSTONE, x, y, z, 0, r == 0 ? Float.MIN_VALUE : red / 255.0, g / 255.0, b / 255.0, 1);
    }

    public static void createAOEParticles(Player p, Double radius, Double angle, Double red, Double green, Double blue, Integer count) {
        Location loc = p.getLocation().clone();
        
        // The ring with 4 degree separation
        for(Integer i = 0; i <= 360; i += 4) {
            Double pointx = loc.getX() + radius * Math.sin(Math.toRadians(i));
            Double pointz = loc.getZ() + radius * Math.cos(Math.toRadians(i));
            redDust(p.getWorld(), pointx, loc.getY(), pointz, red, green, blue);
        }
    }

    public static void createHelixParticles(World w, Double x, Double y, Double z, Double radius, Double angle, Double red, Double green, Double blue, Integer count) {
        Double h1x = radius * Math.sin(Math.toRadians(angle));
        Double h1z = radius * Math.cos(Math.toRadians(angle));
        h1x += x;
        h1z += z;
        for(Integer i = 0; i < count; i++) {
            redDust(w, h1x, y, h1z, red, green, blue);
        }
    }
    
    public void createDefenseParticles(Player player, Double radius, Double angle, Double red, Double green, Double blue) {
        // Hell asked me to make this, not sure what the use is for so I place it here for now
	// Default values are: red: 0, green: 127, blue: 255, radius: 1, angle: 90
		Location loc = player.getLocation().clone();
		double yaw = loc.getYaw() < 0 ? loc.getYaw() + 360 : loc.getYaw();
        
		// The ring
		for (double i = 0; i < 360; i += 5) {
			// Removing part of the ring in front of the player.
			if (yaw - angle / 2 >= 0 && yaw + angle / 2 < 360) {
				if (i >= yaw - angle / 2 && i <= yaw + angle / 2) continue;
			}
			else if (yaw - angle / 2 < 0) {
				if (i >= yaw - angle / 2 + 360 || i <= yaw + angle / 2) continue;
			}
			else if (yaw + angle / 2 >= 360) {
				if (i >= yaw - angle / 2 || i <= yaw + angle / 2 - 360) continue;
			}
			double z = loc.getZ() + Math.cos(Math.toRadians(i)) * radius;
			double x = loc.getX() - Math.sin(Math.toRadians(i)) * radius;
            
			// Spawning the ring.
			for (double y = loc.getY() + 0.7; y <= loc.getY() + 1.7; y += 0.5)
                redDust(p.getWorld(), x, y, z, red, green, blue)
		}
		// The line at the back
		for (double y = loc.getY() + 0.7; y <= loc.getY() + 1.7; y += 0.1) {
			double back = yaw < 180 ? yaw + 180 : yaw - 180;
			double z = loc.getZ() + Math.cos(Math.toRadians(back)) * radius;
			double x = loc.getX() - Math.sin(Math.toRadians(back)) * radius;
			redDust(p.getWorld(), x, y, z, red, green, blue)
		}
	}

}
