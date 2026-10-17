package com.falchus.punishlist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.falchus.lib.task.Promise;
import com.falchus.lib.utils.http.HTTPRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.NonNull;

public interface IFalchusPunishlist {
	
	default Promise<String> ban(@NonNull UUID uuid, @NonNull String ip) {
		return HTTPRequest.get("https://mc-api." + FalchusPunishlist.website + "/player/" + uuid.toString() + "?ip=" + ip).thenApply(response -> {
	    	if (response == null) return null;
	    	
    		JsonObject obj = new Gson().fromJson(response, JsonObject.class);
    		if (!obj.has("ban")) return null;
    		
			JsonObject ban = obj.getAsJsonObject("ban");
			if (!ban.has("active") || !ban.get("active").getAsBoolean()) return null;
			
			String reason = ban.has("reason")
					? ban.get("reason").getAsString()
					: "-";
			long until = ban.has("until")
					? ban.get("until").getAsLong()
					: 0;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			return "§f§m--------------------§r" +
					"\n\n" +
					FalchusPunishlist.colorcode + FalchusPunishlist.nameFull + "§r\n" +
					"§7You have been §cbanned§7!" +
					"\n\n" +
					"§7Reason: §e" + reason + "\n" +
					"§7Until: §e" + (until < 0 ? "Permanent" : sdf.format(new Date(until))) + "\n" +
					"\n" +
					"§7§o" + FalchusPunishlist.discord;
		});
	}
	
	default Promise<String> mute(@NonNull UUID uuid, @NonNull String ip) {
		return HTTPRequest.get("https://mc-api." + FalchusPunishlist.website + "/player/" + uuid.toString() + "?ip=" + ip).thenApply(response -> {
	    	if (response == null) return null;
	    	
    		JsonObject obj = new Gson().fromJson(response, JsonObject.class);
    		if (!obj.has("mute")) return null;
    		
			JsonObject mute = obj.getAsJsonObject("mute");
			if (!mute.has("active") || !mute.get("active").getAsBoolean()) return null;
			
			String reason = mute.has("reason")
					? mute.get("reason").getAsString()
					: "-";
			long until = mute.has("until")
					? mute.get("until").getAsLong()
					: 0;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			return FalchusPunishlist.prefix + "\n" +
					FalchusPunishlist.prefix + "§7You have been §cmuted§7!\n" +
					FalchusPunishlist.prefix + "§7Reason: §e" + reason + "\n" +
					FalchusPunishlist.prefix + "§7Until: §e" + (until < 0 ? "Permanent" : sdf.format(new Date(until))) + "\n" +
					FalchusPunishlist.prefix;
		});
	}
}
