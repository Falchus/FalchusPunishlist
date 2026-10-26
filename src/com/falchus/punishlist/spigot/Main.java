package com.falchus.punishlist.spigot;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.falchus.lib.minecraft.spigot.task.SpigotTask;
import com.falchus.lib.minecraft.spigot.utils.Metrics;
import com.falchus.punishlist.*;
import com.falchus.punishlist.spigot.listeners.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Main extends JavaPlugin {

	FalchusPunishlist main;
	@Getter static Main instance;
	
	ChatCommandListener chatListener;
	JoinQuitListener joinQuitListener;
	
	@Override
	public void onEnable() {
		main = new FalchusPunishlist();
		instance = this;
		new Metrics(this, 29316);

		chatListener = new ChatCommandListener();
		joinQuitListener = new JoinQuitListener();
		
		SpigotTask.of(() -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				FalchusPunishlist.ban(player.getUniqueId(), player.getAddress().getAddress().getHostAddress()).thenAccept(string -> {
					if (string == null) return;
					player.kickPlayer(string);
				});
			}
		}).runTimer(1, TimeUnit.MINUTES);
	}
}
