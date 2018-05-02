package hell.supersoul.magic.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ParticleUtil {

    private static void reddust(World w, Double x, Double y, Double z, Double r, Double g, Double b) {
        w.spawnParticle(Particle.REDSTONE, x, y, z, 0, r / 255.0, g / 255.0, b / 255.0);
    }

    public static void createAOEParticles(Player p, Double radius, Double angle, Double red, Double green, Double blue, Integer count) {
        for(Integer i = 0; i <= 360; i++) {
            Location loc = p.getLocation();
            Double pointx = radius * Math.sin(i);
            Double pointz = radius * Math.cos(i);
            pointx += loc.getX();
            pointz += loc.getZ();
            reddust(p.getWorld(), pointx, loc.getY(), pointz, red, green, blue);
        }
    }

    public static void createHelixParticles(World w, Double x, Double y, Double z, Double radius, Double angle, Double red, Double green, Double blue, Integer count) {
        Double h1x = radius * Math.sin(angle);
        Double h1z = radius * Math.cos(angle);
        h1x += x;
        h1z += z;
        for(Integer i = 0; i < count; i++) {
            reddust(w, h1x, y, h1z, red, green, blue);
        }
    }

}
