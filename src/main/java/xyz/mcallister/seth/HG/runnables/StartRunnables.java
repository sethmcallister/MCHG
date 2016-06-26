package xyz.mcallister.seth.HG.runnables;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.mcallister.seth.HG.AtomicStorage;
import xyz.mcallister.seth.HG.GameState;
import xyz.mcallister.seth.HG.Main;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sethm on 06/04/2016.
 */
public class StartRunnables
{
    private static AtomicInteger count = new AtomicInteger(30);

    private static void increment()
    {
        count.incrementAndGet();
    }

    private static void decrement()
    {
        count.decrementAndGet();
    }

    private static int value()
    {
        return count.get();
    }

    public static AtomicInteger gametime = new AtomicInteger(30);

    private static Location spawn = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5);

    static int starttid = 0;

    public static void Invincible()
    {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getPlugin(), new Runnable()
        {
            public void run()
            {
                Bukkit.broadcastMessage(ChatColor.RED + "You have lost your invincibility.");
                GameState.invincible.set(false);
            }
        }, 120*20);
    }
    //Checks for 20 players every 10 seconds.
    public static void Start()
    {
        starttid = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getPlugin(), new Runnable()
        {
            public void run()
            {
                if(AtomicStorage.value() >= 2)
                {
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(ChatColor.RED + "Game has started. Everyone is now Invincible 2 minuets.");
                    Bukkit.broadcastMessage(ChatColor.RED + "Please do not logout.. You cannot join back.");
                    Bukkit.broadcastMessage(ChatColor.RED + "Maximum game time is 45 minuets.");
                    for(Player target : Bukkit.getOnlinePlayers())
                    {
                        target.teleport(spawn);
                        target.updateInventory();
                    }

                    Invincible();
                    GameLoop();
                    Bukkit.getScheduler().cancelTask(starttid);
                    GameState.invincible.set(true);
                    GameState.lobby.set(false);
                    GameState.started.set(true);
                    GameState.ended.set(false);
                }
                else
                {
                    Bukkit.broadcastMessage(ChatColor.RED + "Not enough players to start the game.");
                }
            }
        }, 0,  10*20);
    }

    public static void GameLoop()
    {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getPlugin(), new Runnable()
        {
            public void run()
            {
                //Broadcasts time remaning within the game.
                if(gametime.get() != 0)
                {
                    gametime.decrementAndGet();
                    Bukkit.broadcastMessage(ChatColor.YELLOW.toString() + gametime.get() + ChatColor.RED + " minuets remaining on the game.");
                    GameLoop();
                }
                else
                {
                    Bukkit.broadcastMessage(ChatColor.RED + "The game has taken too long, suspending game.");
                    Bukkit.shutdown();
                }
                //Checks if there is 15 minuets left within the game.
                if(gametime.get() == 15)
                {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "The feast starts in 5 minuets.");
                }
                //Checks if there is 10 minuets left within the game.
                if(gametime.get() == 10)
                {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "The feast has started.");
                    //todo add feast
                }
            }
        },0, 20);
    }


    public static void Won()
    {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getPlugin(), new Runnable()
        {
            public void run()
            {
                if(value() != 5)
                {
                    Bukkit.broadcastMessage(ChatColor.GOLD + AtomicStorage.winner.get("winner") + ChatColor.RED + " has won!!");
                    increment();
                    Won();
                }
                else
                {
                    Bukkit.shutdown();
                }
            }
        }, 20);
    }
}
