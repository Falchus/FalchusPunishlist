package com.falchus.punishlist.spigot.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.falchus.lib.minecraft.spigot.events.player.PlayerPacketInEvent;
import com.falchus.lib.minecraft.spigot.packets.wrapper.chat.PacketChat;
import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.spigot.Main;

public class ChatCommandListener implements Listener {

    private static final Main plugin = Main.getInstance();
    
    public ChatCommandListener() {
		Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPacketIn(PlayerPacketInEvent event) {
    	if (!(event.getPacket() instanceof PacketChat packet)) return;
    	check(event, event.getPlayer(), (String) packet.getMessage());
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
    	check(event, event.getPlayer(), event.getMessage());
    }
    
    private void check(Cancellable event, Player player, String message) {
    	if (!FalchusPunishlist.isMute(message)) return;
    	UUID uuid = player.getUniqueId();
    	String ip = player.getAddress().getAddress().getHostAddress();
        
    	String string = FalchusPunishlist.get().mute(uuid, ip).join();
    	if (string == null) return;
    	
    	player.sendMessage(string);
    	event.setCancelled(true);
    }
}
