package com.falchus.punishlist;

import java.util.Set;

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
	
	public static IFalchusPunishlist get() {
		return instance;
	}
	
	public static void set(@NonNull IFalchusPunishlist instance) {
		FalchusPunishlist.instance = instance;
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
