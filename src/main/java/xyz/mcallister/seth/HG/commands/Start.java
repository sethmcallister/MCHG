package xyz.mcallister.seth.HG.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.mcallister.seth.HG.GameState;

/**
 * Created by sethm on 06/04/2016.
 */
public class Start implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings)
    {
        if(command.getName().equalsIgnoreCase("forcestart") && sender.hasPermission("hg.admin"))
        {
            if(!sender.hasPermission("hg.admin"))
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to execute that command.");
                return true;
            }
            if(GameState.started.get() == true)
                sender.sendMessage(ChatColor.RED + "The game has already started.");
            else
            {
                Bukkit.broadcastMessage(ChatColor.GREEN + "The game has been started by " + sender.getName());
                GameState.started.set(true);
                GameState.lobby.set(false);
            }
        }
        return false;
    }
}
