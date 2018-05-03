package hell.supersoul.magic.events;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.RegularM;
import hell.supersoul.magic.core.combo.Berserk;
import hell.supersoul.magic.core.regular.Lightning;
import hell.supersoul.magic.core.regular.RegularMEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class EventProcesser extends Event implements Listener {

    Main plugin;
    public Map<String, Long> lasthit = new HashMap<String, Long>();
    public Map<String, Integer> counthit = new HashMap<String, Integer>();


    public EventProcesser(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public HandlerList getHandlers() {
        // TODO Auto-generated method stub
        return null;
    }

    @EventHandler
    public void cast(PlayerInteractEvent e) {
        if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().hasItemMeta() && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Wand")) {
            String itemname = e.getItem().getItemMeta().getDisplayName();
            RegularMEnum.valueOf(itemname.split(" ")[1]).get(Integer.parseInt(itemname.split(" ")[2])).cast(e.getPlayer());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void hit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            Date now = new Date();
            if (lasthit.get(p.getName()) == null) {
                lasthit.put(p.getName(), now.getTime());
            } else {
                lasthit.put(p.getName(), now.getTime());
                if ((now.getTime() - lasthit.get(p.getName())) <= 2000) {
                    //COMBO COUNT
                    if (counthit.get(p.getName()) == 5) {
                        counthit.put(p.getName(), 0);
                        Berserk berserk = new Berserk(5);
                        berserk.cast(e.getEntity());
                    } else {
                        counthit.put(p.getName(), counthit.get(p.getName()));
                    }
                }
            }
        }
    }

}
