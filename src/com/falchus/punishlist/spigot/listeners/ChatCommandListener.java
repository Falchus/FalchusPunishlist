package com.falchus.punishlist.spigot.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.spigot.Main;

public class ChatCommandListener implements Listener {

    private final Main plugin = Main.getInstance();
    
    public ChatCommandListener() {
		Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
    	Player player = event.getPlayer();
    	UUID uuid = player.getUniqueId();
        String message = event.getMessage();
        
        if (!message.startsWith("/")
        		|| message.startsWith("/message")
        		|| message.startsWith("/msg")
        		|| message.startsWith("/tell")
        		|| message.startsWith("/whisper")
        		|| message.startsWith("/reply")
        		|| message.startsWith("/r")) {
        	FalchusPunishlist.mute(uuid, string -> {
        		player.sendMessage(string);
        		event.setCancelled(true);
        	});
        }
    }
}
