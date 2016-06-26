package xyz.mcallister.seth.HG.events.kits;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import xyz.mcallister.seth.HG.utils.KitData;

/**
 * Created by sethm on 26/06/2016.
 */
public class Stomper implements Listener
{
    public static void stomper(EntityDamageEvent event)
    {
        if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            if(KitData.getKit(event.getEntity().getUniqueId()).equalsIgnoreCase("Stomper"))
            {
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    if(player.getLocation().distance(event.getEntity().getLocation()) < 2)
                    {
                        player.damage(event.getDamage());
                    }
                    if(player.getLocation().distance(event.getEntity().getLocation()) > 2)
                    {
                        player.damage(event.getDamage());
                    }
                    if(player.getLocation().distance(event.getEntity().getLocation()) > 4)
                    {
                        event.setDamage(0);
                    }
                }
                event.setDamage(0);
            }
        }
    }
}
