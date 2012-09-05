package net.justicecraft.AntiDupe;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin{
	public final Logger log = Logger.getLogger("Minecraft");
	public static String logPrefix = "[AntiDupe] ";
	public static List<String> recentDeaths = new ArrayList<String>();
	
	// On Disable
	public void onDisable(){
		log.info(logPrefix+" has been disabled.");
	}
	
	// On Enable
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName()+ " version " + pdfFile.getVersion() + " is enabled.");
		getServer().getPluginManager().registerEvents(new PListener(this), this);
		getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run(){
				if(!recentDeaths.isEmpty()){
					recentDeaths.clear();
				}
			}
		}, 20L, 20L);
	}
}