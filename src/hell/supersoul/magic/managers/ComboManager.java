package hell.supersoul.magic.managers;

import hell.supersoul.magic.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
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
    static HashMap<Player, Integer> currentHitTask = new HashMap<>();

    // Make the combo bar, hit ticks measured in ticks
    public static void executeHit(Player player) {
        if (currentHitTask.containsKey(player))
            Bukkit.getScheduler().cancelTask(currentHitTask.get(player));
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
        if (!currentHit.containsKey(player))
            l = "7";
        else if (currentHit.get(player).equals(HitLevel.ZERO))
            l = "7";
        else if (currentHit.get(player).equals(HitLevel.ONE))
            l = "e";
        else if (currentHit.get(player).equals(HitLevel.TWO))
            l = "6";

        String pre = "¡±" + l + "¡±l> ";
        String bar = "";
        bar = bar + StringUtils.repeat("¡±8|", hitTicks.get(0));
        bar = bar + StringUtils.repeat("¡±e|", hitTicks.get(1));
        bar = bar + StringUtils.repeat("¡±6|", hitTicks.get(2));
        bar = bar + StringUtils.repeat("¡±8|", 3);
        String suf = " ¡±" + l + "¡±l<";
        String mid = bar;

        int id = new BukkitRunnable() {
            int n = 0;
            int count = 0;

            @Override
            public void run() {
                String comp = pre;
                if (n > 0)
                    comp = comp + mid.substring(0, n);
                comp = comp + "¡±c|";
                if (n + 3 <= mid.length())
                    comp = comp + mid.substring(n + 3);
                else
                    comp = pre + mid;
                comp = comp + suf;
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(comp));

                if (count <= hitTicks.get(0))
                    currentHit.put(player, HitLevel.ZERO);
                else if (count - hitTicks.get(0) <= hitTicks.get(1))
                    currentHit.put(player, HitLevel.ONE);
                else if (count - hitTicks.get(0) - hitTicks.get(1) <= hitTicks.get(2))
                    currentHit.put(player, HitLevel.TWO);
                else currentHit.put(player, HitLevel.ZERO);
                count++;
                n = n + 3;
                if (n > mid.length()) {
                    currentHitTask.remove(player);
                    currentHit.remove(player);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1).getTaskId();
        currentHitTask.put(player, id);
    }
}
