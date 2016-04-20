package xyz.mcallister.seth.HG.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.mcallister.seth.HG.AtomicStorage;
import xyz.mcallister.seth.HG.GameState;


/**
 * Created by sethm on 06/04/2016.
 */
public class ASyncJoin implements Listener
{
    @EventHandler
    public void Join(PlayerJoinEvent event)
    {
        if(GameState.lobby.get() == true)
        {
            AtomicStorage.increment();
            event.getPlayer().sendMessage(ChatColor.RED + "Please Select a kit using " + ChatColor.BOLD + "/kit <name> " + ChatColor.RED + "you can view all the kits using " + ChatColor.BOLD + "/kits" + ChatColor.RED + ".");
        }
        else
        {
            event.getPlayer().kickPlayer(ChatColor.RED + "Game has already started.");
            event.setJoinMessage(null);
        }
    }
}
