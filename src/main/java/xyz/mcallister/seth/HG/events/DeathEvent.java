package xyz.mcallister.seth.HG.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.mcallister.seth.HG.AtomicStorage;
import xyz.mcallister.seth.HG.GameState;
import xyz.mcallister.seth.HG.runnables.StartRunnables;
import xyz.mcallister.seth.HG.utils.KillData;

/**
 * Created by sethm on 06/04/2016.
 */
public class DeathEvent implements Listener
{
    @EventHandler
    public void Death(PlayerDeathEvent event)
    {
        AtomicStorage.decrement();
        if(event.getEntity().getKiller() instanceof Player)
            Bukkit.broadcastMessage(ChatColor.GOLD + event.getEntity().getPlayer().getName() + ChatColor.RED + " has been slain by " + ChatColor.GOLD + event.getEntity().getKiller().getName() + ChatColor.RED + " using " + event.getEntity().getKiller().getItemInHand().getType() + ".");
        else
            Bukkit.broadcastMessage(ChatColor.GOLD + event.getEntity().getPlayer().getName() + ChatColor.RED + " has died.");
        if(AtomicStorage.value() == 1)
        {
            Bukkit.broadcastMessage(ChatColor.RED + "You have won!! Thanks for playing.");
            GameState.started.set(false);
            GameState.ended.set(true);
            if(event.getEntity().getKiller() instanceof Player)
                AtomicStorage.winner.put("winner", event.getEntity().getKiller().getName());
            StartRunnables.Won();
        }
        Bukkit.broadcastMessage(ChatColor.GREEN + "Only " + AtomicStorage.value() + " players remain!");
        event.getEntity().getPlayer().kickPlayer(ChatColor.RED + "You have died! \n Thanks for playing!");
        event.setDeathMessage(null);
        Bukkit.getWorld("world").strikeLightningEffect(event.getEntity().getPlayer().getLocation());
        KillData.updateKills(event.getEntity().getKiller().getUniqueId());
    }
}
