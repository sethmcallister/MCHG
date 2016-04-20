package xyz.mcallister.seth.HG.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.mcallister.seth.HG.runnables.StartRunnables;

/**
 * Created by sethm on 20/04/2016.
 */
public class Time implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings)
    {
        if(command.getName().equalsIgnoreCase("time"))
        {
            if(StartRunnables.gametime.get() != 1)
                sender.sendMessage(ChatColor.RED + "Only one minuet remains this game!!");
            else
                sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + StartRunnables.gametime.get() + ChatColor.YELLOW + " minuets remain within this game.");
        }
        return false;
    }
}
