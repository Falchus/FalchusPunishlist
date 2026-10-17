package com.falchus.punishlist.spigot.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.falchus.lib.minecraft.spigot.events.player.PlayerPacketInEvent;
import com.falchus.lib.minecraft.spigot.packets.wrapper.chat.PacketChat;
import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.spigot.Main;

public class ChatCommandListener implements Listener {

    private static final Main plugin = Main.getInstance();
    
    public ChatCommandListener() {
		Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onPlayerPacketIn(PlayerPacketInEvent event) {
    	if (!(event.getPacket() instanceof PacketChat packet)) return;
    	Player player = event.getPlayer();
    	UUID uuid = player.getUniqueId();
    	String ip = player.getAddress().getAddress().getHostAddress();
        String message = (String) packet.getMessage();
        
        if (!FalchusPunishlist.isMute(message)) return;
    	String string = FalchusPunishlist.mute(uuid, ip).join();
    	if (string == null) return;
    	
		player.sendMessage(string);
		event.setCancelled(true);
    }
}
