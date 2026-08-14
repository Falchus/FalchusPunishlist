package com.falchus.punishlist.velocity.listeners;

import java.util.UUID;

import com.falchus.lib.minecraft.utils.AdventureUtils;
import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.velocity.Main;
import com.velocitypowered.api.event.EventTask;
import com.velocitypowered.api.event.ResultedEvent.ComponentResult;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;

public class JoinQuitListener {

    private static final Main plugin = Main.getInstance();
    
    public JoinQuitListener() {
    	plugin.getProxy().getEventManager().register(plugin, this);
    }
    
    @Subscribe
    public EventTask onLogin(LoginEvent event) {
    	Player player = event.getPlayer();
    	UUID uuid = player.getUniqueId();
    	String ip = player.getRemoteAddress().getAddress().getHostAddress();
		
    	return EventTask.async(() -> {
	    	FalchusPunishlist.ban(uuid, ip, string -> {
	    		event.setResult(ComponentResult.denied(AdventureUtils.legacy(string)));
	    	});
    	});
    }
}