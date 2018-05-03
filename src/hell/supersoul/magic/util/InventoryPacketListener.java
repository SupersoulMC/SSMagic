package hell.supersoul.magic.util;

import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.managers.EquipmentManager;

public class InventoryPacketListener extends PacketAdapter {

	public InventoryPacketListener() {
		super(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.SET_SLOT);
		ProtocolLibrary.getProtocolManager().addPacketListener(this);
	}

	@Override
	public void onPacketSending(PacketEvent event) {
		// Item packets (id: 0x16)
		if (event.isCancelled())
			return;
		if (event.getPacketType() == PacketType.Play.Server.SET_SLOT) {
			int slot = event.getPacket().getIntegers().read(1) - 36;
			if (slot != event.getPlayer().getInventory().getHeldItemSlot())
				return;
			EquipmentManager.checkAndUpdate(event.getPlayer(), slot);
		}
	}

}
