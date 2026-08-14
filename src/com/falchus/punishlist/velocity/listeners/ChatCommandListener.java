package com.falchus.punishlist.velocity.listeners;

import java.util.UUID;

import com.falchus.lib.minecraft.utils.AdventureUtils;
import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.velocity.Main;
import com.velocitypowered.api.event.EventTask;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent.ChatResult;
import com.velocitypowered.api.proxy.Player;

public class ChatCommandListener {

    private static final Main plugin = Main.getInstance();
    
    public ChatCommandListener() {
    	plugin.getProxy().getEventManager().register(plugin, this);
    }
    
    @SuppressWarnings("deprecation")
	@Subscribe
    public EventTask onChat(PlayerChatEvent event) {
    	Player player = event.getPlayer();
    	UUID uuid = player.getUniqueId();
    	String ip = player.getRemoteAddress().getAddress().getHostAddress();
        String message = event.getMessage();
        
        if (message.startsWith("/")
        		&& !message.startsWith("/message")
        		&& !message.startsWith("/msg")
        		&& !message.startsWith("/tell")
        		&& !message.startsWith("/whisper")
        		&& !message.startsWith("/reply")
        		&& !message.startsWith("/r")) {
        	return null;
        }
        
        return EventTask.async(() -> {
	    	FalchusPunishlist.mute(uuid, ip, string -> {
	    		player.sendMessage(AdventureUtils.legacy(string));
	    		event.setResult(ChatResult.denied());
	    	});
        });
    }
}
