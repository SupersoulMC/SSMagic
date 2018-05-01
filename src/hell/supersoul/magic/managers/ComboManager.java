package hell.supersoul.magic.managers;

import hell.supersoul.magic.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class ComboManager {
    enum HitLevel {
        ZERO, ONE, TWO;
    }

    static HashMap<Player, HitLevel> currentHit = new HashMap<>();
    static HashMap<Player, HitLevel> lastHit = new HashMap<>();

    // Make the combo bar, hit ticks measured in ticks
    public static void executeHit(Player player) {
        ArrayList<Integer> hitTicks = new ArrayList<>();
        if (player == null)
            return;
        if (!player.isOnline())
            return;
        ItemStack item = player.getItemOnCursor();
        if (item == null || item.getType().equals(Material.AIR)) {
            hitTicks.add(10);
            hitTicks.add(6);
            hitTicks.add(4);
        }

        int total = 0;
        for (int i : hitTicks) {
            total += i;
        }
        int t = total;

        String l = "";
        if (!lastHit.containsKey(player))
            l = "7";
        else if (lastHit.get(player).equals(HitLevel.ZERO))
            l = "7";
        else if (lastHit.get(player).equals(HitLevel.ONE))
            l = "e";
        else if (lastHit.get(player).equals(HitLevel.TWO))
            l = "6";

        String pre = "?กำ" + l + "?กำl> ";
        String bar = "";
        bar = bar + StringUtils.repeat("?กำ8|", hitTicks.get(0));
        bar = bar + StringUtils.repeat("?กำe|", hitTicks.get(1));
        bar = bar + StringUtils.repeat("?กำ6|", hitTicks.get(2));
        bar = bar + StringUtils.repeat("?กำ8|", 3);
        String suf = " ?กำ" + l + "?กำl<";
        String mid = bar;

        new BukkitRunnable() {
            int n = 0;

            @Override
            public void run() {
                String comp = pre;
                if (n > 0)
                    comp = comp + mid.substring(0, n);
                comp = comp + "?กำc|";
                if (n + 3 <= mid.length())
                    comp = comp + mid.substring(n + 3);
                else
                    comp = pre + mid;
                comp = comp + suf;
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(comp));

                n = n + 3;
                if (n > mid.length()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.instance, 0, 1);
    }
}