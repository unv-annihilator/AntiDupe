package net.justicecraft.AntiDupe;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.PlayerInventory;


public class PListener implements Listener{
	private Core plugin;
	public PListener(Core instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		if(event.getEntity() instanceof Player){
			Player p = (Player)event.getEntity();
			if(p == null){
				event.getDrops().clear();
			} else {
				if(!plugin.recentDeaths.contains(p.getDisplayName())){
					plugin.recentDeaths.add(p.getDisplayName());
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		if(plugin.recentDeaths.contains(event.getPlayer().getDisplayName())){
			PlayerInventory pinv = event.getPlayer().getInventory();
			pinv.clear();
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(event.isCancelled()){
			return;
		}
		if(event.getInventory().getType() == InventoryType.BREWING){
			if(event.isShiftClick()){
				event.setCancelled(true);
				return;
			}
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
			plugin.log.info(plugin.logPrefix + event.getWhoClicked().getName()+" attempted to dupe "+event.getCursor().getType());
			return;
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event){
		if(event.isCancelled()){
			return;
		}
		Location loc = event.getPlayer().getLocation().subtract(0, 1, 0);
		if(loc.getWorld().getEnvironment() == Environment.THE_END){
			if(plugin.getServer().getWorld(loc.getWorld().getName()).getBlockAt(loc) != null  && plugin.getServer().getWorld(loc.getWorld().getName()).getBlockAt(loc).getType() == Material.AIR){
				event.setCancelled(true);
			}
		}
	}
}