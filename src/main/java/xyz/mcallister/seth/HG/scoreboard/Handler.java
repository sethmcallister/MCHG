package xyz.mcallister.seth.HG.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import xyz.mcallister.seth.HG.Main;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sethm on 30/05/2016.
 */
public class Handler extends Manager implements Listener
{
    public Handler(Main plugin)
    {
        super(plugin);
    }

    private static ConcurrentHashMap<Player, Helper> helpers = new ConcurrentHashMap<>();

    public void init()
    {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
        for (Player player : Bukkit.getOnlinePlayers())
        {
            handleJoin(player);
        }
    }

    public static Helper getScoreboardFor(Player player)
    {
        return helpers.get(player);
    }

    public void handleJoin(Player player)
    {
        Scoreboard bukkitScoreBoard = getPlugin().getServer().getScoreboardManager().getNewScoreboard();
        player.setScoreboard(bukkitScoreBoard);
        Helper helper = new Helper(bukkitScoreBoard, ChatColor.translateAlternateColorCodes('&', "&6&loHG &c[Beta]"));
        helpers.put(player, helper);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        handleJoin(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
        helpers.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        helpers.remove(event.getPlayer());
    }
}
