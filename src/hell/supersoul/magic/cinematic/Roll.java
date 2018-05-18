package hell.supersoul.magic.cinematic;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Roll {

    Location location1;
    Location location2;
    Boolean smoothStart;
    Boolean smoothEnd;

    public Roll(Location location1, Location location2, Boolean smoothStart, Boolean smoothEnd) {
        this.location1 = location1;
        this.location2 = location2;
        this.smoothStart = smoothStart;
        this.smoothEnd = smoothEnd;
    }

    public void start(Player p) {

    }

}
