package com.akaiha;

import org.bukkit.plugin.java.JavaPlugin;

public class Start extends JavaPlugin {

	private int version = 1;
	private boolean messageSave = false;
	private boolean messageRestart = true;
	private String msgSave = "Server Saving...";
	private String[] msgRestart = {"Server Restarting in 30 minutes","Server Restarting in 15 minutes","Server Restarting in 10 minutes","Server Restarting in 5 minutes","Server Restarting in 1 minute","Server Restarting in 30 seconds","Server Restarting..."};
	private Long saveInterval = 36000L;
	private Long restartInterval = 288000L;
	private int intervalNumber = 0;


	public void onEnable() {

		try {
			getLogger().info("Enabling!");
			new ConfigVersion().configVersionCheck(this, version);
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
			this.messageSave = this.getConfig().getBoolean("Message-Enabled-Save");
			this.messageSave = this.getConfig().getBoolean("Message-Enabled-Restart");
			this.msgSave = this.getConfig().getString("Save-Message");
			this.saveInterval = this.getConfig().getLong("Save-Interval");
			saveTask();
			restartTask();
		} catch (NullPointerException e) {
			getLogger().warning("Something Went Wrong!");
			getServer().getPluginManager().disablePlugin(this);
			getLogger().warning("Disabling!");
		}
	}
	
	public void onDisable() {}

	private void saveTask() {
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				if(messageSave) {
					getServer().broadcastMessage(msgSave);
				}
				getServer().dispatchCommand(getServer().getConsoleSender(), "save-all");
				saveTask();
			}
		}, saveInterval);
	}

	private void restartTask() {
		switch(intervalNumber){
			case 1: // 30 minutes
				restartInterval = 36000L;
				break;
			case 2: // 15 minutes
				restartInterval = 18000L;
				break;
			case 3: // 10 minutes
				restartInterval = 12000L;
				break;
			case 4: // 5 minutes
				restartInterval = 6000L;
				break;
			case 5: // 1 minute
				restartInterval = 1200L;
				break;
			case 6: // 30 seconds
				restartInterval = 600L;
				break;
			default:
		}
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				if(messageRestart) {
					getServer().broadcastMessage(msgRestart[intervalNumber]);
				}
				if (intervalNumber == 6) {
					getServer().dispatchCommand(getServer().getConsoleSender(),"stop");
				}
				intervalNumber++;
				restartTask();
			}
		}, restartInterval);
	}
}
