package xyz.mcallister.seth.HG.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.mcallister.seth.HG.AtomicStorage;
import xyz.mcallister.seth.HG.Main;
import xyz.mcallister.seth.HG.utils.KillData;
import xyz.mcallister.seth.HG.utils.KitData;

/**
 * Created by sethm on 30/05/2016.
 */
public class ScoreboardThread
{
    public static void initScoreboard()
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    Helper helper = Handler.getScoreboardFor(player);
                    helper.clear();
                    helper.add(color("&7&m------------"), color("&7&m------------"));
                    helper.add(color("&a&lKit &6» "), color("&7" + KitData.getKit(player.getUniqueId())));
                    helper.add("", "");
                    helper.add(color("&a&lKills &6» "), color("&7" + KillData.getKills(player.getUniqueId())));
                    helper.add("", "");
                    helper.add(color("&9&lPlayers Left"), color(" &6» &7" + AtomicStorage.value()));
                    helper.add(color("&7&m------------"), color("&7&m------------"));
                    helper.update();
                }
            }
        }, 0L, 1L);
    }
    public static String color(String input)
    {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
