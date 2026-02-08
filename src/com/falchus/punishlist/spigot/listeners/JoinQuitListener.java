package com.falchus.punishlist.spigot.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.spigot.Main;

public class JoinQuitListener implements Listener {

    private final Main plugin = Main.getInstance();
    
    public JoinQuitListener() {
		Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
    	UUID uuid = event.getUniqueId();
		
    	FalchusPunishlist.ban(uuid, string -> {
    		event.setLoginResult(Result.KICK_BANNED);
    		event.setKickMessage(string);
    	});
    }
}