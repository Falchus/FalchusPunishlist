package com.falchus.punishlist.velocity.listeners;

import java.util.UUID;

import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.velocity.Main;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent.ChatResult;
import com.velocitypowered.api.proxy.Player;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ChatCommandListener {

    private final Main plugin = Main.getInstance();
    
    public ChatCommandListener() {
    	plugin.getProxy().getEventManager().register(plugin, this);
    }
    
    @SuppressWarnings("deprecation")
	@Subscribe
    public void onChat(PlayerChatEvent event) {
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
        		player.sendMessage(LegacyComponentSerializer.legacySection().deserialize(string));
        		event.setResult(ChatResult.denied());
        	});
        }
    }
}
