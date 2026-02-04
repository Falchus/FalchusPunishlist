package com.falchus.punishlist.velocity.listeners;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.falchus.lib.utils.http.HTTPRequest;
import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.velocity.Main;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.velocitypowered.api.event.ResultedEvent.ComponentResult;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;

public class JoinQuitListener {

    private final Main plugin = Main.getInstance();
    
    public JoinQuitListener() {
    	plugin.getProxy().getEventManager().register(plugin, this);
    }
    
    @Subscribe
    public void onLogin(LoginEvent event) {
    	Player player = event.getPlayer();
    	UUID uuid = player.getUniqueId();
		
    	String response = HTTPRequest.get("https://api." + FalchusPunishlist.website + "/punishlist/" + uuid.toString());
    	if (response != null) {
    		JsonObject obj = new Gson().fromJson(response, JsonObject.class);
    		if (obj.has("ban")) {
    			JsonObject ban = obj.getAsJsonObject("ban");
    			if (ban.has("active") && ban.get("active").getAsBoolean()) {
	    			String reason = ban.has("reason")
	    					? ban.get("reason").getAsString()
	    					: "-";
	    			long until = ban.has("until")
	    					? ban.get("until").getAsLong()
	    					: 0;
	    			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    			
	    			event.setResult(ComponentResult.denied(LegacyComponentSerializer.legacySection().deserialize(
			    		"§f§m--------------------§r" +
	    				"\n\n" +
	    				FalchusPunishlist.colorcode + FalchusPunishlist.nameFull + "§r\n" +
	    				"§7You have been §cbanned§7!" +
	    				"\n\n" +
	    				"§7Reason: §e" + reason + "\n" +
	    				"§7Until: §e" + (until < 0 ? "Permanent" : sdf.format(new Date(until))) + "\n" +
	    				"\n" +
	    				"§7§o" + FalchusPunishlist.discord
	    			)));
	    			return;
    			}
    		}
    	}
    }
}