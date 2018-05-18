package hell.supersoul.magic.cinematic;

import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Sequence {

    String name;
    List<Roll> rolls;

    public Sequence(String name) { //Not created before plugin loads
        this.name = name;
        this.rolls = new LinkedList<>();
    }

    public Sequence(String name, LinkedList<Roll> rolls) { //Loaded from config
        this.name = name;
        this.rolls = rolls;
    }

    public void start(Player p) { //Loop all rolls and start
        for(Roll roll : rolls) {
            roll.start(p);
        }
    }

}
