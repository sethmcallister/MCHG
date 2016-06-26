package xyz.mcallister.seth.HG;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.mcallister.seth.HG.commands.*;
import xyz.mcallister.seth.HG.events.ASyncJoin;
import xyz.mcallister.seth.HG.events.ASyncLeave;
import xyz.mcallister.seth.HG.events.DamageEvent;
import xyz.mcallister.seth.HG.events.DeathEvent;
import xyz.mcallister.seth.HG.events.kits.Stomper;
import xyz.mcallister.seth.HG.runnables.StartRunnables;
import xyz.mcallister.seth.HG.scoreboard.Handler;
import xyz.mcallister.seth.HG.scoreboard.ScoreboardThread;

/**
 * Created by sethm on 06/04/2016.
 */
public class Main extends JavaPlugin
{
    private static Plugin plugin;
    private static Main instance;
    Handler handler = new Handler(this);

    public void onEnable()
    {
        instance = this;
        plugin = this;
        System.out.println("§aMC-HG by Seth McAllister has been enabled.");
        setupCommands();
        setupListeners();
        setupStuff();
        handler.init();
        ScoreboardThread.initScoreboard();
        StartRunnables.Start();
    }

    public void onDisable()
    {
        System.out.println("§4MC-HG by Seth McAllister has been disabled.");
    }

    private void setupCommands()
    {
        getCommand("alive").setExecutor(new Alive());
        getCommand("forcestart").setExecutor(new Start());
        getCommand("forcelobby").setExecutor(new Lobby());
        getCommand("forceend").setExecutor(new ForceEnd());
        getCommand("kits").setExecutor(new Kits());
        getCommand("kit").setExecutor(new Kit());
    }

    public static Plugin getPlugin()
    {
        return plugin;
    }

    public static Main getInstance()
    {
        return instance;
    }

    private void setupListeners()
    {
        Bukkit.getServer().getPluginManager().registerEvents(new ASyncJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ASyncLeave(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Stomper(), this);
    }

    private void setupStuff()
    {

    }

}
