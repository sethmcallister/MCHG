package xyz.mcallister.seth.HG.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by sethm on 20/04/2016.
 */
public class NetherTeleportEvent implements Listener
{
    @EventHandler
    public void onNether(PlayerTeleportEvent event)
    {
        if(event.getCause().equals(Material.PORTAL))
        {
            if(event.getTo().getWorld().equals(Bukkit.getWorld("world_nether")) || event.getTo().equals(Bukkit.getWorld("world_the_end")))
            {
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot leave the overworld.");
                event.setCancelled(true);
            }
        }
    }
}
