package com.falchus.punishlist.velocity.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.falchus.lib.utils.http.HTTPRequest;
import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.velocity.Main;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
        	String response = HTTPRequest.get("https://api." + FalchusPunishlist.website + "/punishlist/" + uuid.toString());
        	if (response != null) {
        		JsonObject obj = new Gson().fromJson(response, JsonObject.class);
        		if (obj.has("mute")) {
        			JsonObject mute = obj.getAsJsonObject("mute");
        			if (mute.has("active") && mute.get("active").getAsBoolean()) {
	        			String reason = mute.has("reason")
	        					? mute.get("reason").getAsString()
	        					: "-";
	        			long until = mute.has("until")
	        					? mute.get("until").getAsLong()
	        					: 0;
	        			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	        			
	        			player.sendMessage(LegacyComponentSerializer.legacySection().deserialize(
	    		    		FalchusPunishlist.prefix + "\n" +
		    				FalchusPunishlist.prefix + "§7You have been §cmuted§7!\n" +
		    				FalchusPunishlist.prefix + "§7Reason: §e" + reason + "\n" +
		    				FalchusPunishlist.prefix + "§7Until: §e" + (until < 0 ? "Permanent" : sdf.format(new Date(until))) + "\n" +
		    				FalchusPunishlist.prefix
	        			));
	                	event.setResult(ChatResult.denied());
	                	return;
        			}
        		}
        	}
        }
    }
}
