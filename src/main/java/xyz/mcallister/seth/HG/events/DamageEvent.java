package xyz.mcallister.seth.HG.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.mcallister.seth.HG.GameState;

/**
 * Created by sethm on 06/04/2016.
 */
public class DamageEvent implements Listener
{
    @EventHandler
    public void Damage(EntityDamageByEntityEvent event)
    {
        if(GameState.lobby.get() == true)
        {
            if(event.getDamager() instanceof Player)
            {
                ((Player) event.getDamager()).sendMessage(ChatColor.RED + "You cannot attack players when the server is in lobby mode.");
                event.setCancelled(true);
            }
        }
    }
}
