package net.justicecraft.AntiDupe;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class PListener implements Listener{
	private Core plugin;
	public PListener(Core instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(event.isCancelled()){
			return;
		}
		if(event.getCursor().getType() == Material.AIR){
			return;
		}
		if(event.isLeftClick() && event.getCursor().getAmount() > 0){
			return;
		}
		if(event.isRightClick() && event.getCursor().getAmount() <= 0){
			event.setCancelled(true);
			plugin.log.info(plugin.logPrefix + event.getWhoClicked().getName()+" attempted to dupe "+event.getCursor().getType());
			return;
		}
		if(event.isLeftClick() && event.getCursor().getAmount() <= 0){
			event.setCancelled(true);
//			event.getCursor().setType(Material.AIR);
			plugin.log.info(plugin.logPrefix + event.getWhoClicked().getName()+" attempted to dupe "+event.getCursor().getType());
			return;
		}
	}
}