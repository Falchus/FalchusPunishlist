package com.falchus.punishlist.velocity.listeners;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.UUID;

import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.velocity.Main;
import com.velocitypowered.api.event.ResultedEvent.ComponentResult;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;

public class JoinQuitListener {

    private final Main plugin = Main.getInstance();
    
    public JoinQuitListener() {
    	plugin.getProxy().getEventManager().register(plugin, this);
    }
    
    @Subscribe
    public void onLogin(LoginEvent event) {
    	UUID uuid = event.getPlayer().getUniqueId();
		
    	FalchusPunishlist.ban(uuid, string -> {
    		event.setResult(ComponentResult.denied(LegacyComponentSerializer.legacySection().deserialize(string)));
    	});
    }
}