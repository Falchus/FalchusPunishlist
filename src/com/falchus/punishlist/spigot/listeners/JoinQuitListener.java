package com.falchus.punishlist.spigot.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.falchus.lib.utils.http.HTTPRequest;
import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.spigot.Main;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JoinQuitListener implements Listener {

    private final Main plugin = Main.getInstance();
    
    public JoinQuitListener() {
		Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
    	UUID uuid = event.getUniqueId();
		
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
	    			
	    			event.setLoginResult(Result.KICK_BANNED);
	    			event.setKickMessage(
			    		"§f§m--------------------§r" +
	    				"\n\n" +
	    				FalchusPunishlist.colorcode + FalchusPunishlist.nameFull + "§r\n" +
	    				"§7You have been §cbanned§7!" +
	    				"\n\n" +
	    				"§7Reason: §e" + reason + "\n" +
	    				"§7Until: §e" + (until < 0 ? "Permanent" : sdf.format(new Date(until))) + "\n" +
	    				"\n" +
	    				"§7§o" + FalchusPunishlist.discord
	    			);
	    			return;
    			}
    		}
    	}
    }
}