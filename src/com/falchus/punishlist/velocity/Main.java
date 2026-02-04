package com.falchus.punishlist.velocity;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.falchus.lib.minecraft.velocity.utils.Metrics;
import com.falchus.punishlist.FalchusPunishlist;
import com.falchus.punishlist.velocity.listeners.*;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Plugin(id = "falchuscore",
		name = "FalchusCore",
//		version = "0.0.0",
		authors = {"Falchus"},
		dependencies = {
			@Dependency(id = "falchuslib", optional = false)
		})
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Main {
	
	final ProxyServer proxy;
	final Logger logger;
	final File dataFolder;
	final File file;
	final Metrics.Factory metricsFactory;
	
	FalchusPunishlist main;
	static Main instance;
    
    ChatCommandListener chatListener;
    JoinQuitListener joinQuitListener;
	
	@Inject
	public Main(ProxyServer proxy, Logger logger, @DataDirectory Path dataFolder, Metrics.Factory metricsFactory) {
		this.proxy = proxy;
		this.logger = logger;
		this.dataFolder = new File(dataFolder.toFile().getParentFile(), this.getClass().getAnnotation(Plugin.class).name());
		this.metricsFactory = metricsFactory;
		
		try {
			file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Subscribe
	public void onProxyInitialize(ProxyInitializeEvent event) {
		main = new FalchusPunishlist();
		
		instance = this;
		metricsFactory.make(this, 29317);

		getProxy().getScheduler().buildTask(this, () -> {
			chatListener = new ChatCommandListener();
			joinQuitListener = new JoinQuitListener();
		}).delay(1, TimeUnit.MILLISECONDS).schedule();
	}
	
	public static Main getInstance() {
		return instance;
	}
}
