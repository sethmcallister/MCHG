package xyz.mcallister.seth.HG;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.mcallister.seth.HG.Kits.Abilites;
import xyz.mcallister.seth.HG.commands.Alive;
import xyz.mcallister.seth.HG.commands.ForceEnd;
import xyz.mcallister.seth.HG.commands.Lobby;
import xyz.mcallister.seth.HG.commands.Start;
import xyz.mcallister.seth.HG.events.ASyncJoin;
import xyz.mcallister.seth.HG.events.ASyncLeave;
import xyz.mcallister.seth.HG.events.DamageEvent;
import xyz.mcallister.seth.HG.events.DeathEvent;
import xyz.mcallister.seth.HG.runnables.StartRunnables;

/**
 * Created by sethm on 06/04/2016.
 */
public class Main extends JavaPlugin
{
    public static Plugin plugin;

    public void onEnable()
    {
        plugin = this;
        System.out.println("§aMC-HG by Seth McAllister has been enabled.");
        setupCommands();
        setupListeners();
        setupStuff();
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
    }

    private void setupListeners()
    {
        Bukkit.getServer().getPluginManager().registerEvents(new ASyncJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ASyncLeave(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Abilites(), this);
    }

    private void setupStuff()
    {

    }
}
