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
        w.spawnParticle(Particle.REDSTONE, x, y, z, 0, r == 0 ? Float.MIN_VALUE : r / 255.0, g / 255.0, b / 255.0);
    }

    public static void createAOEParticles(Player p, double radius, double angle, double red, double green, double blue, int count) {
        Location loc = p.getLocation().clone();

        // The ring with 4 degree separation
        for (int i = 0; i <= 360; i += 4) {
            double pointx = loc.getX() + radius * Math.sin(Math.toRadians(i));
            double pointz = loc.getZ() + radius * Math.cos(Math.toRadians(i));
            redDust(p.getWorld(), pointx, loc.getY(), pointz, red, green, blue);
        }
    }

    public static void createHelixParticles(World w, double x, double y, double z, double radius, double angle, double red, double green, double blue, int count) {
        double h1x = x + radius * Math.sin(Math.toRadians(angle));
        double h1z = z + radius * Math.cos(Math.toRadians(angle));
        for (int i = 0; i < count; i++) {
            redDust(w, h1x, y, h1z, red, green, blue);
        }
    }


    public static void createArcParticles(World w, Location loc, double y1, double y2, double r1, double r2, double increment, int count) {
        Integer increases = 0;
        for(double y = y1; y <= y2; y += increment) {
            increases++;
        }
        Double rincrement = (r2 - r1) / increases;
        for(double y = y1; y <= y2; y += increment) {
            double h1x = loc.getX() + Math.sin(Math.toRadians(r1 + rincrement));
            double h1z = loc.getZ() +  Math.cos(Math.toRadians(r1 + rincrement));
            for (int i = 0; i < count; i++) {
                w.spawnParticle(Particle.CRIT_MAGIC, h1x, y, h1z, 1);
            }
            r1 += rincrement;
        }
    }

    public void createDefenseParticles(Player p, double radius, double angle, double red, double green, double blue) {
        // Hell asked me to make this, not sure what the use is for so I place it here for now
        // Default values are: red: 0, green: 127, blue: 255, radius: 1, angle: 90
        Location loc = p.getLocation().clone();
        double yaw = loc.getYaw() < 0 ? loc.getYaw() + 360 : loc.getYaw();

        // The ring
        for (double i = 0; i < 360; i += 5) {
            // Removing part of the ring in front of the player.
            if (yaw - angle / 2 >= 0 && yaw + angle / 2 < 360) {
                if (i >= yaw - angle / 2 && i <= yaw + angle / 2) continue;
            } else if (yaw - angle / 2 < 0) {
                if (i >= yaw - angle / 2 + 360 || i <= yaw + angle / 2) continue;
            } else if (yaw + angle / 2 >= 360) {
                if (i >= yaw - angle / 2 || i <= yaw + angle / 2 - 360) continue;
            }
            double z = loc.getZ() + Math.cos(Math.toRadians(i)) * radius;
            double x = loc.getX() - Math.sin(Math.toRadians(i)) * radius;

            // Spawning the ring.
            for (double y = loc.getY() + 0.7; y <= loc.getY() + 1.7; y += 0.5)
                redDust(p.getWorld(), x, y, z, red, green, blue);
        }
        // The line at the back
        for (double y = loc.getY() + 0.7; y <= loc.getY() + 1.7; y += 0.1) {
            double back = yaw < 180 ? yaw + 180 : yaw - 180;
            double z = loc.getZ() + Math.cos(Math.toRadians(back)) * radius;
            double x = loc.getX() - Math.sin(Math.toRadians(back)) * radius;
            redDust(p.getWorld(), x, y, z, red, green, blue);
        }
    }

}
