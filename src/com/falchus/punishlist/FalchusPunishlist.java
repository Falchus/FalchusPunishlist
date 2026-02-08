package com.falchus.punishlist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

import com.falchus.lib.utils.http.HTTPRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class FalchusPunishlist {
	
	public static final String name = "Falchus";
	public static final String nameLowercase = name.toLowerCase();
	public static final String nameFull = name + "Punishlist";
	public static final String colorcode = "§f§l";
	public static String prefix = "§8» " + colorcode + nameFull + "§r §8┃ §7";
	public static final String prefixPermission = nameLowercase + ".";
	public static final String noPermissionMessage = prefix + "§cInsufficient permissions!";
	public static final String website = "falchus.com";
	public static final String discord = "discord." + website;
	
	public static void ban(UUID uuid, Consumer<String> consumer) {
    	String response = HTTPRequest.get("https://api." + FalchusPunishlist.website + "/player/" + uuid.toString());
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
	    			
	    			consumer.accept(
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
	
	public static void mute(UUID uuid, Consumer<String> consumer) {
    	String response = HTTPRequest.get("https://api." + FalchusPunishlist.website + "/player/" + uuid.toString());
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
        			
        			consumer.accept(
    		    		FalchusPunishlist.prefix + "\n" +
	    				FalchusPunishlist.prefix + "§7You have been §cmuted§7!\n" +
	    				FalchusPunishlist.prefix + "§7Reason: §e" + reason + "\n" +
	    				FalchusPunishlist.prefix + "§7Until: §e" + (until < 0 ? "Permanent" : sdf.format(new Date(until))) + "\n" +
	    				FalchusPunishlist.prefix
        			);
                	return;
    			}
    		}
    	}
	}
}
