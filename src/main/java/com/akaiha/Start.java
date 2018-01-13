package com.akaiha;

import org.bukkit.plugin.java.JavaPlugin;

public class Start extends JavaPlugin {
	
	public void onEnable() {
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
            	getServer().dispatchCommand(getServer().getConsoleSender(), "save-all");
            	getServer().broadcastMessage("Saving");
            	onEnable();
            }
        }, 6000L);
	}
	
	public void onDisable() {}
}
