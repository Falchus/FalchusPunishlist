package com.falchus.punishlist;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.falchus.lib.cache.Cache;
import com.falchus.lib.task.Promise;

import lombok.NonNull;

public class FalchusPunishlist {
	
	private static IFalchusPunishlist instance = new IFalchusPunishlist() {};
	
	public static final String name = "Falchus";
	public static final String nameLowercase = name.toLowerCase();
	public static final String nameFull = name + "Punishlist";
	public static final String colorcode = "§f§l";
	public static final String prefix = "§8» " + colorcode + nameFull + "§r §8┃ §7";
	public static final String prefixPermission = nameLowercase + ".";
	public static final String noPermissionMessage = prefix + "§cInsufficient permissions!";
	public static final String website = "falchus.com";
	public static final String discord = "discord." + website;
	
	private static final Set<String> muteCommands = Set.of(
		"message", "msg", "tell", "whisper",
		"reply", "r",
		"me", "say"
	);
	
	private static final Cache<String, String> mute = Cache.of()
			.expireAfterWrite(instance.cache(), TimeUnit.MILLISECONDS);
	private static final Cache<String, String> ban = Cache.of()
			.expireAfterWrite(instance.cache(), TimeUnit.MILLISECONDS);
	
	public static IFalchusPunishlist get() {
		return instance;
	}
	
	public static void set(@NonNull IFalchusPunishlist instance) {
		mute.expireAfterWrite(instance.cache(), TimeUnit.MILLISECONDS);
		ban.expireAfterWrite(instance.cache(), TimeUnit.MILLISECONDS);
		
		FalchusPunishlist.instance = instance;
	}
	
	public static Promise<String> mute(@NonNull UUID uuid, @NonNull String ip) {
		if (instance.cache() <= 0) {
			return instance.mute(uuid, ip);
		}
		String key = uuid + "_" + ip;
		String hit = mute.getIfPresent(key);
		if (hit != null) {
			return Promise.of(hit.isEmpty() ? null : hit);
		}
		return instance.mute(uuid, ip).thenApply(result -> {
			mute.put(key, result == null ? "" : result);
			return result;
		});
	}
	
	public static Promise<String> ban(@NonNull UUID uuid, @NonNull String ip) {
		if (instance.cache() <= 0) {
			return instance.ban(uuid, ip);
		}
		String key = uuid + "_" + ip;
		String hit = ban.getIfPresent(key);
		if (hit != null) {
			return Promise.of(hit.isEmpty() ? null : hit);
		}
		return instance.ban(uuid, ip).thenApply(result -> {
			ban.put(key, result == null ? "" : result);
			return result;
		});
	}
	
	public static boolean isMute(String message) {
		message = message.toLowerCase().trim();
		if (!message.startsWith("/")) return true;
		
		String cmd = message.substring(1).split(" ")[0];
		if (cmd.contains(":")) {
			cmd = cmd.substring(cmd.indexOf(':') + 1);
		}
		return muteCommands.contains(cmd);
	}
}
