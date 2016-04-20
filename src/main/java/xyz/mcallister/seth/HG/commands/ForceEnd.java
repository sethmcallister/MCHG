package xyz.mcallister.seth.HG.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.mcallister.seth.HG.GameState;

/**
 * Created by sethm on 20/04/2016.
 */
public class ForceEnd implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings)
    {
        if(command.getName().equalsIgnoreCase("forceend"))
        {
            if(!sender.hasPermission("hg.admin"))
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to execute that command.");
                return true;
            }
            if(GameState.ended.get() == true)
                sender.sendMessage(ChatColor.RED + "The game has already ended.");
            else
            {
                Bukkit.broadcastMessage(ChatColor.RED + "The game has been forcefully ended by an admin.");
            }
        }
        return false;
    }
}
