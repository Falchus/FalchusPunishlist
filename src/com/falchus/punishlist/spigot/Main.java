package com.falchus.punishlist.spigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.falchus.lib.minecraft.spigot.utils.Metrics;
import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.spigot.listeners.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Main extends JavaPlugin {

	private FalchusPunishlist main;
	private static Main instance;
	
	ChatCommandListener chatListener;
	JoinQuitListener joinQuitListener;
	
	@Override
	public void onEnable() {
		main = new FalchusPunishlist();
		instance = this;
		new Metrics(this, 29316);

		Bukkit.getScheduler().runTask(this, () -> {
			chatListener = new ChatCommandListener();
			joinQuitListener = new JoinQuitListener();
		});
	}
	
	public static Main getInstance() {
		return instance;
	}
}
