package hell.supersoul.magic.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import hell.supersoul.magic.Main;

public class InventoryPacketListener extends PacketAdapter {

	public InventoryPacketListener() {
		super(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.SET_SLOT);
		ProtocolLibrary.getProtocolManager().addPacketListener(this);
	}

	@Override
	public void onPacketSending(PacketEvent event) {
		// Item packets (id: 0x16)
		if (event.getPacketType() == PacketType.Play.Server.SET_SLOT) {
			event.getPlayer().sendMessage("FIRED");
		}
	}

}
