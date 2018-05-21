package hell.supersoul.magic.mobs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.core.Magic;
import hell.supersoul.magic.managers.MobManager;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;

public class EventListener implements Listener {
	
	@EventHandler
	public void onMythicMobSpawn(MythicMobSpawnEvent event) {
		MythicMob mythicMob = event.getMobType();
		Bukkit.getLogger().info(mythicMob.getInternalName());
        try {
            Class<?> clazz = Class.forName("hell.supersoul.magic.mobs.entities." + mythicMob.getInternalName());
            Constructor<?> constructor = clazz.getConstructor(MythicMob.class, Entity.class);
            Object instance = constructor.newInstance(mythicMob, event.getEntity());
            MobM mobM = (MobM) instance;
            mobM.onSpawn();
            if (mobM instanceof Listener)
            	Bukkit.getPluginManager().registerEvents((Listener) mobM, Main.getInstance());
        } catch (NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
        	Bukkit.getLogger().info("ERRORED");
            return;
        }
	}
	
	@EventHandler
	public void onMythicMobDeath(MythicMobDeathEvent event) {
		if (MobManager.getAliveMobs().containsKey(event.getEntity())) {
			MobM mobM = MobManager.getAliveMobs().get(event.getEntity());
			mobM.onDeath();
			if (mobM instanceof Listener)
				HandlerList.unregisterAll((Listener) mobM);
		}
			
	}

}
