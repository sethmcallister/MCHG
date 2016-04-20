package xyz.mcallister.seth.HG.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.mcallister.seth.HG.AtomicStorage;

/**
 * Created by sethm on 06/04/2016.
 */
public class ASyncLeave implements Listener
{
    @EventHandler
    public void Leave(PlayerQuitEvent event)
    {
        AtomicStorage.decrement();
        event.setQuitMessage(null);
    }
}
